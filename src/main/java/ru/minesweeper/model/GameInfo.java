package ru.minesweeper.model;

import java.util.UUID;

public class GameInfo {
    private UUID gameId;
    private GameField field;

    public GameInfo(GameField field) {
        this.field = field;
        this.gameId = UUID.randomUUID();
    }

    public GameField getField() {
        return field;
    }

    public void setField(GameField field) {
        this.field = field;
    }

    public UUID getGameId() {
        return gameId;
    }

    public void setGameId(UUID gameId) {
        this.gameId = gameId;
    }

}
