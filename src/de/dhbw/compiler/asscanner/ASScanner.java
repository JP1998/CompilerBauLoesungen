/* **********************************************
 * Duale Hochschule Baden-Württemberg Karlsruhe
 * Prof. Dr. Jörn Eisenbiegler
 *
 * Vorlesung Übersetzerbau
 * Praxis AS-Scanner tabellengesteuert
 * - Scanner-Klasse
 *
 * **********************************************
 */

package de.dhbw.compiler.asscanner;

import java.io.Reader;

public class ASScanner {

    private Reader in = null;

    private static final int WHITESPACE = 0;

    private static final int STATE_WS     = 1;
    private static final int STATE_LBR    = 2;
    private static final int STATE_RBR    = 4;
    private static final int STATE_NAME   = 8;
    private static final int STATE_COMMA  = 16;
    private static final int STATE_NUMBER = 32;
    private static final int STATE_FPNAC  = 64;
    private static final int STATE_FPNAP  = 128;
    private static final int STATE_FPNAE  = 256;
    private static final int STATE_N      = 512;
    private static final int STATE_NU     = 1024;
    private static final int STATE_NUL    = 2048;
    private static final int STATE_NULL   = 4096;
    private static final int STATE_EOF    = 8192;

    private int state;
    private int tokenType;
    private String textRead;

    public ASScanner(Reader input) {
        this.in = input;

        this.state = STATE_WS;
        this.tokenType = Token.INVALID;
        this.textRead = "";
    }

    public Token nextToken() throws Exception {

        Token out = null;

        while(out == null) {
            int c = in.read();

            switch (this.state) {
                case STATE_EOF:  out = new Token(Token.EOF, ""); break;
                case STATE_WS:   out = statemachine_ws(c); break;
                case STATE_LBR: case STATE_RBR: case STATE_COMMA:
                    out = statemachine_rbr_lbr_comma(c); break;
                case STATE_NAME:  out = statemachine_name(c); break;
                case STATE_NUMBER: out = statemachine_number(c); break;
                case STATE_FPNAC: out = statemachine_fpnac(c); break;
                case STATE_FPNAP: out = statemachine_fpnap(c); break;
                case STATE_FPNAE: out = statemachine_fpnae(c); break;
                case STATE_N: out = statemachine_n(c); break;
                case STATE_NU: out = statemachine_nu(c); break;
                case STATE_NUL: out = statemachine_nul(c); break;
                case STATE_NULL: out = statemachine_null(c); break;
            }
        }

        return out;
    }

