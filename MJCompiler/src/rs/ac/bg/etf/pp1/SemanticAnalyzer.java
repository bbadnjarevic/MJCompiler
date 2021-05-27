package rs.ac.bg.etf.pp1;

import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;


import rs.ac.bg.etf.pp1.ast.ArrayDeclaration;
import rs.ac.bg.etf.pp1.ast.BoolConstDecl;
import rs.ac.bg.etf.pp1.ast.CharConstDecl;
import rs.ac.bg.etf.pp1.ast.ConstDecl;
import rs.ac.bg.etf.pp1.ast.MethodDecl;
import rs.ac.bg.etf.pp1.ast.MethodDeclNoParms;
import rs.ac.bg.etf.pp1.ast.MethodDeclWithParms;
import rs.ac.bg.etf.pp1.ast.MethodTypeName;
import rs.ac.bg.etf.pp1.ast.NonVoidMethod;
import rs.ac.bg.etf.pp1.ast.NumConstDecl;
import rs.ac.bg.etf.pp1.ast.ProgName;
import rs.ac.bg.etf.pp1.ast.Program;
import rs.ac.bg.etf.pp1.ast.SyntaxNode;
import rs.ac.bg.etf.pp1.ast.Type;
import rs.ac.bg.etf.pp1.ast.VarDecl;
import rs.ac.bg.etf.pp1.ast.VarDeclaration;
import rs.ac.bg.etf.pp1.ast.VisitorAdaptor;
import rs.ac.bg.etf.pp1.ast.VoidMethod;
import rs.etf.pp1.symboltable.Tab;
import rs.etf.pp1.symboltable.concepts.Obj;
import rs.etf.pp1.symboltable.concepts.Struct;

public class SemanticAnalyzer extends VisitorAdaptor {
	Obj currentMethod = null;
	boolean returnFound = false;
	boolean errorDetected = false;
	int nVars;

	List<Obj> objDeclList = new LinkedList<>();

	Logger log = Logger.getLogger(getClass());

	public boolean passed() {
		return !errorDetected;
	}

	// Reporting
	public void report_error(String message, SyntaxNode info) {
		errorDetected = true;
		StringBuilder msg = new StringBuilder(message);
		int line = (info == null) ? 0 : info.getLine();
		if (line != 0)
			msg.append(" na liniji ").append(line);
		log.error(msg.toString());
	}

	public void report_info(String message, SyntaxNode info) {
		StringBuilder msg = new StringBuilder(message);
		int line = (info == null) ? 0 : info.getLine();
		if (line != 0)
			msg.append(" na liniji ").append(line);
		log.info(msg.toString());
	}

	// Helpers
	private void addObjDeclList(Struct type) {
		for (Obj obj : objDeclList) {
			Table.insert(obj.getKind(), obj.getName(), obj.getAdr(),
					obj.getFpPos() == 1 ? new Struct(Struct.Array, type) : type);
		}
		objDeclList.clear();
	}

	// Program ::= (Program) PROG ProgName DeclarationsList LBRACE MethodDecls RBRACE
	public void visit(Program program) {
		nVars = Table.currentScope.getnVars();
		Table.chainLocalSymbols(program.getProgName().obj);
		Table.closeScope();
	}

	public void visit(ProgName progName) {
		progName.obj = Table.insert(Obj.Prog, progName.getName(), Table.noType);
		Table.openScope();
	}

	// Type
	public void visit(Type type) {
		Obj typeNode = Table.find(type.getTypeName());

		if (typeNode == Table.noObj) {
			report_error("Nije pronadjen tip " + type.getTypeName() + " u tabeli simbola! ", null);
			type.struct = Table.noType;
		} else {
			if (typeNode.getKind() == Obj.Type) {
				type.struct = typeNode.getType();
			} else {
				report_error("Greska: Ime " + type.getTypeName() + " ne predstavlja tip!", type);
				type.struct = Tab.noType;
			}
		}
	}
	

	/* --- CONST DECL --- */
	// ConstDecl ::= (ConstDecl) CONST Type ConstDeclList SEMI
	public void visit(ConstDecl constDecl) {
		addObjDeclList(constDecl.getType().struct);
	}

	// ConstDeclElem ::= (NumConstDecl) IDENT:constName EQUAL NUM_CONST:val
	public void visit(NumConstDecl constDecl) {
		handleConst(constDecl.getConstName(), constDecl.getConstVal());
	}

	// ConstDeclElem ::= (CharConstDecl) IDENT:constName EQUAL CHAR_CONST:constVal
	public void visit(CharConstDecl constDecl) {
		handleConst(constDecl.getConstName(), constDecl.getConstVal().charAt(1));
	}

	// ConstDeclElem ::= (BoolConstDecl) IDENT:constName EQUAL BOOL_CONST:constVal
	public void visit(BoolConstDecl constDecl) {
		handleConst(constDecl.getConstName(), constDecl.getConstVal().equals("true") ? 1 : 0);
	}

	private void handleConst(String name, int value) {
		objDeclList.add(new Obj(Obj.Con, name, null, value, 0));
	}

	/* --- VAL DECL --- */
	// VarDecl ::= (VarDecl) Type:varType VarList SEMI;
	public void visit(VarDecl varDecl) {
		addObjDeclList(varDecl.getType().struct);
	}
	
	// Var ::= (VarDeclaration) IDENT:varName
	public void visit(VarDeclaration var) {
		objDeclList.add(new Obj(Obj.Var, var.getVarName(), null));
	}
	// Var ::= (ArrayDeclaration) IDENT:varName LSQUARE RSQUARE
	public void visit(ArrayDeclaration var) {
		Obj obj = new Obj(Obj.Var, var.getVarName(), null);
		obj.setFpPos(1); // Array flag
		objDeclList.add(obj);
	}
	
	/* --- CLASS DECL --- */
	// TODO Class decl
	
	
	/* --- METHOD DECL --- */
	// MethodDecl ::= (MethodDeclWithParms) MethodTypeName LPAREN FormPars RPAREN VarDeclList LBRACE StatementList RBRACE
	public void visit(MethodDeclWithParms methodDecl) {
		handleMethodEnd(methodDecl);
		
		int formParsCnt = 0;
		methodDecl.getMethodTypeName().obj.setLevel(formParsCnt);
	}
	//  MethodDecl ::= (MethodDeclNoParms) MethodTypeName LPAREN RPAREN VarDeclList LBRACE StatementList RBRACE
	public void visit(MethodDeclNoParms methodDecl) {
		handleMethodEnd(methodDecl);
	}
	
	public void visit(NonVoidMethod method) {
		handleMethodStart(method, method.getName(), method.getType().struct); 
	}
	public void visit(VoidMethod method) {
		handleMethodStart(method, method.getName(), Table.noType);
	}
	private void handleMethodStart(MethodTypeName method, String name, Struct type) {
		currentMethod = Table.insert(Obj.Meth, name, type);
		method.obj = currentMethod;
		Table.openScope();
		report_info("Obradjuje se funkcija " + name, method);
	}
	private void handleMethodEnd(MethodDecl methodDecl) {
		if (!returnFound && currentMethod.getType() != Table.noType) 
			report_error("Semanticka greska na liniji " + methodDecl.getLine() + ": funkcija " + currentMethod.getName() + " nema return iskaz!", null);
    	
    	Table.chainLocalSymbols(currentMethod);
    	Table.closeScope();
    	
    	returnFound = false;
    	currentMethod = null;
	}

}
