/* **********************************************
 * Duale Hochschule Baden-Württemberg Karlsruhe
 * Prof. Dr. Jörn Eisenbiegler
 * 
 * Vorlesung Übersetzerbau
 * Praxis AS-Scanner mit JFlex
 * - Token-Definition frac
 * 
 * **********************************************
 */

package de.dhbw.compiler.jflexasscanner;

public class FracToken extends Token {

	private double value = Double.NaN;

	public FracToken(int type, String text, int line, int column) {
		super(type, text, line, column);
	}

	double getValue() {
		if(Double.isNaN(value)) {
			value = Double.parseDouble(getText().replace('^', 'e'));
		}

		return value;
	}


}
