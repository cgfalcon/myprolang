package org.cgfalcon.myprolang.calc.lexer;

/**
 * Created by Falcon on 13-12-21.
 */


import org.cgfalcon.myprolang.calc.Lexer;

/**
 * This Lexer can accept token like follows:
 *
 * NUM: [0-9]+
 *
 * OP_ADD_TOKEN: '+'
 *
 * OP_MUL_TOKEN: '*'
 *
 * LEFT_BRAKET: '('
 *
 * RIGHT_BRAKET: ')'
 */
public class CalcLexer extends Lexer {


    public CalcLexer(String text) {
        super(text);
    }

    @Override
    public Token nextToken() {
        while (c != EOF) {
            switch (c) {
                case ' ':case '\r':case '\n':case '\t': WS(); continue;
                case '+':
                    consume();
                    return new Token(position - 1, 1, "+", TokenType.OP_ADD_TOKEN);
                case '*':
                    consume();
                    return new Token(position - 1, 1, "*", TokenType.OP_MUL_TOKEN);
                case '(':
                    consume();
                    return new Token(position - 1, 1, "(", TokenType.LEFT_BRAKET);
                case ')':
                    consume();
                    return new Token(position - 1, 1, ")", TokenType.RIGHT_BRAKET);
                default:
                    if (isDigit(c)) {
                        return Num();
                    }
                    throw new IllegalStateException("invalid character: " + c);
            }
        }
        return new Token(position - 1, 1, "EOF", TokenType.EOF);
    }


    /**
     * Recognize Num Token
     * @return
     */
    private Token Num() {
        StringBuilder buffer = new StringBuilder();
        NumTokenStatus status = NumTokenStatus.INIT;
        int starPx = position;
        status = NumTokenStatus.NUM_INT;
        do {
            buffer.append(c);

            consume();
            if (isDigit(c)) {
                if (status == NumTokenStatus.DOT) {
                    status = NumTokenStatus.NUM_FRAC;
                }
            } else {
                if (c == '.' && status == NumTokenStatus.NUM_INT) {
                    status = NumTokenStatus.DOT;
                } else {
                    switch (c) {
                        case ' ':case '\r':case '\n':case '\t': WS(); break;
                        default:
                            throw new IllegalStateException("Unacceptable Char: " + c + " for Token NUM");

                    }
                    break;
                }
            }


        } while (true);
        return new Token(starPx, 1, buffer.toString(), TokenType.NUM);
    }


    private Token ID() {
        StringBuilder buff = new StringBuilder();
        int startPx = position;
        do {
            buff.append(c);
            consume();
            if (!isLetter(c)) {
                throw new IllegalStateException("Unacceptable Char: " + c + " for Token ID");
            }
        } while (isLetter(c));
        return new Token(startPx, 1, buff.toString(), TokenType.ID);
    }

    private void WS() {
        while (c != EOF && (c == ' ' || c == '\t' || c == '\r' || c == '\n')) {
            consume();
        }
    }

    private boolean isDigit(char c) {
        return c >= '0' && c <= '9';
    }

    private boolean isLetter(char c) {
        return (c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z');
    }


}
