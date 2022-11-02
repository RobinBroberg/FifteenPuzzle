import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.ThreadLocalRandom;
import javax.swing.*;

public class FifteenPuzzle extends JFrame implements ActionListener {

    private Tile[][] tiles = new Tile[4][4];
    private final JPanel panel = new JPanel();
    private final JPanel southPanel = new JPanel();
    private final JButton resetButton = new JButton("RESET");
    private final JButton winButton = new JButton("WIN");
    private final int randomOne = ThreadLocalRandom.current().nextInt(1, 4);
    private final int randomTwo = ThreadLocalRandom.current().nextInt(1, 4);


    public FifteenPuzzle() {

        setTitle("FifteenPuzzle");
        panel.setLayout(new GridLayout(4, 4));
        tiles = makeTiles();
        add(panel);
        add(southPanel, BorderLayout.SOUTH);
        southPanel.add(resetButton);
        southPanel.add(winButton);
        resetButton.addActionListener(this);
        winButton.addActionListener(this);
        panel.setBackground(Color.BLACK);
        pack();
        setSize(500, 500);
        setVisible(true);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        shuffleTiles();
    }

    public void shuffleTiles() {
        if (tiles != null) {
            for (int i = 0; i < tiles.length; i++) {
                for (int y = 0; y < tiles.length; y++) {
                    int i1 = (int) (Math.random() * tiles.length);
                    int j1 = (int) (Math.random() * tiles.length);
                    Tile temp = tiles[i][y];
                    tiles[i][y] = tiles[i1][j1];
                    tiles[i1][j1] = temp;
                }
            }
            for (int i = 0; i < tiles.length; i++) {
                for (int y = 0; y < tiles.length; y++) {
                    panel.add(tiles[i][y].getButton());
                    panel.revalidate();
                    panel.repaint();
                }
            }
        }
    }

    public Tile[][] makeTiles() {
        int j = 1;
        for (int i = 0; i < 4; i++) {
            for (int y = 0; y < 4; y++) {
                if (i == randomTwo && y == randomOne) {
                    Tile hiddenTile = new Tile(16);
                    tiles[i][y] = hiddenTile;
                    hiddenTile.getButton().addActionListener(this);
                } else {
                    Tile tile = new Tile(j);
                    tiles[i][y] = tile;
                    tile.getButton().addActionListener(this);
                    j++;
                }
            }
        }
        return tiles;
    }

    // Searched the array of tiles for tile number and returns an array of the indexes
    // If the tile number is not found, returns an empty array
    private int[] searchArray(String tileNumber) {
        int[] result = new int[2];
        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles.length; j++) {
                if (tiles[i][j].getButton().getText().equals(tileNumber)) {
                    result[0] = i;
                    result[1] = j;
                    return result;
                }
            }
        }
        return result;
    }

    private void switchTile(Tile tileOne, Tile tileTwo) {
        int tileOneValue = tileOne.getNumber();
        int tileTwoValue = tileTwo.getNumber();
        tileOne.updateButton(tileTwoValue);
        tileTwo.updateButton(tileOneValue);
        if (isSolved()) {
            victoryScreen();
        }
    }

    public void victoryScreen() {
        JOptionPane.showMessageDialog(null, "Congratulations you solved it!");
    }

    private boolean isSolved() {
        int[] hiddenIndex = searchArray("16");
        if (hiddenIndex[0] == 3 && hiddenIndex[1] == 3) {
            for (int i = 0; i < tiles.length; i++) {
                for (int j = 0; j < tiles.length; j++) {
                    if (tiles[i][j].getNumber() != (i * 4) + j + 1) {
                        return false;
                    }
                }
            }
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == resetButton) {
            shuffleTiles();
        }
        if (e.getSource() == winButton){
            winTheGame();
        }
        if (e.getSource() instanceof JButton button && e.getSource() != resetButton && e.getSource() != winButton) {
            int[] clickedIndex = searchArray(button.getText());
            int[] hiddenIndex = searchArray("16");
            int absColumnDiff = Math.abs(hiddenIndex[1] - clickedIndex[1]);
            int absRowDiff = Math.abs(hiddenIndex[0] - clickedIndex[0]);
            //same row, adjacent column
            if (clickedIndex[0] == hiddenIndex[0] && absColumnDiff == 1) {
                Tile clicked = tiles[clickedIndex[0]][clickedIndex[1]];
                Tile hidden = tiles[hiddenIndex[0]][hiddenIndex[1]];
                switchTile(clicked, hidden);
            }
            //same column, adjacent row
            else if (clickedIndex[1] == hiddenIndex[1] && absRowDiff == 1) {
                Tile clicked = tiles[clickedIndex[0]][clickedIndex[1]];
                Tile hidden = tiles[hiddenIndex[0]][hiddenIndex[1]];
                switchTile(clicked, hidden);
            }
        }
    }
    private Tile[][] makeWinTiles() {
        for (int i = 0; i < 4; i++){
            for (int j = 0; j < 4; j++){
                if (i == 3 && j == 3) {
                    Tile hiddenTile = new Tile(16);
                    tiles[i][j] = hiddenTile;
                    hiddenTile.getButton().addActionListener(this);
                } else {
                    Tile tile = new Tile((i * 4) + j + 1);
                    tiles[i][j] = tile;
                    tile.getButton().addActionListener(this);
                }
            }
        }
        return tiles;
    }

    private void winTheGame(){
        tiles = makeWinTiles();
        panel.removeAll();
        for (int i = 0; i < tiles.length; i++) {
            for (int y = 0; y < tiles.length; y++) {
                panel.add(tiles[i][y].getButton());
                panel.revalidate();
                panel.repaint();
            }
        }
        if (isSolved()){
            victoryScreen();
        }
    }

    public static void main(String[] args) {
        new FifteenPuzzle();

    }
}
