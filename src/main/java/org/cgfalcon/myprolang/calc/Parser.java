package org.cgfalcon.myprolang.calc;


import org.cgfalcon.myprolang.calc.lexer.Token;
import org.cgfalcon.myprolang.calc.lexer.TokenType;


public abstract class Parser {

    protected Lexer lexer;
    protected Token curToken  = null;

    public Parser(Lexer lexer) {
        this.lexer = lexer;
        curToken = lexer.nextToken();
    }

    protected void consume() {
        curToken = lexer.nextToken();
    }

    public void match(TokenType tType) {
        if(curToken.getKind() == tType) consume();
        else {
            throw new IllegalStateException("Unacceptable token: " + curToken);
        }
    }
}
