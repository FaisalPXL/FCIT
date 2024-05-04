/*
GROUP PROJECT (PHASE 1)
Team members:
    Faisal Hussain      2136580
    Abdullah Alharbi    2136600
    Faisal Balkhair     2136443
Course: CPCS 302
Section: CS3
*/
package project302;
import java.io.*;
import java.util.Scanner;

public class Project302 {
    
    static char lookahead;
    static Scanner read;
    static PrintWriter write;
    private static String[] keywords = {"abstract", "boolean", "byte", "case",
        "catch", "char", "class", "continue", "default", "do", "double",
        "else", "extends", "final", "finally", "float", "for", "if",
        "implements", "import", "instanceof", "int", "interface", "long",
        "native", "new", "package", "private", "protected", "public",
        "return", "short", "static", "super", "switch", "synchronized",
        "this", "throw", "throws", "transient", "try", "void", "volatile",
        "while", "false", "true", "null"};
    

    public static void main(String[] args) throws FileNotFoundException {
        File input = new File("input.txt");
        if (!input.exists()) {// if file does not exists
            System.out.println("File does not exist");
            System.exit(0);// end the program
        }
        read = new Scanner(input);
        write = new PrintWriter(new File("result.txt"));

        while (read.hasNext()) {// while there is more elements to read
            String line = read.nextLine();
            char[] myChar = line.toCharArray();
            // call tokenizer to generate tokens
            tokenizer(myChar);
        }
        write.flush();
        read.close();
        write.close();
    }//end main

