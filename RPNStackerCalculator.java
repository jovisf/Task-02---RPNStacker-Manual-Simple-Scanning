
import java.util.Scanner;
import java.util.Stack;

import java.io.File;
import java.io.FileNotFoundException;

import java.util.HashMap;
import java.util.Map;

public class RPNStacker {

    private static Stack<String> stack = new Stack<>();
    private static Boolean debugging = true;

    public static String parseOperation(String operation, Stack<String> stack, Map<String,String> hashTable) throws Exception {
        String result = (stack.empty()) ? "0" : stack.pop();

        if (!stack.empty()) {
            result = Integer.toString(calculate(operation, stack.pop(), result, hashTable)) ;
        }

        return result;
    }

    public static int calculate(String operation, String fstOperator, String sndOperator, Map<String,String> hashTable) throws Exception {
    	 
    	
    	TokenType fstOperatorTokenType = TokenType.EOF;
    	TokenType sndOperatorTokenType = TokenType.EOF;
    	
    	int firstOperand = 0;
    	int secondOperand = 0;
    	
    	if(isId(fstOperator)) {
    		fstOperatorTokenType = TokenType.ID;
    		firstOperand = mapHash(hashTable, fstOperator);
    	}
    	else {
    		fstOperatorTokenType = TokenType.NUM;
    		firstOperand = Integer.parseInt(fstOperator);
    	}
    	
    	if(isId(rightOperator)) {
    		sndOperatorTokenType = TokenType.ID;
    		sndOperand= mapHash(hashTable, sndOperator);
    	}
    	else {
    		sndOperatorTokenType = TokenType.NUM;
    		sndOperand = Integer.parseInt(sndOperator);
    	}
    	
    	if(debugging) {
    		System.out.println(new Token(fstOperatorTokenType, fstOperator));
    		System.out.println(new Token(sndOperatorTokenType, sndOperator));
    	}
    	
    	TokenType tokenType = TokenType.EOF;
    	int value = 0;
    	
    	switch (operation) {
        case "+":
        	tokenType = TokenType.PLUS;
            value = firstOperand + secondOperand;
            break;
        case "-":
        	tokenType = TokenType.MINUS;
        	value = firstOperand - secondOperand;
            break;
        case "*":
        	tokenType = TokenType.STAR;
        	value = firstOperand * secondOperand;
            break;
        case "/": {
        	tokenType = TokenType.SLASH;
        	value = firstOperand / secondOperand;
        	if (secondOperand == 0) {
        		throw new Exception("Cannot divide by 0");
        	}
            break;
        }
        default:
            value = firstOperand;
        }
    	
    	if(debugging) {
    		System.out.println(new Token(tokenType, operation));
    	}
    	
    	return value;
    }

    // check if current input is an integer

    public static boolean isInteger(String input) {
        if (input == null)
            return false;

        return input.matches("(\\d)+");
    }

    // check if current input is an operator

    public static boolean isOperation(String input) {
        if (input == null)
            return false;

        return input.matches("(\\+|-|\\*|/)");
    }
    
    public static boolean isId(String input) {
        if (input == null)
            return false;

        return input.matches("([A-Za-z])");
    }
    
    public static int mapHash (Map<String,String> hashTable, String key) throws Exception {
    	 if (hashTable.containsKey(key)) {
    		 return Integer.parseInt(hashTable.get(key)) ;
    	 }
    	 else {
    		 throw new Exception(key + " cannot be resolved");
    	 }
    }

    public static void main(String[] args) throws Exception {
    	
    	Map<String,String> hashTable = new HashMap<String,String>();
    	hashTable.put("y", new String("10"));

        String filePath = "/home/jvsf/Documentos";
        
        try {
	        File file = new File(filePath);
	        Scanner reader = new Scanner(file);
	
	        while (reader.hasNextLine()) {
	            String line = reader.nextLine();
	
	            if (isInteger(line)) {
	                stack.push(line);
	            } else if (isOperation(line)) {
	                String current = parseOperation(line, stack, hashTable);          
	                if (stack.size() == 0) {
	                    System.out.println();
	                }
	
	                stack.push(current);
	            } 
	            else if (isId(line)) {
	            	stack.push(line);
	            }
	            
	            else {
	                System.out.println("Error: Unexpected character: " + line);
	                throw new Exception("Character not allowed");
	            }
	
	        }
	        System.out.print("Saída = " + stack.pop());
	
	        reader.close();
	    } catch (FileNotFoundException e) {
	        System.out.println("Error when read the file...");
	        e.printStackTrace();
	    }
    }

}