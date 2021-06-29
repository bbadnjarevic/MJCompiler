package rs.ac.bg.etf.pp1;

import rs.etf.pp1.symboltable.Tab;
import rs.etf.pp1.symboltable.concepts.*;
import rs.etf.pp1.symboltable.visitors.DumpSymbolTableVisitor;
import rs.etf.pp1.symboltable.visitors.SymbolTableVisitor;

public class Table extends Tab {
	public static final Struct boolType = new Struct(Struct.Bool);
	
	public static void init() {
		Tab.init();
		
		Tab.currentScope.addToLocals(new Obj(Obj.Type, "bool", boolType));
	}
	
	public static Obj insert(int kind, String name, int adr, Struct type) {
		Obj objNode = Table.insert(kind, name, type);
		objNode.setAdr(adr);
		return objNode;
	}
	
	public static Obj findInCurrentScope(String name) {
		Obj resultObj = null;
		if (Tab.currentScope != null) {
			if (Tab.currentScope.getLocals() != null) {
				resultObj = Tab.currentScope.getLocals().searchKey(name);
			}
		}
		return (resultObj != null) ? resultObj : noObj;
	}
	
	/** Stampa sadrzaj tabele simbola. */
	public static void dump() {
		System.out.println("=====================SYMBOL TABLE DUMP=========================");
		SymbolTableVisitor stv = new MyTableDump();
		for (Scope s = currentScope; s != null; s = s.getOuter()) {
			s.accept(stv);
		}
		System.out.println(stv.getOutput());
	}
}
