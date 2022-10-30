import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import javax.swing.*;

public class FifteenPuzzle2 extends JFrame implements ActionListener {
    private final JButton[] buttons;
    private final JPanel panel = new JPanel();
    private final JButton resetButton = new JButton("RESET");
    private final int randomOne = ThreadLocalRandom.current().nextInt(1, 16);

    // protected List<JButton> list = new ArrayList<>();

    public FifteenPuzzle2() {

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
            Collections.shuffle(List.of(buttons));
            for (JButton button : buttons) {
                panel.add(button);
                panel.revalidate();
                panel.repaint();
            }
        }

    }

    public JButton[] makeButtons() {
        JButton[] buttons = new JButton[16];
        int j = 1;
        for (int i = 0; i < 4; i++) {
            if (i == randomOne) {
                JButton hiddenButton = new JButton();
                buttons[i] = hiddenButton;
                hiddenButton.setVisible(false);
            } else {
                JButton button = new JButton(String.valueOf(j));
                buttons[i] = button;
                button.setFont(new Font(null, Font.BOLD, 20));
                button.setBackground(Color.cyan);
                button.addActionListener(this);
                j++;
            }
        }
        return buttons;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == resetButton) {
            mixAndAddButtons();
            for (int i = 0; i < buttons.length; i++) {
                buttons[i].setBackground(Color.CYAN);
            }
        }

        if (e.getSource() instanceof JButton && e.getSource() != resetButton) {
            ((JButton) e.getSource()).setBackground(Color.RED);
        }
    }

    public static void main(String[] args) {
        new FifteenPuzzle();

    }
}