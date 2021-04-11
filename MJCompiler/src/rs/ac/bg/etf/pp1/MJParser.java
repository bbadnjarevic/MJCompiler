
//----------------------------------------------------
// The following code was generated by CUP v0.10k
// Sun Apr 11 13:17:06 CEST 2021
//----------------------------------------------------

package rs.ac.bg.etf.pp1;

import java_cup.runtime.*;
import org.apache.log4j.*;
import rs.ac.bg.etf.pp1.ast.*;

/** CUP v0.10k generated parser.
  * @version Sun Apr 11 13:17:06 CEST 2021
  */
public class MJParser extends java_cup.runtime.lr_parser {

  /** Default constructor. */
  public MJParser() {super();}

  /** Constructor which sets the default scanner. */
  public MJParser(java_cup.runtime.Scanner s) {super(s);}

  /** Production table. */
  protected static final short _production_table[][] = 
    unpackFromStrings(new String[] {
    "\000\031\000\002\003\010\000\002\002\004\000\002\004" +
    "\004\000\002\004\004\000\002\004\004\000\002\004\002" +
    "\000\002\005\006\000\002\006\005\000\002\006\003\000" +
    "\002\007\005\000\002\007\005\000\002\007\005\000\002" +
    "\010\005\000\002\013\004\000\002\013\002\000\002\011" +
    "\005\000\002\011\003\000\002\012\005\000\002\012\003" +
    "\000\002\014\011\000\002\016\004\000\002\016\002\000" +
    "\002\015\004\000\002\015\002\000\002\021\003" });

  /** Access to production table. */
  public short[][] production_table() {return _production_table;}

  /** Parse-action table. */
  protected static final short[][] _action_table = 
    unpackFromStrings(new String[] {
    "\000\056\000\004\004\005\001\002\000\004\002\060\001" +
    "\002\000\004\025\006\001\002\000\012\010\ufffc\011\ufffc" +
    "\020\ufffc\025\ufffc\001\002\000\012\010\010\011\017\020" +
    "\011\025\016\001\002\000\004\025\016\001\002\000\002" +
    "\001\002\000\012\010\uffff\011\uffff\020\uffff\025\uffff\001" +
    "\002\000\004\025\035\001\002\000\012\010\ufffd\011\ufffd" +
    "\020\ufffd\025\ufffd\001\002\000\012\010\ufffe\011\ufffe\020" +
    "\ufffe\025\ufffe\001\002\000\006\020\uffe9\025\uffe9\001\002" +
    "\000\004\025\020\001\002\000\006\020\uffec\042\021\001" +
    "\002\000\004\025\016\001\002\000\004\020\023\001\002" +
    "\000\010\020\ufff3\021\ufff3\025\ufff3\001\002\000\010\020" +
    "\025\021\uffea\025\016\001\002\000\004\021\031\001\002" +
    "\000\010\020\ufff4\021\ufff4\025\ufff4\001\002\000\004\021" +
    "\030\001\002\000\012\010\uffee\011\uffee\020\uffee\025\uffee" +
    "\001\002\000\004\021\uffeb\001\002\000\004\020\uffed\001" +
    "\002\000\006\012\041\013\040\001\002\000\006\012\ufff1" +
    "\013\ufff1\001\002\000\010\012\uffef\013\uffef\022\036\001" +
    "\002\000\004\023\037\001\002\000\006\012\ufff0\013\ufff0" +
    "\001\002\000\004\025\035\001\002\000\014\010\ufff5\011" +
    "\ufff5\020\ufff5\021\ufff5\025\ufff5\001\002\000\006\012\ufff2" +
    "\013\ufff2\001\002\000\004\021\044\001\002\000\004\002" +
    "\001\001\002\000\004\025\050\001\002\000\006\012\ufff9" +
    "\013\ufff9\001\002\000\006\012\056\013\055\001\002\000" +
    "\004\014\051\001\002\000\010\030\053\031\054\032\052" +
    "\001\002\000\006\012\ufff6\013\ufff6\001\002\000\006\012" +
    "\ufff8\013\ufff8\001\002\000\006\012\ufff7\013\ufff7\001\002" +
    "\000\004\025\050\001\002\000\012\010\ufffb\011\ufffb\020" +
    "\ufffb\025\ufffb\001\002\000\006\012\ufffa\013\ufffa\001\002" +
    "\000\004\002\000\001\002" });

  /** Access to parse-action table. */
  public short[][] action_table() {return _action_table;}

