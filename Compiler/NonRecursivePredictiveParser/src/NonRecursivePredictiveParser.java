/*
COURSE: CPCS-302
SECTION: CS3
GROUP PROJECT PART 2
STUDENTS:
    Faisal Alragheeb    2136580
    Abdullah Alharbi    2136600
    Faisal Balkhair     2136412
 */

import java.io.*;
import java.util.*;

public class NonRecursivePredictiveParser {
    //Initializing stack to hold non terminals and spaces to prent input later on and inarray to divide input and parse table to hold production rules
    private Stack<String> stack;
    StringBuilder spaces = new StringBuilder();
    private String[] inArray;
    private static final Map<Pair<String, String>, String> parseTable = new HashMap<>();

 /*
    (i)
    E --> TE'
    E' --> +TE' | ε
    T --> FT'
    T' --> *FT' | ε
    F --> (E) | id

    (ii)
    FIRST sets (manually calculated)
    FIRST(E) = {(, id}
    FIRST(E') = {+, ε}
    FIRST(T) = {(, id}
    FIRST(T') = {*, ε}
    FIRST(F) = {(, id}

    FOLLOW sets (manually calculated)
    FOLLOW(E) = {$, ) }
    FOLLOW(E') = {$, ) }
    FOLLOW(T) = {+, $, ) }
    FOLLOW(T') = {+, $, ) }
    FOLLOW(F) = {*, +, $, ) }
 */

