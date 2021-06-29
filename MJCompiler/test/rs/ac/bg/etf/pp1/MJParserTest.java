package rs.ac.bg.etf.pp1;

import java.util.List;

import rs.ac.bg.etf.pp1.test.Compiler;
import rs.ac.bg.etf.pp1.test.CompilerError;

public class MJParserTest {

	public static void main(String[] args) {
		Compiler compiler = new MyCompiler();
		List<CompilerError> errors = compiler.compile("test/test302.mj", "test/program.obj");
		if (errors != null)
			for (CompilerError e : errors)
				System.out.println(e.toString());
	}
	
}
