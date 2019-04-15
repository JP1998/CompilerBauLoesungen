/* **********************************************
 * Duale Hochschule Baden-Württemberg Karlsruhe
 * Prof. Dr. Jörn Eisenbiegler
 * 
 * Vorlesung Übersetzerbau
 * Praxis X-Scanner mit JFlex
 * 
 * **********************************************
 */

package de.dhbw.compiler.jflexxscanner;

import java.io.StringReader;


public class JFlexXScannerMain {
	
	private static final String TEST = 
			"program test2;\n"+
			"	x : int;\n"+
			"	y : float;\n"+
			"	z : string;\n"+
			"begin\n"+
			"	x := 4+5+6.2;\n"+
			"	y := 3.56+1.2e3+45.e-67+4e34+3E-1;\n"+
			"	z := \"Hello \\\"World\\\"\" + \":\";\n"+
			"	z := \"Peter\" + 4;\n"+
			"	a := 3+4;\n"+
			"end.";
	
	public static void main(String[] args) throws Exception {
		
		@SuppressWarnings("unused")
		JFlexXScanner scanner = new JFlexXScanner(new StringReader(TEST));
		
		/* 
		 * Lesen Sie hier die einzelnen Token des oben angebenen String mit Hilfe des 
		 * Scanners ein und geben Sie sie auf die Konsole aus. 
		 */

	}

}
