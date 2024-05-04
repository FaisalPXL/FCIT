/*
COURSE: CPCS-302
SECTION: CS3
GROUP PROJECT PART 3
STUDENTS:
    Faisal Alragheeb    2136580
    Abdullah Alharbi    2136600
    Faisal Balkhair     2136412
 */

import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Stack;

public class SLRParser {
    //initialization  Variables
    Scanner scanner;
    Stack stack;
    String readInput;
    String action;
    String input1[];
    String input2[];
    String grammarRule;
    String symbols[];
    String[] symbolsCopy;
    String state;
    String act;
    String space = "";

    //-------------------------------------------------------------------------------------------------
    // main method
    public static void main(String[] args) {
        //creating an object of class SLRParser
        SLRParser SLR = new SLRParser();
        SLR.start();
    }

    //-------------------------------------------------------------------------------------------------
    // grammar Hash table
    public static final HashMap<String,String> grammar = new HashMap<String,String>();

    static {
        grammar.put("0", "T' ---> T");
        grammar.put("1", "T ---> R");
        grammar.put("2", "T ---> a T c");
        grammar.put("3", "R ---> e");
        grammar.put("4", "R ---> b R");
    }

    //-------------------------------------------------------------------------------------------------
    // Saving SLR parse table in a Hash map
    public static final HashMap<String,String> parseTable = new HashMap<String, String>();

    static {
        // Row #0
        parseTable.put("0a", "s3");
        parseTable.put("0b", "s4");
        parseTable.put("0c", "r3");
        parseTable.put("0$", "r3");
        parseTable.put("0T", "1");
        parseTable.put("0R", "2");

        // Row #1
        parseTable.put("1a", "Syntax Error");
        parseTable.put("1b", "Syntax Error");
        parseTable.put("1c", "Syntax Error");
        parseTable.put("1$", "Accept");
        parseTable.put("1T", "Syntax Error");
        parseTable.put("1R", "Syntax Error");

        // Row #2
        parseTable.put("2a", "Syntax Error");
        parseTable.put("2b", "Syntax Error");
        parseTable.put("2c", "r1");
        parseTable.put("2$", "r1");
        parseTable.put("2T", "Syntax Error");
        parseTable.put("2R", "Syntax Error");

        // Row #3
        parseTable.put("3a", "s3");
        parseTable.put("3b", "s4");
        parseTable.put("3c", "r3");
        parseTable.put("3$", "r3");
        parseTable.put("3T", "5");
        parseTable.put("3R", "2");

        // Row #4
        parseTable.put("4a", "Syntax Error");
        parseTable.put("4b", "s4");
        parseTable.put("4c", "r3");
        parseTable.put("4$", "r3");
        parseTable.put("4T", "Syntax Error");
        parseTable.put("4R", "6");

        // Row #5
        parseTable.put("5a", "Syntax Error");
        parseTable.put("5b", "Syntax Error");
        parseTable.put("5c", "s7");
        parseTable.put("5$", "Syntax Error");
        parseTable.put("5T", "Syntax Error");
        parseTable.put("5R", "Syntax Error");

        // Row #6
        parseTable.put("6a", "Syntax Error");
        parseTable.put("6b", "Syntax Error");
        parseTable.put("6c", "r4");
        parseTable.put("6$", "r4");
        parseTable.put("6T", "Syntax Error");
        parseTable.put("6R", "Syntax Error");

        // Row #7
        parseTable.put("7a", "Syntax Error");
        parseTable.put("7b", "Syntax Error");
        parseTable.put("7c", "r2");
        parseTable.put("7$", "r2");
        parseTable.put("7T", "Syntax Error");
        parseTable.put("7R", "Syntax Error");

    }

    //-------------------------------------------------------------------------------------------------
    // start method is same as main method but to handle static errors
    public void start() {
        try {
            File input = new File("input.txt");
            scanner = new Scanner(input);
        } catch (Exception e) {
            System.out.println("File not found!");
            System.exit(0);
        }
        check();
    }

