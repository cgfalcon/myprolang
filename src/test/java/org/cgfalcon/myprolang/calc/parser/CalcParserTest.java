package org.cgfalcon.myprolang.calc.parser;

import org.cgfalcon.myprolang.calc.Lexer;
import org.cgfalcon.myprolang.calc.lexer.CalcLexer;
import org.junit.Test;

/**
 * @author: Falcon
 * @date: 13-12-29.
 */
public class CalcParserTest {

    private CalcParser parser;



    @Test
    public void testCalcAssign() throws Exception {
        String line = "x = 10+9";
        Lexer lexer = new CalcLexer(line);
        parser = new CalcParser(lexer);

        parser.calc();
    }

    @Test
    public void testCalcExpr() {
        String line = "10";
        Lexer lexer = new CalcLexer(line);
        parser = new CalcParser(lexer);

        parser.calc();
    }
}
