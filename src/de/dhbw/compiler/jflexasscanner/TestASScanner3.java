/* **********************************************
 * Duale Hochschule Baden-Württemberg Karlsruhe
 * Prof. Dr. Jörn Eisenbiegler
 * 
 * Vorlesung Übersetzerbau
 * Praxis AS-Scanner mit JFlex
 * - Testfälle Aufgabe 2
 * 
 * **********************************************
 */

package de.dhbw.compiler.jflexasscanner;

import org.junit.Test;

public class TestASScanner3 extends TokenStreamTest {
	
	@Test
	public void tokenInt() throws Exception {
		testTokenList("  0 ", true, new Token(Token.NUM,"0",1,3));
		testTokenList("  00 ", true, new Token(Token.NUM,"00",1,3));
		testTokenList("  1 ", true, new Token(Token.NUM,"1",1,3));
		testTokenList("  12 ", true, new Token(Token.NUM,"12",1,3));
		testTokenList("  123 ", true, new Token(Token.NUM,"123",1,3));
		testTokenList("  1234567890 ", true, new Token(Token.NUM,"1234567890",1,3));
		testTokenList("  078 ", true, new Token(Token.NUM,"078",1,3));
		testTokenList("  004500 ", true, new Token(Token.NUM,"004500",1,3));
	}

	@Test
	public void tokenFrac0() throws Exception {
		testTokenList("  0. ", true, new Token(Token.FRAC,"0.",1,3));
		testTokenList("  0.0 ", true, new Token(Token.FRAC,"0.0",1,3));
		testTokenList("  0.0^0 ", true, new Token(Token.FRAC,"0.0^0",1,3));
	}
	
	@Test
	public void tokenFrac1() throws Exception {
		testTokenList("  1. ", true, new Token(Token.FRAC,"1.",1,3));
		testTokenList("  1.1 ", true, new Token(Token.FRAC,"1.1",1,3));
		testTokenList("  1.1^1 ", true, new Token(Token.FRAC,"1.1^1",1,3));
	}
	
	@Test
	public void tokenFrac123() throws Exception {
		testTokenList("  0.12^34 ", true, new Token(Token.FRAC,"0.12^34",1,3));
		testTokenList("  0.045^0023 ", true, new Token(Token.FRAC,"0.045^0023",1,3));
		testTokenList("  123.4560^7890 ", true, new Token(Token.FRAC,"123.4560^7890",1,3));
	}
	
	@Test
	public void tokenID() throws Exception {
		testTokenList("  012abc ", true, new Token(Token.ID,"012abc",1,3));
		testTokenList("  012abc34 ", true, new Token(Token.ID,"012abc34",1,3));
	}
	
}
