package com.game.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
public class Player implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String title;

    @Enumerated(EnumType.STRING)
    private Race race;
    @Enumerated(EnumType.STRING)
    private Profession profession;
    private Integer experience;
    private Integer level;
    private Integer untilNextLevel;
    @Temporal(TemporalType.DATE)
    private Date birthday;
    private Boolean banned;

    public Player() {}

    public Long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Race getRace() {
        return race;
    }

    public void setRace(Race race) {
        this.race = race;
    }

    public Profession getProfession() {
        return profession;
    }

    public void setProfession(Profession profession) {
        this.profession = profession;
    }

    public Integer getExperience() {
        return experience;
    }

    public void setExperience(int experience) {
        this.experience = experience;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public Integer getUntilNexLevel() {
        return untilNextLevel;
    }

    public void setUntilNexLevel(int untilNexLevel) {
        this.untilNextLevel = untilNexLevel;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public Boolean getBanned() {
        return banned;
    }

    public void setBanned(boolean banned) {
        this.banned = banned;
    }

    @Override
    public String toString() {
        return "id = " + getId() +
                " Name = " + getName() +
                " Title = " + getTitle() +
                " Race = " + getRace() +
                " Profession = " + getProfession() +
                " Experience = " + getExperience() +
                " Level = " + getLevel() +
                " Until next level = " + getUntilNexLevel() +
                " BDay = " + getBirthday().toString() +
                " banned = " + getBanned();
    }
}
