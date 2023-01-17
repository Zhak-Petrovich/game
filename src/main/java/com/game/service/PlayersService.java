package com.game.service;

import com.game.entity.Player;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;

public interface PlayersService {
    Page findAll(Specification<Player> specification, Pageable pageable);
    Page getFilteredPlayers(Specification<Player> specification, Pageable pageable);
    long count(Specification<Player> specification);
    ResponseEntity<?> create(Player player);
    ResponseEntity<?> edit(String id, Player player);
    ResponseEntity<?> delete(String id);
    ResponseEntity<?> get(String id);

}
