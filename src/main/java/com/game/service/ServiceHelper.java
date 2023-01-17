package com.game.service;

import com.game.controller.PlayerOrder;
import com.game.entity.Player;
import com.game.entity.Profession;
import com.game.entity.Race;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class ServiceHelper {

    public static boolean checkPlayer(Player player) {
        if (player.getName() == null
                || player.getTitle() == null
                || player.getRace() == null
                || player.getProfession() == null
                || player.getBirthday() == null
                || player.getExperience() == null) return false;

        if (player.getBanned() == null) player.setBanned(false);

        return player.getName().length() <= 12
                && player.getName().length() > 0
                && !Objects.equals(player.getName(), "")
                && player.getTitle().length() <= 30
                && player.getTitle().length() > 0
                && player.getExperience() >= 0 && player.getExperience() <= 10_000_000
                && !(player.getBirthday().getTime() < 0)
                && player.getBirthday().getYear() + 1900 >= 2000
                && player.getBirthday().getYear() + 1900 <= 3000;
    }

    public static Long checkAndGetId(String id) {
        long result;

        try {
            result = Long.parseLong(id);
            if (result <= 0) return null;
            else return result;
        } catch (NumberFormatException e) {
            return null;
        }
    }

    public static Integer calculateLevel(Integer experience) {
                return (int) Math.floor((Math.sqrt(2500 + 200 * experience) - 50) / 100);
    }

    public static Integer calculateNextLevel(Integer level, Integer experience) {
        return 50 * (level + 1) * (level + 2) - experience;
    }


    public static List<Player> findByPagingCriteria(PlayersService playersService,
                                                    String name,
                                                    String title,
                                                    Race race,
                                                    Profession profession,
                                                    Long after,
                                                    Long before,
                                                    Boolean banned,
                                                    Integer minExperience,
                                                    Integer maxExperience,
                                                    Integer minLevel,
                                                    Integer maxLevel,
                                                    PlayerOrder order,
                                                    Integer pageNumber,
                                                    Integer pageSize) {

        Pageable pageable = PageRequest.of(pageNumber, pageSize);

        Page page = playersService.findAll(new Specification<Player>() {
            @Override
            public Predicate toPredicate(Root<Player> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();
                if (name != null) {
                    predicates.add(criteriaBuilder.and(criteriaBuilder.like(root.get("name"), "%" + name + "%")));
                }
                if (title != null) {
                    predicates.add(criteriaBuilder.and(criteriaBuilder.like(root.get("title"), "%" + title + "%")));
                }
                if (race != null) {
                    predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("race"), race)));
                }
                if (profession != null) {
                    predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("profession"), profession)));
                }
                if (after != null) {
                    predicates.add(criteriaBuilder.and(criteriaBuilder.greaterThanOrEqualTo(root.get("birthday"), new Date(after))));
                }
                if (before != null) {
                    predicates.add(criteriaBuilder.and(criteriaBuilder.lessThanOrEqualTo(root.get("birthday"), new Date(before))));
                }
                if (banned != null) {
                    predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("banned"), banned)));
                }
                if (minExperience != null) {
                    predicates.add(criteriaBuilder.and(criteriaBuilder.greaterThanOrEqualTo(root.get("experience"), minExperience)));
                }
                if (maxExperience != null) {
                    predicates.add(criteriaBuilder.and(criteriaBuilder.lessThanOrEqualTo(root.get("experience"), maxExperience)));
                }
                if (minLevel != null) {
                    predicates.add(criteriaBuilder.and(criteriaBuilder.greaterThanOrEqualTo(root.get("level"), minLevel)));
                }
                if (maxLevel != null) {
                    predicates.add(criteriaBuilder.and(criteriaBuilder.lessThanOrEqualTo(root.get("level"), maxLevel)));
                }
                if (order != null) {
                    query.orderBy(criteriaBuilder.asc(root.get(order.getFieldName())));
                }
                return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        }, pageable);

        return page.getContent();
    }

    public static Integer countByCriteria(PlayersService playersService,
                                          String name,
                                          String title,
                                          Race race,
                                          Profession profession,
                                          Long after,
                                          Long before,
                                          Boolean banned,
                                          Integer minExperience,
                                          Integer maxExperience,
                                          Integer minLevel,
                                          Integer maxLevel) {
        return (int) playersService.count(new Specification<Player>() {
            @Override
            public Predicate toPredicate(Root<Player> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();
                if (name != null) {
                    predicates.add(criteriaBuilder.and(criteriaBuilder.like(root.get("name"), "%" + name + "%")));
                }
                if (title != null) {
                    predicates.add(criteriaBuilder.and(criteriaBuilder.like(root.get("title"), "%" + title + "%")));
                }
                if (race != null) {
                    predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("race"), race)));
                }
                if (profession != null) {
                    predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("profession"), profession)));
                }
                if (after != null) {
                    predicates.add(criteriaBuilder.and(criteriaBuilder.greaterThanOrEqualTo(root.get("birthday"), new Date(after))));
                }
                if (before != null) {
                    predicates.add(criteriaBuilder.and(criteriaBuilder.lessThanOrEqualTo(root.get("birthday"), new Date(before))));
                }
                if (banned != null) {
                    predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("banned"), banned)));
                }
                if (minExperience != null) {
                    predicates.add(criteriaBuilder.and(criteriaBuilder.greaterThanOrEqualTo(root.get("experience"), minExperience)));
                }
                if (maxExperience != null) {
                    predicates.add(criteriaBuilder.and(criteriaBuilder.lessThanOrEqualTo(root.get("experience"), maxExperience)));
                }
                if (minLevel != null) {
                    predicates.add(criteriaBuilder.and(criteriaBuilder.greaterThanOrEqualTo(root.get("level"), minLevel)));
                }
                if (maxLevel != null) {
                    predicates.add(criteriaBuilder.and(criteriaBuilder.lessThanOrEqualTo(root.get("level"), maxLevel)));
                }


                return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        });
    }
}
