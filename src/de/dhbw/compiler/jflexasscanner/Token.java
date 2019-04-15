/* **********************************************
 * Duale Hochschule Baden-Württemberg Karlsruhe
 * Prof. Dr. Jörn Eisenbiegler
 * 
 * Vorlesung Übersetzerbau
 * Praxis AS-Scanner mit JFlex
 * - Token-Definition
 * 
 * **********************************************
 */

package de.dhbw.compiler.jflexasscanner;

public class Token {
	
	public static final int EOF = 0;
	public static final int INVALID = -1;
	public static final int LSBR = 1;
	public static final int RSBR = 2;
	public static final int COMMA = 3;
	public static final int NUM = 4;
	public static final int ID = 5;
	public static final int NULL = 6;
	public static final int FRAC = 7;
	public static final int TO = 8;
	public static final int STR = 9;	
	
	private int type;
	private String text;
	private int line;
	private int columnm;
	
	public Token(int type, String text, int line, int column) {
		this.type = type;
		this.text = text;
		this.line = line;
		this.columnm = column;
	}
	
	public Token(int type, String text) {
		this(type,text,0,0);
	}
	
	public int getType() {
		return type;
	}

	public String getText() {
		return text;
	}
	
	public int getLine() {
		return line;
	}
	
	public int getColumn() {
		return columnm;
	}

	public String toString() {
		return "(" + getTypeName(type) + "," + text + "," + line + "," + columnm + ")";
	}
	
	public static String getTypeName(int tokenType) {
		switch (tokenType) {
			case EOF: return "EOF";
			case INVALID: return "INVALID";
			case LSBR: return "LSBR";
			case RSBR: return "RSBR"; 
			case COMMA: return "COMMA"; 
			case NUM: return "NUM"; 
			case ID: return "ID"; 
			case NULL: return "NULL";
			case FRAC: return "FRAC";
			case TO: return "TO";
			case STR: return "STR";
			default: return "Unknown token type!";
		}
			
	}

	@Override
	public boolean equals(Object obj) {
		if(obj instanceof Token) {
			Token t = (Token) obj;
			return this.type == t.type &&
					this.text.equals(t.text) &&
					this.columnm == t.columnm &&
					this.line == t.line;
		}
		return false;
	}
}