    //-------------------------------------------------------------------------------------------------
    // Read input from file method
    public void read() {
        readInput = scanner.nextLine();
        input1 = readInput.split(" ");
        input2 = input1;
        stack = new Stack();
        stack.push("0");

        System.out.print("\nRight most derivation for the input: ");
        System.out.println(readInput + "\n");
        Header();
    }

    //-------------------------------------------------------------------------------------------------
    // action check method
    public void check() {
        read();
        for (int i = 0; i < input1.length; i++) {
            String temp = stack.peek().toString();
            action = parseTable.get(temp + input1[i]);

            if (action.equals("Syntax Error")) {//if Syntax Error occur exit
                System.out.println("Syntax Error");
                System.exit(0);
            } else if (action.contains("s")) {//if s call shift action method
                shift(action.substring(1), i);

            } else if (action.contains("r")) {//if r call reduce action method
                reduce(action.substring(1));
                i--;
            } else if (action.equals("Accept")) {//if Accept call accept method
                accept(action);
            }
        }
        System.out.println("------------------------------------------------------------------------------------\n");
        //if there is more input call the method again
        if (scanner.hasNext()) {
            check();
        }
    }

    //-------------------------------------------------------------------------------------------------
    // Accept method
    public void accept(String action) {
        // stack print
        System.out.printf("%-25s", stack.toString().replaceAll("\\[", "").replaceAll("]", "").replaceAll(",", ""));

        // input print
        System.out.printf(" | %-25s", Arrays.toString(input2).replaceAll("\\[", "").replaceAll("]", "").replaceAll(",", ""));

        // action print
        System.out.printf(" | %-26s |\n", "Accept ");
    }
    
    //-------------------------------------------------------------------------------------------------
    // Shift method
    public void shift(String action, int index) {
        // stack print
        System.out.printf("%-25s", stack.toString().replaceAll("\\[", "").replaceAll("]", "").replaceAll(",", ""));

        // input print
        System.out.printf(" | %-25s", Arrays.toString(input2).replaceAll("\\[", "").replaceAll("]", "").replaceAll(",", ""));

        // Pushing the currant index element in the input and shift number
        stack.push(input1[index]);
        stack.push(action);

        // action print
        System.out.printf(" | S%-1s (Shift %-1s) %-13s |", action, action, space);
        System.out.println("");

        // Delete first element of the input
        input2 = Arrays.copyOfRange(input2, 1, input2.length);
    }
    
    //-------------------------------------------------------------------------------------------------
    // Reduce method
    public void reduce(String action) {

        // stack print
        System.out.printf("%-25s", stack.toString().replaceAll("\\[", "").replaceAll("]", "").replaceAll(",", ""));

        // input print
        System.out.printf(" | %-25s", Arrays.toString(input2).replaceAll("\\[", "").replaceAll("]", "").replaceAll(",", ""));

        // Assigning rule to grammarRule
        grammarRule = grammar.get(action);

        // action print
        System.out.printf(" | R%-1s (Reduce by %-12s |", action, grammarRule);
        System.out.println("");

        // Splitting rule and save it into symbols
        symbols = grammarRule.split(" ");
        // Saving RHS of rule into symbolsCopy
        symbolsCopy = Arrays.copyOfRange(symbols, 2, symbols.length);
        //if symbols not equals epsilon RHS*2 will pop out of stack 
        if(!symbols[2].equals("e")){
            for (int i = 0; i < symbolsCopy.length * 2; i++) {
                stack.pop();
            }
        }

        state = stack.peek().toString(); // Save top of stack into state
        stack.push(symbols[0]); // Push LHS into stack
        act = state + symbols[0]; //concatenate state number with symbol
        stack.push(parseTable.get(act)); //push concatenated act to stack
    }
    
    //-------------------------------------------------------------------------------------------------
    // Print header
    public static void Header() {
        System.out.println("Stack                     | input                     | Actions          ");
        System.out.println("------------------------------------------------------------------------------------");
    }
}
