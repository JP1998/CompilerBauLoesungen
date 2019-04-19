/* **********************************************
 * Duale Hochschule Baden-Württemberg Karlsruhe
 * Prof. Dr. Jörn Eisenbiegler
 * 
 * Vorlesung Übersetzerbau
 * Praxis X Abstiegsparser
 * - Token-Definition stringConst
 * 
 * **********************************************
 */

package de.dhbw.compiler.xparser;

public class StringConstToken extends Token {
	
	private String value="";
	
	public StringConstToken(int type, String text, int line, int column, String value) {
		super(type,text,line,column);
		this.value = value;
	}
	
	public String getValue() {
		return value;
	}


}
