package rs.ac.bg.etf.pp1;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.Stack;

import org.apache.log4j.Logger;

import rs.ac.bg.etf.pp1.ast.*;
import rs.ac.bg.etf.pp1.test.CompilerError;
import rs.ac.bg.etf.pp1.test.CompilerError.CompilerErrorType;
import rs.etf.pp1.symboltable.Tab;
import rs.etf.pp1.symboltable.concepts.Obj;
import rs.etf.pp1.symboltable.concepts.Struct;

public class SemanticAnalyzer extends VisitorAdaptor {
	boolean mainFound = false;
	boolean errorDetected = false;
	List<CompilerError> errors = new LinkedList<>();
	int switchCnt = 0;
	int doWhileCnt = 0;

	int nVars;
	int paramCnt = 0;

	Obj currentMethod = null;
	Collection<Obj> currentMethodSymbols = null;

	List<Obj> objDeclList = new LinkedList<>();
	Stack<List<Struct>> typeListStack = new Stack<List<Struct>>();
	Stack<Set<Integer>> caseStack = new Stack<Set<Integer>>();

	Logger log = Logger.getLogger(getClass());

	public boolean passed() {
		return !errorDetected;
	}
	
	public List<CompilerError> getErrors() {
		return errors;
	}

	private String getStructString(Struct structToVisit) {
		StringBuilder output = new StringBuilder();
		switch (structToVisit.getKind()) {
		case Struct.Bool:
			output.append("boolean");
			break;
		case Struct.None:
			output.append("notype");
			break;
		case Struct.Int:
			output.append("int");
			break;
		case Struct.Char:
			output.append("char");
			break;
		case Struct.Array:
			output.append("Arr of ");

			switch (structToVisit.getElemType().getKind()) {
			case Struct.None:
				output.append("notype");
				break;
			case Struct.Int:
				output.append("int");
				break;
			case Struct.Char:
				output.append("char");
				break;
			case Struct.Class:
				output.append("Class");
				break;
			}
		}
		return output.toString();

	}

	private String getObjString(Obj objToVisit) {
		StringBuilder output = new StringBuilder();
		switch (objToVisit.getKind()) {
		case Obj.Con:
			output.append("Con ");
			break;
		case Obj.Var:
			output.append("Var ");
			break;
		case Obj.Type:
			output.append("Type ");
			break;
		case Obj.Meth:
			output.append("Meth ");
			break;
		case Obj.Fld:
			output.append("Fld ");
			break;
		case Obj.Prog:
			output.append("Prog ");
			break;
		}

		output.append(objToVisit.getName());
		output.append(": ");

		output.append(getStructString(objToVisit.getType()));
		output.append(", ");
		output.append(objToVisit.getAdr());
		output.append(", ");
		output.append(objToVisit.getLevel() + " ");

		return output.toString();
	}

	// Reporting
	public void report_error(int line, String msg) {
		errorDetected = true;
		String message = "Greska na liniji %d: " + msg;
		log.error(String.format(message, line));
		errors.add(new CompilerError(line, msg, CompilerErrorType.SEMANTIC_ERROR));
	}

	public void report_info(String message, SyntaxNode info) {
		StringBuilder msg = new StringBuilder(message);
		int line = (info == null) ? 0 : info.getLine();
		if (line != 0)
			msg.append(" na liniji ").append(line);
		log.info(msg.toString());
	}

	// Program ::= (Program) PROG ProgName DeclarationsList LBRACE MethodDecls
	// RBRACE
	public void visit(Program program) {
		nVars = Table.currentScope.getnVars();
		Table.chainLocalSymbols(program.getProgName().obj);
		Table.closeScope();
		
		int i = 0;
		for(Obj obj: program.getProgName().obj.getLocalSymbols())
			if (obj.getKind() == Obj.Var)
				obj.setAdr(i++);

		if (!mainFound)
			report_error(program.getLine(), "Mora postojati main metoda tipa void i bez argumenata!");
	}

	public void visit(ProgName progName) {
		progName.obj = Table.insert(Obj.Prog, progName.getName(), Table.noType);
		Table.openScope();
	}

