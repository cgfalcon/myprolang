package org.cgfalcon.myprolang.calc;

import org.cgfalcon.myprolang.calc.lexer.Token;

/**
 * @author: Falcon
 * @date: 14-1-4.
 */
public abstract class ExprNode extends AST {


    protected EvalType eValTyp;

    public ExprNode(Token token) {
        super(token);
    }

    protected EvalType getEvalType() {
        return eValTyp;
    }

    public String toString() {
        if (eValTyp != EvalType.INVALID) {
            return super.toString() + String.format("<Type=%s>", getEvalType());
        }
        return this.toString();
    }

    enum EvalType {
        INVALID,
        INTEGER,
        VECTOR
    }
}
