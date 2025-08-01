import javax.swing.*;
import java.awt.*;

public class SudokuSolver extends JFrame {
    private static final int SIZE = 9;
    private final JTextField[][] cells = new JTextField[SIZE][SIZE];

    public SudokuSolver() {
        setTitle("🧩 Sudoku Solver");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(500, 550);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel gridPanel = new JPanel(new GridLayout(SIZE, SIZE));
        gridPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        Font font = new Font("SansSerif", Font.BOLD, 20);

        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                JTextField cell = new JTextField();
                cell.setHorizontalAlignment(JTextField.CENTER);
                cell.setFont(font);
                cell.setBorder(BorderFactory.createLineBorder(Color.GRAY));
                cells[row][col] = cell;
                gridPanel.add(cell);
            }
        }

        JPanel buttonPanel = new JPanel();
        JButton solveBtn = new JButton("✅ Solve");
        JButton clearBtn = new JButton("🧼 Clear");

        solveBtn.setFont(new Font("SansSerif", Font.BOLD, 16));
        clearBtn.setFont(new Font("SansSerif", Font.BOLD, 16));

        solveBtn.addActionListener(e -> solveSudoku());
        clearBtn.addActionListener(e -> clearGrid());

        buttonPanel.add(solveBtn);
        buttonPanel.add(clearBtn);

        add(gridPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
        setVisible(true);
    }

    private void clearGrid() {
        for (int row = 0; row < SIZE; row++)
            for (int col = 0; col < SIZE; col++)
                cells[row][col].setText("");
    }

    private void solveSudoku() {
        int[][] board = new int[SIZE][SIZE];
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                String text = cells[row][col].getText().trim();
                board[row][col] = text.isEmpty() ? 0 : Integer.parseInt(text);
            }
        }

        if (solve(board)) {
            for (int row = 0; row < SIZE; row++)
                for (int col = 0; col < SIZE; col++)
                    cells[row][col].setText(Integer.toString(board[row][col]));
        } else {
            JOptionPane.showMessageDialog(this, "No solution found!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private boolean solve(int[][] board) {
        for (int row = 0; row < SIZE; row++)
            for (int col = 0; col < SIZE; col++)
                if (board[row][col] == 0) {
                    for (int num = 1; num <= 9; num++) {
                        if (isValid(board, row, col, num)) {
                            board[row][col] = num;
                            if (solve(board)) return true;
                            board[row][col] = 0;
                        }
                    }
                    return false;
                }
        return true;
    }

    private boolean isValid(int[][] board, int row, int col, int num) {
        for (int i = 0; i < SIZE; i++) {
            if (board[row][i] == num || board[i][col] == num)
                return false;
            if (board[3 * (row / 3) + i / 3][3 * (col / 3) + i % 3] == num)
                return false;
        }
        return true;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(SudokuSolver::new);
    }
}