	// Type
	public void visit(Type type) {
		Obj typeNode = Table.find(type.getTypeName());

		if (typeNode == Table.noObj) {
			report_error(type.getLine(),"Nije pronadjen tip " + type.getTypeName() + " u tabeli simbola!");
			type.struct = Table.noType;
		} else {
			if (typeNode.getKind() == Obj.Type) {
				type.struct = typeNode.getType();
			} else {
				report_error(type.getLine(),"'" + type.getTypeName() + "' ne predstavlja tip!");
				type.struct = Tab.noType;
			}
		}
	}

	// Helpers
	private void addObjDeclList(Struct type) {
		for (Obj obj : objDeclList) {
			Obj foundObj = Table.findInCurrentScope(obj.getName());
			if (foundObj != Table.noObj && foundObj.getKind() != Obj.Prog) {
				// Var/Const already decalred!
				report_error(obj.getLevel() ,"'" + obj.getName() + "' vec deklarisano!");
			} else if (obj.getType() != null && !type.equals(obj.getType())) {
				// Type is not equal to const type
				report_error(obj.getLevel(), "Tip konstante '" + obj.getName() + "' nije dobar!");
			} else {
				// All good. Add to sym table
				Table.insert(obj.getKind(), obj.getName(), obj.getAdr(),
						obj.getFpPos() == 1 ? new Struct(Struct.Array, type) : type);
			}
		}
		objDeclList.clear();
	}

	/* --- CONST DECL --- */
	// ConstDecl ::= (ConstDecl) CONST Type ConstDeclList SEMI
	public void visit(ConstDecl constDecl) {
		addObjDeclList(constDecl.getType().struct);
	}

	// ConstDeclElem ::= (NumConstDecl) IDENT:constName EQUAL NUM_CONST:val
	public void visit(NumConstDecl constDecl) {
		handleConst(constDecl.getConstName(), Table.intType, constDecl.getConstVal(), constDecl.getLine());
	}

	// ConstDeclElem ::= (CharConstDecl) IDENT:constName EQUAL CHAR_CONST:constVal
	public void visit(CharConstDecl constDecl) {
		handleConst(constDecl.getConstName(), Table.charType, constDecl.getConstVal().charAt(1), constDecl.getLine());
	}

	// ConstDeclElem ::= (BoolConstDecl) IDENT:constName EQUAL BOOL_CONST:constVal
	public void visit(BoolConstDecl constDecl) {
		handleConst(constDecl.getConstName(), Table.boolType, Boolean.parseBoolean(constDecl.getConstVal()) ? 1 : 0, constDecl.getLine());
	}

	private void handleConst(String name, Struct type, int value, int line) {
		objDeclList.add(new Obj(Obj.Con, name, type, value, line));
	}

	/* --- VAL DECL --- */
	// VarDecl ::= (VarDecl) Type:varType VarList SEMI;
	public void visit(VarDecl varDecl) {
		addObjDeclList(varDecl.getType().struct);
	}

	// Var ::= (VarDeclaration) IDENT:varName
	public void visit(VarDeclaration var) {
		var.obj = new Obj(Obj.Var, var.getVarName(), null);
		objDeclList.add(var.obj);

	}

	// Var ::= (ArrayDeclaration) IDENT:varName LSQUARE RSQUARE
	public void visit(ArrayDeclaration var) {
		var.obj = new Obj(Obj.Var, var.getVarName(), null);
		var.obj.setFpPos(1); // Array flag
		objDeclList.add(var.obj);
	}

	/* --- METHOD DECL --- */
	// MethodDecl ::= (MethodDeclNoParms) MethodTypeName LPAREN RPAREN VarDeclList
	// LBRACE StatementList RBRACE
	public void visit(MethodDecl methodDecl) {
		handleMethodEnd(methodDecl);
		Obj m = methodDecl.getMethodTypeName().obj;
		if (m.getName().equals("main") && m.getLevel() == 0 && m.getType() == Table.noType)
			mainFound = true;
	}

	// MethodTypeName ::= (NonVoidMethod) Type IDENT:name
	public void visit(NonVoidMethod method) {
		handleMethodStart(method, method.getName(), method.getType().struct);
	}

