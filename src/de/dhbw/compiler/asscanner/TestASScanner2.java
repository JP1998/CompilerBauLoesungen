/* **********************************************
 * Duale Hochschule Baden-Württemberg Karlsruhe
 * Prof. Dr. Jörn Eisenbiegler
 *
 * Vorlesung Übersetzerbau
 * Praxis AS-Scanner tabellengesteuert
 * - Testf�lle Aufgabe 2
 *
 * **********************************************
 */

package de.dhbw.compiler.asscanner;

import org.junit.Ignore;
import org.junit.Test;

// @Ignore
public class TestASScanner2 extends TokenStreamTest {

    @Test
    public void token1_1() throws Exception {
        testTokenList("  1.1 ", new Token(Token.FRAC,"1.1"));
    }

    @Test
    public void token1_() throws Exception {
        testTokenList("  1. ", new Token(Token.FRAC,"1."));
    }

    @Test(expected=Exception.class)
    public void token_1() throws Exception {
        testTokenList("  .1 ",
                new Token(Token.INVALID,"."),
                new Token(Token.NUM,"1"));
    }

    @Test
    public void token1_1_1() throws Exception {
        testTokenList("  1.1^1 ", new Token(Token.FRAC,"1.1^1"));
    }

    @Test
    public void token1__1() throws Exception {
        testTokenList("  1.^1 ", new Token(Token.FRAC,"1.^1"));
    }

    @Test
    public void token0120_01230_01230() throws Exception {
        testTokenList("  01234567890.01234567890^01234567890 ",
                new Token(Token.FRAC,"01234567890.01234567890^01234567890"));
    }

    @Test(expected=Exception.class)
    public void token0120_01230_() throws Exception {
        testTokenList("  01230.01230^ ",
                new Token(Token.FRAC,"01230.01230"),
                new Token(Token.INVALID,"^"));
    }

    @Test
    public void token0120_0abc() throws Exception {
        testTokenList("  01230.0abc",
                new Token(Token.FRAC,"01230.0"),
                new Token(Token.ID,"abc"));
    }

    @Test
    public void token0120_abc() throws Exception {
        testTokenList("  01230.abc",
                new Token(Token.FRAC,"01230."),
                new Token(Token.ID,"abc"));
    }

    @Test
    public void token0120_0_45LSBR() throws Exception {
        testTokenList("  01230.0^45[",
                new Token(Token.FRAC,"01230.0^45"),
                new Token(Token.LSBR,"["));
    }

    @Test
    public void sentence1() throws Exception {
        testTokenList("[ hallo, 23.45, 4., 5.^56, 0.0^00, 78]",
                new Token(Token.LSBR,"["),
                new Token(Token.ID,"hallo"),
                new Token(Token.COMMA,","),
                new Token(Token.FRAC,"23.45"),
                new Token(Token.COMMA,","),
                new Token(Token.FRAC,"4."),
                new Token(Token.COMMA,","),
                new Token(Token.FRAC,"5.^56"),
                new Token(Token.COMMA,","),
                new Token(Token.FRAC,"0.0^00"),
                new Token(Token.COMMA,","),
                new Token(Token.NUM,"78"),
                new Token(Token.RSBR,"]"));
    }

}
