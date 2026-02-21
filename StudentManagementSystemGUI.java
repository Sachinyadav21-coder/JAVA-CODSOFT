import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

class Student {
    private String name;
    private int roll;
    private int age;
    private int marks;

    public Student(String name, int roll, int age, int marks) {
        this.name = name;
        this.roll = roll;
        this.age = age;
        this.marks = marks;
    }

    public String getName() {
        return name;
    }

    public int getRoll() {
        return roll;
    }

    public int getAge() {
        return age;
    }

    public int getMarks() {
        return marks;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setMarks(int marks) {
        this.marks = marks;
    }
}

public class StudentManagementSystemGUI extends JFrame {

    private JTextField nameField, rollField, ageField, marksField, searchField;
    private JTable table;
    private DefaultTableModel model;

    private ArrayList<Student> students = new ArrayList<>();

    public StudentManagementSystemGUI() {

        setTitle("Student Management System");
        setSize(800, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        // ===== Title =====
        JLabel title = new JLabel("STUDENT MANAGEMENT SYSTEM", JLabel.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 22));
        title.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(title, BorderLayout.NORTH);

        // ===== Input Panel =====
        JPanel inputPanel = new JPanel(new GridLayout(4, 2, 10, 10));
        inputPanel.setBorder(BorderFactory.createTitledBorder("Student Details"));

        nameField = new JTextField();
        rollField = new JTextField();
        ageField = new JTextField();
        marksField = new JTextField();

        inputPanel.add(new JLabel("Name:"));
        inputPanel.add(nameField);
        inputPanel.add(new JLabel("Roll Number:"));
        inputPanel.add(rollField);
        inputPanel.add(new JLabel("Age:"));
        inputPanel.add(ageField);
        inputPanel.add(new JLabel("Marks:"));
        inputPanel.add(marksField);

        // ===== Button Panel =====
        JPanel buttonPanel = new JPanel(new GridLayout(1, 4, 10, 10));
        JButton addBtn = new JButton("Add");
        JButton editBtn = new JButton("Edit");
        JButton clearBtn = new JButton("Clear");
        JButton exitBtn = new JButton("Exit");

        buttonPanel.add(addBtn);
        buttonPanel.add(editBtn);
        buttonPanel.add(clearBtn);
        buttonPanel.add(exitBtn);

        JPanel leftPanel = new JPanel(new BorderLayout(10, 10));
        leftPanel.add(inputPanel, BorderLayout.CENTER);
        leftPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(leftPanel, BorderLayout.WEST);

        // ===== Table =====
        model = new DefaultTableModel(new String[] { "Name", "Roll", "Age", "Marks" }, 0);
        table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);

        add(scrollPane, BorderLayout.CENTER);

        // ===== Search Panel =====
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        searchField = new JTextField(10);
        JButton searchBtn = new JButton("Search");

        searchPanel.add(new JLabel("Search Roll No:"));
        searchPanel.add(searchField);
        searchPanel.add(searchBtn);

        add(searchPanel, BorderLayout.SOUTH);

        // ===== Button Actions =====
        addBtn.addActionListener(e -> addStudent());
        editBtn.addActionListener(e -> editStudent());
        clearBtn.addActionListener(e -> clearFields());
        exitBtn.addActionListener(e -> System.exit(0));
        searchBtn.addActionListener(e -> searchStudent());

        setVisible(true);
    }

    // ===== Validation =====
    private boolean validateInput() {
        if (nameField.getText().trim().isEmpty() ||
                rollField.getText().trim().isEmpty() ||
                ageField.getText().trim().isEmpty() ||
                marksField.getText().trim().isEmpty()) {

            JOptionPane.showMessageDialog(this, "All fields are required!");
            return false;
        }

        try {
            int age = Integer.parseInt(ageField.getText());
            int marks = Integer.parseInt(marksField.getText());

            if (age <= 0 || marks < 0 || marks > 100) {
                JOptionPane.showMessageDialog(this, "Enter valid Age and Marks (0–100)");
                return false;
            }

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Roll, Age and Marks must be numbers!");
            return false;
        }

        return true;
    }

    // ===== Add Student =====
    private void addStudent() {
        if (!validateInput())
            return;

        int roll = Integer.parseInt(rollField.getText());

        for (Student s : students) {
            if (s.getRoll() == roll) {
                JOptionPane.showMessageDialog(this, "Roll number already exists!");
                return;
            }
        }

        Student s = new Student(
                nameField.getText(),
                roll,
                Integer.parseInt(ageField.getText()),
                Integer.parseInt(marksField.getText()));

        students.add(s);
        model.addRow(new Object[] { s.getName(), s.getRoll(), s.getAge(), s.getMarks() });
        clearFields();
        JOptionPane.showMessageDialog(this, "Student added successfully!");
    }

    // ===== Edit Student =====
    private void editStudent() {
        int row = table.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Select a student from table!");
            return;
        }

        if (!validateInput())
            return;

        Student s = students.get(row);
        s.setName(nameField.getText());
        s.setAge(Integer.parseInt(ageField.getText()));
        s.setMarks(Integer.parseInt(marksField.getText()));

        model.setValueAt(s.getName(), row, 0);
        model.setValueAt(s.getAge(), row, 2);
        model.setValueAt(s.getMarks(), row, 3);

        JOptionPane.showMessageDialog(this, "Student updated successfully!");
    }

    // ===== Search Student =====
    private void searchStudent() {
        try {
            int roll = Integer.parseInt(searchField.getText());

            for (int i = 0; i < students.size(); i++) {
                if (students.get(i).getRoll() == roll) {
                    table.setRowSelectionInterval(i, i);
                    return;
                }
            }

            JOptionPane.showMessageDialog(this, "Student not found!");
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Enter valid roll number!");
        }
    }

    // ===== Clear Fields =====
    private void clearFields() {
        nameField.setText("");
        rollField.setText("");
        ageField.setText("");
        marksField.setText("");
    }

    // ===== Main =====
    public static void main(String[] args) {
        new StudentManagementSystemGUI();
    }
}
