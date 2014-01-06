package org.cgfalcon.myprolang.calc;

import org.cgfalcon.myprolang.calc.lexer.Token;

import java.util.List;

/**
 * @author: Falcon
 * @date: 14-1-4.
 */
public class VectorNode extends ExprNode {

    public VectorNode(Token token) {
        super(token);
        this.eValTyp = EvalType.VECTOR;
    }

    public VectorNode(Token token, List<ExprNode> elements) {
        super(token);
        this.eValTyp = EvalType.VECTOR;
        if (elements != null) {
            for (ExprNode exprNode : elements) {
                addChild(exprNode);
            }
        }
    }
}
