package core;

public class ArithmeticSolver {
	
	public double add(double leftOperand, double rightOperand) {
		return leftOperand + rightOperand;
	}
	
	public double subtract(double leftOperand, double rightOperand) {
		return leftOperand - rightOperand;
	}
	
	public double multiply(double leftOperand, double rightOperand) {
		return leftOperand * rightOperand;
	}
	
	public double divide(double leftOperand, double rightOperand) {
		return leftOperand / rightOperand;
	}
	
	public double modulus(double leftOperand, double rightOperand) {
		return leftOperand % rightOperand;
	}

	public double solve(String operator, double leftOperand, double rightOperand) {
		double answer = 0;
		
		switch (operator) {
			case "+":
				answer = add(leftOperand, rightOperand);
				break;
			case "-":
				answer = subtract(leftOperand, rightOperand);
				break;
			case "x":
				answer = multiply(leftOperand, rightOperand);
				break;
			case "/":
				answer = divide(leftOperand, rightOperand);
				break;
			case "%":
				answer = modulus(leftOperand, rightOperand);
				break;
			default:
				break;
		}
		
		return answer;
	}
}
