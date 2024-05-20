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

    /**
     * Adds a move to the text area and updates the display.
     *
     * @param  line  the move to be added
     */
    public void addMove(String line) {
        textArea.append(line+"      ");
        repaint();
        revalidate();
    }

    /**
     * Appends a new line to the text area, increments the line number, and updates the display.
     *
     * This function appends a newline character to the end of the text area, increments the line number,
     * and appends the line number followed by three spaces to the text area. It then triggers a repaint
     * and revalidation of the component to update the display.
     */
    public void newLine() {
        textArea.append("\n");
        textArea.append(lineNumber+ ".      ");
        repaint();
        revalidate();
    }

    /**
     * Prints the contents of the text area to the console.
     */
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
