package com.d288.vdasi11.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "customers")
@Getter
@Setter
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "customer_id")
    private Long id;

    @NotBlank
    @Column(name = "customer_first_name", nullable = false)
    private String firstName;

    @NotBlank
    @Column(name = "customer_last_name", nullable = false)
    private String lastName;

    @NotBlank
    @Column(name = "address", nullable = false)
    private String address;

    @NotBlank(message = "Postal code is required")
    @JsonProperty("postal_code")
    @Column(name = "postal_code", nullable = false)
    private String postalCode;

    @NotBlank
    @Column(name = "phone", nullable = false)
    private String phone;

    @Column(name = "create_date")
    private Date createDate;

    @Column(name = "last_update")
    private Date lastUpdate;

    // Add this transient field to map the ID from frontend
    @Transient
    private Long divisionId;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "division_id", nullable = false)
    private Division division;

    // Cascade to carts
    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
    private List<Cart> carts = new ArrayList<>();

    public void addCart(Cart cart) {
        carts.add(cart);
        cart.setCustomer(this);
    }

    public Long getDivisionId() {
        return divisionId;
    }

    public void setDivisionId(Long divisionId) {
        this.divisionId = divisionId;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }




}

