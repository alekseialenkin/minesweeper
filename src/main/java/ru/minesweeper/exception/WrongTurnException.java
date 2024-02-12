package ru.minesweeper.exception;

public class WrongTurnException extends RuntimeException{
    public WrongTurnException(String message) {
        super(message);
    }
}
