package org.cgfalcon.myprolang.calc.lexer;

/**
 * Created by Falcon on 13-12-21.
 */
public class Token {

    public static int MAX_TOKEN_LENGTH = 100;

    /**
     * which position (start index) this token in its original line
     */
    public int pos;

    /**
     * which line this token in the source file
     */
    public int line;


    public TokenType kind;

    public String text;

    public Token(String text, TokenType tokenType) {
        this.text = text;
        this.kind = tokenType;
        this.pos = 0;
        this.line = 0;
    }


    public Token(int pos, int line, String text, TokenType kind) {
        this.pos = pos;
        this.line = line;
        this.kind = kind;
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getPos() {
        return pos;
    }

    public void setPos(int pos) {
        this.pos = pos;
    }

    public int getLine() {
        return line;
    }

    public void setLine(int line) {
        this.line = line;
    }

    public TokenType getKind() {
        return kind;
    }

    public void setKind(TokenType kind) {
        this.kind = kind;
    }

    @Override
    public String toString() {
        return "Token{" +
                "line=" + line +
                ", pos=" + pos +
                ", kind=" + kind +
                ", text='" + text + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Token)) return false;

        Token token = (Token) o;

        if (line != token.line) return false;
        if (pos != token.pos) return false;
        if (kind != token.kind) return false;
        if (!text.equals(token.text)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = pos;
        result = 31 * result + line;
        result = 31 * result + kind.hashCode();
        result = 31 * result + text.hashCode();
        return result;
    }
}
