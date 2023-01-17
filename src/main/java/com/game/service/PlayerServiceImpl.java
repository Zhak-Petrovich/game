package com.game.service;

import com.game.entity.Player;
import com.game.repository.PlayersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service("playerService")
@Repository
@Transactional
public class PlayerServiceImpl implements PlayersService {

    PlayersRepository repository;

    @Autowired
    public void setRepository(PlayersRepository repository) {
        this.repository = repository;
    }

    @Override
    public Page findAll(Specification<Player> specification, Pageable pageable) {
        return repository.findAll(specification, pageable);
    }

    @Override
    public Page getFilteredPlayers(Specification<Player> specification, Pageable pageable) {
        return null;
    }

    @Override
    public long count(Specification<Player> specification) {
        return repository.count(specification);
    }

    @Override
    public ResponseEntity<?> create(Player player) {
        if (ServiceHelper.checkPlayer(player)) {
            player.setLevel(ServiceHelper.calculateLevel(player.getExperience()));
            player.setUntilNexLevel(ServiceHelper.calculateNextLevel(player.getLevel(), player.getExperience()));
            Player newPlayer = repository.save(player);
            return new ResponseEntity<>(newPlayer, HttpStatus.OK);
        } else return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @Override
    public ResponseEntity<?> edit(String id, Player player) {
        Long checkedId = ServiceHelper.checkAndGetId(id);
        if (checkedId == null) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        if (!repository.existsById(checkedId)) return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        Player editPlayer = repository.findById(checkedId).get();

        if (player.getName() == null && player.getTitle() == null && player.getRace() == null
                && player.getProfession() == null && player.getBirthday() == null && player.getBanned() == null && player.getExperience() == null)
            return new ResponseEntity<>(editPlayer, HttpStatus.OK);


        if (player.getName() != null && (player.getName().length() > 12 || player.getName().equals(""))
                || player.getTitle() != null && (player.getTitle().length() > 30 || player.getTitle().equals(""))
                || player.getBirthday() != null && (player.getBirthday().getYear() + 1900 < 2000
                || player.getBirthday().getYear() + 1900 > 3000 || player.getBirthday().getTime() < 0)
                || player.getExperience() != null && (player.getExperience() < 0
                || player.getExperience() > 10_000_000)) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);


        if (player.getName() != null) editPlayer.setName(player.getName());
        if (player.getTitle() != null) editPlayer.setTitle(player.getTitle());
        if (player.getRace() != null) editPlayer.setRace(player.getRace());
        if (player.getProfession() != null) editPlayer.setProfession(player.getProfession());
        if (player.getBirthday() != null) editPlayer.setBirthday(player.getBirthday());
        if (player.getBanned() != null) editPlayer.setBanned(player.getBanned());
        if (player.getExperience() != null) {
            editPlayer.setExperience(player.getExperience());
            editPlayer.setLevel(ServiceHelper.calculateLevel(player.getExperience()));
            editPlayer.setUntilNexLevel(ServiceHelper.calculateNextLevel(editPlayer.getLevel(), player.getExperience()));
        }

        return new ResponseEntity<>(repository.save(editPlayer), HttpStatus.OK);

    }

    @Override
    public ResponseEntity<?> delete(String id) {
        Long checkedId = ServiceHelper.checkAndGetId(id);

        if (checkedId != null) {
            if (!repository.existsById(checkedId)) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            repository.deleteById(checkedId);
            return new ResponseEntity<>(HttpStatus.OK);
        } else return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

    }

    @Override
    public ResponseEntity<?> get(String id) {
        Long checkedId = ServiceHelper.checkAndGetId(id);
        if (checkedId == null) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        if (!repository.existsById(checkedId)) return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        return new ResponseEntity<>(repository.findById(checkedId).get(), HttpStatus.OK);
    }
}
