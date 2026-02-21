import java.util.Scanner;

public class MarksCalculator {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        char choice;

        do {
            System.out.print("\nEnter number of students: ");
            int students = sc.nextInt();
            sc.nextLine();

            System.out.print("Enter number of subjects: ");
            int subjects = sc.nextInt();
            sc.nextLine();

            // Subject names (same for all students)
            String[] subjectNames = new String[subjects];
            System.out.println("\nEnter subject names:");
            for (int i = 0; i < subjects; i++) {
                System.out.print("Subject " + (i + 1) + ": ");
                subjectNames[i] = sc.nextLine();
            }

            // Student data arrays
            String[] names = new String[students];
            int[] totalMarks = new int[students];
            double[] percentages = new double[students];
            String[] grades = new String[students];

            // Input student details
            for (int s = 0; s < students; s++) {
                System.out.println("\n--- Student " + (s + 1) + " ---");

                System.out.print("Enter student name: ");
                names[s] = sc.nextLine();

                int total = 0;

                for (int i = 0; i < subjects; i++) {
                    System.out.print("Enter marks for " + subjectNames[i] + " (out of 100): ");
                    int marks = sc.nextInt();
                    sc.nextLine();

                    if (marks < 0 || marks > 100) {
                        System.out.println("Invalid marks! Re-enter.");
                        i--;
                        continue;
                    }

                    total += marks;
                }

                totalMarks[s] = total;
                percentages[s] = (double) total / subjects;

                // Grade calculation
                if (percentages[s] >= 90)
                    grades[s] = "A+";
                else if (percentages[s] >= 80)
                    grades[s] = "A-";
                else if (percentages[s] >= 70)
                    grades[s] = "B+";
                else if (percentages[s] >= 60)
                    grades[s] = "B-";
                else if (percentages[s] >= 50)
                    grades[s] = "C";
                else if (percentages[s] >= 40)
                    grades[s] = "D";
                else
                    grades[s] = "F";
            }

            // Display results
            System.out.println("\n================ ALL STUDENTS RESULT ================");
            System.out.printf("%-5s %-15s %-12s %-15s %-8s%n",
                    "No", "Name", "Total", "Percentage", "Grade");
            System.out.println("----------------------------------------------------");

            for (int i = 0; i < students; i++) {
                System.out.printf("%-5d %-15s %-12d %-14.2f %-8s%n",
                        (i + 1), names[i], totalMarks[i], percentages[i], grades[i]);
            }

            // Ask for restart
            System.out.print("\nDo you want to enter another batch? (Y/N): ");
            choice = sc.next().charAt(0);
            sc.nextLine();

        } while (choice == 'Y' || choice == 'y');

        sc.close();
        System.out.println("\nProgram terminated.");
    }
}
