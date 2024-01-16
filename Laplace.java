import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Laplace {

	public static void main(String[] args) {
		System.out.println("Welcome to Laplace!");
		System.out.println();
		System.out.println("This program takes in a differential equation and outputs a new set of algebraic equations" +
		                   " using the Laplace Transform method.");
		System.out.println();
		System.out.println("The program can take in first, second and third order differential equations.");
		System.out.println();
		System.out.println("Example first order equation: 5y' + 9y");
		System.out.println("Example second order equation: y'' + 2y' + 3y");
		System.out.println("Example third order equation: 4y''' + 10y'' - 3y' - 5y");
		System.out.println();
		System.out.println("Each term can have an integer coefficient, and terms can be separated by a \"+\" or a \"-\".");
		System.out.println();
		System.out.println("You must also provide a set of initial conditions (e.g. y(0), y'(0), etc.)");
		System.out.println();
		Scanner scanner =  new Scanner(System.in);
		while (true) {
			System.out.println("Enter a differential equation, or enter \"quit\" to end the program: ");
			String eqn = scanner.nextLine();
			if (eqn.equals("quit") || eqn.equals("q")) {
				break;
			}

			int orderSetting = findOrder(eqn);
			if (orderSetting < 0) {
				System.out.println("Invalid input.");
			} else {
				processEqn(orderSetting, eqn, scanner);
			}
		}
		
		System.out.println("Program terminated.");
		scanner.close();
	}
	
	private static void processEqn(int order, String eqn, Scanner scanner) {
		int arr[] = null;
		if (order == 1) {
			Pattern pattern = Pattern.compile("([0-9]*)y'( )+([\\+-])( )+([0-9]*)y$", Pattern.CASE_INSENSITIVE);
			Matcher m = pattern.matcher(eqn);
			if (!m.find()) {
				System.out.println("Invalid input.");
			} else {
				arr = processConds(1, scanner);
				int cond1 = arr[0];
				String eqn1 = "sY(s) - " + cond1;
				System.out.println("Eqn 1: L[y'](s) = " + eqn1);
				System.out.println("Eqn 2: " + m.group(1) + "(" + eqn1 + ") " + m.group(3) + " " + m.group(5) + "Y(s)");
			}
		} else if (order == 2) {
			Pattern pattern = Pattern.compile("([0-9]*)y''( )+([\\+-])( )+([0-9]*)y'( )+([\\+-])( )+([0-9]*)y$", Pattern.CASE_INSENSITIVE);
			Matcher m = pattern.matcher(eqn);
			if (!m.find()) {
				System.out.println("Invalid input.");
			} else {
				arr = processConds(2, scanner);
				int cond1 = arr[0], cond2 = arr[1];
				String eqn1 = "sY(s) - " + cond1;
				String eqn2 = "s^(2)Y(s) - " + cond1 + "s - " + cond2;
				System.out.println("Eqn 1: L[y'](s) = " + eqn1);
				System.out.println("Eqn 2: L[y''](s) = " + eqn2);
				System.out.println("Eqn 3: " + m.group(1) + "(" + eqn2 + ") " + m.group(3) + " " + m.group(5) +
						           "(" + eqn1 + ") " + m.group(7) + " " + m.group(9) + "Y(s)");
			}
		} else if (order == 3) {
			Pattern pattern = Pattern.compile("([0-9]*)y'''( )+([\\+-])( )+([0-9]*)y''( )+([\\+-])( )+([0-9]*)y'( )+([\\+-])( )+([0-9]*)y$", Pattern.CASE_INSENSITIVE);
			Matcher m = pattern.matcher(eqn);
			if (!m.find()) {
				System.out.println("Invalid input.");
			} else {
				arr = processConds(3, scanner);
				int cond1 = arr[0], cond2 = arr[1], cond3 = arr[2];
				String eqn1 = "sY(s) - " + cond1;
				String eqn2 = "s^(2)Y(s) - " + cond1 + "s - " + cond2;
				String eqn3 = "s^(3)Y(s) - " + cond1 + "s^2 - " + cond2 + "s - " + cond3;
				System.out.println("Eqn 1: L[y'](s) = " + eqn1);
				System.out.println("Eqn 2: L[y''](s) = " + eqn2);
				System.out.println("Eqn 3: L[y'''](s) = " + eqn3);
				System.out.println("Eqn 4: " + m.group(1) + "(" + eqn3 + ") " + m.group(3) + " " + m.group(5) +
						           "(" + eqn2 + ") " + m.group(7) + " " + m.group(9) + "(" + eqn1 + ") " +
						           m.group(11) + " " + m.group(13) + "Y(s)");
				
			}
		}

	}
	
	private static int[] processConds(int order, Scanner scanner) {
		String cond1 = "", cond2 = "", cond3 = "";
		int cond1Int = -1, cond2Int = -1, cond3Int = -1;
		
		while (true) {
			try {
				System.out.println("y(0) = ");
				cond1 = scanner.nextLine();
				cond1Int = Integer.parseInt(cond1);
				break;
			} catch (NumberFormatException e) {
				System.err.println("Input must be numerical value.");
				try {
					Thread.sleep(100);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
			}
		}

		if (order > 1) {
			while (true) {
				try {
					System.out.println("y'(0) = ");
					cond2 = scanner.nextLine();
					cond2Int = Integer.parseInt(cond2);
					break;
				} catch (NumberFormatException e) {
					System.err.println("Input must be numerical value.");
					try {
						Thread.sleep(100);
					} catch (InterruptedException e1) {
						e1.printStackTrace();
					}
				}
			}			
		}
		
		if (order > 2) {
			while (true) {
				try {
					System.out.println("y''(0) = ");
					cond3 = scanner.nextLine();
					cond3Int = Integer.parseInt(cond3);
					break;
				} catch (NumberFormatException e) {
					System.err.println("Input must be numerical value.");
					try {
						Thread.sleep(100);
					} catch (InterruptedException e1) {
						e1.printStackTrace();
					}
				}
			}			
		}
		
		return new int[] {cond1Int, cond2Int, cond3Int};
	}
	
	private static int findOrder(String eqn) {
		Pattern order1 = Pattern.compile("y'"), order2 = Pattern.compile("y''"), order3 = Pattern.compile("y'''");
		Matcher m1 = order1.matcher(eqn), m2 = order2.matcher(eqn), m3 = order3.matcher(eqn);
		if (m3.find()) {
			return 3; // Eqn is third order
		} else if (m2.find()) {
			return 2; // Eqn is second order
		} else if (m1.find()) {
			return 1; // Eqn is first order
		} else {
			return -1; // Not a diff eq
		}	
	}
	

}
