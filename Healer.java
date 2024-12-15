class Healer extends Character implements Healing, Moving {
    public Healer(int x, int y, String tag) {
        super("Healer", x, y, tag);
    }

    @Override
    public void heal(Character[][] board) {
        System.out.println(tag + " heals in a 3x3 area.");
        for (int i = Math.max(0, this.x - 1); i <= Math.min(7, this.x + 1); i++) {
            for (int j = Math.max(0, this.y - 1); j <= Math.min(7, this.y + 1); j++) {
                if (board[i][j] != null) {
                    board[i][j].health = Math.min(100, board[i][j].health + 20);
                    System.out.println(board[i][j].tag + " healed to " + board[i][j].health + " health.");
                }
            }
        }
    }

    @Override
    public void move(Character[][] board, int newX, int newY) {
        if (newX == this.x + 1 && newY == this.y && isValidMove(board, newX, newY)) {
            board[this.x][this.y] = null;
            this.x = newX;
            this.y = newY;
            board[newX][newY] = this;
            System.out.println(tag + " moved to (" + newX + ", " + newY + ")");
        } else {
            System.out.println("Invalid move for " + tag);
        }
    }
}
