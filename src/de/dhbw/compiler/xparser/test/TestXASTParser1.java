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
public class TestXASTParser1 extends ParseTreeTest {
	
	@Test
	public void program00BeginEnd() throws Exception {
		String test =	"program beginEnd;\n"+
						"begin\n"+
						"end.";
		String expected = "(program beginEnd DECLLIST STATLIST)";
		testParseTree(test, expected);
	}

	@Test
	public void program01BeginEndMore() throws Exception {
		String test =	"program beginEndMore;\n"+
						"begin\n"+
						"end. falsch";
		String expected = null;
		testParseTree(test, expected);
	}
	
	@Test
	public void program02BeginEnd2() throws Exception {
		String test =	"program beginEnd2;\n"+
						"begin\n"+
						"	begin\n"+
						"	end;"+
						"end.";
		String expected = "(program beginEnd2 DECLLIST (STATLIST STATLIST))";
		testParseTree(test, expected);
	}

	
	@Test
	public void program10Assign() throws Exception {
		String test =	"program assign;\n"+
						"  x: int;"+
						"begin\n"+
						"	x :=0;"+
						"end.";
		String expected = "(program assign (DECLLIST (DECL x int)) (STATLIST (:= x 0)))";
		testParseTree(test, expected);
	}

	@Test
	public void program11ExprPlus() throws Exception {
		String test =	"program exprPlus;\n"+
						"  x: int;"+
						"begin\n"+
						"	x :=0+1;"+
						"end.";
		String expected = "(program exprPlus (DECLLIST (DECL x int)) (STATLIST (:= x (+ 0 1))))";
		testParseTree(test, expected);
	}

	@Test
	public void program12ExprMinus() throws Exception {
		String test =	"program exprMinus;\n"+
						"  x: int;"+
						"begin\n"+
						"	x :=0-1;"+
						"end.";
		String expected = "(program exprMinus (DECLLIST (DECL x int)) (STATLIST (:= x (- 0 1))))";
		testParseTree(test, expected);
	}

	@Test
	public void program13ExprMul() throws Exception {
		String test =	"program exprMul;\n"+
						"  x: int;"+
						"begin\n"+
						"	x :=0*1;"+
						"end.";
		String expected = "(program exprMul (DECLLIST (DECL x int)) (STATLIST (:= x (* 0 1))))";
		testParseTree(test, expected);
	}

	@Test
	public void program14ExprDiv() throws Exception {
		String test =	"program exprDiv;\n"+
						"  x: int;"+
						"begin\n"+
						"	x :=0/1;"+
						"end.";
		String expected = "(program exprDiv (DECLLIST (DECL x int)) (STATLIST (:= x (/ 0 1))))";
		testParseTree(test, expected);
	}

	@Test
	public void program15ExprUMinus() throws Exception {
		String test =	"program exprUMinus;\n"+
						"  x: int;"+
						"begin\n"+
						"	x :=0--1;"+
						"end.";
		String expected = "(program exprUMinus (DECLLIST (DECL x int)) (STATLIST (:= x (- 0 (- 1)))))";
		testParseTree(test, expected);
	}


	@Test
	public void program16ExprPlusMinus() throws Exception {
		String test =	"program exprPlus;\n"+
						"  x: int;"+
						"begin\n"+
						"	x :=0+1+-2-3+4;"+
						"end.";
		String expected = "(program exprPlus (DECLLIST (DECL x int)) "
				+ "(STATLIST (:= x (+ (- (+ (+ 0 1) (- 2)) 3) 4))))";
		testParseTree(test, expected);
	}

	@Test
	public void program17ExprMulDiv() throws Exception {
		String test =	"program exprMul;\n"+
						"  x: int;"+
						"begin\n"+
						"	x :=0*1/2*-3*4;"+
						"end.";
		String expected = "(program exprMul (DECLLIST (DECL x int)) "
				+ "(STATLIST (:= x (* (* (/ (* 0 1) 2) (- 3)) 4))))";
		testParseTree(test, expected);
	}

	@Test
	public void program18ExprAll() throws Exception {
		String test =	"program exprAll;\n"+
						"  x: int;"+
						"begin\n"+
						"	x :=0*1+2/-3*(4-5+6)*7;"+
						"end.";
		String expected = "(program exprAll (DECLLIST (DECL x int)) "
				+ "(STATLIST (:= x (+ (* 0 1) (* (* (/ 2 (- 3)) (+ (- 4 5) 6)) 7)))))";
		testParseTree(test, expected);
	}
	
