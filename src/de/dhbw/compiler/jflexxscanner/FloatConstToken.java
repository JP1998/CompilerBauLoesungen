/* **********************************************
 * Duale Hochschule Baden-Württemberg Karlsruhe
 * Prof. Dr. Jörn Eisenbiegler
 * 
 * Vorlesung Übersetzerbau
 * Praxis X-Scanner mit JFlex
 * - Token-Definition floatConst
 * 
 * **********************************************
 */

package de.dhbw.compiler.jflexxscanner;

public class FloatConstToken extends Token {
	
	public FloatConstToken(int type, String text, int line, int column) {
		super(type, text, line, column);
	}

	public double getValue() {
		return Double.parseDouble(this.getText());
	}


}
