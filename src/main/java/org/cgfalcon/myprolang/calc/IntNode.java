package org.cgfalcon.myprolang.calc;

import org.cgfalcon.myprolang.calc.lexer.Token;

/**
 * @author: Falcon
 * @date: 14-1-4.
 */
public class IntNode extends ExprNode {

    public IntNode(Token token) {
        super(token);
        this.eValTyp = EvalType.INTEGER;
    }

}
