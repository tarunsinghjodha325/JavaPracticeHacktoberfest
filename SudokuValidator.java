import java.util.*;

/**
 * SudokuValidator
 * Validates a 9x9 Sudoku board.
 *
 * Rules:
 * - Digits 1..9 may appear at most once in each row, column, and 3x3 box.
 * - '.' (dot) is treated as empty.
 *
 * Usage:
 *   1) Just run to test the built-in sample boards.
 *   2) Or provide 9 lines via STDIN (digits and '.'), e.g.:
 *      java SudokuValidator < board.txt
 */
public class SudokuValidator {

    // Validate a 9x9 board of characters '1'..'9' or '.'
    public static boolean isValidSudoku(char[][] board) {
        // rows and cols
        for (int i = 0; i < 9; i++) {
            boolean[] rowSeen = new boolean[10];
            boolean[] colSeen = new boolean[10];
            for (int j = 0; j < 9; j++) {
                char r = board[i][j];
                if (r != '.') {
                    int d = r - '0';
                    if (d < 1 || d > 9 || rowSeen[d]) return false;
                    rowSeen[d] = true;
                }
                char c = board[j][i];
                if (c != '.') {
                    int d = c - '0';
                    if (d < 1 || d > 9 || colSeen[d]) return false;
                    colSeen[d] = true;
                }
            }
        }

        // 3x3 boxes
        for (int boxRow = 0; boxRow < 9; boxRow += 3) {
            for (int boxCol = 0; boxCol < 9; boxCol += 3) {
                boolean[] seen = new boolean[10];
                for (int i = 0; i < 3; i++) {
                    for (int j = 0; j < 3; j++) {
                        char ch = board[boxRow + i][boxCol + j];
                        if (ch != '.') {
                            int d = ch - '0';
                            if (d < 1 || d > 9 || seen[d]) return false;
                            seen[d] = true;
                        }
                    }
                }
            }
        }
        return true;
    }

    // Helper to read a board from stdin if exactly 9 lines are given
    private static Optional<char[][]> readBoardFromStdin() {
        Scanner sc = new Scanner(System.in);
        List<String> lines = new ArrayList<>();
        while (sc.hasNextLine()) {
            String line = sc.nextLine().trim();
            if (!line.isEmpty()) lines.add(line);
            if (lines.size() == 9) break;
        }
        if (lines.size() != 9) return Optional.empty();

        char[][] b = new char[9][9];
        for (int i = 0; i < 9; i++) {
            String s = lines.get(i).replaceAll("\\s+", "");
            if (s.length() != 9) return Optional.empty();
            b[i] = s.toCharArray();
        }
        return Optional.of(b);
    }

    private static void printBoard(char[][] b) {
        for (int i = 0; i < 9; i++) {
            if (i % 3 == 0) System.out.println("+-------+-------+-------+");
            for (int j = 0; j < 9; j++) {
                if (j % 3 == 0) System.out.print("| ");
                System.out.print(b[i][j] + " ");
            }
            System.out.println("|");
        }
        System.out.println("+-------+-------+-------+");
    }

    public static void main(String[] args) {
        // Try to read a board from STDIN; else use demo boards
        Optional<char[][]> maybeBoard = readBoardFromStdin();

        if (maybeBoard.isPresent()) {
            char[][] board = maybeBoard.get();
            System.out.println("Input board:");
            printBoard(board);
            System.out.println("Valid? " + isValidSudoku(board));
            return;
        }

        // --- Demo boards ---
        char[][] validBoard = {
            {'5','3','.','.','7','.','.','.','.'},
            {'6','.','.','1','9','5','.','.','.'},
            {'.','9','8','.','.','.','.','6','.'},
            {'8','.','.','.','6','.','.','.','3'},
            {'4','.','.','8','.','3','.','.','1'},
            {'7','.','.','.','2','.','.','.','6'},
            {'.','6','.','.','.','.','2','8','.'},
            {'.','.','.','4','1','9','.','.','5'},
            {'.','.','.','.','8','.','.','7','9'}
        };

        char[][] invalidRowBoard = {
            {'5','5','.','.','7','.','.','.','.'}, // duplicate 5 in row 0
            {'6','.','.','1','9','5','.','.','.'},
            {'.','9','8','.','.','.','.','6','.'},
            {'8','.','.','.','6','.','.','.','3'},
            {'4','.','.','8','.','3','.','.','1'},
            {'7','.','.','.','2','.','.','.','6'},
            {'.','6','.','.','.','.','2','8','.'},
            {'.','.','.','4','1','9','.','.','5'},
            {'.','.','.','.','8','.','.','7','9'}
        };

        System.out.println("Demo: valid board");
        printBoard(validBoard);
        System.out.println("Valid? " + isValidSudoku(validBoard)); // true

        System.out.println("\nDemo: invalid board (row duplicate)");
        printBoard(invalidRowBoard);
        System.out.println("Valid? " + isValidSudoku(invalidRowBoard)); // false
    }
}
