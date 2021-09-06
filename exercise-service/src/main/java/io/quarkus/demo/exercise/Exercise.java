package io.quarkus.demo.exercise;

import io.quarkus.hibernate.orm.panache.PanacheEntity;

import javax.persistence.Entity;

@Entity
public class Exercise extends PanacheEntity {

    public String name;
    public String type;
}
