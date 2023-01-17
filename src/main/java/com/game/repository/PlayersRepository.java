package com.game.repository;

import com.game.entity.Player;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface PlayersRepository extends PagingAndSortingRepository<Player, Long>, JpaSpecificationExecutor<Player> {
}
