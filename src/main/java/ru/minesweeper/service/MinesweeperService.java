package ru.minesweeper.service;

import org.springframework.stereotype.Service;
import ru.minesweeper.exception.WrongTurnException;
import ru.minesweeper.model.GameField;
import ru.minesweeper.model.GameInfo;
import ru.minesweeper.to.GameInfoTo;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class MinesweeperService {
    private final Map<UUID, GameInfo> repository = new HashMap<>();
    private final Map<UUID, GameInfoTo> turnRepository = new HashMap<>();

    public GameInfo makeNewGame(GameInfo gameInfo) {
        repository.put(gameInfo.getGameId(), gameInfo);
        return repository.get(gameInfo.getGameId());
    }

    public void doTurn(UUID gameId, GameInfoTo gameInfoTo) {
        int col = gameInfoTo.getCol();
        int row = gameInfoTo.getRow();
        GameInfoTo existed = turnRepository.get(gameId);
        if (existed != null) {
            if (existed.getCol() == col && existed.getRow() == row) {
                throw new WrongTurnException("You have already made this turn");
            } else if (repository.get(gameId).getField().isCompleted()) {
                throw new WrongTurnException("Game is finished, you can't make turn");
            }
        }
        turnRepository.put(gameId, gameInfoTo);
        GameInfo info = repository.get(gameId);
        makeTurn(col, row, info.getField());
    }

    public void makeTurn(int col, int row, GameField gameField) {
        if (gameField.isCompleted() || gameField.getRevealed()[row][col]) {
            gameField.setCompleted(false);
            return; // Invalid move
        }

        if (gameField.getField()[row][col] == 'X') {
            gameField.setCompleted(true);
            revealAllMines(gameField);
            return; // Game over, stepped on a mine
        }

        revealCell(row, col, gameField);

        // Check condition for winning the game
        gameField.setUncoveredCells(gameField.getUncoveredCells() + 1);
        if (gameField.getUncoveredCells() == gameField.getWidth() * gameField.getHeight() - gameField.getMinesCount()) {
            gameField.setCompleted(true);
        }

    }

    private void revealCell(int row, int col, GameField gameField) {
        if (row < 0 || row >= gameField.getHeight() || col < 0 || col >= gameField.getWidth() || gameField.getRevealed()[row][col]) {
            return; // Out of bounds or already revealed
        }
        boolean[][] revealed = gameField.getRevealed();
        revealed[row][col] = true;
        gameField.setRevealed(revealed);

        if (gameField.getField()[row][col] == ' ') {
            for (int i = -1; i <= 1; i++) {
                for (int j = -1; j <= 1; j++) {
                    revealCell(row + i, col + j, gameField);
                }
            }
        }
    }

    private void revealAllMines(GameField gameField) {
        for (int i = 0; i < gameField.getHeight(); i++) {
            for (int j = 0; j < gameField.getWidth(); j++) {
                if (gameField.getField()[i][j] == 'X') {
                    boolean[][] revealed = gameField.getRevealed();
                    revealed[i][j] = true;
                    gameField.setRevealed(revealed);
                }
            }
        }
    }

    public GameInfo get(UUID gameId) {
        return repository.get(gameId);
    }
}
