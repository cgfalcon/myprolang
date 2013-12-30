package org.cgfalcon.myprolang.calc.lexer;

/**
 * Created by Falcon on 13-12-21.
 */
public enum TokenType {

    EOF("EOF"),
    LEFT_BRAKET("("),
    RIGHT_BRAKET(")"),

    NUM("NUM"),
    OP_ADD_TOKEN("+"),
    ID("ID"),
    OP_MUL_TOKEN("*"),
    OP_SUB_TOKEN("-"),
    SEMI(";"),
    EQUAL("=");

    private TokenType(String token) {
        this.name = token;
    }

    private String name;
}
