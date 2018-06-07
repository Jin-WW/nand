package com.inwebland.nand2tetris;

import java.io.*;

public class CodeWriter {
    private BufferedWriter writer;

    private int labelCounter = 0;
    private int variableCounter = 0;
    private static final int SP_LOC = 0;
    private static final int LCL_LOC = 1;
    private static final int ARG_LOC = 2;
    private static final int THIS_LOC = 3;
    private static final int THAT_LOC = 4;

    private static final int TEMP_BASE = 5;


    private static final int STACK_LOC = 256;
    private static final int HEAP_LOC = 2048;
    private static final int IO_LOC = 16384;
    private static final int TRUE = -1;
    private static final int FALSE = 0;

    private String fileName;

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

    public void setFileName(String fileName){
        this.fileName = fileName;
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
                writeLine("M=M+D");
                break;
            case "sub":
                writeLine("@" + SP_LOC);
                writeLine("A=M-1");
                writeLine("M=M-D");
                break;
            case "neg":
                writeLine("@" + SP_LOC);
                writeLine("M=M+1");
                writeLine("A=M-1");
                writeLine("M=0");
                writeLine("M=M-D");
                break;
            case "eq":
                String ifEqualLabel = "IF_EQUAL." + labelCounter;
                labelCounter++;
                String endEqualLabel = "IF_END." + labelCounter;
                labelCounter++;

                writeLine("@" + SP_LOC);
                writeLine("M=M-1");
                writeLine("A=M");
                writeLine("D=M-D");
                writeLine("@" + ifEqualLabel);
                writeLine("D;JEQ");
                writeLine("D=" + FALSE);
                writeLine("@" + endEqualLabel);
                writeLine("0;JMP");
                writeLine("(" + ifEqualLabel + ")");
                writeLine("D=" + TRUE);
                writeLine("(" + endEqualLabel + ")");
                writeLine("@" + SP_LOC);
                writeLine("M=M+1");
                writeLine("A=M-1");
                writeLine("M=D");
                break;
            case "gt":
                String ifGreaterLabel = "IF_GREATER." + labelCounter;
                labelCounter++;
                String greaterEndEqualLabel = "IF_END." + labelCounter;
                labelCounter++;

                writeLine("@" + SP_LOC);
                writeLine("M=M-1");
                writeLine("A=M");
                writeLine("D=M-D");
                writeLine("@" + ifGreaterLabel);
                writeLine("D;JGT");
                writeLine("D=" + FALSE);
                writeLine("@" + greaterEndEqualLabel);
                writeLine("0;JMP");
                writeLine("(" + ifGreaterLabel + ")");
                writeLine("D=" + TRUE);
                writeLine("(" + greaterEndEqualLabel + ")");
                writeLine("@" + SP_LOC);
                writeLine("M=M+1");
                writeLine("A=M-1");
                writeLine("M=D");
                break;
            case "lt":
                String ifLesserLabel = "IF_LESSER." + labelCounter;
                labelCounter++;
                String lesserEndEqualLabel = "IF_END." + labelCounter;
                labelCounter++;

                writeLine("@" + SP_LOC);
                writeLine("M=M-1");
                writeLine("A=M");
                writeLine("D=M-D");
                writeLine("@" + ifLesserLabel);
                writeLine("D;JLT");
                writeLine("D=" + FALSE);
                writeLine("@" + lesserEndEqualLabel);
                writeLine("0;JMP");
                writeLine("(" + ifLesserLabel + ")");
                writeLine("D=" + TRUE);
                writeLine("(" + lesserEndEqualLabel + ")");
                writeLine("@" + SP_LOC);
                writeLine("M=M+1");
                writeLine("A=M-1");
                writeLine("M=D");
                break;
            case "and":
                writeLine("@" + SP_LOC);
                writeLine("A=M-1");
                writeLine("M=D&M");
                break;
            case "or":
                writeLine("@" + SP_LOC);
                writeLine("A=M-1");
                writeLine("M=D|M");
                break;
            case "not":
                writeLine("@" + SP_LOC);
                writeLine("M=M+1");
                writeLine("A=M-1");
                writeLine("M=!D");
                break;
        }

    }

    private int getBase(String segment){
        switch (segment) {
            case "local":
                return LCL_LOC;
            case "argument":
                return ARG_LOC;
            case "this":
                return THIS_LOC;
            case "that":
                return THAT_LOC;
            default:
                throw new IllegalArgumentException("Unregistered segment");
        }
    }

    public void writePushPop(CommandType command, String segment, int index){
        writeLine("// " + command + " " + segment + " " + index);
        // push
        if(command == CommandType.C_PUSH){
            // write value to d
            switch (segment){
                case "local":
                case "argument":
                case "this":
                case "that":
                    writeLine("@" + index);
                    writeLine("D=A");
                    writeLine("@" + getBase(segment));
                    writeLine("A=M+D");
                    writeLine("D=M");
                    break;
                case "constant":
                    writeLine("@" + index);
                    writeLine("D=A");
                    break;
                case "static":
                    writeLine("@" + fileName + "." + index);
                    writeLine("D=M");
                    break;
                case "temp":
                    writeLine("@" + (TEMP_BASE + index));
                    writeLine("D=M");
                    break;
                case "pointer":
                    // index | target
                    //   0   | this
                    //   1   | that
                    if(index == 0){
                        writeLine("@" + THIS_LOC);
                    }
                    else if(index == 1){
                        writeLine("@" + THAT_LOC);
                    }
                    writeLine("D=M");
                    break;
            }
            // sp++
            writeLine("@" + SP_LOC);
            writeLine("M=M+1;");
            // *(sp - 1) = D
            writeLine("A=M-1");
            writeLine("M=D");
        }
        else{ // pop
            // sp--
            writeLine("@" + SP_LOC);
            writeLine("M=M-1;");
            // *(sp) = D
            writeLine("A=M");
            writeLine("D=M");
            switch (segment){
                case "local":
                case "argument":
                case "this":
                case "that":
                    writeLine("@popSegmentValue");
                    writeLine("M=D");
                    writeLine("@" + index);
                    writeLine("D=A");
                    writeLine("@" + getBase(segment));
                    writeLine("D=M+D");
                    writeLine("@popSegmentAddress");
                    writeLine("M=D");
                    writeLine("@popSegmentValue");
                    writeLine("D=M");
                    writeLine("@popSegmentAddress");
                    writeLine("A=M");
                    writeLine("M=D");
                    break;
                case "static":
                    writeLine("@" + fileName + "." + index);
                    writeLine("M=D");
                    break;
                case "temp":
                    writeLine("@" + (TEMP_BASE + index));
                    writeLine("M=D");
                    break;
                case "pointer":
                    // index | target
                    //   0   | this
                    //   1   | that
                    if(index == 0){
                        writeLine("@" + THIS_LOC);
                    }
                    else if(index == 1){
                        writeLine("@" + THAT_LOC);
                    }
                    writeLine("M=D");
                    break;
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
