/* Name -  Aryan Saxena
 * Date - 25-11-2024
 * Bodmas.java will calculate the expression according to Bodmas rules 
 */
package BODMAS;
import java.util.ArrayList;
import java.util.Scanner;

public class Bodmas {
	public String expression ;
	Scanner input = new Scanner(System.in);
	
	// this function will take user expression for calculations
	public void startOfProgram() {
		System.out.println(Constants.BODMAS_RULES);
		System.out.println(Constants.CORRECT_FORMAT_GUIDE);
		System.out.println(Constants.WRONG_FORMAT);
		System.out.println(Constants.CORRECT_FORMAT);
		while(true) {
			System.out.print(Constants.ENTER_EXPRESSION);
			expression = input.nextLine();
	
			try {
				//End the program if expression is 0 
				if(expression.trim().charAt(0) == '0' && expression.trim().length() == 1) {
					System.out.println(Constants.PROGRAM_END);
					break;
				}else {
					// trim and remove spaces in expression
					if(checkErrors(expression.trim().replace(" ", ""))) {
						Double digit = calculateExpression(expression.trim().replace(" ", ""));
						System.out.println(digit);
					}else {
						System.out.println(Constants.INCORECT_FORMAT);
					}
					
				}
			}catch(Exception error) {
				System.out.println(Constants.INCORECT_FORMAT);
				System.out.print(Constants.ENTER_EXPRESSION);
				input.nextLine();
				continue;
			}
		}
	}
	
	// to check errors in a expression
	public static boolean checkErrors(String expression) {
		for(int i=0;i<expression.length();i++) {
			char current = expression.charAt(i);
			if(Character.isDigit(current)) {
				
				//if next variable is an '(' after digit then return false
				if(i+1<expression.length() && expression.charAt(i+1) == '(' ) {
					return false;
				}
				
				continue;
			}else if(current == '+' || current=='-' || current == '*' || current == '/' || current == '(' || current == ')' 
					|| current == 'M' || current == '.') {
					
				// if next variable is a operator after an operator except brackets
					if(current == '(' && expression.charAt(i+1) == '+' || expression.charAt(i+1) == '-' || expression.charAt(i+1) == '*' || expression.charAt(i+1) == '/' || expression.charAt(i+1) == '(' || expression.charAt(i+1) == ')') {
						return true;
					}
					else if(i+1<expression.length() && 
						( expression.charAt(i+1) == '+' || expression.charAt(i+1) =='-' || expression.charAt(i+1) == '*'  
						|| expression.charAt(i+1) == '/' || expression.charAt(i+1) == 'M' )) {
						return false;
					}
					continue;
			}else {
				return false;
			}
		}	
		return true;
	}
	
	// This function calculate the expression
    public double calculateExpression(String expression) {
    	
    	//Calculate bracket expression first,
        while (expression.contains("(")) {
        	
        	//Get last index of  openBracket
            int openBracket = expression.lastIndexOf('('); 
            //Get first index of  closeBracket
            int closeBracket = expression.indexOf(')', openBracket); 

            // Get sub-expression 
            String subExpression = expression.substring(openBracket + 1, closeBracket);
            double subResult = calculateExpression(subExpression);

            expression = expression.substring(0, openBracket) + subResult + expression.substring(closeBracket + 1);
        }

        // Store number and operators 
        ArrayList<Double> numbers = new ArrayList<>();
        ArrayList<Character> operators = new ArrayList<>();
        
        //Store number tempory 
        StringBuilder numberBuilder = new StringBuilder();

        for (int i = 0; i < expression.length(); i++) {
            char c = expression.charAt(i);
            if (Character.isDigit(c) || c == '.') {
                numberBuilder.append(c); 
            } else {
                numbers.add(Double.parseDouble(numberBuilder.toString())); 
                numberBuilder.setLength(0); 
                operators.add(c); 
            }
        }
        // for last digit
        numbers.add(Double.parseDouble(numberBuilder.toString()));

        // for division and multiply
        for (int i = 0; i < operators.size(); i++) {
            char operator = operators.get(i);
            if (operator == '*' || operator == '/') {
                double num1 = numbers.get(i);
                double num2 = numbers.get(i + 1);
                double result = (operator == '*') ? (num1 * num2) : (num1 / num2);

                numbers.set(i, result); 
                
                //remove next digit and operator
                numbers.remove(i + 1);
                operators.remove(i);
                i--;
            }
        }
        
        
        for (int i = 0; i < operators.size(); i++) {
            char operator = operators.get(i);
            if (operator == 'M') {
                double num1 = numbers.get(i);
                double num2 = numbers.get(i + 1);
                double result = num1 % num2;

                numbers.set(i, result); 
                numbers.remove(i + 1);
                operators.remove(i);
                i--;
            }
        }

        for (int i = 0; i < operators.size(); i++) {
            char operator = operators.get(i);
            double num1 = numbers.get(i);
            double num2 = numbers.get(i + 1);
            double result = (operator == '+') ? (num1 + num2) : (num1 - num2);

            numbers.set(i, result); 
            numbers.remove(i + 1); 
            operators.remove(i); 
            i--; 
        }

        return numbers.get(0);
    }
}