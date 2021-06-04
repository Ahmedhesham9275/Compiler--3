package Compiler;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Pattern;

public class Scanner {
    public static void main(String[] args) throws IOException {
        // TODO code application logic here
        scan("/home/ahmed/Documents/ScannerEditor/firsttest");
    }

    static String identifierRegularExpression = "[a-zA-Z_][a-zA-Z0-9_]+";
    static String digit = "[0-9]+";
    static String comment = "([/][-][_a-zA-Z0-9]+[-][/])|(--[_a-zA-Z0-9]+)";

    static String token = "";
    static char character;
    static char character2;
    static java.util.Scanner inputCode;

    static HashMap<String, String> keywords = new HashMap<String, String>();
    static HashMap<Character, String> oneLetterOp = new HashMap<>();
    static List Operation = new ArrayList();
    static HashMap<String, String> twoLetterOp = new HashMap<>();

    public static void scan(String filePath) throws IOException {
        keywords.put("Pattern", "Class");
        keywords.put("DerivedFrom", "Inheritance");
        keywords.put("TrueFor", "Condition");
        keywords.put("Else", "Condition");
        keywords.put("Ity", "Integer");
        keywords.put("Sity", "SignedInteger");
        keywords.put("Whatever", "Loop");
        keywords.put("Cwq", "Character");


        oneLetterOp.put('^', "Line Delimiter");
        oneLetterOp.put('@', "Start Symbol");
        oneLetterOp.put('#', "Token Delimiter");
        oneLetterOp.put('@', "Start Program");
        oneLetterOp.put('~', "Logical Operator");
        oneLetterOp.put('(', "Brace"); //begin the condition
        oneLetterOp.put(')', "Brace"); //end the condition
        oneLetterOp.put('{', "Brace"); //begin the loop
        oneLetterOp.put('}', "Brace"); //end for loop
        oneLetterOp.put('.', "Line Delimiter");
        oneLetterOp.put('=', "Assignment Operator");
        oneLetterOp.put('<', "relational Operator");
        oneLetterOp.put('+', "Arithmetic Operator");
        oneLetterOp.put('-', "Arithmetic Operator");
        oneLetterOp.put('*', "Arithmetic Operator");
        oneLetterOp.put('/', "Arithmetic Operator");
        oneLetterOp.put('!', "Logical Operator");


        twoLetterOp.put("==", "relational operator");
        twoLetterOp.put("!=", "relational operator");
        twoLetterOp.put("->", "Access Operator");
        twoLetterOp.put("--", "Comment");
        twoLetterOp.put("/-", "Comment");
        twoLetterOp.put("&&", "Logical Operator");
        twoLetterOp.put("||", "Logical Operator");


        String tokenCategory = null;
        String characterCategory = null;
        int line = 1;

        JFileChooser j = new JFileChooser("f:");

        // Invoke the showsOpenDialog function to show the save dialog
        int dialog = j.showOpenDialog(null);

        // If the user selects a file
        if (dialog == JFileChooser.APPROVE_OPTION) {
            File fi = new File(j.getSelectedFile().getAbsolutePath());

            try {
                // String
                // File reader
                FileReader fr = new FileReader(fi);

                // Buffered reader
                BufferedReader br = new BufferedReader(fr);
                // Initialize sl
                int c = 0;
                String op = "";
                while ((c = br.read()) != -1) {
                    boolean foundOp = false;
                    boolean foundCategory = false;
                    boolean foundOp2 = false;
                    character = (char) c;
                    //System.out.printf("%c",character);
                    if (character == '\n') {
                        //System.out.println("New Line outside " + token);
                        line++;
                        continue;
                    }
                    if (oneLetterOp.containsKey(character)) {
                        op = "";
                        foundOp = true;
                        op = op + character;
                        if ((c = br.read()) != -1) {
                            character2 = (char) c;
                            if (character2 != '\n') {
                                op += character2;
                                if (twoLetterOp.containsKey((op))) {
                                    foundOp2 = true;
                                }
                                //System.out.println("New Line inside " + token);
                                line++;
                            }
                        }
                    }
                    if (foundOp2) {
                        //code Token checking goes here
                        if (keywords.containsKey(token)) {
                            System.out.println(token + " " + keywords.get(token));
                        } else if (Pattern.matches(identifierRegularExpression, token)) {
                            System.out.println(token + " Identifier ");

                        } else if (Pattern.matches(digit, token)) {
                            System.out.println(token + " Constant ");

                        } else {
                            System.out.println(token + " Error");
                        }
                        System.out.println(op + " " + twoLetterOp.get(op));
                        token = "";
                    } else if (foundOp) {
                        //System.out.println(token );
                        //code Token checking goes here
                        if (keywords.containsKey(token)) {
                            System.out.println(token + " " + keywords.get(token));
                        } else if (Pattern.matches(identifierRegularExpression, token)) {
                            System.out.println(token + " Identifier ");

                        } else if (Pattern.matches(digit, token)) {
                            System.out.println(token + " Constant ");

                        } else if(token !=""){
                            System.out.println(token + " Error ");
                        }
                        System.out.println(character + " operator " + oneLetterOp.get(character));
                        token = "";
                        if (character2 != '\n') {
                            if(oneLetterOp.containsKey(character2))
                            {
                                System.out.println(character2 + " operator " + oneLetterOp.get(character2));
                            }
                            else {
                                token += character2;
                            }
                        }
                    } else {
                        token += character;
                    }
                }

//                if(String.valueOf(character2).equals(token)) {
//                    if (Pattern.matches(digit, token)) {
//                        System.out.println(token + " Constant ");
//                    }
//                    else if(oneLetterOp.containsKey(character2)){
//                        System.out.println("final one "+oneLetterOp.get(character2));
//                    }
//                }
//                else{
//                    System.out.println(character2 + " final " +token);
//                }

//                    if (!foundOp)
//                        token = token + character;
//                    if (keywords.containsKey(token)) {
//                        tokenCategory = "keyword";
//                        foundCategory = true;
//                    } else if (oneLetterOp.containsKey(token) || oneLetterOp.containsKey(character)) {
//                        tokenCategory = "Symbol";
//                        foundCategory = true;
//
//                    } else if (Operation.contains(token)) {
//                        tokenCategory = "operation";
//                        foundCategory = true;
//                    } else if (foundOp && Pattern.matches(identifierRegularExpression, token)) {
//                        tokenCategory = "identifier";
//                        foundCategory = true;
//
//                    }
//                    else if (Pattern.matches(comment, token)) {
//                        tokenCategory = "comment";
//                        foundCategory = true;
//
//                    } else if ( foundOp && Pattern.matches(digit, token)) {
//                        tokenCategory = "digit";
//                        foundCategory = true;
//                    }
//                    else if ( !foundCategory && foundOp ) {
//                        tokenCategory = "error " + line;
//                        foundCategory = true;
//                    }
//                    if (foundCategory) {
//                        if (foundOp && tokenCategory != "error ") {
//                            System.out.println("<" + character +":" +token+"," + tokenCategory + "> ");
//                            System.out.println("<"+token+" >");
//                        } else {
//                            System.out.println("<" + token + "," + tokenCategory + "> ");
//                        }
//                        token = "";
//                    }
//                    if ((char) c == '\n') {
//                        line++;
//                    }

            } catch (Exception evt) {
                evt.printStackTrace();
            }

        }
    }
}