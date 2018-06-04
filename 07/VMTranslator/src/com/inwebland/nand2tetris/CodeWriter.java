package com.inwebland.nand2tetris;

import java.io.*;

public class CodeWriter {
    private BufferedWriter writer;

    private static final int SP_LOC = 0;
    private static final int STACK_LOC = 256;
    private static final int HEAP_LOC = 2048;
    private static final int IO_LOC = 16384;

    public CodeWriter(File output){
        try {
            FileOutputStream fos = new FileOutputStream(output);
            writer = new BufferedWriter(new OutputStreamWriter(fos));
            writeLine("// Created by Xi Jin");
        }
        catch(IOException exception){
            System.out.println(exception.getMessage());
            System.exit(1);
        }
    }

    private void writeLine(String line){
        try {
            writer.write(line + "\r\n");
        }
        catch(IOException exception){
            System.out.println(exception.getMessage());
            System.exit(1);
        }
    }

    public void writeArithmetic(String command){
        writeLine("// arithmetic: " + command);
        // get the last stack elem in register d
        // sp--
        writeLine("@" + SP_LOC);
        writeLine("M=M-1");
        writeLine("A=M");
        writeLine("D=M");

        switch (command){
            case "add":
                writeLine("@" + SP_LOC);
                writeLine("A=M-1");
                writeLine("M=M-D");
            case "sub":
            case "neg":
            case "eq":
            case "gt":
            case "lt":
            case "and":
            case "or":
            case "not":
                break;
        }

    }

    public void writePushPop(CommandType command, String segment, int index){
        writeLine("// " + command + " " + segment + " " + index);
        // push
        if(command == CommandType.C_PUSH){
            switch (segment){
                case "constant":
                    writeLine("@" + index);
                    writeLine("D=A");
                    // *sp = index
                    writeLine("@" + SP_LOC);
                    writeLine("A=M");
                    writeLine("M=D");
                    // sp++
                    writeLine("@" + SP_LOC);
                    writeLine("M=M+1");
            }
        }
    }

    public void close(){
        try {
            writer.close();
        }
        catch(IOException exception){
            System.out.println(exception.getMessage());
            System.exit(1);
        }
    }
}
