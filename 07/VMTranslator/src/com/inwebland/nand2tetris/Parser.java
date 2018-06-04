package com.inwebland.nand2tetris;

import java.io.*;

public class Parser {
    private BufferedReader reader;
    private Command nextCommand;
    private boolean ended;
    private boolean processed;

    class Command {
        CommandType type;
        String arg1;
        Integer arg2;
        String line;
        private Command(CommandType type, String arg1, Integer arg2, String line){
            this.type = type;
            this.arg1 = arg1;
            this.arg2 = arg2;
            this.line = line;
        }
    }

    public Parser(File file){
        ended = false;
        processed = true;
        try {
            FileInputStream fis = new FileInputStream(file);
            reader = new BufferedReader(new InputStreamReader(fis));
        }
        catch(IOException exception){
            System.out.println(exception.getMessage());
            System.exit(1);
        }
    }

    public boolean hasMoreCommands(){
        if(!processed){
            return true;
        }
        else if(ended){
            return false;
        }

        try {
            String line;
            do {
                line = reader.readLine();
                if(line == null){
                    ended = true;
                    nextCommand = null;
                    // close reader here
                    close();
                    return false;
                }
            }
            while(isComment(line));
            nextCommand = parseLine(line);
            processed = false;
            return true;
        }
        catch(IOException exception){
            System.out.println(exception.getMessage());
            return false;
        }
    }

    public void advance(){
        processed = true;
    }

    public CommandType commandType(){
        if(nextCommand == null){
            return null;
        }
        return nextCommand.type;
    }

    public String arg1(){
        if(nextCommand == null){
            return null;
        }
        return nextCommand.arg1;
    }

    public int arg2(){
        if(nextCommand == null){
            return -1;
        }
        return nextCommand.arg2;
    }

    public String getLine(){
        return nextCommand.line;
    }

    private boolean isComment(String line){
        return line.matches("(^\\s*\\/\\/.*||\\s*)");
    }

    private String trim(String line){
        String commentRemoved = line.replaceAll("\\/\\/.*$", "");
        return commentRemoved.trim();
    }

    private Command parseLine(String line){
        line = trim(line);
        String[] literals = line.split(" ");
        String firstLiteral = literals[0];
        CommandType type = null;
        String arg1 = null;
        Integer arg2 = null;
        switch (firstLiteral) {
            case "push":
                type = CommandType.C_PUSH;
                arg1 = literals[1];
                arg2 = Integer.parseInt(literals[2]);
                break;
            case "pop":
                type = CommandType.C_POP;
                arg1 = literals[1];
                arg2 = Integer.parseInt(literals[2]);
                break;
            case "add":
            case "sub":
            case "neg":
            case "eq":
            case "gt":
            case "lt":
            case "and":
            case "or":
            case "not":
                type = CommandType.C_ARITHMETIC;
                break;

            default:
                throw new IllegalArgumentException("Cannot parse line: " + line);
        }

        return new Command(type, arg1, arg2, line);
    }

    private void close(){
        try {
            reader.close();
        }
        catch(IOException exception){
            System.out.println(exception.getMessage());
            System.exit(1);
        }
    }
}
