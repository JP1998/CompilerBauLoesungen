/* **********************************************
 * Duale Hochschule Baden-Württemberg Karlsruhe
 * Prof. Dr. Jörn Eisenbiegler
 * 
 * Vorlesung Übersetzerbau
 * Praxis AS-Scanner mit JFlex
 * - Testfall-Utility
 * 
 * **********************************************
 */

package de.dhbw.compiler.jflexasscanner;

import static org.junit.Assert.assertEquals;

import java.io.StringReader;

public abstract class TokenStreamTest {
	
	protected void testTokenList(String in, boolean convert, Token... TokenList) throws Exception {
		JFlexASScanner scanner = new JFlexASScanner(new StringReader(in));
		Token myToken;
		
		for (Token expected : TokenList) {
			myToken = scanner.nextToken();

			assertEquals("Expected: " + expected.toString() + "; Found: " + myToken.toString(), expected, myToken);

			assertEquals("Expect Token "+expected.toString()+". Error in type.",
					expected.getType(), myToken.getType());
			assertEquals("Expect Token "+expected.toString()+". Error in text.",
					expected.getText(), myToken.getText());
			assertEquals("Expect Token "+expected.toString()+". Error in line.",
					expected.getLine(), myToken.getLine());
			assertEquals("Expect Token "+expected.toString()+". Error in column.",
					expected.getColumn(), myToken.getColumn());

			if (convert) {
				switch (myToken.getType()) {
					case Token.NUM:
						int intValue = Integer.parseInt(expected.getText());
						assertEquals("Expected Token value "+intValue,
								intValue,((NumToken)myToken).getValue());
						break;
					case Token.FRAC:
						double doubleValue = Double.parseDouble(expected.getText().replace("^", "e"));
						assertEquals("Expected Token value "+doubleValue,
								doubleValue,((FracToken)myToken).getValue(), doubleValue*0.0000001);
						break;
				}
			}
		}
		
		myToken = scanner.nextToken();
		assertEquals("Expected End of File (EOF), read " + myToken.toString() + ".", Token.EOF, myToken.getType());	
	}
	
}
