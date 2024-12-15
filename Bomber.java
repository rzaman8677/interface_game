class Bomber extends Character implements Bombing, Moving {
    public Bomber(int x, int y, String tag) {
        super("Bomber", x, y, tag);
    }

    @Override
    public void bomb(Character[][] board, int targetX, int targetY) {
        System.out.println(tag + " bombs at (" + targetX + ", " + targetY + ")");
        for (int i = Math.max(0, targetX - 1); i <= Math.min(7, targetX + 1); i++) {
            for (int j = Math.max(0, targetY - 1); j <= Math.min(7, targetY + 1); j++) {
                if (board[i][j] != null) {
                    board[i][j].health -= 40;
                    System.out.println(board[i][j].tag + " took 40 damage.");
                    if (board[i][j].health <= 0) board[i][j] = null;
                }
            }
        }
    }

    @Override
    public void move(Character[][] board, int newX, int newY) {
        int deltaX = Math.abs(newX - this.x);
        int deltaY = Math.abs(newY - this.y);
        if ((deltaX <= 2 && deltaY <= 2) && isValidMove(board, newX, newY)) {
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
