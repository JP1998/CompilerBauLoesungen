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

public class StringToken extends Token {
	
	private String value;
	
	public StringToken(int type, String text, int line, int column, String value) {
		super(type,text,line,column);
		this.value = value;
	}
	
	String getValue() {
		return value;
	}


}
