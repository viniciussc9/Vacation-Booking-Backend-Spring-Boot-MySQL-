package com.d288.vdasi11.services;

import com.d288.vdasi11.entities.Cart;
import com.d288.vdasi11.entities.CartItem;
import com.d288.vdasi11.entities.Customer;
import lombok.Getter;
import lombok.Setter;
import java.util.Set;

@Getter
@Setter
public class Purchase {
    private Customer customer;
    private Cart cart;
    private Set<CartItem> cartItems;
    private int partySize;
}
