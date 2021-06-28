package rs.ac.bg.etf.pp1;

import java.util.Stack;

import rs.ac.bg.etf.pp1.ast.Assignement;
import rs.ac.bg.etf.pp1.ast.BoolFactor;
import rs.ac.bg.etf.pp1.ast.CharFactor;
import rs.ac.bg.etf.pp1.ast.DesignatorActPars;
import rs.ac.bg.etf.pp1.ast.DesignatorFactor;
import rs.ac.bg.etf.pp1.ast.DesignatorFactorActPars;
import rs.ac.bg.etf.pp1.ast.Div;
import rs.ac.bg.etf.pp1.ast.FactorList;
import rs.ac.bg.etf.pp1.ast.MethodDecl;
import rs.ac.bg.etf.pp1.ast.MethodTypeName;
import rs.ac.bg.etf.pp1.ast.Minus;
import rs.ac.bg.etf.pp1.ast.Mod;
import rs.ac.bg.etf.pp1.ast.Mul;
import rs.ac.bg.etf.pp1.ast.NonVoidMethod;
import rs.ac.bg.etf.pp1.ast.NumFactor;
import rs.ac.bg.etf.pp1.ast.Plus;
import rs.ac.bg.etf.pp1.ast.PrintConstStmt;
import rs.ac.bg.etf.pp1.ast.PrintStmt;
import rs.ac.bg.etf.pp1.ast.ProgName;
import rs.ac.bg.etf.pp1.ast.ReturnStmt;
import rs.ac.bg.etf.pp1.ast.ReturnVoid;
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
	
	private Stack<Integer> operationsStack = new Stack<>();

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
	
	public void visit(MethodDecl methodDecl){
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
			Code.loadConst(size == -1 ? 1: size);
			Code.put(Code.bprint);
		}
	}
	public void visit(PrintStmt printStmt){
		handlePrintStmt(printStmt.getExpr().struct, -1);
	}
	public void visit(PrintConstStmt printStmt) {
		handlePrintStmt(printStmt.getExpr().struct, printStmt.getN2());
	}
	
	// Return
	public void visit(ReturnVoid returnStmt){
		Code.put(Code.exit);
		Code.put(Code.return_);
	}
	public void visit(ReturnStmt returnStmt){
		Code.put(Code.exit);
		Code.put(Code.return_);
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
		if(functionObj.getType() != Table.noType)
			Code.put(Code.pop);
		
	}

	// Factor
	// Designator
	public void visit(DesignatorFactor factor) {
		Code.load(factor.getDesignator().obj);
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

}
