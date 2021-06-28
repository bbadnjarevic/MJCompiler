package rs.ac.bg.etf.pp1;

import java.util.Stack;

import rs.ac.bg.etf.pp1.ast.AfterIf;
import rs.ac.bg.etf.pp1.ast.Assignement;
import rs.ac.bg.etf.pp1.ast.BoolFactor;
import rs.ac.bg.etf.pp1.ast.CharFactor;
import rs.ac.bg.etf.pp1.ast.CondFact1Expr;
import rs.ac.bg.etf.pp1.ast.CondFact2Expr;
import rs.ac.bg.etf.pp1.ast.Decrease;
import rs.ac.bg.etf.pp1.ast.DesignatorActPars;
import rs.ac.bg.etf.pp1.ast.DesignatorFactor;
import rs.ac.bg.etf.pp1.ast.DesignatorFactorActPars;
import rs.ac.bg.etf.pp1.ast.DesignatorJustIdent;
import rs.ac.bg.etf.pp1.ast.DesignatorWithExpr;
import rs.ac.bg.etf.pp1.ast.Div;
import rs.ac.bg.etf.pp1.ast.ElemAccess;
import rs.ac.bg.etf.pp1.ast.Equal;
import rs.ac.bg.etf.pp1.ast.FactorList;
import rs.ac.bg.etf.pp1.ast.FuncDesignator;
import rs.ac.bg.etf.pp1.ast.GreaterThen;
import rs.ac.bg.etf.pp1.ast.GreaterThenEqual;
import rs.ac.bg.etf.pp1.ast.Increase;
import rs.ac.bg.etf.pp1.ast.InitAND;
import rs.ac.bg.etf.pp1.ast.InitElse;
import rs.ac.bg.etf.pp1.ast.InitOR;
import rs.ac.bg.etf.pp1.ast.InitThen;
import rs.ac.bg.etf.pp1.ast.LessThen;
import rs.ac.bg.etf.pp1.ast.LessThenEqual;
import rs.ac.bg.etf.pp1.ast.MethodDecl;
import rs.ac.bg.etf.pp1.ast.MethodTypeName;
import rs.ac.bg.etf.pp1.ast.Minus;
import rs.ac.bg.etf.pp1.ast.Mod;
import rs.ac.bg.etf.pp1.ast.Mul;
import rs.ac.bg.etf.pp1.ast.NegTerms;
import rs.ac.bg.etf.pp1.ast.NewFactorArray;
import rs.ac.bg.etf.pp1.ast.NextAND;
import rs.ac.bg.etf.pp1.ast.NextOR;
import rs.ac.bg.etf.pp1.ast.NonVoidMethod;
import rs.ac.bg.etf.pp1.ast.NotEqual;
import rs.ac.bg.etf.pp1.ast.NumFactor;
import rs.ac.bg.etf.pp1.ast.Plus;
import rs.ac.bg.etf.pp1.ast.PrintConstStmt;
import rs.ac.bg.etf.pp1.ast.PrintStmt;
import rs.ac.bg.etf.pp1.ast.ProgName;
import rs.ac.bg.etf.pp1.ast.ReadStmt;
import rs.ac.bg.etf.pp1.ast.ReturnStmt;
import rs.ac.bg.etf.pp1.ast.ReturnVoid;
import rs.ac.bg.etf.pp1.ast.SkipElse;
import rs.ac.bg.etf.pp1.ast.SyntaxNode;
import rs.ac.bg.etf.pp1.ast.TermList;
import rs.ac.bg.etf.pp1.ast.VisitorAdaptor;
import rs.ac.bg.etf.pp1.ast.VoidMethod;
import rs.etf.pp1.mj.runtime.Code;
import rs.etf.pp1.symboltable.concepts.Obj;
import rs.etf.pp1.symboltable.concepts.Struct;

public class CodeGenerator extends VisitorAdaptor {
	private int mainPc;

	public int getMainPc() {
		return mainPc;
	}

	public static int getCharValue(String str) {
		StringBuffer sb = new StringBuffer(str);
		sb.delete(str.length() - 1, str.length());
		sb.delete(0, 1);
		String s = sb.toString();
		s = s.replaceAll("\\\\n", "\n");
		s = s.replaceAll("\\\\t", "\t");
		s = s.replaceAll("\\\\b", "\b");
		s = s.replaceAll("\\\\f", "\f");
		s = s.replaceAll("\\\\r", "\r");
		s = s.replaceAll("\\\\'", "\'");
		return s.charAt(0);
	}
	
	private enum LogicalOp {
		AND, OR
	}

	private Stack<Integer> operationsStack = new Stack<>();
	private Stack<Integer> skipElsePatch = new Stack<>(); 
	private Stack<Integer> orElsePatch = new Stack<>();
	private Stack<Integer> thenPatch = new Stack<>();

