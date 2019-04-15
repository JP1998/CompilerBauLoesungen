/* **********************************************
 * Duale Hochschule Baden-Württemberg Karlsruhe
 * Prof. Dr. Jörn Eisenbiegler
 * 
 * Vorlesung Übersetzerbau
 * Praxis Xmin Abstiegsparser
 * - Testsuite für Scanner
 * 
 * **********************************************
 */
package de.dhbw.compiler.jflexxscanner;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ TestXScanner1.class, TestXScanner2.class, TestXScanner3.class })
public class TestAll {

}
