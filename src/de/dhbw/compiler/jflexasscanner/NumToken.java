/* **********************************************
 * Duale Hochschule Baden-Württemberg Karlsruhe
 * Prof. Dr. Jörn Eisenbiegler
 * 
 * Vorlesung Übersetzerbau
 * Praxis AS-Scanner mit JFlex
 * - Token-Definition num
 * 
 * **********************************************
 */

package de.dhbw.compiler.jflexasscanner;

public class NumToken extends Token {

	private int value = -1;

	public NumToken(int type, String text, int line, int column) {
		super(type,text,line,column);
	}
	
	int getValue() {
		if(value == -1) {
			value = Integer.parseInt(getText());
		}

		return value;
	}


}
