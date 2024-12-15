abstract class Character {
    String type;
    int health;
    int x, y;
    String tag;

    public Character(String type, int x, int y, String tag) {
        this.type = type;
        this.health = 100;
        this.x = x;
        this.y = y;
        this.tag = tag;
    }

    public void display() {
        System.out.printf("%s ", tag);
    }

    public String getTag() {
        return tag;
    }

    protected boolean isValidMove(Character[][] board, int newX, int newY) {
        return newX >= 0 && newX < 8 && newY >= 0 && newY < 8 && board[newX][newY] == null;
    }
}
