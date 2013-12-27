package org.cgfalcon.myprolang.calc.parser;

/**
 * Created by Falcon on 13-12-27.
 */


import org.cgfalcon.myprolang.calc.Lexer;
import org.cgfalcon.myprolang.calc.Parser;

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
 *          | ID '=' expr ([+|-] expr)*
 *
 *      expr:
 *          | NUM
 *          | NUM '+' NUM
 *          | NUM '-' NUM
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


}