    public static void tokenizer(char[] chars) {
        //to record the position of lookahead
        int c = 0;
        String lexeme = "";
        int caseNum = 0;
        while (c < chars.length) {
            //Read one character from the chars array and store it in lookahead
            lookahead = chars[c];
            switch (caseNum) {
                case 0:
                    if (lookahead == '\r' || lookahead == '\t' || lookahead == ' ' || lookahead == '\n'){
                        caseNum = 0;
                        //skip the white space by reading another character
                        c = c + 1;
                    } 
                    else if (lookahead == '_' || Character.isLetter(lookahead)){
                        caseNum = 1;
                        lexeme += lookahead;
                        c = c + 1;
                    } 
                    else if (Character.isDigit(lookahead)){
                        caseNum = 3;
                    } 
                    else if (lookahead == ',') {
                        caseNum = 5;
                        lexeme += lookahead;
                    } 
                    else if (lookahead == ';') {
                        caseNum = 6;
                        lexeme += lookahead;
                    } 
                    else if (lookahead == ':') {
                        caseNum = 7;
                        lexeme += lookahead;
                    } 
                    else if (lookahead == '?') {
                        caseNum = 8;
                        lexeme += lookahead;
                    } 
                    else if (lookahead == '{') {
                        caseNum = 9;
                        lexeme += lookahead;
                    } 
                    else if (lookahead == '}') {
                        caseNum = 10;
                        lexeme += lookahead;
                    } 
                    else if (lookahead == '(') {
                        caseNum = 11;
                        lexeme += lookahead;
                    } 
                    else if (lookahead == ')') {
                        caseNum = 12;
                        lexeme += lookahead;
                    } 
                    else if (lookahead == '=') {
                        caseNum = 13;
                        lexeme += lookahead;
                    } 
                    else if (lookahead == '+') {
                        caseNum = 14;
                        lexeme += lookahead;
                    } 
                    else if (lookahead == '-') {
                        caseNum = 17;
                        lexeme += lookahead;
                    } 
                    else if (lookahead == '*') {
                        caseNum = 20;
                        lexeme += lookahead;
                    } 
                    else if (lookahead == '/') {
                        caseNum = 22;
                        lexeme += lookahead;
                    } 
                    else if (lookahead == '%') {
                        caseNum = 24;
                        lexeme += lookahead;
                    } 
                    else if (lookahead == '<') {
                        caseNum = 26;
                        lexeme += lookahead;
                    } 
                    else if (lookahead == '>') {
                        caseNum = 28;
                        lexeme += lookahead;
                    } 
                    else if (lookahead == '&') {
                        caseNum = 31;
                        lexeme += lookahead;
                    } 
                    else if (lookahead == '|') {
                        caseNum = 32;
                        lexeme += lookahead;
                    } 
                    else if (lookahead == '!') {
                        caseNum = 33;
                        lexeme += lookahead;
                    } 
                    else if (lookahead == '[') {
                        caseNum = 35;
                        lexeme += lookahead;
                    } 
                    else if (lookahead == ']') {
                        caseNum = 36;
                        lexeme += lookahead;
                    } 
                    else if (lookahead == '\'') {
                        caseNum = 37;
                        lexeme += lookahead;
                    } 
                    else if (lookahead == '\"') {
                        caseNum = 38;
                        lexeme += lookahead;
                    } 
                    else {
                        error();
                    }
                    break;
                    
                // Case 1 for completing ID
                case 1:
                    if (lookahead == '_' || Character.isLetter(lookahead) || Character.isDigit(lookahead)) {
                        lexeme += lookahead;//to start new lexeme
                        c = c + 1;
                        caseNum = 1;
                    } else {
                        caseNum = 2;
                    }
                    break;
                    
                //case of IDENTIFIER TOKEN
                case 2:
                    //printing IDENTIFIER TOKEN
                    //to know whether it's a keyword or identifier     
                    boolean flag = true;
                    for (int i = 0; i < keywords.length; i++) {
                        if (lexeme.equals(keywords[i])) {
                            write.println(lexeme + "\t\t" + lexeme);
                            System.out.println(lexeme + "\t\t" + lexeme);
                            caseNum = 0;
                            lexeme = "";//to start new lexeme
                            //keyword is found 
                            flag = false;
                            break;
                        }
                    }
                    //if the token is main method 
                    if (lexeme.equals("main")) {
                        write.println(lexeme + "\t\t" + lexeme);
                        System.out.println(lexeme + "\t\t" + lexeme);
                        caseNum = 0;
                        lexeme = "";//to start new lexeme
                        //main word is found
                        flag = false;
                    }
                    //if it is not a keyword tokenize it to ID
                    if (flag) {
                        write.println(lexeme + "\t\t" + "ID");
                        System.out.println(lexeme + "\t\t" + "ID");
                        caseNum = 0;
                        lexeme = "";//to start new lexeme
                    }
                    break;
                    
                case 3:
                    //if there is more than one number
                    while (Character.isDigit(lookahead)) {
                        lexeme += lookahead;
                        c = c + 1;
                        lookahead = chars[c];
                    }
                    //if it is a float 
                    if(lookahead == '.'){
                        lexeme += lookahead;
                        c = c + 1;
                        caseNum = 42;
                        break;
                    }
                    //if not a float then an int
                    else
                        caseNum = 4;
                    break;
                //case of float numbers
                case 42:
                    while (Character.isDigit(lookahead)) {
                        lexeme += lookahead;
                        c = c + 1;
                        if(c < chars.length)
                            lookahead = chars[c];
                        else
                            break;
                    }
                    write.println(lexeme + "\t\t" + "Float");
                    System.out.println(lexeme + "\t\t" + "Float");
                    caseNum = 0;
                    lexeme = "";//to start new lexeme
                    break;
                    
                //case of int numbers
                case 4:
                    write.println(lexeme + "\t\t" + "Int_Literal");
                    System.out.println(lexeme + "\t\t" + "Int_Literal");
                    caseNum = 0;
                    lexeme = "";//to start new lexeme
                    break;
                    
                //case of comma
                case 5:
                    write.println(lexeme + "\t\t" + "COMMA");
                    System.out.println(lexeme + "\t\t" + "COMMA");
                    caseNum = 0;
                    lexeme = "";//to start new lexeme
                    c = c + 1;
                    break;
                    
                //case of semi colon
                case 6:
                    write.println(lexeme + "\t\t" + "Semi_Colon");
                    System.out.println(lexeme + "\t\t" + "Semi_Colon");
                    caseNum = 0;
                    lexeme = "";//to start new lexeme
                    c = c + 1;
                    break;
                    
                //case of colon
                case 7:
                    write.println(lexeme + "\t\t" + "COLON");
                    System.out.println(lexeme + "\t\t" + "COLON");
                    caseNum = 0;
                    lexeme = "";//to start new lexeme
                    c = c + 1;
                    break;
                    
                //case of question mark
                case 8:
                    write.println(lexeme + "\t\t" + "Question_Mark");
                    System.out.println(lexeme + "\t\t" + "Question_Mark");
                    caseNum = 0;
                    lexeme = "";//to start new lexeme
                    c = c + 1;
                    break;
                    
                //case of left curly
                case 9:
                    write.println(lexeme + "\t\t" + "Left_Curly");
                    System.out.println(lexeme + "\t\t" + "Left_Curly");
                    caseNum = 0;
                    lexeme = "";//to start new lexeme
                    c = c + 1;
                    break;
                    
                //case of right curly
                case 10:
                    write.println(lexeme + "\t\t" + "Right_Curly");
                    System.out.println(lexeme + "\t\t" + "Right_Curly");
                    caseNum = 0;
                    lexeme = "";//to start new lexeme
                    c = c + 1;
                    break;
                    
                //case of left parentheses
                case 11:
                    write.println(lexeme + "\t\t" + "Left_Paren");
                    System.out.println(lexeme + "\t\t" + "Left_Paren");
                    caseNum = 0;
                    lexeme = "";//to start new lexeme
                    c = c + 1;
                    break;
                    
                //case of right parentheses
                case 12:
                    write.println(lexeme + "\t\t" + "Right_Paren");
                    System.out.println(lexeme + "\t\t" + "Right_Paren");
                    caseNum = 0;
                    lexeme = "";//to start new lexeme
                    c = c + 1;
                    break;
                    
                //case of assign operator
                case 13:
                    c = c + 1;
                    lookahead = chars[c];
                    //if there is == then it is equality
                    if (lookahead == '=') {
                        lexeme += lookahead;
                        caseNum = 30;
                        break;
                    }
                    write.println(lexeme + "\t\t" + "Assign_Op");
                    System.out.println(lexeme + "\t\t" + "Assign_Op");
                    caseNum = 0;
                    lexeme = "";//to start new lexeme
                    //c = c + 1;
                    break;
                    
                //case of addition
                case 14:
                    c = c+1;
                    lookahead = chars[c];
                    //if there is ++ then it is incremint
                    if (lookahead == '+') {
                        lexeme += lookahead;
                        caseNum = 15;
                        break;
                    }
                    //if there is += then it is addition assignment
                    if (lookahead == '=') {
                        lexeme += lookahead;
                        caseNum = 16;
                        break;
                    }
                    write.println(lexeme + "\t\t" + "Add_Op");
                    System.out.println(lexeme + "\t\t" + "Add_Op");
                    caseNum = 0;
                    lexeme = "";//to start new lexeme
                    break;
                    
                //case of increment
                case 15:
                    write.println(lexeme + "\t\t" + "Inc_Op");
                    System.out.println(lexeme + "\t\t" + "Inc_Op");
                    caseNum = 0;
                    lexeme = "";//to start new lexeme
                    c = c + 1;
                    break;
                    
                //case of addition assingnment
                case 16:
                    write.println(lexeme + "\t\t" + "Add_Assignment");
                    System.out.println(lexeme + "\t\t" + "Add_Assignment");
                    caseNum = 0;
                    lexeme = "";//to start new lexeme
                    c = c + 1;
                    break;
                    
                //case of subtraction
                case 17:
                    c = c + 1;
                    lookahead = chars[c];
                    //if there is -- then it is decrement
                    if (lookahead == '-') {
                        lexeme += lookahead;
                        caseNum = 18;
                        break;
                    }
                    //if there is -= then it is subtraction assignment
                    if (lookahead == '=') {
                        lexeme += lookahead;
                        caseNum = 19;
                        break;
                    }
                    write.println(lexeme + "\t\t" + "Sub_Op");
                    System.out.println(lexeme + "\t\t" + "Sub_Op");
                    caseNum = 0;
                    lexeme = "";//to start new lexeme
                    break;
                    
                //case of decrement 
                case 18:
                    write.println(lexeme + "\t\t" + "Dec_Op");
                    System.out.println(lexeme + "\t\t" + "Dec_Op");
                    caseNum = 0;
                    lexeme = "";//to start new lexeme
                    c = c + 1;
                    break;
                    
                //case of subtraction assignment
                case 19:
                    write.println(lexeme + "\t\t" + "Sub_Assignment");
                    System.out.println(lexeme + "\t\t" + "Sub_Assignment");
                    caseNum = 0;
                    lexeme = "";//to start new lexeme
                    c = c + 1;
                    break;
                    
                //case of multiplication
                case 20:
                    c = c + 1;
                    lookahead = chars[c];
                    //if there is *= then it is multiplication assignment
                    if (lookahead == '=') {
                        lexeme += lookahead;
                        caseNum = 21;
                        break;
                    }
                    //if there is */ then it is end of a multi line comment
                    else if(lookahead == '/'){
                        lexeme += lookahead;
                        caseNum = 41;
                        break;
                    }
                    write.println(lexeme + "\t\t" + "Mul_Op");
                    System.out.println(lexeme + "\t\t" + "Mul_Op");
                    caseNum = 0;
                    lexeme = "";//to start new lexeme
                    break;
                
                //case of multiplication assignment
                case 21:
                    write.println(lexeme + "\t\t" + "Mul_Assignment");
                    System.out.println(lexeme + "\t\t" + "Mul_Assignment");
                    caseNum = 0;
                    lexeme = "";//to start new lexeme
                    c = c + 1;
                    break;
                
                //case of ending a multi line comment
                case 41:
                    write.println(lexeme + "\t\t" + "End_Multi_line_comment");
                    System.out.println(lexeme + "\t\t" + "End_Multi_line_comment");
                    caseNum = 0;
                    lexeme = "";//to start new lexeme
                    c = c + 1;
                    break;
                    
                //case of division
                case 22:
                    c = c + 1;
                    lookahead = chars[c];
                    //if there is /= then it is division assignment
                    if (lookahead == '=') {
                        lexeme += lookahead;
                        caseNum = 23;
                        break;
                    }
                    //if there is // then it is a single line comment
                    else if(lookahead == '/'){
                        lexeme += lookahead;
                        caseNum = 39;
                        break;
                    }
                    //if there is /* then it is a start of a multi line comment
                    else if(lookahead == '*'){
                        lexeme += lookahead;
                        caseNum = 40;
                        break;
                    }
                    write.println(lexeme + "\t\t" + "Div_Op");
                    System.out.println(lexeme + "\t\t" + "Div_Op");
                    caseNum = 0;
                    lexeme = "";//to start new lexeme
                    break;
                    
                //case of division assignment
                case 23:
                    write.println(lexeme + "\t\t" + "Div_Assignment");
                    System.out.println(lexeme + "\t\t" + "Div_Assignment");
                    caseNum = 0;
                    lexeme = "";//to start new lexeme
                    c = c + 1;
                    break;
                    
                //case of single line comment
                case 39:
                    write.println(lexeme + "\t\t" + "Single_line_comment");
                    System.out.println(lexeme + "\t\t" + "Single_line_comment");
                    caseNum = 0;
                    lexeme = "";//to start new lexeme
                    c = c + 1;
                    break;
                    
                //case of starting a multi line comment
                case 40:
                    write.println(lexeme + "\t\t" + "Start_Multi_line_comment");
                    System.out.println(lexeme + "\t\t" + "Start_Multi_line_comment");
                    caseNum = 0;
                    lexeme = "";//to start new lexeme
                    c = c + 1;
                    break;
                    
                //case of remender
                case 24:
                    c = c + 1;
                    lookahead = chars[c];
                    //if there is %= then it is remender assignment 
                    if (lookahead == '=') {
                        lexeme += lookahead;
                        caseNum = 25;
                        break;
                    }
                    write.println(lexeme + "\t\t" + "Rem_Op");
                    System.out.println(lexeme + "\t\t" + "Rem_Op");
                    caseNum = 0;
                    lexeme = "";//to start new lexeme
                    break;
                    
                //case of remender assignment
                case 25:
                    write.println(lexeme + "\t\t" + "Rem_Assignment");
                    System.out.println(lexeme + "\t\t" + "Rem_Assignment");
                    caseNum = 0;
                    lexeme = "";//to start new lexeme
                    c = c + 1;
                    break;
                    
                //case of lessthan
                case 26:
                    c = c + 1;
                    lookahead = chars[c];
                    //if there is <= then it is lessthan or equal   
                    if (lookahead == '=') {
                        lexeme += lookahead;
                        caseNum = 27;
                        break;
                    }
                    write.println(lexeme + "\t\t" + "Lessthan_Op");
                    System.out.println(lexeme + "\t\t" + "Lessthan_Op");
                    caseNum = 0;
                    lexeme = "";//to start new lexeme
                    c = c + 1;
                    break;
                    
                //case of lessthan or equal
                case 27:
                    write.println(lexeme + "\t\t" + "Lessthan_or_equal_Op");
                    System.out.println(lexeme + "\t\t" + "Lessthan_or_equal_Op");
                    caseNum = 0;
                    lexeme = "";//to start new lexeme
                    c = c + 1;
                    break;
                    
                //case of greaterthan
                case 28:
                    c = c + 1;
                    lookahead = chars[c];
                    //if there is >=  then it is greaterthan or equal 
                    if (lookahead == '=') {
                        lexeme += lookahead;
                        caseNum = 29;
                        break;
                    }
                    write.println(lexeme + "\t\t" + "Greaterthan_Op");
                    System.out.println(lexeme + "\t\t" + "Greaterthan_Op");
                    caseNum = 0;
                    lexeme = "";//to start new lexeme
                    c = c + 1;
                    break;
                    
                //case of greaterthan or equal
                case 29:
                    write.println(lexeme + "\t\t" + "Greaterthan_or_equal_Op");
                    System.out.println(lexeme + "\t\t" + "Greaterthan_or_equal_Op");
                    caseNum = 0;
                    lexeme = "";//to start new lexeme
                    c = c + 1;
                    break;
                    
                //case of equalety
                case 30:
                    write.println(lexeme + "\t\t" + "Equal_to_Op");
                    System.out.println(lexeme + "\t\t" + "Equal_to_Op");
                    caseNum = 0;
                    lexeme = "";//to start new lexeme
                    c = c + 1;
                    break;
                
                //case of logical AND
                case 31:
                    //if there is one & in the end of the line, it is an error
                    c = c + 1;
                    if (c < chars.length){
                        lookahead = chars[c];
                    }
                    else{
                        error();
                    }
                    if (lookahead == '&') {
                        lexeme += lookahead;
                        write.println(lexeme + "\t\t" + "Log_AND_Op");
                        System.out.println(lexeme + "\t\t" + "Log_AND_Op");
                        caseNum = 0;
                        lexeme = "";//to start new lexeme
                        c = c + 1;
                    }
                    //if there is one & it is an error
                    else{
                        error();
                    }
                    break;
                
                //case of logical OR
                case 32:
                    //if there is one | in the end of the line, it is an error
                    c = c + 1;
                    if (c < chars.length){
                        lookahead = chars[c];
                    }
                    else{
                        error();
                    }
                    if (lookahead == '|') {
                        lexeme += lookahead;
                        write.println(lexeme + "\t\t" + "Log_OR_Op");
                        System.out.println(lexeme + "\t\t" + "Log_OR_Op");
                        caseNum = 0;
                        lexeme = "";//to start new lexeme  
                        c = c + 1;
                    }
                    //if there is one | it is an error
                    else{
                        error();
                    }
                    break;
                    
                //case of logical NOT
                case 33:
                    c = c + 1;
                    lookahead = chars[c];
                    //if there is != then it is NOT EQUAL
                    if (lookahead == '=') {
                        lexeme += lookahead;
                        caseNum = 34;
                        break;
                    }
                    write.println(lexeme + "\t\t" + "Log_NOT_Op");
                    System.out.println(lexeme + "\t\t" + "Log_NOT_Op");
                    caseNum = 0;
                    lexeme = "";//to start new lexeme
                    break;
                    
                //case of NOT EQUAL
                case 34:
                    write.println(lexeme + "\t\t" + "Not_equal_to_Op");
                    System.out.println(lexeme + "\t\t" + "Not_equal_to_Op");
                    caseNum = 0;
                    lexeme = "";//to start new lexeme
                    c = c + 1;
                    break;
                    
                //case of left square bracket
                case 35:
                    write.println(lexeme + "\t\t" + "Left_Square_bracket");
                    System.out.println(lexeme + "\t\t" + "Left_Square_bracket");
                    caseNum = 0;
                    lexeme = "";//to start new lexeme
                    c = c + 1;
                    break;
                    
                //case of right square bracket
                case 36:
                    write.println(lexeme + "\t\t" + "Right_Square_bracket");
                    System.out.println(lexeme + "\t\t" + "Right_Square_bracket");
                    caseNum = 0;
                    lexeme = "";//to start new lexeme
                    c = c + 1;
                    break;
                    
                //case of apostrothes
                case 37:
                    write.println(lexeme + "\t\t" + "Apostrophes");
                    System.out.println(lexeme + "\t\t" + "Apostrophes");
                    caseNum = 0;
                    lexeme = "";//to start new lexeme
                    c = c + 1;
                    break;
                    
                //case of quotation mark
                case 38:
                    write.println(lexeme + "\t\t" + "Quotation_mark");
                    System.out.println(lexeme + "\t\t" + "Quotation_mark");
                    caseNum = 0;
                    lexeme = "";//to start new lexeme
                    c = c + 1;
                    break;
            }//end of main switch
        }
    }//end of tokenizer
    
    //error method if there is an unrecognized token 
    public static void error(){
        System.err.println("UNRECOGNIZED_TOKEN");
        write.println("UNRECOGNIZED_TOKEN");
        write.flush();
        write.close();
        read.close();
        System.exit(0);
    }
}//end class
