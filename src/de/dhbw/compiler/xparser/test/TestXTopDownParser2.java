/* **********************************************
 * Duale Hochschule Baden-Württemberg Karlsruhe
 * Prof. Dr. Jörn Eisenbiegler
 * 
 * Vorlesung Übersetzerbau
 * Praxis X Abstiegsparser
 * - Testklasse Syntax X
 * 
 * **********************************************
 */

package de.dhbw.compiler.xparser.test;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestXTopDownParser2 extends ParseTreeTest {
	
	@Test
	public void program30Decl() throws Exception {
		String test =	"program decl;\n"+
						"  x: int;"+
						"begin\n"+
						"end.";
		String expected = "(APROGRAM program decl ; (DECLLIST (DECL MODIFIER x : (TYPE int) ;))"+
						  " (BLOCK begin STATLIST end) . )";
		testParseTree(test, expected);
	}


	@Test
	public void program31DeclRead() throws Exception {
		String test =	"program declRead;\n"+
						"  read x: int;"+
						"begin\n"+
						"end.";
		String expected = "(APROGRAM program declRead ; (DECLLIST (DECL (MODIFIER read) x : (TYPE int) ;))"+
				  		  " (BLOCK begin STATLIST end) . )";
		testParseTree(test, expected);
	}


	@Test
	public void program32DeclPrint() throws Exception {
		String test =	"program declPrint;\n"+
						"  print x: int;"+
						"begin\n"+
						"end.";
		String expected = "(APROGRAM program declPrint ; (DECLLIST (DECL (MODIFIER print) x : (TYPE int) ;))"+
				  		  " (BLOCK begin STATLIST end) . )";
		testParseTree(test, expected);
	}


	@Test
	public void program33DeclReadPrint() throws Exception {
		String test =	"program declReadPrint;\n"+
						"  read  print x: int;"+
						"begin\n"+
						"end.";
		String expected = "(APROGRAM program declReadPrint ; (DECLLIST (DECL (MODIFIER read print) x : (TYPE int) ;))"+
				  		  " (BLOCK begin STATLIST end) . )";
		testParseTree(test, expected);
	}

	@Test
	public void program34DeclMult() throws Exception {
		String test =	"program declMult;\n"+
						"  read  print a: int;"+
						"  b: int;"+
						"  read c: int;"+
						"  print d: int;"+
						"begin\n"+
						"end.";
		String expected = "(APROGRAM program declMult ; (DECLLIST (DECL (MODIFIER read print) a : (TYPE int) ;)" +
				" (DECL MODIFIER b : (TYPE int) ;)" +
				" (DECL (MODIFIER read) c : (TYPE int) ;)" +
				" (DECL (MODIFIER print) d : (TYPE int) ;)" +
				") (BLOCK begin STATLIST end) . )";
		testParseTree(test, expected);
	}

	@Test
	public void program35DeclFloatString() throws Exception {
		String test =	"program decl;\n"+
						"  x: float;"+
						"  y: string;"+
						"begin\n"+
						"end.";
		String expected = "(APROGRAM program decl ; (DECLLIST (DECL MODIFIER x : (TYPE float) ;) (DECL MODIFIER y : (TYPE string) ;))"+
						  " (BLOCK begin STATLIST end) . )";
		testParseTree(test, expected);
	}


	@Test
	public void program36DeclReadFloatString() throws Exception {
		String test =	"program declRead;\n"+
						"  read x: float;"+
						"  read y: string;"+
						"begin\n"+
						"end.";
		String expected = "(APROGRAM program declRead ; (DECLLIST (DECL (MODIFIER read) x : (TYPE float) ;) "+
				 		  "(DECL (MODIFIER read) y : (TYPE string) ;))"+
				  		  " (BLOCK begin STATLIST end) . )";
		testParseTree(test, expected);
	}


	@Test
	public void program37DeclPrintFloatString() throws Exception {
		String test =	"program declRead;\n"+
						"  print x: float;"+
						"  print y: string;"+
						"begin\n"+
						"end.";
		String expected = "(APROGRAM program declRead ; (DECLLIST (DECL (MODIFIER print) x : (TYPE float) ;) "+
				 		  "(DECL (MODIFIER print) y : (TYPE string) ;))"+
				  		  " (BLOCK begin STATLIST end) . )";
		testParseTree(test, expected);
	}


	@Test
	public void program38DeclReadPrintFloatString() throws Exception {
		String test =	"program declRead;\n"+
						"  read print x: float;"+
						"  read print y: string;"+
						"begin\n"+
						"end.";
		String expected = "(APROGRAM program declRead ; (DECLLIST (DECL (MODIFIER read print) x : (TYPE float) ;) "+
				 		  "(DECL (MODIFIER read print) y : (TYPE string) ;))"+
				  		  " (BLOCK begin STATLIST end) . )";
		testParseTree(test, expected);
	}

	@Test
	public void program40FloatAssign() throws Exception {
		String test =	"program exprAll;\n"+
						"  x: float;"+
						"begin\n"+
						"	x :=1.23e-45;"+
						"end.";
		String expected = "(APROGRAM program exprAll ; (DECLLIST (DECL MODIFIER x : (TYPE float) ;))"+
						  " (BLOCK begin (STATLIST (STATWITHSEMI (STAT "+
						  "(ASSIGNSTAT x := (EXPR (EXPR2 (EXPR3 1.23e-45))))) ;)) end) . )";
		testParseTree(test, expected);
	}
	
	@Test
	public void program41FloatExpr1() throws Exception {
		String test =	"program exprAll;\n"+
						"  x: float;"+
						"begin\n"+
						"	x :=0.0*1.1+2/-3.4e-6*(4.4-5+6.5)*7.4;"+
						"end.";
		String expected = "(APROGRAM program exprAll ; (DECLLIST (DECL MODIFIER x : (TYPE float) ;))"+
						  " (BLOCK begin (STATLIST (STATWITHSEMI (STAT "+
						  "(ASSIGNSTAT x := (EXPR (EXPR2 (EXPR3 0.0) * (EXPR2 (EXPR3 1.1))) + (EXPR (EXPR2 (EXPR3 2) "+
						  "/ (EXPR2 (EXPR3 - 3.4e-6) * (EXPR2 (EXPR3 ( (EXPR (EXPR2 (EXPR3 4.4)) - (EXPR (EXPR2 (EXPR3 5)) "+
						  "+ (EXPR (EXPR2 (EXPR3 6.5))))) )) * (EXPR2 (EXPR3 7.4))))))))) ;)) end) . )";
		testParseTree(test, expected);
	}
	
	@Test
	public void program42FloatExpr2() throws Exception {
		String test =	"program exprAll;\n"+
						"  x: float;"+
						"begin\n"+
						"	x :=0.0+-1.2--2.3*-4.5/-6.7;"+
						"end.";
		String expected = "(APROGRAM program exprAll ; (DECLLIST (DECL MODIFIER x : (TYPE float) ;))"+
						  " (BLOCK begin (STATLIST (STATWITHSEMI (STAT "+
						  "(ASSIGNSTAT x := (EXPR (EXPR2 (EXPR3 0.0)) + (EXPR (EXPR2 (EXPR3 - 1.2)) - (EXPR (EXPR2 (EXPR3 - 2.3) *"+
						  " (EXPR2 (EXPR3 - 4.5) / (EXPR2 (EXPR3 - 6.7))))))))) ;)) end) . )";
		testParseTree(test, expected);
	}
	

	@Test
	public void program43StringAssign() throws Exception {
		String test =	"program exprAll;\n"+
						"  x: string;"+
						"begin\n"+
						"	x :=\"Hello\";"+
						"end.";
		String expected = "(APROGRAM program exprAll ; (DECLLIST (DECL MODIFIER x : (TYPE string) ;))"+
						  " (BLOCK begin (STATLIST (STATWITHSEMI (STAT "+
						  "(ASSIGNSTAT x := (EXPR (EXPR2 (EXPR3 \"Hello\"))))) ;)) end) . )";
		testParseTree(test, expected);
	}
	
	@Test
	public void program44StringExpr1() throws Exception {
		String test =	"program exprAll;\n"+
						"  x: string;"+
						"begin\n"+
						"	x := x + \"Hello\";"+
						"end.";
		String expected = "(APROGRAM program exprAll ; (DECLLIST (DECL MODIFIER x : (TYPE string) ;))"+
						  " (BLOCK begin (STATLIST (STATWITHSEMI (STAT "+
						  "(ASSIGNSTAT x := (EXPR (EXPR2 (EXPR3 x)) + (EXPR (EXPR2 (EXPR3 \"Hello\")))))) ;)) end) . )";
		testParseTree(test, expected);
	}
	
	@Test
	public void program50LoopWhile1() throws Exception {
		String test =	"program exprAll;\n"+
						"  x: string;"+
						"begin\n"+
						"	while (2<x) x:= x+1;"+
						"end.";
		String expected = "(APROGRAM program exprAll ; (DECLLIST (DECL MODIFIER x : (TYPE string) ;)) (BLOCK begin (STATLIST "
				+ "(STATWITHSEMI (STAT (WHILESTAT while ( (COND (EXPR (EXPR2 (EXPR3 2))) < (EXPR (EXPR2 (EXPR3 x)))) )"
				+ " (STAT (ASSIGNSTAT x := (EXPR (EXPR2 (EXPR3 x)) + (EXPR (EXPR2 (EXPR3 1)))))))) ;)) end) . )";
		testParseTree(test, expected);
	}
	
	@Test
	public void program51LoopWhile2() throws Exception {
		String test =	"program exprAll;\n"+
						"  x: string;"+
						"begin\n"+
						"	while (2<x) begin "+
						"		x:= x+1;"+
						"		x:= x+1;"+
						"   end;"+
						"end.";
		String expected = "(APROGRAM program exprAll ; (DECLLIST (DECL MODIFIER x : (TYPE string) ;)) (BLOCK begin (STATLIST "
				+ "(STATWITHSEMI (STAT (WHILESTAT while ( (COND (EXPR (EXPR2 (EXPR3 2))) < (EXPR (EXPR2 (EXPR3 x)))) )"
				+ " (STAT (BLOCK begin (STATLIST "
				+ "(STATWITHSEMI (STAT (ASSIGNSTAT x := (EXPR (EXPR2 (EXPR3 x)) + (EXPR (EXPR2 (EXPR3 1)))))) ;) "
				+ "(STATWITHSEMI (STAT (ASSIGNSTAT x := (EXPR (EXPR2 (EXPR3 x)) + (EXPR (EXPR2 (EXPR3 1)))))) ;)) end)))) ;)) end) . )";
		testParseTree(test, expected);
	}
	
	@Test
	public void program52LoopFor1() throws Exception {
		String test =	"program exprAll;\n"+
						"  x: string;"+
						"  y: string;"+
						"begin\n"+
						"	for (x:=1; x<10; x:=x+1) y:= y+1;"+
						"end.";
		String expected = "(APROGRAM program exprAll ; (DECLLIST (DECL MODIFIER x : (TYPE string) ;) "
				+ "(DECL MODIFIER y : (TYPE string) ;)) (BLOCK begin (STATLIST "
				+ "(STATWITHSEMI (STAT (FORSTAT for ( (ASSIGNSTAT x := (EXPR (EXPR2 (EXPR3 1)))) ; "
				+ "(COND (EXPR (EXPR2 (EXPR3 x))) < (EXPR (EXPR2 (EXPR3 10)))) ; "
				+ "(ASSIGNSTAT x := (EXPR (EXPR2 (EXPR3 x)) + (EXPR (EXPR2 (EXPR3 1))))) ) "
				+ "(STAT (ASSIGNSTAT y := (EXPR (EXPR2 (EXPR3 y)) + (EXPR (EXPR2 (EXPR3 1)))))))) ;)) end) . )";
		testParseTree(test, expected);
	}
	
	@Test
	public void program53LoopFor2() throws Exception {
		String test =	"program exprAll;\n"+
						"  x: string;"+
						"  y: string;"+
						"begin\n"+
						"	for (x:=1; x<10; x:=x+1) begin y:= y+1; end;"+
						"end.";
		String expected = "(APROGRAM program exprAll ; (DECLLIST (DECL MODIFIER x : (TYPE string) ;) "
				+ "(DECL MODIFIER y : (TYPE string) ;)) (BLOCK begin (STATLIST "
				+ "(STATWITHSEMI (STAT (FORSTAT for ( (ASSIGNSTAT x := (EXPR (EXPR2 (EXPR3 1)))) ; "
				+ "(COND (EXPR (EXPR2 (EXPR3 x))) < (EXPR (EXPR2 (EXPR3 10)))) ; "
				+ "(ASSIGNSTAT x := (EXPR (EXPR2 (EXPR3 x)) + (EXPR (EXPR2 (EXPR3 1))))) ) (STAT "
				+ "(BLOCK begin (STATLIST (STATWITHSEMI (STAT "
				+ "(ASSIGNSTAT y := (EXPR (EXPR2 (EXPR3 y)) + (EXPR (EXPR2 (EXPR3 1)))))) ;)) end)))) ;)) end) . )";
		testParseTree(test, expected);
	}
	
	
	@Test
	public void program91BeispielFolien() throws Exception {
		String test =	"program test5;\n"+
				"	read  x : int;\n"+
				"	print y : float;\n"+
				"	z : int;\n"+
				"begin\n"+
				"	while (x<4) begin\n"+
				"		for (z:=0; z<4; z:=z+1) x:=x+2;\n"+
				"		if x=4 then begin\n"+
				"			x:=z*(x+2);\n"+
				"			x:=x+10;\n"+
				"		end else y:=100.e-3;\n"+
				"	end;\n"+
				"end.\n";
		String expected = "(APROGRAM program test5 ; "
				+ "(DECLLIST (DECL (MODIFIER read) x : (TYPE int) ;) (DECL (MODIFIER print) y : (TYPE float) ;) "
				+ "(DECL MODIFIER z : (TYPE int) ;)) "
				+ "(BLOCK begin (STATLIST (STATWITHSEMI (STAT "
				+ "(WHILESTAT while ( (COND (EXPR (EXPR2 (EXPR3 x))) < (EXPR (EXPR2 (EXPR3 4)))) ) (STAT "
				+ "(BLOCK begin (STATLIST (STATWITHSEMI (STAT "
				+ "(FORSTAT for ( (ASSIGNSTAT z := (EXPR (EXPR2 (EXPR3 0)))) ; "
				+ "(COND (EXPR (EXPR2 (EXPR3 z))) < (EXPR (EXPR2 (EXPR3 4)))) ; "
				+ "(ASSIGNSTAT z := (EXPR (EXPR2 (EXPR3 z)) + (EXPR (EXPR2 (EXPR3 1))))) ) "
				+ "(STAT (ASSIGNSTAT x := (EXPR (EXPR2 (EXPR3 x)) + (EXPR (EXPR2 (EXPR3 2)))))))) ;) "
				+ "(STATWITHSEMI (STAT (CONDSTAT if (COND (EXPR (EXPR2 (EXPR3 x))) = (EXPR (EXPR2 (EXPR3 4)))) "
				+ "then (STAT (BLOCK begin (STATLIST (STATWITHSEMI (STAT "
				+ "(ASSIGNSTAT x := (EXPR (EXPR2 (EXPR3 z) * (EXPR2 (EXPR3 ( (EXPR (EXPR2 (EXPR3 x)) + "
				+ "(EXPR (EXPR2 (EXPR3 2)))) ))))))) ;) "
				+ "(STATWITHSEMI (STAT (ASSIGNSTAT x := (EXPR (EXPR2 (EXPR3 x)) + (EXPR (EXPR2 (EXPR3 10)))))) ;)) end)) "
				+ "else (STAT (ASSIGNSTAT y := (EXPR (EXPR2 (EXPR3 100.e-3))))))) ;)) end)))) ;)) end) . )";
		testParseTree(test, expected);
	}

	
}
