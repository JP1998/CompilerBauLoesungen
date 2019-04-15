/* **********************************************
 * Duale Hochschule Baden-Württemberg Karlsruhe
 * Prof. Dr. Jörn Eisenbiegler
 * 
 * Vorlesung Übersetzerbau
 * Praxis X-Scanner mit JFlex
 * - Token-Definition intConst
 * 
 * **********************************************
 */

package de.dhbw.compiler.jflexxscanner;

public class IntConstToken extends Token {
	
	public IntConstToken(int type, String text, int line, int column) {
		super(type, text, line, column);
	}

	public int getValue() {
		return Integer.parseInt(this.getText());
	}


}
