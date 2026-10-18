import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

class Student {
    String name;
    double marks;

    public Student(String name, double marks) {
        this.name = name;
        this.marks = marks;
    }
}

public class main {
    private static final String FILE_NAME = "students.txt";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ArrayList<Student> students = new ArrayList<>();
        
        // Load existing records from file at startup
        loadFromFile(students);
        
        int choice;

        do {
            System.out.println("\n--- Student Grade Analyzer ---");
            System.out.println("1. Add a Student");
            System.out.println("2. View All Students");
            System.out.println("3. Show Statistics (Average, Highest, Lowest)");
            System.out.println("4. Exit & Save");
            System.out.print("Enter your choice (1-4): ");
            
            choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline buffer

            switch (choice) {
                case 1:
                    System.out.print("Enter student name: ");
                    String name = scanner.nextLine();
                    System.out.print("Enter student marks (out of 100): ");
                    double marks = scanner.nextDouble();
                    
                    Student newStudent = new Student(name, marks);
                    students.add(newStudent);
                    saveToFile(newStudent);
                    System.out.println("Student added and saved successfully!");
                    break;

                case 2:
                    if (students.isEmpty()) {
                        System.out.println("No records found.");
                    } else {
                        System.out.println("\n--- Student Records ---");
                        for (int i = 0; i < students.size(); i++) {
                            Student s = students.get(i);
                            System.out.println((i + 1) + ". Name: " + s.name + " | Marks: " + s.marks);
                        }
                    }
                    break;

                case 3:
                    if (students.isEmpty()) {
                        System.out.println("No data available to calculate statistics.");
                    } else {
                        double sum = 0;
                        double highest = students.get(0).marks;
                        double lowest = students.get(0).marks;
                        String topStudent = students.get(0).name;
                        String lowStudent = students.get(0).name;

                        for (Student s : students) {
                            sum += s.marks;
                            if (s.marks > highest) {
                                highest = s.marks;
                                topStudent = s.name;
                            }
                            if (s.marks < lowest) {
                                lowest = s.marks;
                                lowStudent = s.name;
                            }
                        }

                        double average = sum / students.size();
                        System.out.println("\n--- Performance Statistics ---");
                        System.out.println("Total Students: " + students.size());
                        System.out.println("Class Average: " + String.format("%.2f", average));
                        System.out.println("Highest Scorer: " + topStudent + " (" + highest + ")");
                        System.out.println("Lowest Scorer: " + lowStudent + " (" + lowest + ")");
                    }
                    break;

                case 4:
                    System.out.println("Exiting application. All data is saved!");
                    break;

                default:
                    System.out.println("Invalid choice! Please select between 1 and 4.");
            }
        } while (choice != 4);

        scanner.close();
    }

    // Save a single student to text file
    private static void saveToFile(Student s) {
        try (FileWriter fw = new FileWriter(FILE_NAME, true)) {
            fw.write(s.name + "," + s.marks + "\n");
        } catch (IOException e) {
            System.out.println("Error saving to file: " + e.getMessage());
        }
    }

    // Load past students from text file when app starts
    private static void loadFromFile(ArrayList<Student> students) {
        File file = new File(FILE_NAME);
        if (!file.exists()) return;

        try (Scanner fileScanner = new Scanner(file)) {
            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine();
                String[] parts = line.split(",");
                if (parts.length == 2) {
                    String name = parts[0];
                    double marks = Double.parseDouble(parts[1]);
                    students.add(new Student(name, marks));
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
    }
}