    static {
        //Initializing parse table using the grammer above
        parseTable.put(new Pair<>("E", "id"), "TE'");
        parseTable.put(new Pair<>("E", "("), "TE'");
        parseTable.put(new Pair<>("E'", "+"), "+TE'");
        parseTable.put(new Pair<>("E'", ")"), "e");
        parseTable.put(new Pair<>("E'", "$"), "e");
        parseTable.put(new Pair<>("T", "id"), "FT'");
        parseTable.put(new Pair<>("T", "("), "FT'");
        parseTable.put(new Pair<>("T'", "+"), "e");
        parseTable.put(new Pair<>("T'", "*"), "*FT'");
        parseTable.put(new Pair<>("T'", ")"), "e");
        parseTable.put(new Pair<>("T'", "$"), "e");
        parseTable.put(new Pair<>("F", "id"), "id");
        parseTable.put(new Pair<>("F", "("), "(E)");
    }
    //-------------------------------------------------------------------------------------------------------
    public static void main(String[] args) {
        try (BufferedReader br = new BufferedReader(new FileReader("input.txt"))) {
            //reading lines from the input file
            String line;
            while ((line = br.readLine()) != null) {
                NonRecursivePredictiveParser parser = new NonRecursivePredictiveParser(line);
                parser.parse();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //-------------------------------------------------------------------------------------------------------
    public NonRecursivePredictiveParser(String input) {
        // convert input to an array and split it by space
        inArray = input.split(" ");
        this.stack = new Stack<>();
    }
    //-------------------------------------------------------------------------------------------------------
    public void parse() {
        //push the start symbol to the stack
        stack.push("$");
        stack.push("E");
        // print STACK and INPUT and OUTPUT and starting stack
        System.out.printf("%-16s%-15s%-15s\n", "\nSTACK", "INPUT", "OUTPUT");
        System.out.printf("%-15s", "$E");

        //Initialize pointer by 0 to start from the beginning of the input and set currentsymbol to the first input
        int pointer = 0;
        String currentSymbol = inArray[pointer];

        //start a loop that ends only by emptying the stack
        while (!Objects.equals(stack.peek(), "$")) {
            StringBuilder dash = new StringBuilder();

            // concatonate the input to a string and print it
            StringBuilder inString = new StringBuilder();
            for (int i = pointer; i < inArray.length; i++) {
                inString.append(inArray[i]);
            }
            String conInput = spaces.toString();
            conInput = conInput.concat(inString.toString());
            System.out.printf("%-15s", conInput);

            //set peek to a variable
            String peek = stack.peek();

            //if nonterminal has dash ad the dash to the stack
            if (peek.contains("'")){
                dash= new StringBuilder();
                stack.pop();
                dash.append(stack.peek());
                dash.append("'");
                stack.push("'");
            }
            // Check if peek is non-terminal
            if (Character.isUpperCase(peek.charAt(0))) {
                Pair<String, String> pair = new Pair<>(String.valueOf(peek), String.valueOf(currentSymbol));
                String production = parseTable.get(pair);
                // if production found
                if (production != null) {
                    stack.pop();
                    // production print
                    if (peek.equals("'")){
                        System.out.printf("%s->%-15s\n", dash,production);
                    }else{
                        System.out.printf("%s->%-15s\n", peek,production);
                    }

                    //if the production has  ' , swap it with the previous char in the production
                    if (production.contains("'") || production.contains("d")) {
                        char[] prod = production.toCharArray();
                        for (int i = 0; i < prod.length; i++) {
                            if (prod[i] == '\'' || prod[i] == 'd') {
                                char temp = prod[i - 1];
                                prod[i - 1] = prod[i];
                                prod[i] = temp;
                            }
                        }
                        production = String.valueOf(prod);
                    }
                    // pop onto stack if production has epsilon
                    if (production.equals("e")) {
                        stack.pop();
                    }

                    // Push production onto stack reversely, if epsilon (e) skip
                    for (int i = production.length() - 1; i >= 0; i--) {
                        char prodChar = production.charAt(i);
                        stack.push(String.valueOf(prodChar));

                    }

                    PrintStack(stack);
                }
                else if (currentSymbol.equals(")")){
                    Error(peek, currentSymbol);
                    pointer++;
                    currentSymbol = inArray[pointer];
                    PrintStack(stack);
                }
                else {
                    // Error parse handling: rule is not found
                    Error(peek, currentSymbol);
                    stack.pop();
                    PrintStack(stack);

                }
            }
            // Check if peek has '
            else if (peek.contains("'")) {

                Pair<String, String> pair = new Pair<>(dash.toString(), String.valueOf(currentSymbol));
                String production = parseTable.get(pair);
                if (production != null) {
                    stack.pop();
                    stack.pop();
                    //production print
                    if (peek.equals("'")){
                        System.out.printf("%s->%-15s\n", dash,production);
                    }else {
                        System.out.printf("%s->%-15s\n", peek, production);
                    }
                    // Handling apostrophes and epsilon production
                    if (production.contains("'") || production.contains("d")) {
                        char[] prod = production.toCharArray();
                        for (int i = 0; i < prod.length; i++) {
                            if (prod[i] == '\'' || prod[i] == 'd') {
                                char temp = prod[i - 1];
                                prod[i - 1] = prod[i];
                                prod[i] = temp;
                            }
                        }
                        production = String.valueOf(prod);
                    }

                    // Push the production onto the stack in reverse order, skipping epsilon (e)
                    for (int i = production.length() - 1; i >= 0; i--) {
                        char prodChar = production.charAt(i);
                        if (prodChar == ('e')){
                            continue;
                        }
                        stack.push(String.valueOf(prodChar));
                    }
                    // Print the current stack configuration
                    PrintStack(stack);

                } else if (production.equals("e")) {
                    stack.pop();
                    if (peek.equals("'") || peek.equals("d")) {
                        stack.pop();
                    }
                } else {
                    // Error handling: no rule found
                    Error(peek, currentSymbol);
                    continue;
                }

            }

            else if (currentSymbol.contains(peek)) {
                stack.pop(); // Pop the top of the stack
                spaces.append(" "); // Append space for formatting
                if (currentSymbol.equals("id")) {
                    stack.pop(); // Pop the top of the stack
                    spaces.append(" "); // Append space for formatting
                }

                pointer++;
                if (pointer < inArray.length) {
                    // Move to the next input symbol
                    currentSymbol = inArray[pointer];
                }
                System.out.println();
                // Print the current stack configuration
                PrintStack(stack);
            } else {
                // Error handling: terminal does not match input symbol
                Error(peek, currentSymbol);
            }
        }

        //check if parsing is successful
        if (stack.peek().equals("$") && (currentSymbol.equals("$"))) {
            System.out.printf("%7s", currentSymbol);
            String successes = "parsing successfully halts";
            System.out.printf("%34s\n", successes);
        }
    }
    //-------------------------------------------------------------------------------------------------------
    // Method to print error messages and handle error recovery strategies
    private void Error(String peek, String currentSymbol) {
        // If the error is specific to certain symbols, print a message to skip them
        if (peek.equals("E") && currentSymbol.equals("*") || peek.equals("E") && currentSymbol.equals("(") || peek.equals("E") && currentSymbol.equals(")")){
            System.out.println("Error, Skip " + currentSymbol);
        }
        // Otherwise, print a message to pop the top of the stack
        else {
            System.out.println("Error, Pop " + peek);
        }
    }
    //-------------------------------------------------------------------------------------------------------
    // Method to print the current stack configuration
    private void PrintStack( Stack<String> stack ){
        StringBuilder stackAsString = new StringBuilder();
        for (String s : stack) {
            stackAsString.append(s);
        }
        System.out.printf("%-15s",stackAsString);
    }
    //-------------------------------------------------------------------------------------------------------
}
