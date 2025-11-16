package com.d288.vdasi11.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "divisions")
@Getter
@Setter
public class Division {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "division_id")
    private Long id;

    @Column(name = "division")
    private String divisionName;

    @Column(name = "create_date")
    private Date createDate;

    @Column(name = "last_update")
    private Date lastUpdate;

    // Many divisions belong to one country
    @ManyToOne
    @JoinColumn(name = "country_id")
    private Country country;

    // One division has many customers
    @OneToMany(mappedBy = "division", cascade = CascadeType.ALL)
    private Set<Customer> customers;

    // Optional helper method to set country and sync country_id
    public void setCountry(Country country) {
        this.country = country;
    }
}

