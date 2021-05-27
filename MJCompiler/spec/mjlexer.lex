
package rs.ac.bg.etf.pp1;

import java_cup.runtime.Symbol;

%%

%{

	// ukljucivanje informacije o poziciji tokena
	private Symbol new_symbol(int type) {
		error_handler();
		return new Symbol(type, yyline+1, yycolumn);
	}
	
	// ukljucivanje informacije o poziciji tokena
	private Symbol new_symbol(int type, Object value) {
		error_handler();
		return new Symbol(type, yyline+1, yycolumn, value);
	}
	
	private void error_handler() {
		if (error_string.length() > 0) {
			System.err.println("Leksicka greska (" + error_string + ") u liniji "+(error_line) + " na poziciji: " + (error_column+1 - error_string.length()));
			error_string = "";
		}
	}
	String error_string = "";
	int error_line, error_column;
	boolean errors = false;

%}

%cup
%line
%column

%xstate COMMENT

%eofval{
	return new_symbol(sym.EOF);
%eofval}

%%

// White spaces
" " 	{ }
"\b" 	{ }
"\t" 	{ }
"\r\n" 	{ }
"\f" 	{ }


// Keywords
"program"   { return new_symbol(sym.PROG, yytext());}
"break"		{ return new_symbol(sym.BREAK, yytext()); }
"class"		{ return new_symbol(sym.CLASS, yytext()); }
"enum"		{ return new_symbol(sym.ENUM, yytext()); }
"else"		{ return new_symbol(sym.ELSE, yytext()); }
"const"		{ return new_symbol(sym.CONST, yytext()); }
"if"		{ return new_symbol(sym.IF, yytext()); }
"switch"	{ return new_symbol(sym.SWITCH, yytext()); }
"do"		{ return new_symbol(sym.DO, yytext()); }
"while" 	{ return new_symbol(sym.WHILE, yytext()); }
"new" 		{ return new_symbol(sym.NEW, yytext()); }
"print" 	{ return new_symbol(sym.PRINT, yytext()); }
"read" 		{ return new_symbol(sym.READ, yytext()); }
"return" 	{ return new_symbol(sym.RETURN, yytext()); }
"void" 		{ return new_symbol(sym.VOID, yytext()); }
"extends" 	{ return new_symbol(sym.EXTENDS, yytext()); }
"continue" 	{ return new_symbol(sym.CONTINUE, yytext()); }
"case" 		{ return new_symbol(sym.CASE, yytext()); }
"yield"		{ return new_symbol(sym.YIELD, yytext()); }


// Tokens
("true"|"false")				{ return new_symbol(sym.BOOL_CONST, yytext()); }
[0-9]+  						{ return new_symbol(sym.NUM_CONST, new Integer (yytext())); }
'.'								{ return new_symbol(sym.CHAR_CONST, yytext()); }
([a-z]|[A-Z])[a-zA-Z0-9_]* 		{ return new_symbol(sym.IDENT, yytext()); }


// Operators
"+" 		{ return new_symbol(sym.PLUS, yytext()); }
"-" 		{ return new_symbol(sym.MINUS, yytext()); }
"*"			{ return new_symbol(sym.MUL, yytext()); }
"/"			{ return new_symbol(sym.DIV, yytext()); }
"%"			{ return new_symbol(sym.MOD, yytext()); }
"=="		{ return new_symbol(sym.EQ, yytext()); }
"!="		{ return new_symbol(sym.NEQ, yytext()); }
">"			{ return new_symbol(sym.GT, yytext()); }
">="		{ return new_symbol(sym.GTE, yytext()); }
"<"			{ return new_symbol(sym.LT, yytext()); }
"<="		{ return new_symbol(sym.LTE, yytext()); }
"&&"		{ return new_symbol(sym.AND, yytext()); }
"||"		{ return new_symbol(sym.OR, yytext()); }
"=" 		{ return new_symbol(sym.EQUAL, yytext()); }
"++"		{ return new_symbol(sym.INC, yytext()); }
"--"		{ return new_symbol(sym.DEC, yytext()); }
";" 		{ return new_symbol(sym.SEMI, yytext()); }
"," 		{ return new_symbol(sym.COMMA, yytext()); }
"."			{ return new_symbol(sym.DOT, yytext()); }
"(" 		{ return new_symbol(sym.LPAREN, yytext()); }
")" 		{ return new_symbol(sym.RPAREN, yytext()); }
"{" 		{ return new_symbol(sym.LBRACE, yytext()); }
"}"			{ return new_symbol(sym.RBRACE, yytext()); }
"[" 		{ return new_symbol(sym.LSQUARE, yytext()); }
"]"			{ return new_symbol(sym.RSQUARE, yytext()); }
"?"			{ return new_symbol(sym.QUESTION, yytext()); }
":"			{ return new_symbol(sym.COLON, yytext()); }


// Comments
"//" 				{ yybegin(COMMENT); }
<COMMENT> . 		{ yybegin(COMMENT); }
<COMMENT> "\r\n"	{ yybegin(YYINITIAL); }


// Error
.	{ errors = true; error_string += yytext(); error_line = yyline+1; error_column = yycolumn; }


