/* **********************************************
 * Duale Hochschule Baden-Württemberg Karlsruhe
 * Prof. Dr. Jörn Eisenbiegler
 * 
 * Vorlesung Übersetzerbau
 * Praxis AS-Scanner mit JFlex
 * - Testfälle Aufgabe 1
 * 
 * **********************************************
 */

package de.dhbw.compiler.jflexasscanner;

import org.junit.Test;

public class TestASScanner2 extends TokenStreamTest {
	
	@Test
	public void token1_1() throws Exception {
		testTokenList("  1.1 ", false, new Token(Token.FRAC,"1.1",1,3));
	}

	@Test
	public void token1_() throws Exception {
		testTokenList("  1. ", false, new Token(Token.FRAC,"1.",1,3));
	}
	
	@Test
	public void token_1() throws Exception {
		testTokenList("  .1 ", false, 
				new Token(Token.INVALID,".",1,3), 
				new Token(Token.NUM,"1",1,4));
	}
	
	@Test
	public void token1_1_1() throws Exception {
		testTokenList("  1.1^1 ", false, new Token(Token.FRAC,"1.1^1",1,3));
	}

	@Test
	public void token1__1() throws Exception {
		testTokenList("  1.^1 ", false, new Token(Token.FRAC,"1.^1",1,3));
	}

	@Test
	public void token0120_01230_01230() throws Exception {
		testTokenList("  01234567890.01234567890^01234567890 ", false, 
				new Token(Token.FRAC,"01234567890.01234567890^01234567890",1,3));
	}

	@Test
	public void token0120_01230_() throws Exception {
		testTokenList("  01230.01230^ ", false, 
				new Token(Token.FRAC,"01230.01230",1,3), 
				new Token(Token.INVALID,"^",1,14));
	}

	@Test
	public void token0120_0abc() throws Exception {
		testTokenList("  01230.0abc",false, 
				new Token(Token.FRAC,"01230.0",1,3),
				new Token(Token.ID,"abc",1,10));
	}

	@Test
	public void token0120_abc() throws Exception {
		testTokenList("  01230.abc", false, 
				new Token(Token.FRAC,"01230.",1,3),
				new Token(Token.ID,"abc",1,9));
	}

	@Test
	public void token0120_0_45LSBR() throws Exception {
		testTokenList("  01230.0^45[", false, 			
				new Token(Token.FRAC,"01230.0^45",1,3),
				new Token(Token.LSBR,"[",1,13));
	}

	@Test
	public void sentence1() throws Exception {
		testTokenList("[ hallo, 23.45, 4., 5.^56, 0.0^00, 78]", false, 
				new Token(Token.LSBR,"[",1,1),
				new Token(Token.ID,"hallo",1,3),
				new Token(Token.COMMA,",",1,8),
				new Token(Token.FRAC,"23.45",1,10),
				new Token(Token.COMMA,",",1,15),
				new Token(Token.FRAC,"4.",1,17),
				new Token(Token.COMMA,",",1,19),
				new Token(Token.FRAC,"5.^56",1,21),
				new Token(Token.COMMA,",",1,26),
				new Token(Token.FRAC,"0.0^00",1,28),
				new Token(Token.COMMA,",",1,34),
				new Token(Token.NUM,"78",1,36),
				new Token(Token.RSBR,"]",1,38));
	}

}
