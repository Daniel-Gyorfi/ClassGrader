package grade;

import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

public class Util {
	
	public double[][] grades;
	public double[] weight;
	public double[] factor;
	public String[] sections;
	public Scanner read;
	public boolean scan;
	
	public Util(int a, boolean scanned) {
		this.read = new Scanner(System.in);
		this.grades = new double[a][];
		this.weight = new double[a];
		factor = new double[a];
		sections = new String[a];
		scan = scanned;
	} // constructor
	
	public void input() {
		// initializes every section, a double array
		for (int i = 0; i < grades.length; i++) {
			System.out.println("Grades in section " + (i) + ": ");
			this.grades[i] = new double[read.nextInt()];
		}
	}
	
	public void gradeSec() {
		System.out.println("Input the grades for every section.");
		// loops between every section, and for every section loops through
		// each double array, initializing the value
		for (int i = 0; i < grades.length; i++) {
			System.out.println("Section " + (i + 1) + ", " + grades[i].length + " grades");
			System.out.println("Input the weight percentage as a decimal: ");
			weight[i] = read.nextDouble();
			System.out.println("Input a multiple of one hundred points by which to divide");
			System.out.println("the total section grade, usually the same as the number of");
			System.out.println("assignments if they are weighted equally.");
			factor[i] = read.nextDouble();
			for (int j = 0; j < grades[i].length; j++) {
				System.out.println("Grade " + (j+1) + ": ");
				grades[i][j] = read.nextDouble();
			}
		}
	}
	
	public void gradeSec(int sec) {
		//grades for a single section, identified by index in main grades array, are calculated
		System.out.println("Input the weight percentage as a decimal: ");
		weight[sec] = read.nextDouble();
		System.out.println("Input a multiple of one hundred points by which to divide");
		System.out.println("the total section grade, usually the same as the number of");
		System.out.println("assignments if they are weighted equally.");
		factor[sec] = read.nextDouble();
		System.out.println("Input the grades for section " + sec + ".");
		for (int i = 0; i < grades[sec].length; i++) {
			System.out.println("Grade " + i + ": ");
			grades[sec][i] = read.nextDouble();
		}
	}
	
	public double gradeCalc() {
		//calculates total class grade
		double grade = 0;
		for (int i = 0; i < grades.length; i++) {
			double temp = 0;
			for (int j = 0; j < grades[i].length; j++) {
				temp += grades[i][j];
			}
			temp = temp/factor[i] * weight[i];
			grade += temp;
		}
		return grade;
	}
	
	public double gradeCalc(int sec) {
		//calculates grade in a particular section
		double grade = 0;
		for (int i = 0; i < grades[sec].length; i++) {
			double temp = 0;
			temp += grades[sec][i];
			temp = temp/factor[sec] * weight[sec];
			grade += temp;
		}
		return grade;
	}
	
	public void gradeBreakdown() {
		//prints grade in each section
		System.out.println("Grade Breakdown:");
		for (int i = 0; i < grades.length; i++) {
			System.out.println(sections[i] + " (" + i +"): " + gradeCalc(i) + "/" +(100 * weight[i]));
		}
	}
	
	public void csvCalc(int line, File file) throws FileNotFoundException, NullPointerException {
		//scans a formatted csv file into usable grade data, but does not calculate a final grade
		Scanner io = new Scanner(file);
		Scanner str = null;
		for (int i = 0; i < line; i++) {
			String row = io.nextLine();
			str = new Scanner(row);
			str.useDelimiter(", ");
			int count = 0;
			while (str.hasNext() && !str.next().isEmpty()) {
				count++;
			}
			str = new Scanner(row);
			str.useDelimiter(", ");
			this.grades[i] = new double[count - 3];
			for (int j = 0; j < 3; j++) {
				if (j == 0) {
					this.sections[i] = str.next();
				} else if (j==1) {
					this.weight[i] = Double.parseDouble(str.next());
				} else {
					this.factor[i] = Integer.parseInt(str.next());
				}
			}
			for (int j = 3; j < count; j++) {
				this.grades[i][j-3] = Double.parseDouble(str.next());
			}
		}
		io.close();
	}
	
}