	// MethodTypeName ::= (VoidMethod) VOID IDENT:name
	public void visit(VoidMethod method) {
		handleMethodStart(method, method.getName(), Table.noType);
	}

	// MethodParams ::= (MethParams) LPAREN FormPars RPAREN
	public void visit(MethParams methdParams) {
		currentMethod.setLevel(paramCnt);
//		currentMethodSymbols = Table.currentScope().getLocals().symbols();
		Table.chainLocalSymbols(currentMethod);
	}

	// MethodParams ::= (NoParams) LPAREN RPAREN
	public void visit(NoParams methParams) {
		currentMethod.setLevel(paramCnt);
//		Table.chainLocalSymbols(currentMethod);
	}

	private void handleMethodStart(MethodTypeName method, String name, Struct type) {
		currentMethod = Table.insert(Obj.Meth, name, type);
		method.obj = currentMethod;
		Table.openScope();
		report_info("Obradjuje se funkcija " + name, method);
	}

	private void handleMethodEnd(MethodDecl methodDecl) {
		currentMethod.setLevel(paramCnt);
		Table.chainLocalSymbols(currentMethod);
		Table.closeScope();
		
		int i = 0;
		for(Obj obj: currentMethod.getLocalSymbols())
			obj.setAdr(i++);

		paramCnt = 0;
		currentMethod = null;
	}
	
	// FormParm ::= (TypeVar) Type Var;
	public void visit(TypeVar formParm) {
		formParm.obj = new Obj(Obj.Var, formParm.getVar().obj.getName(), formParm.getType().struct);
	}

	// FormPars ::= (FormParsList) FormPars COMMA FormParm
	public void visit(FormParsList parsList) {
		objDeclList.clear();
		if (parsList.getFormParm()== null || parsList.getFormParm().obj == null)
			return;
		Obj obj = new Obj(Obj.Var, parsList.getFormParm().obj.getName(), parsList.getFormParm().obj.getType());

		Obj foundObj = Table.findInCurrentScope(obj.getName());
		if (foundObj != Table.noObj && foundObj.getKind() != Obj.Prog) {
			// Var/Const already decalred!
			report_error(parsList.getLine(),"'" + obj.getName() + "' vec deklarisano!");
		} else {
			paramCnt++;
			Table.insert(Obj.Var, obj.getName(), obj.getType());
		}

	}

	// FormPars ::= (FormParsElem) FormParm
	public void visit(FormParsElem parsElem) {
		if (parsElem.getFormParm() == null || parsElem.getFormParm().obj == null)
			return;
		
		Obj obj = new Obj(Obj.Var, parsElem.getFormParm().obj.getName(), parsElem.getFormParm().obj.getType());

		Obj foundObj = Table.findInCurrentScope(obj.getName());
		if (foundObj != Table.noObj && foundObj.getKind() != Obj.Prog) {
			// Var/Const already decalred!
			report_error(parsElem.getLine(), "'" + obj.getName() + "' vec deklarisano!");
		} else {
			paramCnt++;
			Table.insert(Obj.Var, obj.getName(), obj.getType());
		}
	}

	/* --- DESIGNATOR --- */
	// Designator ::= (DesignatorJustIdent) IDENT
	public void visit(DesignatorJustIdent designator) {
		handleDesignator(designator, designator.getName());
	}

	// Designator ::= (DesignatorWithExpr) IDENT LSQUARE Expr RSQUARE
	public void visit(DesignatorWithExpr designator) {
		designator.obj = designator.getElemAccess().obj;
		if (designator.getExpr().struct != Table.intType)
			report_error(designator.getLine(),
					"Izraz za indeksiranje niza " + designator.obj.getName() + " mora biti tipa int!");
		designator.obj = new Obj(Obj.Elem, designator.obj.getName(), designator.obj.getType().getElemType());
		
	}
	
