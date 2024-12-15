class Hybrid extends Character implements Bombing, Healing, Moving {
    public Hybrid(int x, int y, String tag) {
        super("Hybrid", x, y, tag);
    }

    @Override
    public void bomb(Character[][] board, int targetX, int targetY) {
        System.out.println(tag + " bombs at (" + targetX + ", " + targetY + ") with half damage.");
        for (int i = Math.max(0, targetX - 1); i <= Math.min(7, targetX + 1); i++) {
            for (int j = Math.max(0, targetY - 1); j <= Math.min(7, targetY + 1); j++) {
                if (board[i][j] != null) {
                    board[i][j].health -= 20;
                    System.out.println(board[i][j].tag + " took 20 damage.");
                    if (board[i][j].health <= 0) board[i][j] = null;
                }
            }
        }
    }

    @Override
    public void heal(Character[][] board) {
        System.out.println(tag + " heals itself for 10 health.");
        this.health = Math.min(100, this.health + 10);
        System.out.println(tag + " healed to " + this.health + " health.");
    }

    @Override
    public void move(Character[][] board, int newX, int newY) {
        if (Math.abs(newX - this.x) == Math.abs(newY - this.y) && isValidMove(board, newX, newY)) {
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
