package org.cgfalcon.myprolang.calc;

import org.cgfalcon.myprolang.calc.lexer.Token;
import org.cgfalcon.myprolang.calc.lexer.TokenType;

/**
 * @author: Falcon
 * @date: 14-1-4.
 */
public class Main {


    public static void main(String[] args) {
        Token one = new Token("1", TokenType.NUM);
        Token two = new Token("2", TokenType.NUM);
        Token add = new Token("+", TokenType.OP_ADD_TOKEN);

        ExprNode root = new AddNode(new IntNode(one), add, new IntNode(two));

        System.out.printf(root.toStringTree());
    }
}
