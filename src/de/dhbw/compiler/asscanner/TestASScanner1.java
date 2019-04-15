/* **********************************************
 * Duale Hochschule Baden-Württemberg Karlsruhe
 * Prof. Dr. Jörn Eisenbiegler
 *
 * Vorlesung Übersetzerbau
 * Praxis AS-Scanner tabellengesteuert
 * - Testf�lle Aufgabe 1
 *
 * **********************************************
 */

package de.dhbw.compiler.asscanner;

import org.junit.Ignore;
import org.junit.Test;

public class TestASScanner1 extends TokenStreamTest {

    @Test
    public void tokenLSBR() throws Exception {
        testTokenList("  [ ", new Token(Token.LSBR,"["));
    }

    @Test
    public void tokenRSBR() throws Exception {
        testTokenList("  ] ", new Token(Token.RSBR,"]"));
    }

    @Test
    public void tokenCOMMA() throws Exception {
        testTokenList("  , ", new Token(Token.COMMA,","));
    }

    @Test
    public void tokenZAHL() throws Exception {
        testTokenList("  0 ", new Token(Token.NUM,"0"));
        testTokenList("  9 ", new Token(Token.NUM,"9"));
        testTokenList("  1234567890 ", new Token(Token.NUM,"1234567890"));
    }

    @Test
    public void tokenID() throws Exception {
        testTokenList("  a ", new Token(Token.ID,"a"));
        testTokenList("  A ", new Token(Token.ID,"A"));
        testTokenList("  x ", new Token(Token.ID,"x"));
        testTokenList("  X ", new Token(Token.ID,"X"));
        testTokenList("  0a ", new Token(Token.ID,"0a"));
        testTokenList("  X0 ", new Token(Token.ID,"X0"));
        testTokenList("  1234567890a ", new Token(Token.ID,"1234567890a"));
        testTokenList("  abcdefghijklmnopqrstuvwxyz ", new Token(Token.ID,"abcdefghijklmnopqrstuvwxyz"));
        testTokenList("  ABCDEFGHIJKLMNOPQRSTUVWXYZ ", new Token(Token.ID,"ABCDEFGHIJKLMNOPQRSTUVWXYZ"));
        testTokenList("  NULL ", new Token(Token.ID,"NULL"));
        testTokenList("  NulL ", new Token(Token.ID,"NulL"));
    }

//    @Ignore
    @Test
    public void tokenNULL() throws Exception {
        testTokenList("  null ", new Token(Token.NULL,"null"));
    }

    @Test
    public void tokenEOF() throws Exception {
        testTokenList(" ");
        testTokenList(" ", new Token(Token.EOF,""));
        testTokenList("", new Token(Token.EOF,""));
    }

    @Test(expected=Exception.class)
    public void invalidÄ() throws Exception {
        testTokenList(" Ä ", new Token(Token.INVALID,"Ä"));
    }

    public void invalidGatter() throws Exception {
        testTokenList(" # ", new Token(Token.INVALID,"#"));
    }

    @Test
    public void sentence1() throws Exception {
        testTokenList(" [ [ ] hallo ,,,Duda,123,76",
                new Token(Token.LSBR,"["),
                new Token(Token.LSBR,"["),
                new Token(Token.RSBR,"]"),
                new Token(Token.ID,"hallo"),
                new Token(Token.COMMA,","),
                new Token(Token.COMMA,","),
                new Token(Token.COMMA,","),
                new Token(Token.ID,"Duda"),
                new Token(Token.COMMA,","),
                new Token(Token.NUM,"123"),
                new Token(Token.COMMA,","),
                new Token(Token.NUM,"76"));
    }

//    @Ignore
    @Test
    public void sentence2() throws Exception {
        testTokenList("[34, x,peter,  34kurt, g56, null,null, 8]",
                new Token(Token.LSBR,"["),
                new Token(Token.NUM,"34"),
                new Token(Token.COMMA,","),
                new Token(Token.ID,"x"),
                new Token(Token.COMMA,","),
                new Token(Token.ID,"peter"),
                new Token(Token.COMMA,","),
                new Token(Token.ID,"34kurt"),
                new Token(Token.COMMA,","),
                new Token(Token.ID,"g56"),
                new Token(Token.COMMA,","),
                new Token(Token.NULL,"null"),
                new Token(Token.COMMA,","),
                new Token(Token.NULL,"null"),
                new Token(Token.COMMA,","),
                new Token(Token.NUM,"8"),
                new Token(Token.RSBR,"]"));
    }

    @Test
    public void sentence3() throws Exception {
        testTokenList("[ a, b, \n c, \r\nd]",
                new Token(Token.LSBR,"["),
                new Token(Token.ID,"a"),
                new Token(Token.COMMA,","),
                new Token(Token.ID,"b"),
                new Token(Token.COMMA,","),
                new Token(Token.ID,"c"),
                new Token(Token.COMMA,","),
                new Token(Token.ID,"d"),
                new Token(Token.RSBR,"]"));
    }


}
