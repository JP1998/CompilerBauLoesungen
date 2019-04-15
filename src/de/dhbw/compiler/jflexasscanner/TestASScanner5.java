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

public class TestASScanner5 extends TokenStreamTest {
	
	@Test
	public void stringABC() throws Exception {
		testTokenList("\"abc\"", true, 
				new Token(Token.STR,"\"abc\"",1,1));
		testTokenList(" \"abc\" ", true, 
				new Token(Token.STR,"\"abc\"",1,2));
	}
	
	@Test
	public void stringEmpty() throws Exception {
		testTokenList(" \"\" ", true, 
				new Token(Token.STR,"\"\"",1,2));
		testTokenList(" \" \" ", true, 
				new Token(Token.STR,"\" \"",1,2));
	}
	
	@Test
	public void stringEscape() throws Exception {
		testTokenList("\"\\\"\"", true, 
				new Token(Token.STR,"\"\\\"\"",1,1));
		testTokenList(" \"\\\"\\\"\\\"\" ", true, 
				new Token(Token.STR,"\"\\\"\\\"\\\"\"",1,2));
		testTokenList(" \" \\\" \" ", true, 
				new Token(Token.STR,"\" \\\" \"",1,2));
	}
	
	@Test
	public void stringAll() throws Exception {	
		testTokenList("\"abcdefghijklmnopqrstuvwxyz0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ\\\" \"", true, 
				new Token(Token.STR,"\"abcdefghijklmnopqrstuvwxyz0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ\\\" \"",1,1));
		testTokenList("\"abcde\\\"\"\"fg\\\"hi\" \"jkl\\\"\"", true, 
				new Token(Token.STR,"\"abcde\\\"\"",1,1),
				new Token(Token.STR,"\"fg\\\"hi\"",1,10),
				new Token(Token.STR,"\"jkl\\\"\"",1,19));
	}

}
