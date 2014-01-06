package org.cgfalcon.myprolang.calc;

import org.cgfalcon.myprolang.calc.lexer.Token;

/**
 * @author: Falcon
 * @date: 14-1-4.
 */
public class AddNode extends ExprNode {

    public AddNode(Token token) {
        super(token);
    }

    public AddNode(ExprNode leftExpr, Token token, ExprNode rightExpr) {
        super(token);
        addChild(leftExpr);
        addChild(rightExpr);
    }
}
