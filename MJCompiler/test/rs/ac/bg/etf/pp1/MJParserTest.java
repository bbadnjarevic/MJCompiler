package rs.ac.bg.etf.pp1;

import rs.ac.bg.etf.pp1.test.Compiler;

public class MJParserTest {

	
	public static void main(String[] args) {
		Compiler compiler = new MyCompiler();
		compiler.compile("test/test302.mj", "test/program.obj");
	}
	
}
