package org.cgfalcon.myprolang.antlr.graphics;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CommonTokenStream;

import java.io.IOException;

/**
 * @author: Falcon
 * @date: 14-1-4.
 */
public class Main {


    public static void main(String[] args) throws IOException {

        CharStream input = new ANTLRInputStream(System.in);

        GraphicsLexer lexer = new GraphicsLexer(input);
        GraphicsParser parser = new GraphicsParser(new CommonTokenStream(lexer));
        parser.file();

    }
}
