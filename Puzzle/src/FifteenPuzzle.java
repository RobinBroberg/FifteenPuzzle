import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.ThreadLocalRandom;
import javax.swing.*;

public class FifteenPuzzle extends JFrame implements ActionListener {
    private final JButton[][] buttons;
    private final JPanel panel = new JPanel();
    private final JButton resetButton = new JButton("RESET");
    private final int randomOne = ThreadLocalRandom.current().nextInt(1, 4);
    private final int randomTwo = ThreadLocalRandom.current().nextInt(1, 4);

    private final JButton hiddenButton = new JButton();


    public FifteenPuzzle() {

        setTitle("FifteenPuzzle");
        panel.setLayout(new GridLayout(4, 4));
        buttons = makeButtons();
        add(panel);
        add(resetButton, BorderLayout.SOUTH);
        resetButton.addActionListener(this);
        panel.setBackground(Color.BLACK);
        pack();
        setSize(500, 500);
        setVisible(true);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        mixAndAddButtons();
    }

    public void mixAndAddButtons() {
        if (buttons != null) {
            for (int i = 0; i < buttons.length; i++) {
                for (int y = 0; y < buttons[i].length; y++) {
                    int i1 = (int) (Math.random() * buttons.length);
                    int j1 = (int) (Math.random() * buttons[y].length);
                    JButton temp = buttons[i][y];
                    buttons[i][y] = buttons[i1][j1];
                    buttons[i1][j1] = temp;
                }
            }
            for (int i = 0; i < buttons.length; i++) {
                for (int y = 0; y < buttons.length; y++) {
                    panel.add(buttons[i][y]);
                    panel.revalidate();
                    panel.repaint();
                }
            }
        }
    }

    public JButton[][] makeButtons() {
        JButton[][] buttons = new JButton[4][4];
        int j = 1;
        for (int i = 0; i < 4; i++) {
            for (int y = 0; y < 4; y++) {
                if (i == randomTwo && y == randomOne) {
                    buttons[i][y] = hiddenButton;
                    hiddenButton.setVisible(false);

                } else {
                    JButton button = new JButton(String.valueOf(j));
                    buttons[i][y] = button;
                    button.setFont(new Font(null, Font.BOLD, 20));
                    button.setBackground(Color.cyan);
                    button.addActionListener(this);
                    j++;
                }
            }
        }
        return buttons;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == resetButton) {
            mixAndAddButtons();
            for (int i = 0; i < buttons.length; i++) {
                for (int y = 0; y < buttons.length; y++) {
                    buttons[i][y].setVisible(true);
                    hiddenButton.setVisible(false);
                }
            }
        }
        if (e.getSource() instanceof JButton && e.getSource() != resetButton) {
            JButton button = (JButton) e.getSource();
            button.setVisible(false);
            String name = button.getText();
            System.out.println(name);
        }

    }

    public static void main(String[] args) {
        new FifteenPuzzle();

    }
}
