package grade;

import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

public class Driver {
	
	public static void main(String[] args) {
		// initialize variables
		Scanner type = new Scanner(System.in);
		Util course = null;
		String choice = "";
		//menu for choosing to manually input grades or scan them from a file
		//loop repeats until a valid choice has been input
		while (!choice.equalsIgnoreCase("scan") && !choice.equalsIgnoreCase("input")) {
		System.out.println("Will you input values manually or scan a CSV file?");
		System.out.println("scan/input");
		choice = type.next();
		int line = 0;
		boolean cont = true;
		if (choice.equalsIgnoreCase("scan")) {
			while (cont) {
				//loop will repeat until working filename is input or user decides not to scan
				System.out.println("input the file name, or type \"exit\"");
				String path = type.next();
				if (path.equalsIgnoreCase("exit")) {
					//user decides not to scan a file, can begin manual input instead
					cont = false;
					choice = "";
				} else {
					//user inputs from a file, resets loop if invalid filename
					try {
						File csv = new File(path);
						Scanner lineCount = new Scanner(csv);
						cont = false;
						while (lineCount.hasNextLine() && !lineCount.nextLine().isEmpty()) {
							line += 1;
						}
						lineCount.close();
						course = new Util(line, true);
						course.csvCalc(line, csv);
						System.out.println();
					} catch (FileNotFoundException e) {
						System.out.println("Invalid file name");
						continue;
					} catch (NullPointerException e) {
						System.out.println("improperly formatted file");
					}
				}
			}
		} else if (choice.equalsIgnoreCase("input")) {
			System.out.println("input the number of grade categories(integers only)");
			line = type.nextInt();
			course = new Util(line, false);
			course.input();
		}
		}
		double grade = 0;
		menu(type, course, grade);
		System.exit(0);
	}
	
	public static void menu(Scanner type, Util course, double grade) {
		boolean exit = false;
		//loop-based menu, ends if user decides to exit(exit == true)
		while (!exit) {
			//menu options displayed to user
			System.out.println("1 - manually input all grades");
			System.out.println("2 - calculate");
			System.out.println("3 - input grades for a section");
			System.out.println("4 - print current grade");
			System.out.println("5 - print class breakdown");
			System.out.println("6 - exit");
			System.out.print("option: ");
			//switch case for selecting menu options
			switch (type.next()) {
				case ("1") : course.gradeSec();
				//manually input all grades
					System.out.println();
					break;
				case ("2") : grade = course.gradeCalc();
				//calculates grade from current data
					System.out.println("Grade calculated: " + grade);
					System.out.println();
					break;
				case("3") : System.out.println("Input the index number given to the section");
				//manually input all grades of a certain section
					int sec = type.nextInt();
					course.gradeSec(sec);
					System.out.println();
					break;
				case ("4") : if (grade == 0) grade = course.gradeCalc();
				//prints grade that was previously calculated--will not register newly input data
					System.out.println("Current grade: " + grade);
					System.out.println();
					break;
				case ("5") : course.gradeBreakdown();
				System.out.println();
					break;
				case ("6") : exit = true;
					break;
				default : System.out.println("invalid response");
					System.out.println();
			}
		}
		type.close();
	}

}