	// (DesignatorWithExpr) ElemAccess LSQUARE Expr RSQUARE
	// ElemAccess ::= (ElemAccess) IDENT:name; 
	public void visit(ElemAccess designator) {
		String name = designator.getName();

		String msg = "Pretraga na liniji " + designator.getLine() + "(";
		Obj obj = Table.find(name);
		if (obj == Tab.noObj)
			report_error(designator.getLine(), msg + name + "), nije nadjeno! ");
		else
			report_info(msg + name + "), nadjeno " + getObjString(obj), null);

		designator.obj = obj;
		
		if (designator.obj.getType().getKind() != Struct.Array && designator.obj.getKind() != Obj.Elem) {
			report_error(designator.getLine(), name + " nije moguce indeksirati!");
		}
	}

	private void handleDesignator(Designator designator, String name) {
		String msg = "Pretraga na liniji " + designator.getLine() + "(";
		Obj obj = Table.find(name);
		if (obj == Tab.noObj)
			report_error(designator.getLine(), msg + name + "), nije nadjeno! ");
		else
			report_info(msg + name + "), nadjeno " + getObjString(obj), null);

		designator.obj = obj;
	}

	// FuncDesignator ::= (FuncDesignator) Designator;
	public void visit(FuncDesignator funcDesignator) {
		typeListStack.push(new LinkedList<Struct>());
		funcDesignator.obj = funcDesignator.getDesignator().obj;
	}

	/* --- DESIGNATOR STATEMENT --- */
	// DesignatorStatement ::= (Assignement) Designator Assignop Expr SEMI
	public void visit(Assignement stmt) {
		Obj designator = stmt.getDesignator().obj;
		Struct exprType = stmt.getExpr().struct;

		if (designator.getKind() != Obj.Var && designator.getKind() != Obj.Elem)
			report_error(stmt.getLine(), designator.getName() + " nije promenljiva ili element niz!");

		if (exprType != null && !exprType.assignableTo(designator.getType()))
			report_error(stmt.getLine(), "Nekompatibilna dodela!");

	}

	// DesignatorStatement ::= (Increase) Designator INC SEMI
	public void visit(Increase stmt) {
		handleIncDec(stmt, stmt.getDesignator().obj);
	}

	// DesignatorStatement ::= (Decrease) Designator DEC SEMI
	public void visit(Decrease stmt) {
		handleIncDec(stmt, stmt.getDesignator().obj);
	}

	private void handleIncDec(DesignatorStatement stmt, Obj designator) {
		if (designator.getKind() != Obj.Var && designator.getKind() != Obj.Elem)
			report_error(stmt.getLine(), designator.getName() + " nije promenljiva ili element niz!");

		if (designator.getType() != Table.intType) {
			report_error(stmt.getLine(), designator.getName() + " nije tipa int!");
		}
	}

	// DesignatorStatement ::= (DesignatorActPars) FuncDesignator PossibleActPars
	// SEMI
	public void visit(DesignatorActPars stmt) {
		handleFuncCall(stmt.getFuncDesignator().obj, stmt.getLine());
	}

	private void handleFuncCall(Obj designator, int line) {
		if (designator.getKind() != Obj.Meth)
			report_error(line, "Nije pronadjena globalna funkcija sa datim nazivom!");

		List<Struct> typeList = typeListStack.pop();
		if (typeList.size() != designator.getLevel())
			report_error(line, "Ocekivan broj parametara: " + designator.getLevel() + ". Dobijeno: " + typeList.size());
		else {
			Iterator<Obj> params = designator.getLocalSymbols().iterator();
			int index = 0;
			for (Struct paramType : typeList) {
				Struct other = params.next().getType();
				if (!paramType.assignableTo(other))
					report_error(line, "Na poziciji " + index + ". Nekompatibilan tip prosledjenog parametra!");
				index++;
			}
		}
	}

	// ActPars ::= (ActParsList) ActPars COMMA Expr
	public void visit(ActParsList pars) {
		typeListStack.lastElement().add(pars.getExpr().struct);
	}

	// ActPars ::= (OneExpr) Expr
	public void visit(OneExpr par) {
		typeListStack.lastElement().add(par.getExpr().struct);
	}

	/* --- STATEMENT --- */
	// Statement ::= (PrintStmt) PRINT LPAREN Expr RPAREN SEMI
	public void visit(PrintStmt stmt) {
		handlePrintStmt(stmt.getExpr().struct, stmt.getLine());
	}

