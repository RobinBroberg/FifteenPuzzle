import javax.swing.*;
import java.awt.*;

public class Tile {
    private int number;
    private final JButton button;

    public Tile(int Number) {
        number = Number;
        button = new JButton(String.valueOf(number));
        initializeButton();

    }
    private void initializeButton(){
        button.setText(String.valueOf(number));
        if (number==16){
            button.setVisible(false);
        }
        else {
            button.setFont(new Font(null, Font.BOLD, 20));
            button.setBackground(Color.cyan);
            button.setVisible(true);
        }
    }
    public void updateButton(int newNumber){
        number = newNumber;
        initializeButton();
    }

    public int getNumber() {
        return number;
    }

    public JButton getButton() {
        return button;
    }
}
