package org.cgfalcon.myprolang.calc.lexer;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;


public class CalcLexerTest {
    private CalcLexer lexer;


    @Before
    public void setUp() throws Exception {
        lexer = new CalcLexer("1+2");
    }

    @Test
    public void testGetTokenSimple() throws Exception {

        lexer.setLine("1+2");

        Token token = lexer.nextToken();
        assertEquals(new Token(0, 1, "1", TokenType.NUM), token);

        Token tokenOp = lexer.nextToken();
        assertEquals(1, tokenOp.getPos());
        assertEquals(1, tokenOp.getLine());
        assertEquals(TokenType.OP_ADD_TOKEN, tokenOp.getKind());

        Token tokenOperand2 = lexer.nextToken();
        assertEquals(new Token(2, 1, "2", TokenType.NUM), tokenOperand2);

        Token tokenEndLine = lexer.nextToken();
        assertEquals(2, tokenEndLine.getPos());
        assertEquals(1, tokenEndLine.getLine());
        assertEquals(TokenType.EOF, tokenEndLine.getKind());
    }

    @Test
    public void tesLexerComplex1() throws Exception {
        String line = "34\n";
        lexer.setLine(line);

        Token token = lexer.nextToken();
        assertEquals(new Token(0, 1, "34", TokenType.NUM), token);

        String line2 = "34.9384 0.4\n";
        lexer.setLine(line2);

        Token token2 = lexer.nextToken();
        assertEquals(new Token(0, 1, "34.9384", TokenType.NUM), token2);

        Token token3 = lexer.nextToken();
        assertEquals(new Token(8, 1, "0.4", TokenType.NUM), token3);

    }

    @Test
    public void testAdd() throws Exception {
        String line = "45.3 + 0.23\n";
        lexer.setLine(line);

        Token token = lexer.nextToken();
        assertEquals(new Token(0, 1, "45.3", TokenType.NUM), token);

        Token tokenAdd = lexer.nextToken();
        assertEquals(5, tokenAdd.getPos());
        assertEquals(TokenType.OP_ADD_TOKEN, tokenAdd.getKind());

        Token tokenOperand = lexer.nextToken();
        assertEquals(new Token(7, 1, "0.23", TokenType.NUM), tokenOperand);

    }


    @Test
    public void testID() {
        String line = "dbd";
        lexer.setLine(line);

        Token token = lexer.nextToken();
        assertEquals(new Token(0, 1, "dbd", TokenType.ID), token);
    }

    @Test
    public void testAssign() {
       String line = "x = 10";
        lexer.setLine(line);

        Token tokenX = lexer.nextToken();
        assertEquals(new Token(0, 1, "x", TokenType.ID), tokenX);

        Token tokenEqual = lexer.nextToken();
        assertEquals(new Token(2, 1, "=", TokenType.EQUAL), tokenEqual);

        Token tokenNum = lexer.nextToken();
        assertEquals(new Token(4, 1, "10", TokenType.NUM), tokenNum);
    }
}