	// Statement ::= (PrintConstStmt) PRINT LPAREN Expr COMMA NUM_CONST RPAREN SEMI
	public void visit(PrintConstStmt stmt) {
		handlePrintStmt(stmt.getExpr().struct, stmt.getLine());
	}

	private void handlePrintStmt(Struct t, int line) {
		if (t != Table.intType && t != Table.charType && t != Table.boolType)
			report_error(line, "print funkcija prima parametre tipa: int, char ili bool");
	}

	// Statement ::= (ReturnVoid) RETURN SEMI
	public void visit(ReturnVoid stmt) {
		if (currentMethod == null)
			report_error(stmt.getLine(), "Return iskaz ne sme postojati van globalnih funkcija!");
		else if (currentMethod.getType() != Table.noType)
			report_error(stmt.getLine(),
					"Funkcija " + currentMethod.getName() + " ocekuje povratnu vrednost razlicitu od void!");
	}

	// Statement ::= (ReturnStmt) RETURN Expr SEMI
	public void visit(ReturnStmt stmt) {
		if (currentMethod == null)
			report_error(stmt.getLine(), "Return iskaz ne sme postojati van globalnih funkcija!");
		else if (!currentMethod.getType().compatibleWith(stmt.getExpr().struct))
			report_error(stmt.getLine(),
					"Tip izraza u return naredbi nije kompatibilan sa tipom povratne vrednosti funkcije "
							+ currentMethod.getName());
	}

	// Statement ::= (ReadStmt) READ LPAREN Designator RPAREN SEMI
	public void visit(ReadStmt stmt) {
		Obj d = stmt.getDesignator().obj;
		if (d.getKind() != Obj.Var && d.getKind() != Obj.Elem)
			report_error(stmt.getLine(), "Moguce je ucitati vrednost u promenljivu ili element niza!");
		if (d.getType() != Table.intType && d.getType() != Table.charType && d.getType() != Table.boolType)
			report_error(stmt.getLine(), "Promenljiva mora biti tipa int, char ili bool");
	}

	// Statement ::= (YieldStmt) YIELD Expr SEMI
	public void visit(YieldStmt stmt) {
		if (switchCnt == 0)
			report_error(stmt.getLine(), "Yield se mora nalaziti unutar case grane!");
		stmt.struct = stmt.getExpr().struct;
	}

	// Statement ::= (DoWhileStmt) InitDoWhile DO Statement WHILE LPAREN Condition
	// RPAREN SEMI
	public void visit(DoWhileStmt stmt) {
		if (stmt.getCondition().struct != Table.boolType)
			report_error(stmt.getLine(), "Uslovni izraz unutar do-while petlje mora biti tipa bool!");

		doWhileCnt--;
		stmt.struct = stmt.getStatement().struct;
	}

	// InitDoWhile
	public void visit(InitDoWhile i) {
		doWhileCnt++;
	}

	// Statement ::= (BreakStmt) BREAK SEMI
	public void visit(BreakStmt stmt) {
		if (doWhileCnt == 0)
			report_error(stmt.getLine(), "Break naredba se moze koristiti samo unutar do-while petlje!");
	}

	// Statement ::= (ContinueStmt) CONTINUE SEMI
	public void visit(ContinueStmt stmt) {
		if (doWhileCnt == 0)
			report_error(stmt.getLine(), "Continue naredba se moze koristiti samo unutar do-while petlje!");
	}

	// StatementList ::= (StmtList) StatementList Statement
	public void visit(StmtList stmtList) {
		Struct stmtListType = stmtList.getStatementList().struct;
		Struct stmtType = stmtList.getStatement().struct;

		stmtList.struct = stmtListType != null ? stmtListType : stmtType;
	}
	
	// Statement ::= (IfStmt) InitIf LPAREN IfCondition RPAREN InitThen Statement AfterIf
	public void visit(IfStmt stmt) {
		stmt.struct = stmt.getStatement().struct;
	}
	
