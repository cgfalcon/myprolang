package org.cgfalcon.myprolang.calc;


import org.cgfalcon.myprolang.calc.lexer.Token;

/**
 * Created by Falcon on 13-12-27.
 */
public abstract class Parser {

    protected Lexer lexer;
    protected Token curToken  = null;
    protected Token nextToken = null;

    public Parser(Lexer lexer) {
        this.lexer = lexer;
        curToken = lexer.nextToken();
    }

    protected void consume() {
        nextToken = lexer.nextToken();
    }
}
