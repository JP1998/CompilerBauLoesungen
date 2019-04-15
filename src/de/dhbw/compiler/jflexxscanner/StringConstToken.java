/* **********************************************
 * Duale Hochschule Baden-Württemberg Karlsruhe
 * Prof. Dr. Jörn Eisenbiegler
 * 
 * Vorlesung Übersetzerbau
 * Praxis X-Scanner mit JFlex
 * - Token-Definition stringConst
 * 
 * **********************************************
 */

package de.dhbw.compiler.jflexxscanner;

public class StringConstToken extends Token {
	
	public StringConstToken(int type, String text, int line, int column) {
		super(type, text, line, column);
	}

	public String getValue() {
		return this.getText().replace("\\\\", "\\").replace("\\\"", "\"");
	}


}