  /** <code>reduce_goto</code> table. */
  protected static final short[][] _reduce_table = 
    unpackFromStrings(new String[] {
    "\000\056\000\004\003\003\001\001\000\002\001\001\000" +
    "\002\001\001\000\004\004\006\001\001\000\012\005\011" +
    "\010\014\014\013\021\012\001\001\000\004\021\044\001" +
    "\001\000\004\017\042\001\001\000\002\001\001\000\006" +
    "\011\032\012\033\001\001\000\002\001\001\000\002\001" +
    "\001\000\002\001\001\000\002\001\001\000\004\016\021" +
    "\001\001\000\004\021\031\001\001\000\002\001\001\000" +
    "\004\013\023\001\001\000\010\010\025\015\026\021\012" +
    "\001\001\000\002\001\001\000\002\001\001\000\002\001" +
    "\001\000\002\001\001\000\002\001\001\000\002\001\001" +
    "\000\002\001\001\000\002\001\001\000\002\001\001\000" +
    "\002\001\001\000\002\001\001\000\004\012\041\001\001" +
    "\000\002\001\001\000\002\001\001\000\002\001\001\000" +
    "\002\001\001\000\006\006\046\007\045\001\001\000\002" +
    "\001\001\000\002\001\001\000\002\001\001\000\002\001" +
    "\001\000\002\001\001\000\002\001\001\000\002\001\001" +
    "\000\004\007\056\001\001\000\002\001\001\000\002\001" +
    "\001\000\002\001\001" });

  /** Access to <code>reduce_goto</code> table. */
  public short[][] reduce_table() {return _reduce_table;}

  /** Instance of action encapsulation class. */
  protected CUP$MJParser$actions action_obj;

  /** Action encapsulation object initializer. */
  protected void init_actions()
    {
      action_obj = new CUP$MJParser$actions(this);
    }

  /** Invoke a user supplied parse action. */
  public java_cup.runtime.Symbol do_action(
    int                        act_num,
    java_cup.runtime.lr_parser parser,
    java.util.Stack            stack,
    int                        top)
    throws java.lang.Exception
  {
    /* call code in generated class */
    return action_obj.CUP$MJParser$do_action(act_num, parser, stack, top);
  }

  /** Indicates start state. */
  public int start_state() {return 0;}
  /** Indicates start production. */
  public int start_production() {return 1;}

  /** <code>EOF</code> Symbol index. */
  public int EOF_sym() {return 0;}

  /** <code>error</code> Symbol index. */
  public int error_sym() {return 1;}


  /** Scan to get the next Symbol. */
  public java_cup.runtime.Symbol scan()
    throws java.lang.Exception
    {

	Symbol s = this.getScanner().next_token();
	if (s != null && s.value != null) 
		log.info(s.toString() + " " + s.value.toString());
	return s;

    }



	Logger log = Logger.getLogger(getClass());
   
   
    // slede redefinisani metodi za prijavu gresaka radi izmene teksta poruke
     
    public void report_fatal_error(String message, Object info) throws java.lang.Exception {
      done_parsing();
      report_error(message, info);
    }
  
    public void syntax_error(Symbol cur_token) {
        report_error("\nSintaksna greska", cur_token);
    }
  
    public void unrecovered_syntax_error(Symbol cur_token) throws java.lang.Exception {
        report_fatal_error("Fatalna greska, parsiranje se ne moze nastaviti", cur_token);
    }

    public void report_error(String message, Object info) {
    	StringBuilder msg = new StringBuilder(message); 
    	if (info instanceof Symbol)
            msg.append (" na liniji ").append(((Symbol)info).left);
        log.error(msg.toString());
    }



}

/** Cup generated class to encapsulate user supplied action code.*/
class CUP$MJParser$actions {
  private final MJParser parser;

  /** Constructor */
  CUP$MJParser$actions(MJParser parser) {
    this.parser = parser;
  }

