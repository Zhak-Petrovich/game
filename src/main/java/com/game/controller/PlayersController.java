package com.game.controller;

import com.game.entity.Profession;
import com.game.entity.Race;
import com.game.entity.Player;
import com.game.service.ServiceHelper;
import com.game.service.PlayersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class PlayersController {
    private PlayersService playersService;

    @Autowired
    public void setPlayersService(@Qualifier("playerService") PlayersService playersService) {
        this.playersService = playersService;
    }

    @GetMapping("/rest/players")
    public ResponseEntity<?> getPlayersList(@RequestParam(value = "name", required = false) String name,
                                            @RequestParam(value = "title", required = false) String title,
                                            @RequestParam(value = "race", required = false) Race race,
                                            @RequestParam(value = "profession", required = false) Profession profession,
                                            @RequestParam(value = "after", required = false) Long after,
                                            @RequestParam(value = "before", required = false) Long before,
                                            @RequestParam(value = "banned", required = false) Boolean banned,
                                            @RequestParam(value = "minExperience", required = false) Integer minExperience,
                                            @RequestParam(value = "maxExperience", required = false) Integer maxExperience,
                                            @RequestParam(value = "minLevel", required = false) Integer minLevel,
                                            @RequestParam(value = "maxLevel", required = false) Integer maxLevel,
                                            @RequestParam(value = "order", required = false) PlayerOrder playerOrder,
                                            @RequestParam(value = "pageNumber", required = false, defaultValue = "0") Integer pageNumber,
                                            @RequestParam(value = "pageSize", required = false, defaultValue = "3") Integer pageSize) {

        List<Player> result = ServiceHelper.findByPagingCriteria(playersService,
                name,
                title,
                race,
                profession,
                after,
                before,
                banned,
                minExperience,
                maxExperience,
                minLevel,
                maxLevel,
                playerOrder,
                pageNumber,
                pageSize);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/rest/players/count")
    public ResponseEntity<?> getPlayersCount(@RequestParam(value = "name", required = false) String name,
                                             @RequestParam(value = "title", required = false) String title,
                                             @RequestParam(value = "race", required = false) Race race,
                                             @RequestParam(value = "profession", required = false) Profession profession,
                                             @RequestParam(value = "after", required = false) Long after,
                                             @RequestParam(value = "before", required = false) Long before,
                                             @RequestParam(value = "banned", required = false) Boolean banned,
                                             @RequestParam(value = "minExperience", required = false) Integer minExperience,
                                             @RequestParam(value = "maxExperience", required = false) Integer maxExperience,
                                             @RequestParam(value = "minLevel", required = false) Integer minLevel,
                                             @RequestParam(value = "maxLevel", required = false) Integer maxLevel) {

        Integer result = ServiceHelper.countByCriteria(playersService,
                name,
                title,
                race,
                profession,
                after,
                before,
                banned,
                minExperience,
                maxExperience,
                minLevel,
                maxLevel);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping("/rest/players")
    public ResponseEntity<?> createPlayer(@RequestBody Player player) {
        return playersService.create(player);
    }

    @GetMapping("/rest/players/{id}")
    public ResponseEntity<?> getPlayer(@PathVariable String id) {
        return playersService.get(id);
    }

    @PostMapping("/rest/players/{id}")
    public ResponseEntity<?> updatePlayer(@PathVariable String id, @RequestBody Player player) {
        return playersService.edit(id, player);
    }

    @DeleteMapping("/rest/players/{id}")
    public ResponseEntity<?> deletePlayer(@PathVariable String id) {
        return playersService.delete(id);
    }
}
