/* **********************************************
 * Duale Hochschule Baden-Württemberg Karlsruhe
 * Prof. Dr. Jörn Eisenbiegler
 * 
 * Vorlesung Übersetzerbau
 * Praxis AST für X
 * - Token-Definition
 * 
 * **********************************************
 */

package de.dhbw.compiler.xparser;

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
	
	// Token für innere Knoten des Ableitungsbaumes	
	public static final int TYPE 			= 101;
	public static final int MODIFIER		= 102;
	public static final int DECL 			= 103;
	public static final int EXPR 			= 104;
	public static final int EXPR2 			= 105;
	public static final int EXPR3 			= 106;
	public static final int ASSIGNSTAT		= 107;
	public static final int FORSTAT			= 108;
	public static final int WHILESTAT		= 109;
	public static final int CONDSTAT		= 110;
	public static final int COND 			= 111;
	public static final int STAT 			= 112;
	public static final int BLOCK 			= 113;
	public static final int STATLIST		= 114;
	public static final int STATWITHSEMI	= 115;
	public static final int DECLLIST		= 116;
	public static final int APROGRAM		= 117;
	public static final int UMINUS			= 118;
	

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
	
	public Token(int type) {
		this(type, getTypeName(type));
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
			// Token für innere Knoten des Ableitungsbaumes	
			case TYPE: 			return "TYPE";
			case MODIFIER:		return "MODIFIER";
			case DECL: 			return "DECL";
			case EXPR: 			return "EXPR";
			case EXPR2: 		return "EXPR2";
			case EXPR3: 		return "EXPR3";
			case ASSIGNSTAT:	return "ASSIGNSTAT";
			case FORSTAT:		return "FORSTAT";
			case WHILESTAT:		return "WHILESTAT";
			case CONDSTAT:		return "CONDSTAT";
			case COND: 			return "COND";
			case STAT: 			return "STAT";
			case BLOCK: 		return "BLOCK";
			case STATLIST:		return "STATLIST";
			case STATWITHSEMI:	return "STATWITHSEMI";
			case DECLLIST:		return "DECLLIST";
			case APROGRAM:		return "APROGRAM";
			case UMINUS:		return "UMINUS";
			default: return "Unknown token type!";
		}
			
	}

}