	// Statement ::= (IfElseStmt) InitIf LPAREN IfCondition RPAREN InitThen Statement SkipElse InitElse Statement AfterIf
	public void visit(IfElseStmt stmt) {
		if (stmt.getStatement().struct != null && stmt.getStatement1().struct != null 
				&& stmt.getStatement().struct != stmt.getStatement1().struct)
			report_error(stmt.getLine(), "Sve yield naredbe moraju vracati isti tip!");
		stmt.struct = stmt.getStatement().struct != null ? stmt.getStatement().struct : stmt.getStatement1().struct;
	}
	
	// Statement ::= (MoreStmts) LBRACE StatementList RBRACE
	public void visit(MoreStmts stmt) {
		stmt.struct = stmt.getStatementList().struct;
	}

	/* --- CONDITION --- */
	// IfCondition ::= (IfCond) Condition
	public void visit(IfCond ifCondition) {
		if (ifCondition.getCondition().struct != Table.boolType)
			report_error(ifCondition.getLine(), "Uslovni izraz unutar if naredbe mora biti tipa bool");
	}

	// Condition ::= (CondTerms) Condition OR CondTerm
	public void visit(CondTerms condition) {
		condition.struct = Table.boolType;
	}

	// Condition ::= (OneCondTerm) CondTerm
	public void visit(OneCondTerm condition) {
		condition.struct = condition.getCondTerm().struct;
	}

	// CondTerm ::= (CondFacts) CondTerm AND CondFact
	public void visit(CondFacts condTerm) {
		condTerm.struct = Table.boolType;
	}

	// CondTerm ::= (OneCondFact) CondFact
	public void visit(OneCondFact condTerm) {
		condTerm.struct = condTerm.getCondFact().struct;
	}

	// CondFact ::= (CondFact1Expr) Expr
	public void visit(CondFact1Expr condFact) {
		condFact.struct = condFact.getExpr().struct;
		if (condFact.struct != Table.boolType) {
			report_error(condFact.getLine(), "Izraz mora biti tipa bool!");
		}
	}

	// CondFact ::= (CondFact2Expr) Expr Relop Expr
	public void visit(CondFact2Expr condFact) {
		if (!condFact.getExpr().struct.compatibleWith(condFact.getExpr1().struct))
			report_error(condFact.getLine(), "Izrazi nisu kompatibilni za relacionu operaciju");
		if ((condFact.getExpr().struct.getKind() == Struct.Array
				|| condFact.getExpr1().struct.getKind() == Struct.Array)
				&& (condFact.getRelop().obj == null
						|| (condFact.getRelop().obj.getName() != "!=" && condFact.getRelop().obj.getName() != "==")))
			report_error(condFact.getLine(), "Uz promenljive tipa niza se mogu koristiti samo != i ==");
		condFact.struct = Table.boolType;
	}

	/* --- EXPR --- */
	// Expr ::= (BasicTerms) Terms
	public void visit(BasicTerms expr) {
		expr.struct = expr.getTerms().struct;
	}

	// Expr ::= (NegTerms) MINUS Terms
	public void visit(NegTerms expr) {
		if (expr.getTerms().struct != Table.intType) {
			report_error(expr.getLine(), "Operand mora biti tipa int");
		}
		expr.struct = expr.getTerms().struct;
	}

	// Expr ::= (SwitchExpr) InitSwitch SWITCH LPAREN Expr RPAREN LBRACE CaseList
	// DefaultCase RBRACE
	public void visit(SwitchExpr expr) {
		Struct caseListType = expr.getCaseList().struct;
		Struct defaultCaseType = expr.getDefaultCase().struct;

		if (caseListType != null && !caseListType.equals(defaultCaseType))
			report_error(expr.getLine(), "Sve YIELD naredbe moraju vracati isti tip!");

		if (expr.getExpr().struct != Table.intType)
			report_error(expr.getLine(), "Izraz unutar switch naredbe mora biti celobrojnog tipa!");

		expr.struct = defaultCaseType;
		switchCnt--;
		caseStack.pop();
	}

	/* ---- CASE ---- */
	// InitSwitch
	public void visit(InitSwitch i) {
		switchCnt++;
		caseStack.push(new HashSet<Integer>());
	}

