package org.cgfalcon.myprolang.calc;

import org.cgfalcon.myprolang.calc.lexer.Token;

/**
 * Created by Falcon on 13-12-26.
 */
public abstract class Lexer {

    protected static final char EOF = (char)-1;
    protected String text;
    protected int position = 0;
    // current char
    protected char c;

    public Lexer(String text) {
        this.text = text;
        c = text.charAt(position);
    }

    public void consume() {
        position++;
        if (position >= text.length()) c = EOF;
        else c = text.charAt(position);
    }

    public abstract Token nextToken();

    public void setLine(String line) {
        this.text = line;
        position = 0;
        c = text.charAt(position);
    }
}