    private Token statemachine_ws(int c) throws Exception {
        switch (c) {
            case -1:   return step(WHITESPACE, STATE_EOF,   false, Token.EOF);
            case ']':  return step(c,          STATE_RBR,   false, Token.RSBR);
            case '[':  return step(c,          STATE_LBR,   false, Token.LSBR);
            case ',':  return step(c,          STATE_COMMA, false, Token.COMMA);
            case 'n':  return step(c,          STATE_N,     false, Token.ID);
            default:
                if(Character.isWhitespace(c)) {
                    return step(WHITESPACE, STATE_WS, false, Token.INVALID);
                } else if(c >= '0' && c <= '9') {
                    return step(c, STATE_NUMBER, false, Token.NUM);
                } else if((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z')) {
                    return step(c, STATE_NAME, false, Token.ID);
                } else {
                    throw new Exception("unexpected character " + ((char) c));
                }
        }
    }

    private Token statemachine_rbr_lbr_comma(int c) throws Exception {
        switch (c) {
            case -1:   return step(WHITESPACE, STATE_EOF,   true, Token.EOF);

            case ']':  return step(c,          STATE_RBR,   true, Token.RSBR);
            case '[':  return step(c,          STATE_LBR,   true, Token.LSBR);
            case ',':  return step(c,          STATE_COMMA, true, Token.COMMA);
            case 'n':  return step(c,          STATE_N,     true, Token.ID);
            default:
                if(Character.isWhitespace(c)) {
                    return step(WHITESPACE, STATE_WS, true, Token.INVALID);
                } else if(c >= '0' && c <= '9') {
                    return step(c, STATE_NUMBER, true, Token.NUM);
                } else if((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z')) {
                    return step(c, STATE_NAME, true, Token.ID);
                } else {
                    throw new Exception("unexpected character " + ((char) c));
                }
        }
    }

    private Token statemachine_name(int c) throws Exception {
        switch (c) {
            case -1:   return step(WHITESPACE, STATE_EOF,   true, Token.EOF);

            case ']':  return step(c,          STATE_RBR,   true, Token.RSBR);
            case '[':  return step(c,          STATE_LBR,   true, Token.LSBR);
            case ',':  return step(c,          STATE_COMMA, true, Token.COMMA);
            default:
                if(Character.isWhitespace(c)) {
                    return step(WHITESPACE, STATE_WS, true, Token.INVALID);
                } else if((c >= '0' && c <= '9') || (c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z')) {
                    return step(c, STATE_NAME, false, Token.ID);
                } else {
                    throw new Exception("unexpected character " + ((char) c));
                }
        }
    }

    private Token statemachine_number(int c) throws Exception {
        switch (c) {
            case -1:   return step(WHITESPACE, STATE_EOF,   true, Token.EOF);

            case ']':  return step(c,          STATE_RBR,   true, Token.RSBR);
            case '[':  return step(c,          STATE_LBR,   true, Token.LSBR);
            case ',':  return step(c,          STATE_COMMA, true, Token.COMMA);
            case '.':  return step(c,          STATE_FPNAC, false, Token.FRAC);
            default:
                if(Character.isWhitespace(c)) {
                    return step(WHITESPACE, STATE_WS, true, Token.INVALID);
                } else if((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z')) {
                    return step(c, STATE_NAME, false, Token.ID);
                } else if(c >= '0' && c <= '9') {
                    return step(c, STATE_NUMBER, false, Token.NUM);
                } else {
                    throw new Exception("unexpected character " + ((char) c));
                }
        }
    }

    private Token statemachine_fpnac(int c) throws Exception {
        switch (c) {
            case -1:   return step(WHITESPACE, STATE_EOF,   true, Token.EOF);

            case ']':  return step(c,          STATE_RBR,   true, Token.RSBR);
            case '[':  return step(c,          STATE_LBR,   true, Token.LSBR);
            case ',':  return step(c,          STATE_COMMA, true, Token.COMMA);
            case '^':  return step(c,          STATE_FPNAP, false, Token.INVALID);
            default:
                if(Character.isWhitespace(c)) {
                    return step(WHITESPACE, STATE_WS, true, Token.INVALID);
                } else if((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z')) {
                    return step(c, STATE_NAME, true, Token.ID);
                } else if(c >= '0' && c <= '9') {
                    return step(c, STATE_FPNAC, false, Token.FRAC);
                } else {
                    throw new Exception("unexpected character " + ((char) c));
                }
        }
    }

    private Token statemachine_fpnap(int c) throws Exception {
        if(c >= '0' && c <= '9') {
            return step(c, STATE_FPNAE, false, Token.FRAC);
        } else {
            throw new Exception("unexpected character " + ((char) c));
        }
    }

    private Token statemachine_fpnae(int c) throws Exception {
        switch (c) {
            case -1:   return step(WHITESPACE, STATE_EOF,   true, Token.EOF);

            case ']':  return step(c,          STATE_RBR,   true, Token.RSBR);
            case '[':  return step(c,          STATE_LBR,   true, Token.LSBR);
            case ',':  return step(c,          STATE_COMMA, true, Token.COMMA);
            default:
                if(Character.isWhitespace(c)) {
                    return step(WHITESPACE, STATE_WS, true, Token.INVALID);
                } else if((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z')) {
                    return step(c, STATE_NAME, true, Token.ID);
                } else if(c >= '0' && c <= '9') {
                    return step(c, STATE_FPNAE, false, Token.FRAC);
                } else {
                    throw new Exception("unexpected character " + ((char) c));
                }
        }
    }

    private Token statemachine_n(int c) throws Exception {
        switch (c) {
            case -1:   return step(WHITESPACE, STATE_EOF,   true, Token.EOF);

            case ']':  return step(c,          STATE_RBR,   true, Token.RSBR);
            case '[':  return step(c,          STATE_LBR,   true, Token.LSBR);
            case ',':  return step(c,          STATE_COMMA, true, Token.COMMA);
            case 'u':  return step(c,          STATE_NU,     false, Token.ID);
            default:
                if(Character.isWhitespace(c)) {
                    return step(WHITESPACE, STATE_WS, true, Token.INVALID);
                } else if((c >= '0' && c <= '9') || (c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z')) {
                    return step(c, STATE_NAME, false, Token.ID);
                } else {
                    throw new Exception("unexpected character " + ((char) c));
                }
        }
    }

    private Token statemachine_nu(int c) throws Exception {
        switch (c) {
            case -1:   return step(WHITESPACE, STATE_EOF,   true, Token.EOF);

            case ']':  return step(c,          STATE_RBR,   true, Token.RSBR);
            case '[':  return step(c,          STATE_LBR,   true, Token.LSBR);
            case ',':  return step(c,          STATE_COMMA, true, Token.COMMA);
            case 'l':  return step(c,          STATE_NUL,   false, Token.ID);
            default:
                if(Character.isWhitespace(c)) {
                    return step(WHITESPACE, STATE_WS, true, Token.INVALID);
                } else if((c >= '0' && c <= '9') || (c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z')) {
                    return step(c, STATE_NAME, false, Token.ID);
                } else {
                    throw new Exception("unexpected character " + ((char) c));
                }
        }
    }

    private Token statemachine_nul(int c) throws Exception {
        switch (c) {
            case -1:   return step(WHITESPACE, STATE_EOF,   true, Token.EOF);

            case ']':  return step(c,          STATE_RBR,   true, Token.RSBR);
            case '[':  return step(c,          STATE_LBR,   true, Token.LSBR);
            case ',':  return step(c,          STATE_COMMA, true, Token.COMMA);
            case 'l':  return step(c,          STATE_NULL,   false, Token.NULL);
            default:
                if(Character.isWhitespace(c)) {
                    return step(WHITESPACE, STATE_WS, true, Token.INVALID);
                } else if((c >= '0' && c <= '9') || (c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z')) {
                    return step(c, STATE_NAME, false, Token.ID);
                } else {
                    throw new Exception("unexpected character " + ((char) c));
                }
        }
    }

    private Token statemachine_null(int c) throws Exception {
        switch (c) {
            case -1:   return step(WHITESPACE, STATE_EOF,   true, Token.EOF);

            case ']':  return step(c,          STATE_RBR,   true, Token.RSBR);
            case '[':  return step(c,          STATE_LBR,   true, Token.LSBR);
            case ',':  return step(c,          STATE_COMMA, true, Token.COMMA);
            default:
                if(Character.isWhitespace(c)) {
                    return step(WHITESPACE, STATE_WS, true, Token.INVALID);
                } else if((c >= '0' && c <= '9') || (c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z')) {
                    return step(c, STATE_NAME, false, Token.ID);
                } else {
                    throw new Exception("unexpected character " + ((char) c));
                }
        }
    }

    private Token step(int c, int newState, boolean create, int newTokenType) {
        Token out = null;
        if(create) {
            out = new Token(tokenType, textRead);
            textRead = "";
        }
        if(c != WHITESPACE) {
            textRead += (char) c;
        }
        state = newState;
        tokenType = newTokenType;
        return out;
    }
}
