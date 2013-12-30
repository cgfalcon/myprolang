package org.cgfalcon.myprolang.calc.parser;

/**
 * Created by Falcon on 13-12-27.
 */


import org.cgfalcon.myprolang.calc.Lexer;
import org.cgfalcon.myprolang.calc.Parser;
import org.cgfalcon.myprolang.calc.lexer.Token;
import org.cgfalcon.myprolang.calc.lexer.TokenType;

import java.util.ArrayList;
import java.util.List;

/**
 * To implement a calculator Parser, accept following Phrase
 *
 *      x = 1 + 3    # compute value to variable x
 *      1 + 5        # evaluate directly
 *      z = x + y    # evaluate sum of x and y, and assign result to z
 *      x + y        # evaluate sum of x and y directly
 *
 *      stats: stat+
 *
 *      stat:
 *          | expr
 *          | ID '=' expr
 *
 *      expr:
 *          | NUM [('+'|'-') NUM]*
 *
 *      NUM:  [0-9]+
 *
 *      ID:   [a-zA-Z]+
 *
 *      WS:   \s+
 *
 */
public class CalcParser extends Parser {

    public CalcParser(Lexer lexer) {
        super(lexer);
    }

    public void calc() {
       stats();
    }

    private void stats() {
        switch (curToken.getKind()) {
            case ID:
                match(TokenType.ID);match(TokenType.EQUAL);expr();
                break;
            case NUM:
                expr();
                break;
            default:
                throw new IllegalStateException("Unacceptable token: " + curToken);
        }
    }

    private void expr() {
        match(TokenType.NUM);
        while (curToken.getKind() == TokenType.OP_ADD_TOKEN || curToken.getKind() == TokenType.OP_MUL_TOKEN) {
            if (curToken.getKind() == TokenType.OP_ADD_TOKEN) {
                match(TokenType.OP_ADD_TOKEN);
            } else if (curToken.getKind() == TokenType.OP_SUB_TOKEN) {
                match(TokenType.OP_SUB_TOKEN);
            }
            match(TokenType.NUM);
        }
    }



}
