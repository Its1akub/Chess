package Game;

import javax.swing.*;
import java.awt.*;

public class MovePanel extends JPanel {
    private JTextArea textArea;
    private JScrollPane scrollPane;
    private int lineNumber;

    public MovePanel() {
        setBackground(new Color(48, 46, 43));
        textArea = new JTextArea(500, 35);
        textArea.setBackground(new Color(48, 46, 43));
        textArea.setForeground(new Color(255, 255, 255));
        textArea.setEditable(false);
        textArea.setAutoscrolls(true);
        textArea.setCursor(null);
        textArea.setFont(new Font("Arial", Font.BOLD, 17));
        //textArea.setCaretColor(Color.WHITE);
        scrollPane = new JScrollPane(textArea);
        scrollPane.setBorder(null);
        add(scrollPane);
    }
    public void addMove(String line) {
        textArea.append(line+"      ");
        repaint();
        revalidate();
    }
    public void newLine() {
        textArea.append("\n");
        textArea.append(lineNumber+ ".      ");
        repaint();
        revalidate();
    }
    public void print() {
        System.out.println(textArea.getText());
    }

    public int getLineNumber() {
        return lineNumber;
    }

    public void setLineNumber(int lineNumber) {
        this.lineNumber = lineNumber;
    }
}
