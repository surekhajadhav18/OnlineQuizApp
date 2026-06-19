import javax.swing.*;
import java.awt.event.*;

public class OnlineQuizApp extends JFrame implements ActionListener {

    JLabel questionLabel, timerLabel, subjectLabel;
    JRadioButton option1, option2, option3, option4;
    ButtonGroup group;
    JButton nextButton, startButton;
    JComboBox<String> subjectBox;

    int score = 0;
    int currentQuestion = 0;
    int timeLeft = 15;

    Timer timer;

    String[][] questions;

    String[][] javaQuestions = {
            {"Java is a?", "Programming Language", "Database", "Operating System", "Compiler", "Programming Language"},
            {"Keyword used for inheritance?", "implements", "extends", "import", "super", "extends"},
            {"JVM stands for?", "Java Virtual Machine", "Java Variable Method", "Joint VM", "None", "Java Virtual Machine"},
            {"Which package is imported by default?", "java.util", "java.io", "java.lang", "java.awt", "java.lang"},
            {"Java is developed by?", "Google", "Microsoft", "Sun Microsystems", "Apple", "Sun Microsystems"}
    };

    String[][] pythonQuestions = {
            {"Python is?", "Compiled", "Interpreted", "Database", "OS", "Interpreted"},
            {"Python file extension?", ".java", ".cpp", ".py", ".html", ".py"},
            {"Who developed Python?", "Dennis Ritchie", "James Gosling", "Guido van Rossum", "Bjarne Stroustrup", "Guido van Rossum"},
            {"Python supports?", "OOP", "Procedural", "Functional", "All", "All"},
            {"Keyword for function?", "func", "define", "def", "function", "def"}
    };

    String[][] cppQuestions = {
            {"Creator of C++?", "James Gosling", "Dennis Ritchie", "Bjarne Stroustrup", "Guido", "Bjarne Stroustrup"},
            {"C++ supports?", "OOP", "Procedural", "Both", "None", "Both"},
            {"Header file for input/output?", "stdio.h", "iostream", "string", "math", "iostream"},
            {"Operator used for scope resolution?", "::", ".", "->", "#", "::"},
            {"Extension of C++ file?", ".java", ".cpp", ".py", ".html", ".cpp"}
    };

    String[][] dbmsQuestions = {
            {"DBMS stands for?", "Database Management System", "Data Base Method System", "Data Management Service", "None", "Database Management System"},
            {"SQL stands for?", "Structured Query Language", "Simple Query Language", "System Query Language", "None", "Structured Query Language"},
            {"Primary Key must be?", "Duplicate", "Unique", "Null", "Optional", "Unique"},
            {"Which command retrieves data?", "SELECT", "INSERT", "DELETE", "UPDATE", "SELECT"},
            {"Which normal form removes redundancy?", "1NF", "2NF", "3NF", "BCNF", "3NF"}
    };

    public OnlineQuizApp() {

        setTitle("Online Quiz Application");
        setSize(800, 500);
        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        subjectLabel = new JLabel("Select Subject:");
        subjectLabel.setBounds(30, 20, 120, 30);
        add(subjectLabel);

        String[] subjects = {"Java", "Python", "C++", "DBMS"};
        subjectBox = new JComboBox<>(subjects);
        subjectBox.setBounds(150, 20, 150, 30);
        add(subjectBox);

        startButton = new JButton("Start Quiz");
        startButton.setBounds(330, 20, 120, 30);
        add(startButton);

        timerLabel = new JLabel("Time Left: 15");
        timerLabel.setBounds(620, 20, 120, 30);
        add(timerLabel);

        questionLabel = new JLabel();
        questionLabel.setBounds(50, 80, 700, 30);
        add(questionLabel);

        option1 = new JRadioButton();
        option2 = new JRadioButton();
        option3 = new JRadioButton();
        option4 = new JRadioButton();

        option1.setBounds(50, 130, 400, 30);
        option2.setBounds(50, 170, 400, 30);
        option3.setBounds(50, 210, 400, 30);
        option4.setBounds(50, 250, 400, 30);

        add(option1);
        add(option2);
        add(option3);
        add(option4);

        group = new ButtonGroup();
        group.add(option1);
        group.add(option2);
        group.add(option3);
        group.add(option4);

        nextButton = new JButton("Next");
        nextButton.setBounds(320, 330, 120, 40);
        nextButton.addActionListener(this);
        add(nextButton);

        option1.setEnabled(false);
        option2.setEnabled(false);
        option3.setEnabled(false);
        option4.setEnabled(false);
        nextButton.setEnabled(false);

        timer = new Timer(1000, e -> {
            timeLeft--;
            timerLabel.setText("Time Left: " + timeLeft);

            if (timeLeft <= 0) {
                checkAnswer();
                nextQuestion();
            }
        });

        startButton.addActionListener(e -> {

            String selectedSubject = subjectBox.getSelectedItem().toString();

            switch (selectedSubject) {
                case "Java":
                    questions = javaQuestions;
                    break;

                case "Python":
                    questions = pythonQuestions;
                    break;

                case "C++":
                    questions = cppQuestions;
                    break;

                case "DBMS":
                    questions = dbmsQuestions;
                    break;
            }

            score = 0;
            currentQuestion = 0;

            option1.setEnabled(true);
            option2.setEnabled(true);
            option3.setEnabled(true);
            option4.setEnabled(true);
            nextButton.setEnabled(true);

            loadQuestion();
            timer.start();
        });

        setVisible(true);
    }

    private void loadQuestion() {

        group.clearSelection();

        questionLabel.setText(
                "Q" + (currentQuestion + 1) + ": "
                        + questions[currentQuestion][0]);

        option1.setText(questions[currentQuestion][1]);
        option2.setText(questions[currentQuestion][2]);
        option3.setText(questions[currentQuestion][3]);
        option4.setText(questions[currentQuestion][4]);

        timeLeft = 15;
        timerLabel.setText("Time Left: 15");
    }

    private void checkAnswer() {

        String selected = "";

        if (option1.isSelected())
            selected = option1.getText();
        else if (option2.isSelected())
            selected = option2.getText();
        else if (option3.isSelected())
            selected = option3.getText();
        else if (option4.isSelected())
            selected = option4.getText();

        if (selected.equals(questions[currentQuestion][5])) {
            score++;
        }
    }

    private void nextQuestion() {

        currentQuestion++;

        if (currentQuestion < questions.length) {
            loadQuestion();
        } else {

            timer.stop();

            double percentage =
                    ((double) score / questions.length) * 100;

            JOptionPane.showMessageDialog(
                    this,
                    "Quiz Completed!\n\n" +
                            "Score: " + score + "/" + questions.length +
                            "\nPercentage: " +
                            String.format("%.2f", percentage) + "%"
            );

            int choice = JOptionPane.showConfirmDialog(
                    this,
                    "Do you want to play again?",
                    "Restart Quiz",
                    JOptionPane.YES_NO_OPTION
            );

            if (choice == JOptionPane.YES_OPTION) {
                score = 0;
                currentQuestion = 0;
            } else {
                System.exit(0);
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        checkAnswer();
        nextQuestion();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(OnlineQuizApp::new);
    }
}
