package ru.minesweeper.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Random;

public class GameField {
    private int width;
    private int height;
    private int minesCount;
    private char[][] field;
    private boolean completed;
    @JsonIgnore
    private int uncoveredCells;
    @JsonIgnore
    private boolean[][] revealed;

    public GameField(int width, int height, int minesCount) {
        this.width = width;
        this.height = height;
        this.minesCount = minesCount;
        this.field = new char[width][height];
        this.uncoveredCells = 0;
        this.revealed = new boolean[height][width];
        initializeField();
    }

    private void initializeField() {
        Random random = new Random();
        for (int i = 0; i < minesCount; i++) {
            field[random.nextInt(height)][random.nextInt(width)] = 'X';
        }
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (field[i][j] != 'X') {
                    field[i][j] = ' ';
                }
            }
        }
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                if (field[row][col] != 'X') {
                    int count = countAdjacentMines(row, col);
                    if (count > 0) {
                        field[row][col] = (char) (count + '0');
                    }
                }
            }
        }
    }

    private int countAdjacentMines(int row, int col) {
        int count = 0;
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                int newRow = row + i;
                int newCol = col + j;
                if (newRow >= 0 && newRow < height && newCol >= 0 && newCol < width) {
                    if (field[newRow][newCol] == 'X') {
                        count++;
                    }
                }
            }
        }
        return count;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getMinesCount() {
        return minesCount;
    }

    public void setMinesCount(int minesCount) {
        this.minesCount = minesCount;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public char[][] getField() {
        return field;
    }

    public void setField(char[][] field) {
        this.field = field;
    }

    public int getUncoveredCells() {
        return uncoveredCells;
    }

    public void setUncoveredCells(int uncoveredCells) {
        this.uncoveredCells = uncoveredCells;
    }

    public boolean[][] getRevealed() {
        return revealed;
    }

    public void setRevealed(boolean[][] revealed) {
        this.revealed = revealed;
    }
}