	public void visit(ProgName progName) {
		Obj obj = null;

		// chr
		obj = Table.find("chr");
		obj.setAdr(Code.pc);
		Code.put(Code.enter);
		Code.put(1);
		Code.put(1);
		Code.put(Code.load_n);
		Code.put(Code.exit);
		Code.put(Code.return_);

		// ord
		obj = Table.find("obj");
		obj.setAdr(Code.pc);
		Code.put(Code.enter);
		Code.put(1);
		Code.put(1);
		Code.put(Code.load_n);
		Code.put(Code.exit);
		Code.put(Code.return_);

		// len
		obj = Table.find("len");
		obj.setAdr(Code.pc);
		Code.put(Code.enter);
		Code.put(1);
		Code.put(1);
		Code.put(Code.load_n);
		Code.put(Code.arraylength);
		Code.put(Code.exit);
		Code.put(Code.return_);

	}

	// Method
	private void handleMethod(MethodTypeName methodTypeName, String methodName) {
		if ("main".equalsIgnoreCase(methodName))
			mainPc = Code.pc;

		methodTypeName.obj.setAdr(Code.pc);

		int fpCnt = methodTypeName.obj.getLevel();
		int varCnt = methodTypeName.obj.getLocalSymbols().size();

		Code.put(Code.enter);
		Code.put(fpCnt);
		Code.put(varCnt);

	}

	public void visit(NonVoidMethod method) {
		handleMethod(method, method.getName());
	}

	public void visit(VoidMethod method) {
		handleMethod(method, method.getName());
	}

	public void visit(MethodDecl methodDecl) {
		if (methodDecl.getMethodTypeName().obj.getType() != Table.noType) {
			Code.put(Code.trap);
			Code.put(1);
		}
		Code.put(Code.exit);
		Code.put(Code.return_);
	}

	// Statements
	// PrintStmt
	private void handlePrintStmt(Struct struct, int size) {
		if (struct == Table.intType || struct == Table.boolType) {
			Code.loadConst(size == -1 ? 5 : size);
			Code.put(Code.print);
		} else {
			// Prints char
			Code.loadConst(size == -1 ? 1 : size);
			Code.put(Code.bprint);
		}
	}
	public void visit(PrintStmt printStmt) {
		handlePrintStmt(printStmt.getExpr().struct, -1);
	}
	public void visit(PrintConstStmt printStmt) {
		handlePrintStmt(printStmt.getExpr().struct, printStmt.getN2());
	}
	
	// ReadStmt
	public void visit(ReadStmt readStmt) {
		Code.put(Code.read);
		Code.store(readStmt.getDesignator().obj);
	}

	// Return
	public void visit(ReturnVoid returnStmt) {
		Code.put(Code.exit);
		Code.put(Code.return_);
	}

	public void visit(ReturnStmt returnStmt) {
		Code.put(Code.exit);
		Code.put(Code.return_);
	}
	
	// If
	public void visit(InitThen initThen) {
		for(Integer i: thenPatch)
			Code.fixup(i);
		thenPatch.clear();
	}
	public void visit(AfterIf afterIf) {
		for(Integer i: orElsePatch)
			Code.fixup(i);
		orElsePatch.clear();

		for(Integer i: skipElsePatch)
			Code.fixup(i);
		skipElsePatch.clear();
		
	}
	public void visit(InitElse initElse) {
		for(Integer i: orElsePatch)
			Code.fixup(i);
		orElsePatch.clear();
	}
	public void visit(SkipElse skipElse) {
		skipElsePatch.push(Code.pc + 1);
		Code.putJump(0);
	}
	
	
	// Conditions
	// InitOR
	public void visit(InitOR initOR) {
		thenPatch.push(Code.pc + 1);
		Code.putJump(0);

		for(Integer i: orElsePatch)
			Code.fixup(i);
		orElsePatch.clear();
	}
	public void visit(NextOR nextOR) {
		for(Integer i: thenPatch)
			Code.fixup(i);
		thenPatch.clear();
	}

	// CondFact
	public void visit(CondFact2Expr condFact) {
		handleCondFact(condFact.getRelop().obj.getAdr());
	}
	public void visit(CondFact1Expr condFact) {
		Code.put(Code.const_1);
		handleCondFact(Code.eq);
	}
	private void handleCondFact(int op) {
		orElsePatch.push(Code.pc + 1);
		Code.putFalseJump(op, 0);
	}
	
	
	// Designator
	public void visit(DesignatorJustIdent designator) {
		Class<? extends SyntaxNode> parentClass = designator.getParent().getClass();
		if (FuncDesignator.class != parentClass && Assignement.class != parentClass && ReadStmt.class != parentClass) {
			Code.load(designator.obj);
		}
	}
	public void visit(DesignatorWithExpr designator) {
		Class<? extends SyntaxNode> parentClass = designator.getParent().getClass();
		if (Increase.class != parentClass && Decrease.class != parentClass
				&& parentClass != Assignement.class && ReadStmt.class != parentClass)
			Code.load(designator.obj);
	}
	
