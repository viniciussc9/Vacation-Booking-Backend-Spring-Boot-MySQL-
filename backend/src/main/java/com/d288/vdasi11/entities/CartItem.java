package com.d288.vdasi11.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Set;
import java.util.HashSet;

@Entity
@Table(name = "cart_items")
@Getter
@Setter
public class CartItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cart_item_id")
    private Long id;

    @Column(name = "create_date")
    private Date createDate;

    @Column(name = "last_update")
    private Date lastUpdate;

    // Many cart items belong to one cart
    @ManyToOne
    @JoinColumn(name = "cart_id")
    private Cart cart;

    // Many cart items are linked to many excursions (owning side)
    @ManyToMany
    @JoinTable(
            name = "excursion_cartitem",
            joinColumns = @JoinColumn(name = "cart_item_id"),
            inverseJoinColumns = @JoinColumn(name = "excursion_id")
    )
    //private Set<Excursion> excursions;
    private Set<Excursion> excursions = new HashSet<>();

    // One cart item is linked to one vacation
    @ManyToOne
    @JoinColumn(name = "vacation_id")
    private Vacation vacation;
}


