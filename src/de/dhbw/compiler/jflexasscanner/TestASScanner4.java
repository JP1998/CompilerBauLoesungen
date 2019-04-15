/* **********************************************
 * Duale Hochschule Baden-Württemberg Karlsruhe
 * Prof. Dr. Jörn Eisenbiegler
 * 
 * Vorlesung Übersetzerbau
 * Praxis AS-Scanner mit JFlex
 * - Testfälle Aufgabe 3
 * 
 * **********************************************
 */

package de.dhbw.compiler.jflexasscanner;

import org.junit.Test;

public class TestASScanner4 extends TokenStreamTest {
	
	@Test
	public void tokenTo() throws Exception {
		testTokenList("  .. ", false, new Token(Token.TO,"..",1,3));
	}

	@Test
	public void tokenNumToNum() throws Exception {
		testTokenList("  4 .. 5 ", false, 
				new Token(Token.NUM,"4",1,3), 
				new Token(Token.TO,"..",1,5), 
				new Token(Token.NUM,"5",1,8));
		testTokenList("  4..5 ", false, 
				new Token(Token.NUM,"4",1,3), 
				new Token(Token.TO,"..",1,4), 
				new Token(Token.NUM,"5",1,6));
		testTokenList("  4......5 ", false, 
				new Token(Token.NUM,"4",1,3), 
				new Token(Token.TO,"..",1,4), 
				new Token(Token.TO,"..",1,6), 
				new Token(Token.TO,"..",1,8), 
				new Token(Token.NUM,"5",1,10));
	}

	@Test
	public void tokenFracToNum() throws Exception {
		testTokenList("  4. .. 5 ", false, 
				new Token(Token.FRAC,"4.",1,3), 
				new Token(Token.TO,"..",1,6), 
				new Token(Token.NUM,"5",1,9));
		testTokenList("  4.5 ", false, new Token(Token.FRAC,"4.5",1,3));
		testTokenList("  4...5 ", false, 
				new Token(Token.FRAC,"4.",1,3), 
				new Token(Token.TO,"..",1,5), 
				new Token(Token.NUM,"5",1,7));
		testTokenList("  4.......5 ", false, 
				new Token(Token.FRAC,"4.",1,3), 
				new Token(Token.TO,"..",1,5), 
				new Token(Token.TO,"..",1,7), 
				new Token(Token.TO,"..",1,9), 
				new Token(Token.NUM,"5",1,11));
	}

}
