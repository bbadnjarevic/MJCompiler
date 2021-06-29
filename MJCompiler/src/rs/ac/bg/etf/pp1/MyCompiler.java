package rs.ac.bg.etf.pp1;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;

import java_cup.runtime.Symbol;
import rs.ac.bg.etf.pp1.ast.Program;
import rs.ac.bg.etf.pp1.test.Compiler;
import rs.ac.bg.etf.pp1.test.CompilerError;
import rs.ac.bg.etf.pp1.util.Log4JUtils;
import rs.etf.pp1.mj.runtime.Code;

public class MyCompiler implements Compiler {

	static {
		DOMConfigurator.configure(Log4JUtils.instance().findLoggerConfigFile());
		Log4JUtils.instance().prepareLogFile(Logger.getRootLogger());
	}

	@Override
	public List<CompilerError> compile(String sourceFilePath, String outputFilePath) {
		
		Logger log = Logger.getLogger(MJParserTest.class);
		List<CompilerError> errors = new LinkedList<>();
		
		Reader br = null;
		try {
			File sourceCode = new File(sourceFilePath);
			log.info("Compiling source file: " + sourceCode.getAbsolutePath());
			
			br = new BufferedReader(new FileReader(sourceCode));
			Yylex lexer = new Yylex(br);
			
			MJParser p = new MJParser(lexer);
			

	        Program prog = null;
	        try {
	        	Symbol s = p.parse();  //pocetak parsiranja
	        	prog = (Program)(s.value); 
	        } catch(ClassCastException e) {
	        	return p.getErrors();
	        }
	        Table.init();
	        
			// ispis sintaksnog stabla
			log.info(prog.toString(""));
			log.info("===================================");

			// ispis prepoznatih programskih konstrukcija
			SemanticAnalyzer v = new SemanticAnalyzer();
			prog.traverseBottomUp(v); 
			
			// ispis tabele simbola
			log.info("===================================");
			Table.dump();
			
			if(p.errorDetected || !v.passed()){
				log.error("Parsiranje NIJE uspesno zavrseno!");
				errors.addAll(lexer.getErrors());
				errors.addAll(p.getErrors());
				errors.addAll(v.getErrors());
				return errors.isEmpty() ? null : errors;
			}
			
			log.info("Parsiranje uspesno zavrseno!");
			
			File objFile = new File(outputFilePath);
			if(objFile.exists()) objFile.delete();
			
			CodeGenerator codeGenerator = new CodeGenerator();
			prog.traverseBottomUp(codeGenerator);
			Code.dataSize = v.nVars;
			Code.mainPc = codeGenerator.getMainPc();
			Code.write(new FileOutputStream(objFile));
			
			log.info("Zavrseno generisanje koda!");
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} 
		finally {
			if (br != null) try { br.close(); } catch (IOException e1) { log.error(e1.getMessage(), e1); }
		}
		
		return null;
	}

}
