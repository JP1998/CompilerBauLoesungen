/* **********************************************
 * Duale Hochschule Baden-Württemberg Karlsruhe
 * Prof. Dr. Jörn Eisenbiegler
 * 
 * Vorlesung Übersetzerbau
 * Praxis X Abstiegsparser
 * 
 * **********************************************
 */

package de.dhbw.compiler.xparser;

import java.io.IOException;
import java.util.ArrayList;

public class TokenReader {
	
	private ArrayList<Token> list = new ArrayList<>();
	private int tokenindex = 0;
	
	public TokenReader(JFlexXScanner s) throws IOException {
		Token mytoken;
		do {
			mytoken = s.nextToken();
			list.add(mytoken);
		} while(mytoken.getType()!=Token.EOF);
	}
	
	public Token nextToken() {
		return list.get(tokenindex++);
	}
	
	public int getPosition() {
		return tokenindex;
	}
	
	public void setPosition(int position) {
		tokenindex = position;
	}
	
	public int getTokenCount() {
		return list.size();
	}

}
