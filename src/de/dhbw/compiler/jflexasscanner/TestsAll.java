/* **********************************************
 * Duale Hochschule Baden-Württemberg Karlsruhe
 * Prof. Dr. Jörn Eisenbiegler
 * 
 * Vorlesung Übersetzerbau
 * Praxis AS-Scanner mit JFlex
 * - Testsuite
 * 
 * **********************************************
 */

package de.dhbw.compiler.jflexasscanner;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ TestASScanner1.class, TestASScanner2.class, TestASScanner3.class, TestASScanner4.class, TestASScanner5.class })
public class TestsAll {

}
