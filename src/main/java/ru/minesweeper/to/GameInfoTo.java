package ru.minesweeper.to;

public class GameInfoTo {
    private String gameId;
    private int col;
    private int row;

    public GameInfoTo(String gameId, int col, int row) {
        this.gameId = gameId;
        this.col = col;
        this.row = row;
    }

    public String getGameId() {
        return gameId;
    }

    public void setGameId(String gameId) {
        this.gameId = gameId;
    }

    public int getCol() {
        return col;
    }

    public void setCol(int col) {
        this.col = col;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }
}