	@Test
	public void program19ExprVar() throws Exception {
		String test =	"program exprAll;\n"+
						"  x: int;"+
						"  y: int;"+
						"  z: int;"+
						"begin\n"+
						"	x :=0*x+y;"+
						"end.";
		String expected = "(program exprAll (DECLLIST (DECL x int) (DECL y int) (DECL z int)) "
				+ "(STATLIST (:= x (+ (* 0 x) y))))";
		testParseTree(test, expected);
	}
	
	@Test
	public void program20Cond() throws Exception {
		String test =	"program cond;\n"+
						"  x: int;"+
						"begin\n"+
						"	if 2<3 then x:=1;"+
						"end.";
		String expected = "(program cond (DECLLIST (DECL x int)) (STATLIST (if (< 2 3) (:= x 1))))";
		testParseTree(test, expected);
	}

	@Test
	public void program21CondElse() throws Exception {
		String test =	"program condElse;\n"+
						"  x: int;"+
						"begin\n"+
						"	if 2>3 then x:=1 else x:=2;"+
						"end.";
		String expected = "(program condElse (DECLLIST (DECL x int)) (STATLIST (if (> 2 3) (:= x 1) (:= x 2))))";
		testParseTree(test, expected);
	}

	@Test
	public void program22CondElse2() throws Exception {
		String test =	"program condelse2;\n"+
						"  x: int;"+
						"begin\n"+
						"	if 2=3 then if 4<5 then x:=1 else x:=2 else x:=3;"+
						"end.";
		String expected = "(program condelse2 (DECLLIST (DECL x int)) "
				+ "(STATLIST (if (= 2 3) (if (< 4 5) (:= x 1) (:= x 2)) (:= x 3))))";
		testParseTree(test, expected);
	}

	
	@Test
	public void program23CondElse3() throws Exception {
		String test =	"program condelse3;\n"+
						"  x: int;"+
						"begin\n"+
						"	if 2<3 then if 4>5 then x:=1 else if 6=7 then x:=2 else x:=3;"+
						"end.";
		String expected = "(program condelse3 (DECLLIST (DECL x int)) "
				+ "(STATLIST (if (< 2 3) (if (> 4 5) (:= x 1) (if (= 6 7) (:= x 2) (:= x 3))))))";
		testParseTree(test, expected);
	}

	
	@Test
	public void program24CondElse4() throws Exception {
		String test =	"program condelse4;\n"+
						"  x: int;"+
						"begin\n"+
						"	if 2<3 then if 4>5 then x:=1 else x:=2 else if 6=7 then x:=3 else x:=4;"+
						"end.";
		String expected = "(program condelse4 (DECLLIST (DECL x int)) "
				+ "(STATLIST (if (< 2 3) (if (> 4 5) (:= x 1) (:= x 2)) (if (= 6 7) (:= x 3) (:= x 4)))))";
		testParseTree(test, expected);
	}

	
	@Test
	public void program90Xmin1() throws Exception {
		String test = "program xmin1;\n"+
				"	read x : int;\n"+
				"	print y : int;\n"+
				"begin\n"+
				"	y := 25+2*x-6*x;\n"+
				"   if x<y then \n"+
				"		begin\n" +
				"			x:= -3/6*(y+2);\n"+
				"   		if y<x then y:=y+3 else y:=y+4;\n"+
				"		end\n" +
				"	else\n" +
				"		begin\n" +
				"   		if y<x then y:=y+3;\n"+
				"			x:= y*(y-x)/y;\n"+
				"		end;\n" +
				"	y := x*y;\n"+
				"end.";
		String expected = "(program xmin1 (DECLLIST (DECL x int read) (DECL y int print)) "
				+ "(STATLIST (:= y (- (+ 25 (* 2 x)) (* 6 x))) (if (< x y) "
				+ "(STATLIST (:= x (* (/ (- 3) 6) (+ y 2))) (if (< y x) (:= y (+ y 3)) (:= y (+ y 4)))) "
				+ "(STATLIST (if (< y x) (:= y (+ y 3))) (:= x (/ (* y (- y x)) y)))) (:= y (* x y))))";
		testParseTree(test, expected);
	}

	
}
