/* **********************************************
 * Duale Hochschule Baden-Württemberg Karlsruhe
 * Prof. Dr. Jörn Eisenbiegler
 * 
 * Vorlesung Übersetzerbau
 * Praxis X-Scanner mit JFlex
 * - Token-Definition
 * 
 * **********************************************
 */

package de.dhbw.compiler.jflexxscanner;

public class Token {
	
	public static final int EOF 		= -1;
	public static final int INVALID		= 0;
	public static final int ID 			= 1;
	public static final int INTCONST 	= 2;
	public static final int FLOATCONST 	= 3;
	public static final int STRINGCONST	= 4;
	public static final int LBR 		= 5;
	public static final int RBR 		= 6;
	public static final int MULT 		= 7;
	public static final int PLUS 		= 8;
	public static final int MINUS 		= 9;
	public static final int DOT 		= 10;
	public static final int DIV 		= 11;
	public static final int COLON 		= 12;
	public static final int ASSIGN 		= 13;
	public static final int SEMICOLON 	= 14;
	public static final int LESS 		= 15;
	public static final int EQUALS 		= 16;
	public static final int MORE 		= 17;
	public static final int BEGIN 		= 18;
	public static final int ELSE 		= 19;
	public static final int END 		= 20;
	public static final int FLOAT 		= 21;
	public static final int FOR 		= 22;
	public static final int IF 			= 23;
	public static final int INT 		= 24;
	public static final int PRINT 		= 25;
	public static final int PROGRAM 	= 26;
	public static final int READ 		= 27;
	public static final int STRING 		= 28;
	public static final int THEN 		= 29;
	public static final int WHILE 		= 30;
	
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
			case EOF: 			return "EOF";
			case INVALID: 		return "INVALID";
			case ID:			return "ID";
			case INTCONST:		return "INTCONST";
			case FLOATCONST:	return "FLOATCONST";
			case STRINGCONST:	return "STRINGCONST";
			case LBR:			return "LBR";
			case RBR:			return "RBR";
			case MULT:			return "MULT";
			case PLUS:			return "PLUS";
			case MINUS:			return "MINUS";
			case DOT:			return "DOT";
			case DIV:			return "DIV";
			case COLON:			return "COLON";
			case ASSIGN:		return "ASSIGN";
			case SEMICOLON:		return "SEMICOLON";
			case LESS:			return "LESS";
			case EQUALS:		return "EQUALS";
			case MORE:			return "MORE";
			case BEGIN:			return "BEGIN";
			case ELSE:			return "ELSE";
			case END:			return "END";
			case FLOAT:			return "FLOAT";
			case FOR:			return "FOR";
			case IF:			return "IF";
			case INT:			return "INT";
			case PRINT:			return "PRINT";
			case PROGRAM:		return "PROGRAM";
			case READ:			return "READ";
			case STRING:		return "STRING";
			case THEN:			return "THEN";
			case WHILE:			return "WHILE";
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
