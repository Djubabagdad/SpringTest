package com.grokonez.jwtauthentication.model;

import org.hibernate.annotations.NaturalId;

import javax.persistence.*;

@Entity
@Table(name="state")
public class State {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="state_id")
    private Long id;

    @NaturalId
    @Column(name ="state_name")
    private String name;

    public State() {
    }

    public State(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
