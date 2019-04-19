/* **********************************************
 * Duale Hochschule Baden-Württemberg Karlsruhe
 * Prof. Dr. Jörn Eisenbiegler
 * 
 * Vorlesung Übersetzerbau
 * Praxis AST für X
 * - Testklasse Token
 * 
 * **********************************************
 */

package de.dhbw.compiler.xparser.test;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestXASTParser2 extends ParseTreeTest {
	
	@Test
	public void program30Decl() throws Exception {
		String test =	"program decl;\n"+
						"  x: int;"+
						"begin\n"+
						"end.";
		String expected = "(program decl (DECLLIST (DECL x int)) STATLIST)";
		testParseTree(test, expected);
	}


	@Test
	public void program31DeclRead() throws Exception {
		String test =	"program declRead;\n"+
						"  read x: int;"+
						"begin\n"+
						"end.";
		String expected = "(program declRead (DECLLIST (DECL x int read)) STATLIST)";
		testParseTree(test, expected);
	}


	@Test
	public void program32DeclPrint() throws Exception {
		String test =	"program declPrint;\n"+
						"  print x: int;"+
						"begin\n"+
						"end.";
		String expected = "(program declPrint (DECLLIST (DECL x int print)) STATLIST)";
		testParseTree(test, expected);
	}


	@Test
	public void program33DeclReadPrint() throws Exception {
		String test =	"program declReadPrint;\n"+
						"  read  print x: int;"+
						"begin\n"+
						"end.";
		String expected = "(program declReadPrint (DECLLIST (DECL x int read print)) STATLIST)";
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
		String expected = "(program declMult (DECLLIST (DECL a int read print) (DECL b int) (DECL c int read) (DECL d int print)) "
				+ "STATLIST)";
		testParseTree(test, expected);
	}

	@Test
	public void program35DeclFloatString() throws Exception {
		String test =	"program decl;\n"+
						"  x: float;"+
						"  y: string;"+
						"begin\n"+
						"end.";
		String expected = "(program decl (DECLLIST (DECL x float) (DECL y string)) STATLIST)";
		testParseTree(test, expected);
	}


	@Test
	public void program36DeclReadFloatString() throws Exception {
		String test =	"program declRead;\n"+
						"  read x: float;"+
						"  read y: string;"+
						"begin\n"+
						"end.";
		String expected = "(program declRead (DECLLIST (DECL x float read) (DECL y string read)) STATLIST)";
		testParseTree(test, expected);
	}


	@Test
	public void program37DeclPrintFloatString() throws Exception {
		String test =	"program declRead;\n"+
						"  print x: float;"+
						"  print y: string;"+
						"begin\n"+
						"end.";
		String expected = "(program declRead (DECLLIST (DECL x float print) (DECL y string print)) STATLIST)";
		testParseTree(test, expected);
	}


	@Test
	public void program38DeclReadPrintFloatString() throws Exception {
		String test =	"program declRead;\n"+
						"  read print x: float;"+
						"  read print y: string;"+
						"begin\n"+
						"end.";
		String expected = "(program declRead (DECLLIST (DECL x float read print) (DECL y string read print)) STATLIST)";
		testParseTree(test, expected);
	}

	@Test
	public void program40FloatAssign() throws Exception {
		String test =	"program exprAll;\n"+
						"  x: float;"+
						"begin\n"+
						"	x :=1.23e-45;"+
						"end.";
		String expected = "(program exprAll (DECLLIST (DECL x float)) (STATLIST (:= x 1.23e-45)))";
		testParseTree(test, expected);
	}
	
	@Test
	public void program41FloatExpr1() throws Exception {
		String test =	"program exprAll;\n"+
						"  x: float;"+
						"begin\n"+
						"	x :=0.0*1.1+2/-3.4e-6*(4.4-5+6.5)*7.4;"+
						"end.";
		String expected = "(program exprAll (DECLLIST (DECL x float)) "
				+ "(STATLIST (:= x (+ (* 0.0 1.1) (* (* (/ 2 (- 3.4e-6)) (+ (- 4.4 5) 6.5)) 7.4)))))";
		testParseTree(test, expected);
	}
	
	@Test
	public void program42FloatExpr2() throws Exception {
		String test =	"program exprAll;\n"+
						"  x: float;"+
						"begin\n"+
						"	x :=0.0+-1.2--2.3*-4.5/-6.7;"+
						"end.";
		String expected = "(program exprAll (DECLLIST (DECL x float)) "
				+ "(STATLIST (:= x (- (+ 0.0 (- 1.2)) (/ (* (- 2.3) (- 4.5)) (- 6.7))))))";
		testParseTree(test, expected);
	}
	

	@Test
	public void program43StringAssign() throws Exception {
		String test =	"program exprAll;\n"+
						"  x: string;"+
						"begin\n"+
						"  x :=\"Hello\";"+
						"end.";
		String expected = "(program exprAll (DECLLIST (DECL x string)) (STATLIST (:= x \"Hello\")))";
		testParseTree(test, expected);
	}
	
	@Test
	public void program44StringExpr1() throws Exception {
		String test =	"program exprAll;\n"+
						"  x: string;"+
						"begin\n"+
						"	x := x + \"Hello\";"+
						"end.";
		String expected = "(program exprAll (DECLLIST (DECL x string)) (STATLIST (:= x (+ x \"Hello\"))))";
		testParseTree(test, expected);
	}
	
	@Test
	public void program50LoopWhile1() throws Exception {
		String test =	"program exprAll;\n"+
						"  x: string;"+
						"begin\n"+
						"	while (2<x) x:= x+1;"+
						"end.";
		String expected = "(program exprAll (DECLLIST (DECL x string)) (STATLIST (while (< 2 x) (:= x (+ x 1)))))";
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
		String expected = "(program exprAll (DECLLIST (DECL x string)) "
				+ "(STATLIST (while (< 2 x) (STATLIST (:= x (+ x 1)) (:= x (+ x 1))))))";
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
		String expected = "(program exprAll (DECLLIST (DECL x string) (DECL y string)) "
				+ "(STATLIST (for (:= x 1) (< x 10) (:= x (+ x 1)) (:= y (+ y 1)))))";
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
		String expected = "(program exprAll (DECLLIST (DECL x string) (DECL y string)) "
				+ "(STATLIST (for (:= x 1) (< x 10) (:= x (+ x 1)) (STATLIST (:= y (+ y 1))))))";
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
		String expected = "(program test5 (DECLLIST (DECL x int read) (DECL y float print) (DECL z int)) "
				+ "(STATLIST (while (< x 4) (STATLIST (for (:= z 0) (< z 4) (:= z (+ z 1)) (:= x (+ x 2))) (if (= x 4) "
				+ "(STATLIST (:= x (* z (+ x 2))) (:= x (+ x 10))) (:= y 100.e-3))))))";
		testParseTree(test, expected);
	}

	
}
