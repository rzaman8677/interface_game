import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Random;

public class StrategyGame {
    static Character[][] board = new Character[8][8];
    static Scanner scanner = new Scanner(System.in);
    static List<Character> player1Pieces = new ArrayList<>();
    static List<Character> player2Pieces = new ArrayList<>();

    public static void main(String[] args) {
        initializeBoard();
        displayBoard();
        gameLoop();
    }

    private static void initializeBoard() {
        Random random = new Random();
    
        placePieceRandomly(0, 1, new Bomber(0, 0, "P1-B1"));
        placePieceRandomly(0, 1, new Healer(0, 0, "P1-H1"));
        placePieceRandomly(0, 1, new Hybrid(0, 0, "P1-Hy1"));
        placePieceRandomly(0, 1, new Bomber(0, 0, "P1-B2"));
        placePieceRandomly(0, 1, new Healer(0, 0, "P1-H2"));
        placePieceRandomly(0, 1, new Hybrid(0, 0, "P1-Hy2"));
    
        placePieceRandomly(6, 7, new Bomber(7, 0, "P2-B1"));
        placePieceRandomly(6, 7, new Healer(7, 0, "P2-H1"));
        placePieceRandomly(6, 7, new Hybrid(7, 0, "P2-Hy1"));
        placePieceRandomly(6, 7, new Bomber(7, 0, "P2-B2"));
        placePieceRandomly(6, 7, new Healer(7, 0, "P2-H2"));
        placePieceRandomly(6, 7, new Hybrid(7, 0, "P2-Hy2"));
    
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (board[i][j] != null) {
                    if (i < 2) player1Pieces.add(board[i][j]);
                    if (i > 5) player2Pieces.add(board[i][j]);
                }
            }
        }
    }
    
    private static void placePieceRandomly(int rowStart, int rowEnd, Character piece) {
        Random random = new Random();
        int x, y;
        do {
            x = rowStart + random.nextInt(rowEnd - rowStart + 1);
            y = random.nextInt(8);
        } while (board[x][y] != null);
    
        piece.x = x;
        piece.y = y;
        board[x][y] = piece;
    }

    private static void displayBoard() {
        System.out.println("\nBoard:");
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (board[i][j] == null) System.out.print(" .   ");
                else System.out.print(board[i][j].getTag() + "(" + board[i][j].health + ") ");
            }
            System.out.println();
        }
    }

    private static void gameLoop() {
        boolean isPlayer1Turn = true;

        while (true) {
            System.out.println(isPlayer1Turn ? "\nPlayer 1's turn:" : "\nPlayer 2's turn:");
            displayPlayerPieces(isPlayer1Turn);

            Character piece = selectPiece(isPlayer1Turn);
            if (piece == null) continue;

            boolean validAction = false;
            while (!validAction) {
                System.out.println("Choose action: (move, bomb, heal)");
                String action = scanner.next();

                if (action.equalsIgnoreCase("move") && piece instanceof Moving) {
                    showAvailableMoves(piece);
                    System.out.println("Enter new position (row col): ");
                    int newX = safeInput();
                    int newY = safeInput();
                    ((Moving) piece).move(board, newX, newY);
                    validAction = true;
                } else if (action.equalsIgnoreCase("bomb") && piece instanceof Bombing) {
                    showBombRange();
                    System.out.println("Enter target position (row col): ");
                    int targetX = safeInput();
                    int targetY = safeInput();
                    ((Bombing) piece).bomb(board, targetX, targetY);
                    validAction = true;
                } else if (action.equalsIgnoreCase("heal") && piece instanceof Healing) {
                    ((Healing) piece).heal(board);
                    validAction = true;
                } else {
                    System.out.println("Invalid action. Please try again.");
                }
            }

            displayBoard();
            if (checkWinCondition()) break;
            isPlayer1Turn = !isPlayer1Turn;
        }
    }

    private static Character selectPiece(boolean isPlayer1) {
        System.out.println("Enter the tag of the piece to move (e.g., P1-B1): ");
        String tag = scanner.next();
        List<Character> pieces = isPlayer1 ? player1Pieces : player2Pieces;

        for (Character piece : pieces) {
            if (piece.getTag().equals(tag) && piece.health > 0) {
                return piece;
            }
        }
        System.out.println("Invalid tag. Choose a valid piece.");
        return null;
    }

    private static void displayPlayerPieces(boolean isPlayer1) {
        List<Character> pieces = isPlayer1 ? player1Pieces : player2Pieces;
        System.out.println("Your pieces:");
        for (Character piece : pieces) {
            if (piece.health > 0) {
                System.out.println(" - " + piece.getTag() + " at (" + piece.x + ", " + piece.y + ")");
            }
        }
    }

    private static void showAvailableMoves(Character piece) {
        System.out.println("Available moves:");
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (piece instanceof Bomber) {
                    int deltaX = Math.abs(i - piece.x);
                    int deltaY = Math.abs(j - piece.y);
                    if ((deltaX <= 2 && deltaY <= 2) && piece.isValidMove(board, i, j)) {
                        System.out.println(" - (" + i + ", " + j + ")");
                    }
                } else if (piece instanceof Healer) {
                    if (i == piece.x + 1 && j == piece.y && piece.isValidMove(board, i, j)) {
                        System.out.println(" - (" + i + ", " + j + ")");
                    }
                } else if (piece instanceof Hybrid) {
                    if (Math.abs(i - piece.x) == Math.abs(j - piece.y) && piece.isValidMove(board, i, j)) {
                        System.out.println(" - (" + i + ", " + j + ")");
                    }
                }
            }
        }
    }

    private static void showBombRange() {
        System.out.println("Bomb can hit a 3x3 area around the target.");
    }

    private static int safeInput() {
        while (!scanner.hasNextInt()) {
            System.out.println("Invalid input. Enter a valid number: ");
            scanner.next();
        }
        return scanner.nextInt();
    }

    private static boolean checkWinCondition() {
        long player1Alive = player1Pieces.stream()
            .filter(p -> (p instanceof Bomber || p instanceof Hybrid) && p.health > 0)
            .count();

        long player2Alive = player2Pieces.stream()
            .filter(p -> (p instanceof Bomber || p instanceof Hybrid) && p.health > 0)
            .count();

        if (player1Alive == 0) {
            System.out.println("\nPlayer 2 wins! All Player 1's Bombers and Hybrids are eliminated.");
            return true;
        } else if (player2Alive == 0) {
            System.out.println("\nPlayer 1 wins! All Player 2's Bombers and Hybrids are eliminated.");
            return true;
        }
        return false;
    }
}
