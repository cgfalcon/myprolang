package org.cgfalcon.myprolang.calc;

import org.cgfalcon.myprolang.calc.lexer.Token;
import org.cgfalcon.myprolang.calc.lexer.TokenType;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: Falcon
 * @date: 14-1-4.
 */

/**
 * Homogeneous AST
 */
public class AST {

    private Token token;
    private List<AST> children;

    public AST() {

    }

    public AST(Token token) {
        this.token = token;
    }

    public void addChild(AST child) {
        if (children == null) {
            children = new ArrayList<AST>();
        }
        children.add(child);
    }


    public boolean isNil() {
        return token == null;
    }

    public String toString() {
        return token == null ? "nil" : token.toString();
    }

    public String toStringTree() {
        if (children == null || children.size() == 0) {
            return this.toString();
        }
        StringBuilder treeToString = new StringBuilder();
        if (!isNil()) {
            treeToString.append("( ");
            treeToString.append(toString());
            treeToString.append(" ");
        }


        for (int i = 0; i < children.size(); i++) {
            AST subTree = children.get(i);
            if (i > 0) {
                treeToString.append(" ");
            }
            treeToString.append(subTree.toStringTree());
        }

        if (!isNil()) {

            treeToString.append(" )");
        }
        return treeToString.toString();
    }

    public static void main(String[] args) {
        Token one = new Token("1", TokenType.NUM);
        Token two = new Token("2", TokenType.NUM);
        Token add = new Token("+", TokenType.OP_ADD_TOKEN);

        AST root = new AST(add);
        root.addChild(new AST(one));
        root.addChild(new AST(two));

        System.out.println("1 + 2: " + root.toStringTree());

        AST list= new AST();
        list.addChild(new AST(one));
        list.addChild(new AST(two));

        System.out.println("list: " + list.toStringTree());

    }
}