	public void visit(ElemAccess elem) {
		Code.load(elem.obj);
	}

	// DesignatorStatement
	// Assignement
	public void visit(Assignement designatorStmt) {
		Code.store(designatorStmt.getDesignator().obj);
	}

	// FuncCall
	public void visit(DesignatorActPars designatorStmt) {
		Obj functionObj = designatorStmt.getFuncDesignator().obj;
		int offset = functionObj.getAdr() - Code.pc;
		Code.put(Code.call);
		Code.put2(offset);
		if (functionObj.getType() != Table.noType)
			Code.put(Code.pop);

	}
	
	// Designator ++ 
	public void visit(Increase designatorStmt) {
		if (designatorStmt.getDesignator().obj.getKind() == Obj.Elem) {
			Code.put(Code.dup2);
			Code.load(designatorStmt.getDesignator().obj);
		}
		Code.put(Code.const_1);
		Code.put(Code.add);
		Code.store(designatorStmt.getDesignator().obj);
	}

	// Designator -- 
	public void visit(Decrease designatorStmt) {
		if (designatorStmt.getDesignator().obj.getKind() == Obj.Elem) {
			Code.put(Code.dup2);
			Code.load(designatorStmt.getDesignator().obj);
		}
		Code.put(Code.const_1);
		Code.put(Code.sub);
		Code.store(designatorStmt.getDesignator().obj);
	}

	// Factor
	// Designator
	public void visit(DesignatorFactor factor) {
//		Code.load(factor.getDesignator().obj);
		// TODO Designator
	}

	// Designator Functions
	public void visit(DesignatorFactorActPars factor) {
		Obj functionObj = factor.getFuncDesignator().obj;
		int offset = functionObj.getAdr() - Code.pc;
		Code.put(Code.call);
		Code.put2(offset);
	}

	// Consts
	public void visit(NumFactor numConst) {
		Obj obj = new Obj(Obj.Con, "$", numConst.struct);
		obj.setLevel(0);
		obj.setAdr(numConst.getNum());
		Code.load(obj);
	}

	public void visit(CharFactor charConst) {
		Obj obj = new Obj(Obj.Con, "$", charConst.struct);
		obj.setLevel(0);
		obj.setAdr(getCharValue(charConst.getCh()));
		Code.load(obj);
	}

	public void visit(BoolFactor boolConst) {
		Obj obj = new Obj(Obj.Con, "$", boolConst.struct);
		obj.setLevel(0);
		obj.setAdr(Boolean.parseBoolean(boolConst.getBool()) ? 1 : 0);
		Code.load(obj);
	}
	
	// new array
	public void visit(NewFactorArray factor) {
		int elemSize = factor.getType().struct != Table.charType ? 1 : 0;
		Code.put(Code.newarray);
		Code.put(elemSize);
	}
	
	// Expr
	public void visit(NegTerms expr) {
		Code.put(Code.neg);
	}

	// Term
	public void visit(TermList terms) {
		Code.put(operationsStack.pop());
	}

	public void visit(FactorList term) {
		Code.put(operationsStack.pop());
	}

	// Operations
	// Addop
	public void visit(Plus p) {
		operationsStack.push(Code.add);
	}

	public void visit(Minus minus) {
		operationsStack.push(Code.sub);
	}

	// Mulop
	public void visit(Mul mul) {
		operationsStack.push(Code.mul);
	}

	public void visit(Div div) {
		operationsStack.push(Code.div);
	}

	public void visit(Mod mod) {
		operationsStack.push(Code.rem);
	}
	
	// Relop
	public void visit(Equal relop) {
		relop.obj = new Obj(Obj.Elem, "", Table.noType, Code.eq, 0);
	}
	public void visit(NotEqual relop) {
		relop.obj = new Obj(Obj.Elem, "", Table.noType, Code.ne, 0);
	}
	public void visit(GreaterThen relop) {
		relop.obj = new Obj(Obj.Elem, "", Table.noType, Code.gt, 0);
	}
	public void visit(GreaterThenEqual relop) {
		relop.obj = new Obj(Obj.Elem, "", Table.noType, Code.ge, 0);
	}
	public void visit(LessThen relop) {
		relop.obj = new Obj(Obj.Elem, "", Table.noType, Code.lt, 0);
	}
	public void visit(LessThenEqual relop) {
		relop.obj = new Obj(Obj.Elem, "", Table.noType, Code.le, 0);
	}

}
