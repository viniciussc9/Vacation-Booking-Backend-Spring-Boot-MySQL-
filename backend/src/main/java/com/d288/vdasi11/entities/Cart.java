package com.d288.vdasi11.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "carts")
@Getter
@Setter
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cart_id")
    private Long id;

    @Column(name = "package_price", nullable = false)
    private BigDecimal package_price;

    @Column(name = "party_size", nullable = false)
    private int party_size;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private StatusType status;

    @Column(name = "order_tracking_number", nullable = false)
    private String orderTrackingNumber;

    @Column(name = "create_date", nullable = false)
    private Date create_date;

    @Column(name = "last_update", nullable = false)
    private Date last_update;

    // Correct relationship: Many carts belong to one customer
    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    // One cart has many cart items
    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL)
    private Set<CartItem> cartItems = new HashSet<>();

    //Add Item method():
    public void addItem(CartItem item) {
        cartItems.add(item);
        item.setCart(this);
    }

    // CreateDate

    public Date getCreateDate() {
        return create_date;
    }

    public void setCreateDate(Date createDate) {
        this.create_date = createDate;
    }

    public Date getLastUpdate() {
        return last_update;
    }

    public void setLastUpdate(Date lastUpdate) {
        this.last_update = lastUpdate;
    }

    public int getPartySize() {
        return this.party_size;
    }

    public void setPartySize(int partySize) {
        this.party_size = partySize;
    }





}