	// CaseList ::= (Cases) CaseList InitCass COLON StatementList
	public void visit(Cases caseList) {
		Struct caseListType = caseList.getCaseList().struct;
		Struct stmtListType = caseList.getStatementList().struct;

		if (caseListType != null && stmtListType != null && !caseListType.equals(stmtListType))
			report_error(caseList.getLine(), "Sve YIELD naredbe moraju vracati isti tip!");

		if (caseListType != null)
			caseList.struct = caseListType;
		else if (stmtListType != null)
			caseList.struct = stmtListType;

	}
	
	// InitCase ::= (InitCase) CASE NUM_CONST:num
	public void visit(InitCase initCase) {
		Integer num = initCase.getNum();
		if (caseStack.lastElement().contains(num))
			report_error(initCase.getLine(), "Ne sme postojati vise case grana sa istom celobrojnom konstantom!");
		caseStack.lastElement().add(num);
	}

	// DefaultCase ::= (DefaultCase) DEFAULT COLON StatementList
	public void visit(DefaultCase defaultCase) {
		defaultCase.struct = defaultCase.getStatementList().struct;
		if (defaultCase.struct == null)
			report_error(defaultCase.getLine(), "Podrazumevana grana unutar switch-a mora imati yield naredbu!");
	}

	/* --- TERM --- */
	// Terms ::= (TermList) Terms Addop Term
	public void visit(TermList terms) {
		if (terms.getTerms().struct != Table.intType || terms.getTerm().struct != Table.intType)
			report_error(terms.getLine(), "Dozvoljeni su samo operandi tipa int");
		terms.struct = terms.getTerm().struct;
	}

	// Terms ::= (OneTerm) Term
	public void visit(OneTerm terms) {
		terms.struct = terms.getTerm().struct;
	}

	// Term ::= (FactorList) Term Mulop Factor
	public void visit(FactorList term) {
		if (term.getTerm().struct != Table.intType || term.getFactor().struct != Table.intType)
			report_error(term.getLine(), "Dozvoljeni su samo operandi tipa int");

		term.struct = term.getFactor().struct;
	}

	// Term ::= (OneFactor) Factor
	public void visit(OneFactor term) {
		term.struct = term.getFactor().struct;
	}

	/* --- FACTOR --- */
	// Factor ::= (DesignatorFactor) Designator
	public void visit(DesignatorFactor factor) {
		factor.struct = factor.getDesignator().obj.getType();
	}

	// Factor ::= (DesignatorFactorActPars) FuncDesignator PossibleActPars
	public void visit(DesignatorFactorActPars factor) {
		handleFuncCall(factor.getFuncDesignator().obj, factor.getLine());
		factor.struct = factor.getFuncDesignator().obj.getType();
	}
	
	// Factor ::= NEW Type
	public void visit(NewFactor factor) {
		Struct s = factor.getType().struct;
		if (s == Table.intType || s == Table.boolType || s == Table.charType || s == Table.noType)
			report_error(factor.getLine(), "Operator NEW se moze koristiti samo sa korisnicki definisanim tipovima!");
	}

	// Factor ::= NEW Type LSQUARE Expr RSQUARE
	public void visit(NewFactorArray factor) {
		if (factor.getExpr().struct != Table.intType)
			report_error(factor.getLine(), "Velicina niza mora biti tipa int");
		factor.struct = new Struct(Struct.Array, factor.getType().struct);
	}

	// Factor ::= (ExprFactor) LPAREN Expr RPAREN
	public void visit(ExprFactor factor) {
		// TODO Not sure about this one
		factor.struct = factor.getExpr().struct;
	}

	public void visit(NumFactor factor) {
		factor.struct = Table.intType;
	}

	public void visit(CharFactor factor) {
		factor.struct = Table.charType;
	}

	public void visit(BoolFactor factor) {
		factor.struct = Table.boolType;
	}

	/* --- RELOP --- */
	public void visit(Equal relop) {
		relop.obj = new Obj(Obj.Elem, "==", Table.noType);
	}

	public void visit(NotEqual relop) {
		relop.obj = new Obj(Obj.Elem, "!=", Table.noType);
	}
}