  /** Method with the actual generated action code. */
  public final java_cup.runtime.Symbol CUP$MJParser$do_action(
    int                        CUP$MJParser$act_num,
    java_cup.runtime.lr_parser CUP$MJParser$parser,
    java.util.Stack            CUP$MJParser$stack,
    int                        CUP$MJParser$top)
    throws java.lang.Exception
    {
      /* Symbol object for return from actions */
      java_cup.runtime.Symbol CUP$MJParser$result;

      /* select the action based on the action number */
      switch (CUP$MJParser$act_num)
        {
          /*. . . . . . . . . . . . . . . . . . . .*/
          case 24: // Type ::= IDENT 
            {
              Type RESULT = null;
		int typeNameleft = ((java_cup.runtime.Symbol)CUP$MJParser$stack.elementAt(CUP$MJParser$top-0)).left;
		int typeNameright = ((java_cup.runtime.Symbol)CUP$MJParser$stack.elementAt(CUP$MJParser$top-0)).right;
		String typeName = (String)((java_cup.runtime.Symbol) CUP$MJParser$stack.elementAt(CUP$MJParser$top-0)).value;
		 RESULT=new Type(typeName); RESULT.setLine(typeNameleft); 
              CUP$MJParser$result = new java_cup.runtime.Symbol(15/*Type*/, ((java_cup.runtime.Symbol)CUP$MJParser$stack.elementAt(CUP$MJParser$top-0)).left, ((java_cup.runtime.Symbol)CUP$MJParser$stack.elementAt(CUP$MJParser$top-0)).right, RESULT);
            }
          return CUP$MJParser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 23: // ClassMethods ::= 
            {
              ClassMethods RESULT = null;
		 RESULT=new NoClassMethods(); 
              CUP$MJParser$result = new java_cup.runtime.Symbol(11/*ClassMethods*/, ((java_cup.runtime.Symbol)CUP$MJParser$stack.elementAt(CUP$MJParser$top-0)).right, ((java_cup.runtime.Symbol)CUP$MJParser$stack.elementAt(CUP$MJParser$top-0)).right, RESULT);
            }
          return CUP$MJParser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 22: // ClassMethods ::= LBRACE RBRACE 
            {
              ClassMethods RESULT = null;
		 RESULT=new ClassMethodsList(); 
              CUP$MJParser$result = new java_cup.runtime.Symbol(11/*ClassMethods*/, ((java_cup.runtime.Symbol)CUP$MJParser$stack.elementAt(CUP$MJParser$top-1)).left, ((java_cup.runtime.Symbol)CUP$MJParser$stack.elementAt(CUP$MJParser$top-0)).right, RESULT);
            }
          return CUP$MJParser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 21: // Extends ::= 
            {
              Extends RESULT = null;
		 RESULT=new NoExtends(); 
              CUP$MJParser$result = new java_cup.runtime.Symbol(12/*Extends*/, ((java_cup.runtime.Symbol)CUP$MJParser$stack.elementAt(CUP$MJParser$top-0)).right, ((java_cup.runtime.Symbol)CUP$MJParser$stack.elementAt(CUP$MJParser$top-0)).right, RESULT);
            }
          return CUP$MJParser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 20: // Extends ::= EXTENDS Type 
            {
              Extends RESULT = null;
		int T1left = ((java_cup.runtime.Symbol)CUP$MJParser$stack.elementAt(CUP$MJParser$top-0)).left;
		int T1right = ((java_cup.runtime.Symbol)CUP$MJParser$stack.elementAt(CUP$MJParser$top-0)).right;
		Type T1 = (Type)((java_cup.runtime.Symbol) CUP$MJParser$stack.elementAt(CUP$MJParser$top-0)).value;
		 RESULT=new ClassExtends(T1); RESULT.setLine(T1left); 
              CUP$MJParser$result = new java_cup.runtime.Symbol(12/*Extends*/, ((java_cup.runtime.Symbol)CUP$MJParser$stack.elementAt(CUP$MJParser$top-1)).left, ((java_cup.runtime.Symbol)CUP$MJParser$stack.elementAt(CUP$MJParser$top-0)).right, RESULT);
            }
          return CUP$MJParser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 19: // ClassDecl ::= CLASS IDENT Extends LBRACE VarDeclsList ClassMethods RBRACE 
            {
              ClassDecl RESULT = null;
		int classNameleft = ((java_cup.runtime.Symbol)CUP$MJParser$stack.elementAt(CUP$MJParser$top-5)).left;
		int classNameright = ((java_cup.runtime.Symbol)CUP$MJParser$stack.elementAt(CUP$MJParser$top-5)).right;
		String className = (String)((java_cup.runtime.Symbol) CUP$MJParser$stack.elementAt(CUP$MJParser$top-5)).value;
		int E1left = ((java_cup.runtime.Symbol)CUP$MJParser$stack.elementAt(CUP$MJParser$top-4)).left;
		int E1right = ((java_cup.runtime.Symbol)CUP$MJParser$stack.elementAt(CUP$MJParser$top-4)).right;
		Extends E1 = (Extends)((java_cup.runtime.Symbol) CUP$MJParser$stack.elementAt(CUP$MJParser$top-4)).value;
		int V2left = ((java_cup.runtime.Symbol)CUP$MJParser$stack.elementAt(CUP$MJParser$top-2)).left;
		int V2right = ((java_cup.runtime.Symbol)CUP$MJParser$stack.elementAt(CUP$MJParser$top-2)).right;
		VarDeclsList V2 = (VarDeclsList)((java_cup.runtime.Symbol) CUP$MJParser$stack.elementAt(CUP$MJParser$top-2)).value;
		int C3left = ((java_cup.runtime.Symbol)CUP$MJParser$stack.elementAt(CUP$MJParser$top-1)).left;
		int C3right = ((java_cup.runtime.Symbol)CUP$MJParser$stack.elementAt(CUP$MJParser$top-1)).right;
		ClassMethods C3 = (ClassMethods)((java_cup.runtime.Symbol) CUP$MJParser$stack.elementAt(CUP$MJParser$top-1)).value;
		 RESULT=new ClassDecl(className, E1, V2, C3); RESULT.setLine(classNameleft); 
              CUP$MJParser$result = new java_cup.runtime.Symbol(10/*ClassDecl*/, ((java_cup.runtime.Symbol)CUP$MJParser$stack.elementAt(CUP$MJParser$top-6)).left, ((java_cup.runtime.Symbol)CUP$MJParser$stack.elementAt(CUP$MJParser$top-0)).right, RESULT);
            }
          return CUP$MJParser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 18: // VarDecl ::= IDENT 
            {
              VarDecl RESULT = null;
		int varNameleft = ((java_cup.runtime.Symbol)CUP$MJParser$stack.elementAt(CUP$MJParser$top-0)).left;
		int varNameright = ((java_cup.runtime.Symbol)CUP$MJParser$stack.elementAt(CUP$MJParser$top-0)).right;
		String varName = (String)((java_cup.runtime.Symbol) CUP$MJParser$stack.elementAt(CUP$MJParser$top-0)).value;
		 RESULT=new VarDeclaration(varName); RESULT.setLine(varNameleft); 
              CUP$MJParser$result = new java_cup.runtime.Symbol(8/*VarDecl*/, ((java_cup.runtime.Symbol)CUP$MJParser$stack.elementAt(CUP$MJParser$top-0)).left, ((java_cup.runtime.Symbol)CUP$MJParser$stack.elementAt(CUP$MJParser$top-0)).right, RESULT);
            }
          return CUP$MJParser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 17: // VarDecl ::= IDENT LSQUARE RSQUARE 
            {
              VarDecl RESULT = null;
		int varNameleft = ((java_cup.runtime.Symbol)CUP$MJParser$stack.elementAt(CUP$MJParser$top-2)).left;
		int varNameright = ((java_cup.runtime.Symbol)CUP$MJParser$stack.elementAt(CUP$MJParser$top-2)).right;
		String varName = (String)((java_cup.runtime.Symbol) CUP$MJParser$stack.elementAt(CUP$MJParser$top-2)).value;
		 RESULT=new ArrayDeclaration(varName); RESULT.setLine(varNameleft); 
              CUP$MJParser$result = new java_cup.runtime.Symbol(8/*VarDecl*/, ((java_cup.runtime.Symbol)CUP$MJParser$stack.elementAt(CUP$MJParser$top-2)).left, ((java_cup.runtime.Symbol)CUP$MJParser$stack.elementAt(CUP$MJParser$top-0)).right, RESULT);
            }
          return CUP$MJParser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 16: // VarDeclList ::= VarDecl 
            {
              VarDeclList RESULT = null;
		int V1left = ((java_cup.runtime.Symbol)CUP$MJParser$stack.elementAt(CUP$MJParser$top-0)).left;
		int V1right = ((java_cup.runtime.Symbol)CUP$MJParser$stack.elementAt(CUP$MJParser$top-0)).right;
		VarDecl V1 = (VarDecl)((java_cup.runtime.Symbol) CUP$MJParser$stack.elementAt(CUP$MJParser$top-0)).value;
		 RESULT=new OneElemVarDeclList(V1); RESULT.setLine(V1left); 
              CUP$MJParser$result = new java_cup.runtime.Symbol(7/*VarDeclList*/, ((java_cup.runtime.Symbol)CUP$MJParser$stack.elementAt(CUP$MJParser$top-0)).left, ((java_cup.runtime.Symbol)CUP$MJParser$stack.elementAt(CUP$MJParser$top-0)).right, RESULT);
            }
          return CUP$MJParser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 15: // VarDeclList ::= VarDeclList COMMA VarDecl 
            {
              VarDeclList RESULT = null;
		int V1left = ((java_cup.runtime.Symbol)CUP$MJParser$stack.elementAt(CUP$MJParser$top-2)).left;
		int V1right = ((java_cup.runtime.Symbol)CUP$MJParser$stack.elementAt(CUP$MJParser$top-2)).right;
		VarDeclList V1 = (VarDeclList)((java_cup.runtime.Symbol) CUP$MJParser$stack.elementAt(CUP$MJParser$top-2)).value;
		int V2left = ((java_cup.runtime.Symbol)CUP$MJParser$stack.elementAt(CUP$MJParser$top-0)).left;
		int V2right = ((java_cup.runtime.Symbol)CUP$MJParser$stack.elementAt(CUP$MJParser$top-0)).right;
		VarDecl V2 = (VarDecl)((java_cup.runtime.Symbol) CUP$MJParser$stack.elementAt(CUP$MJParser$top-0)).value;
		 RESULT=new VarDeclarationlList(V1, V2); RESULT.setLine(V1left); 
              CUP$MJParser$result = new java_cup.runtime.Symbol(7/*VarDeclList*/, ((java_cup.runtime.Symbol)CUP$MJParser$stack.elementAt(CUP$MJParser$top-2)).left, ((java_cup.runtime.Symbol)CUP$MJParser$stack.elementAt(CUP$MJParser$top-0)).right, RESULT);
            }
          return CUP$MJParser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 14: // VarDeclsList ::= 
            {
              VarDeclsList RESULT = null;
		 RESULT=new NoVarDecls(); 
              CUP$MJParser$result = new java_cup.runtime.Symbol(9/*VarDeclsList*/, ((java_cup.runtime.Symbol)CUP$MJParser$stack.elementAt(CUP$MJParser$top-0)).right, ((java_cup.runtime.Symbol)CUP$MJParser$stack.elementAt(CUP$MJParser$top-0)).right, RESULT);
            }
          return CUP$MJParser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 13: // VarDeclsList ::= VarDeclsList VarDecls 
            {
              VarDeclsList RESULT = null;
		int V1left = ((java_cup.runtime.Symbol)CUP$MJParser$stack.elementAt(CUP$MJParser$top-1)).left;
		int V1right = ((java_cup.runtime.Symbol)CUP$MJParser$stack.elementAt(CUP$MJParser$top-1)).right;
		VarDeclsList V1 = (VarDeclsList)((java_cup.runtime.Symbol) CUP$MJParser$stack.elementAt(CUP$MJParser$top-1)).value;
		int V2left = ((java_cup.runtime.Symbol)CUP$MJParser$stack.elementAt(CUP$MJParser$top-0)).left;
		int V2right = ((java_cup.runtime.Symbol)CUP$MJParser$stack.elementAt(CUP$MJParser$top-0)).right;
		VarDecls V2 = (VarDecls)((java_cup.runtime.Symbol) CUP$MJParser$stack.elementAt(CUP$MJParser$top-0)).value;
		 RESULT=new VarDeclsList_(V1, V2); RESULT.setLine(V1left); 
              CUP$MJParser$result = new java_cup.runtime.Symbol(9/*VarDeclsList*/, ((java_cup.runtime.Symbol)CUP$MJParser$stack.elementAt(CUP$MJParser$top-1)).left, ((java_cup.runtime.Symbol)CUP$MJParser$stack.elementAt(CUP$MJParser$top-0)).right, RESULT);
            }
          return CUP$MJParser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 12: // VarDecls ::= Type VarDeclList SEMI 
            {
              VarDecls RESULT = null;
		int varTypeleft = ((java_cup.runtime.Symbol)CUP$MJParser$stack.elementAt(CUP$MJParser$top-2)).left;
		int varTyperight = ((java_cup.runtime.Symbol)CUP$MJParser$stack.elementAt(CUP$MJParser$top-2)).right;
		Type varType = (Type)((java_cup.runtime.Symbol) CUP$MJParser$stack.elementAt(CUP$MJParser$top-2)).value;
		int V1left = ((java_cup.runtime.Symbol)CUP$MJParser$stack.elementAt(CUP$MJParser$top-1)).left;
		int V1right = ((java_cup.runtime.Symbol)CUP$MJParser$stack.elementAt(CUP$MJParser$top-1)).right;
		VarDeclList V1 = (VarDeclList)((java_cup.runtime.Symbol) CUP$MJParser$stack.elementAt(CUP$MJParser$top-1)).value;
		 RESULT=new VarDecls(varType, V1); RESULT.setLine(varTypeleft); 
              CUP$MJParser$result = new java_cup.runtime.Symbol(6/*VarDecls*/, ((java_cup.runtime.Symbol)CUP$MJParser$stack.elementAt(CUP$MJParser$top-2)).left, ((java_cup.runtime.Symbol)CUP$MJParser$stack.elementAt(CUP$MJParser$top-0)).right, RESULT);
            }
          return CUP$MJParser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 11: // ConstDecl ::= IDENT EQUAL BOOL_CONST 
            {
              ConstDecl RESULT = null;
		int constNameleft = ((java_cup.runtime.Symbol)CUP$MJParser$stack.elementAt(CUP$MJParser$top-2)).left;
		int constNameright = ((java_cup.runtime.Symbol)CUP$MJParser$stack.elementAt(CUP$MJParser$top-2)).right;
		String constName = (String)((java_cup.runtime.Symbol) CUP$MJParser$stack.elementAt(CUP$MJParser$top-2)).value;
		 RESULT=new BoolConstDecl(constName); RESULT.setLine(constNameleft); 
              CUP$MJParser$result = new java_cup.runtime.Symbol(5/*ConstDecl*/, ((java_cup.runtime.Symbol)CUP$MJParser$stack.elementAt(CUP$MJParser$top-2)).left, ((java_cup.runtime.Symbol)CUP$MJParser$stack.elementAt(CUP$MJParser$top-0)).right, RESULT);
            }
          return CUP$MJParser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 10: // ConstDecl ::= IDENT EQUAL CHAR_CONST 
            {
              ConstDecl RESULT = null;
		int constNameleft = ((java_cup.runtime.Symbol)CUP$MJParser$stack.elementAt(CUP$MJParser$top-2)).left;
		int constNameright = ((java_cup.runtime.Symbol)CUP$MJParser$stack.elementAt(CUP$MJParser$top-2)).right;
		String constName = (String)((java_cup.runtime.Symbol) CUP$MJParser$stack.elementAt(CUP$MJParser$top-2)).value;
		 RESULT=new CharConstDecl(constName); RESULT.setLine(constNameleft); 
              CUP$MJParser$result = new java_cup.runtime.Symbol(5/*ConstDecl*/, ((java_cup.runtime.Symbol)CUP$MJParser$stack.elementAt(CUP$MJParser$top-2)).left, ((java_cup.runtime.Symbol)CUP$MJParser$stack.elementAt(CUP$MJParser$top-0)).right, RESULT);
            }
          return CUP$MJParser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 9: // ConstDecl ::= IDENT EQUAL NUM_CONST 
            {
              ConstDecl RESULT = null;
		int constNameleft = ((java_cup.runtime.Symbol)CUP$MJParser$stack.elementAt(CUP$MJParser$top-2)).left;
		int constNameright = ((java_cup.runtime.Symbol)CUP$MJParser$stack.elementAt(CUP$MJParser$top-2)).right;
		String constName = (String)((java_cup.runtime.Symbol) CUP$MJParser$stack.elementAt(CUP$MJParser$top-2)).value;
		 RESULT=new NumConstDecl(constName); RESULT.setLine(constNameleft); 
              CUP$MJParser$result = new java_cup.runtime.Symbol(5/*ConstDecl*/, ((java_cup.runtime.Symbol)CUP$MJParser$stack.elementAt(CUP$MJParser$top-2)).left, ((java_cup.runtime.Symbol)CUP$MJParser$stack.elementAt(CUP$MJParser$top-0)).right, RESULT);
            }
          return CUP$MJParser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 8: // ConstDeclList ::= ConstDecl 
            {
              ConstDeclList RESULT = null;
		int C1left = ((java_cup.runtime.Symbol)CUP$MJParser$stack.elementAt(CUP$MJParser$top-0)).left;
		int C1right = ((java_cup.runtime.Symbol)CUP$MJParser$stack.elementAt(CUP$MJParser$top-0)).right;
		ConstDecl C1 = (ConstDecl)((java_cup.runtime.Symbol) CUP$MJParser$stack.elementAt(CUP$MJParser$top-0)).value;
		 RESULT=new OneElemConstDeclList(C1); RESULT.setLine(C1left); 
              CUP$MJParser$result = new java_cup.runtime.Symbol(4/*ConstDeclList*/, ((java_cup.runtime.Symbol)CUP$MJParser$stack.elementAt(CUP$MJParser$top-0)).left, ((java_cup.runtime.Symbol)CUP$MJParser$stack.elementAt(CUP$MJParser$top-0)).right, RESULT);
            }
          return CUP$MJParser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 7: // ConstDeclList ::= ConstDeclList COMMA ConstDecl 
            {
              ConstDeclList RESULT = null;
		int C1left = ((java_cup.runtime.Symbol)CUP$MJParser$stack.elementAt(CUP$MJParser$top-2)).left;
		int C1right = ((java_cup.runtime.Symbol)CUP$MJParser$stack.elementAt(CUP$MJParser$top-2)).right;
		ConstDeclList C1 = (ConstDeclList)((java_cup.runtime.Symbol) CUP$MJParser$stack.elementAt(CUP$MJParser$top-2)).value;
		int C2left = ((java_cup.runtime.Symbol)CUP$MJParser$stack.elementAt(CUP$MJParser$top-0)).left;
		int C2right = ((java_cup.runtime.Symbol)CUP$MJParser$stack.elementAt(CUP$MJParser$top-0)).right;
		ConstDecl C2 = (ConstDecl)((java_cup.runtime.Symbol) CUP$MJParser$stack.elementAt(CUP$MJParser$top-0)).value;
		 RESULT=new ConstDeclarationList(C1, C2); RESULT.setLine(C1left); 
              CUP$MJParser$result = new java_cup.runtime.Symbol(4/*ConstDeclList*/, ((java_cup.runtime.Symbol)CUP$MJParser$stack.elementAt(CUP$MJParser$top-2)).left, ((java_cup.runtime.Symbol)CUP$MJParser$stack.elementAt(CUP$MJParser$top-0)).right, RESULT);
            }
          return CUP$MJParser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 6: // ConstDecls ::= CONST Type ConstDeclList SEMI 
            {
              ConstDecls RESULT = null;
		int T1left = ((java_cup.runtime.Symbol)CUP$MJParser$stack.elementAt(CUP$MJParser$top-2)).left;
		int T1right = ((java_cup.runtime.Symbol)CUP$MJParser$stack.elementAt(CUP$MJParser$top-2)).right;
		Type T1 = (Type)((java_cup.runtime.Symbol) CUP$MJParser$stack.elementAt(CUP$MJParser$top-2)).value;
		int C2left = ((java_cup.runtime.Symbol)CUP$MJParser$stack.elementAt(CUP$MJParser$top-1)).left;
		int C2right = ((java_cup.runtime.Symbol)CUP$MJParser$stack.elementAt(CUP$MJParser$top-1)).right;
		ConstDeclList C2 = (ConstDeclList)((java_cup.runtime.Symbol) CUP$MJParser$stack.elementAt(CUP$MJParser$top-1)).value;
		 RESULT=new ConstDecls(T1, C2); RESULT.setLine(T1left); 
              CUP$MJParser$result = new java_cup.runtime.Symbol(3/*ConstDecls*/, ((java_cup.runtime.Symbol)CUP$MJParser$stack.elementAt(CUP$MJParser$top-3)).left, ((java_cup.runtime.Symbol)CUP$MJParser$stack.elementAt(CUP$MJParser$top-0)).right, RESULT);
            }
          return CUP$MJParser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 5: // DeclarationsList ::= 
            {
              DeclarationsList RESULT = null;
		 RESULT=new NoDecls(); 
              CUP$MJParser$result = new java_cup.runtime.Symbol(2/*DeclarationsList*/, ((java_cup.runtime.Symbol)CUP$MJParser$stack.elementAt(CUP$MJParser$top-0)).right, ((java_cup.runtime.Symbol)CUP$MJParser$stack.elementAt(CUP$MJParser$top-0)).right, RESULT);
            }
          return CUP$MJParser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 4: // DeclarationsList ::= DeclarationsList ClassDecl 
            {
              DeclarationsList RESULT = null;
		int D1left = ((java_cup.runtime.Symbol)CUP$MJParser$stack.elementAt(CUP$MJParser$top-1)).left;
		int D1right = ((java_cup.runtime.Symbol)CUP$MJParser$stack.elementAt(CUP$MJParser$top-1)).right;
		DeclarationsList D1 = (DeclarationsList)((java_cup.runtime.Symbol) CUP$MJParser$stack.elementAt(CUP$MJParser$top-1)).value;
		int C2left = ((java_cup.runtime.Symbol)CUP$MJParser$stack.elementAt(CUP$MJParser$top-0)).left;
		int C2right = ((java_cup.runtime.Symbol)CUP$MJParser$stack.elementAt(CUP$MJParser$top-0)).right;
		ClassDecl C2 = (ClassDecl)((java_cup.runtime.Symbol) CUP$MJParser$stack.elementAt(CUP$MJParser$top-0)).value;
		 RESULT=new ClassDeclarationsList(D1, C2); RESULT.setLine(D1left); 
              CUP$MJParser$result = new java_cup.runtime.Symbol(2/*DeclarationsList*/, ((java_cup.runtime.Symbol)CUP$MJParser$stack.elementAt(CUP$MJParser$top-1)).left, ((java_cup.runtime.Symbol)CUP$MJParser$stack.elementAt(CUP$MJParser$top-0)).right, RESULT);
            }
          return CUP$MJParser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 3: // DeclarationsList ::= DeclarationsList VarDecls 
            {
              DeclarationsList RESULT = null;
		int D1left = ((java_cup.runtime.Symbol)CUP$MJParser$stack.elementAt(CUP$MJParser$top-1)).left;
		int D1right = ((java_cup.runtime.Symbol)CUP$MJParser$stack.elementAt(CUP$MJParser$top-1)).right;
		DeclarationsList D1 = (DeclarationsList)((java_cup.runtime.Symbol) CUP$MJParser$stack.elementAt(CUP$MJParser$top-1)).value;
		int V2left = ((java_cup.runtime.Symbol)CUP$MJParser$stack.elementAt(CUP$MJParser$top-0)).left;
		int V2right = ((java_cup.runtime.Symbol)CUP$MJParser$stack.elementAt(CUP$MJParser$top-0)).right;
		VarDecls V2 = (VarDecls)((java_cup.runtime.Symbol) CUP$MJParser$stack.elementAt(CUP$MJParser$top-0)).value;
		 RESULT=new VarDeclarationsList(D1, V2); RESULT.setLine(D1left); 
              CUP$MJParser$result = new java_cup.runtime.Symbol(2/*DeclarationsList*/, ((java_cup.runtime.Symbol)CUP$MJParser$stack.elementAt(CUP$MJParser$top-1)).left, ((java_cup.runtime.Symbol)CUP$MJParser$stack.elementAt(CUP$MJParser$top-0)).right, RESULT);
            }
          return CUP$MJParser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 2: // DeclarationsList ::= DeclarationsList ConstDecls 
            {
              DeclarationsList RESULT = null;
		int D1left = ((java_cup.runtime.Symbol)CUP$MJParser$stack.elementAt(CUP$MJParser$top-1)).left;
		int D1right = ((java_cup.runtime.Symbol)CUP$MJParser$stack.elementAt(CUP$MJParser$top-1)).right;
		DeclarationsList D1 = (DeclarationsList)((java_cup.runtime.Symbol) CUP$MJParser$stack.elementAt(CUP$MJParser$top-1)).value;
		int C2left = ((java_cup.runtime.Symbol)CUP$MJParser$stack.elementAt(CUP$MJParser$top-0)).left;
		int C2right = ((java_cup.runtime.Symbol)CUP$MJParser$stack.elementAt(CUP$MJParser$top-0)).right;
		ConstDecls C2 = (ConstDecls)((java_cup.runtime.Symbol) CUP$MJParser$stack.elementAt(CUP$MJParser$top-0)).value;
		 RESULT=new ConstDeclarationsList(D1, C2); RESULT.setLine(D1left); 
              CUP$MJParser$result = new java_cup.runtime.Symbol(2/*DeclarationsList*/, ((java_cup.runtime.Symbol)CUP$MJParser$stack.elementAt(CUP$MJParser$top-1)).left, ((java_cup.runtime.Symbol)CUP$MJParser$stack.elementAt(CUP$MJParser$top-0)).right, RESULT);
            }
          return CUP$MJParser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 1: // $START ::= Program EOF 
            {
              SyntaxNode RESULT = null;
		int start_valleft = ((java_cup.runtime.Symbol)CUP$MJParser$stack.elementAt(CUP$MJParser$top-1)).left;
		int start_valright = ((java_cup.runtime.Symbol)CUP$MJParser$stack.elementAt(CUP$MJParser$top-1)).right;
		Program start_val = (Program)((java_cup.runtime.Symbol) CUP$MJParser$stack.elementAt(CUP$MJParser$top-1)).value;
		RESULT = start_val;
              CUP$MJParser$result = new java_cup.runtime.Symbol(0/*$START*/, ((java_cup.runtime.Symbol)CUP$MJParser$stack.elementAt(CUP$MJParser$top-1)).left, ((java_cup.runtime.Symbol)CUP$MJParser$stack.elementAt(CUP$MJParser$top-0)).right, RESULT);
            }
          /* ACCEPT */
          CUP$MJParser$parser.done_parsing();
          return CUP$MJParser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 0: // Program ::= PROG IDENT DeclarationsList LBRACE MethodDecls RBRACE 
            {
              Program RESULT = null;
		int I1left = ((java_cup.runtime.Symbol)CUP$MJParser$stack.elementAt(CUP$MJParser$top-4)).left;
		int I1right = ((java_cup.runtime.Symbol)CUP$MJParser$stack.elementAt(CUP$MJParser$top-4)).right;
		String I1 = (String)((java_cup.runtime.Symbol) CUP$MJParser$stack.elementAt(CUP$MJParser$top-4)).value;
		int D2left = ((java_cup.runtime.Symbol)CUP$MJParser$stack.elementAt(CUP$MJParser$top-3)).left;
		int D2right = ((java_cup.runtime.Symbol)CUP$MJParser$stack.elementAt(CUP$MJParser$top-3)).right;
		DeclarationsList D2 = (DeclarationsList)((java_cup.runtime.Symbol) CUP$MJParser$stack.elementAt(CUP$MJParser$top-3)).value;
		int M3left = ((java_cup.runtime.Symbol)CUP$MJParser$stack.elementAt(CUP$MJParser$top-1)).left;
		int M3right = ((java_cup.runtime.Symbol)CUP$MJParser$stack.elementAt(CUP$MJParser$top-1)).right;
		MethodDecls M3 = (MethodDecls)((java_cup.runtime.Symbol) CUP$MJParser$stack.elementAt(CUP$MJParser$top-1)).value;
		 RESULT=new Program(I1, D2, M3); RESULT.setLine(I1left); 
              CUP$MJParser$result = new java_cup.runtime.Symbol(1/*Program*/, ((java_cup.runtime.Symbol)CUP$MJParser$stack.elementAt(CUP$MJParser$top-5)).left, ((java_cup.runtime.Symbol)CUP$MJParser$stack.elementAt(CUP$MJParser$top-0)).right, RESULT);
            }
          return CUP$MJParser$result;

          /* . . . . . .*/
          default:
            throw new Exception(
               "Invalid action number found in internal parse table");

        }
    }
}

