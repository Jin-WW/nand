package com.inwebland.nand2tetris;

import java.io.*;

import static com.inwebland.nand2tetris.CommandType.*;

public class VMTranslator {
    private Parser parser;
    private CodeWriter codeWriter;

    private VMTranslator(String relativeFileName){
        File file = new File(relativeFileName);
        String outputFileName = relativeFileName.replace(".vm", ".asm");
        parser = new Parser(file);
        codeWriter = new CodeWriter(new File(outputFileName));
        codeWriter.setFileName(relativeFileName.replace(".vm", ""));
        while (parser.hasMoreCommands()){
            CommandType commandType = parser.commandType();
            switch (commandType){
                case C_POP:
                case C_PUSH:
                    codeWriter.writePushPop(commandType, parser.arg1(), parser.arg2());
                    break;
                case C_ARITHMETIC:
                    // arithmetic only have one literal which is command
                    codeWriter.writeArithmetic(parser.getLine());
                    break;
                default:
                    throw new RuntimeException("Cannot process command: " + commandType);
            }
            parser.advance();
        }
        codeWriter.close();
    }

    public static void main(String[] args) {
        String relativeFileName = args[0];
        new VMTranslator(relativeFileName);
    }
}
