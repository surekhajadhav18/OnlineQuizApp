import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class OnlineQuizApp extends JFrame implements ActionListener {

    JLabel questionLabel, timerLabel;
    JRadioButton option1, option2, option3, option4;
    ButtonGroup group;
    JButton nextButton;

    int score = 0;
    int currentQuestion = 0;
    int timeLeft = 15;

    Timer timer;

    String[][] questions = {
            {"Which language is used for Android Development?",
                    "Java", "Python", "C++", "PHP", "Java"},

            {"Which company developed Java?",
                    "Microsoft", "Sun Microsystems", "Google", "Apple", "Sun Microsystems"},

            {"Which keyword is used for inheritance?",
                    "extends", "implement", "inherits", "super", "extends"},

            {"Which collection allows duplicates?",
                    "Set", "Map", "HashSet", "ArrayList", "ArrayList"},

            {"Java is a?",
                    "Compiler", "Programming Language", "Database", "OS", "Programming Language"}
    };

    public OnlineQuizApp() {

        setTitle("Online Quiz Application");
        setSize(700, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

        timerLabel = new JLabel("Time Left: 15");
        timerLabel.setBounds(550, 20, 120, 30);
        add(timerLabel);

        questionLabel = new JLabel();
        questionLabel.setBounds(50, 50, 600, 30);
        add(questionLabel);

        option1 = new JRadioButton();
        option2 = new JRadioButton();
        option3 = new JRadioButton();
        option4 = new JRadioButton();

        option1.setBounds(50, 100, 300, 30);
        option2.setBounds(50, 140, 300, 30);
        option3.setBounds(50, 180, 300, 30);
        option4.setBounds(50, 220, 300, 30);

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
        nextButton.setBounds(280, 280, 120, 40);
        nextButton.addActionListener(this);
        add(nextButton);

        loadQuestion();

        timer = new Timer(1000, e -> {
            timeLeft--;
            timerLabel.setText("Time Left: " + timeLeft);

            if (timeLeft <= 0) {
                checkAnswer();
                nextQuestion();
            }
        });

        timer.start();

        setVisible(true);
    }

    private void loadQuestion() {

        group.clearSelection();

        questionLabel.setText(
                (currentQuestion + 1) + ". "
                        + questions[currentQuestion][0]);

        option1.setText(questions[currentQuestion][1]);
        option2.setText(questions[currentQuestion][2]);
        option3.setText(questions[currentQuestion][3]);
        option4.setText(questions[currentQuestion][4]);

        timeLeft = 15;
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

        if (selected.equals(
                questions[currentQuestion][5])) {

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
                    "Quiz Completed!\n\nScore: "
                            + score + "/"
                            + questions.length
                            + "\nPercentage: "
                            + percentage + "%"
            );

            System.exit(0);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        checkAnswer();
        nextQuestion();
    }

    public static void main(String[] args) {

        SwingUtilities.invokeLater(
                OnlineQuizApp::new
        );
    }
}