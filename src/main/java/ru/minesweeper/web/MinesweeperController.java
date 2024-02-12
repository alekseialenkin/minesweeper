package ru.minesweeper.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.minesweeper.model.GameField;
import ru.minesweeper.model.GameInfo;
import ru.minesweeper.service.MinesweeperService;
import ru.minesweeper.to.GameFieldTo;
import ru.minesweeper.to.GameInfoTo;

import java.net.URI;
import java.util.UUID;

@RestController
@RequestMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
public class MinesweeperController {
    private static final Logger log = LoggerFactory.getLogger(MinesweeperController.class);

    @Autowired
    private MinesweeperService service;

    @PostMapping(value = "/new", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GameInfo> makeNewGame(@RequestBody GameFieldTo gameFieldTo) {
        GameInfo created = service.makeNewGame(new GameInfo(new GameField(gameFieldTo.getWidth(),
                gameFieldTo.getHeight(), gameFieldTo.getMinesCount())));
        log.info("start new game {}", created);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/new")
                .buildAndExpand(created.getGameId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @PutMapping("/turn")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void turn(@RequestBody GameInfoTo gameInfoTo) {
        log.info("next game turn for id = {}", gameInfoTo.getGameId());
        service.doTurn(UUID.fromString(gameInfoTo.getGameId()), gameInfoTo);
    }

    @GetMapping
    public GameInfo getInfo(@RequestParam(name = "game_id") String id) {
        log.info("get information about current game with id {}", id);
        return service.get(UUID.fromString(id));
    }
}
