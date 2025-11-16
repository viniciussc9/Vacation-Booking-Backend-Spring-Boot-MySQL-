package com.d288.vdasi11.services;

import com.d288.vdasi11.entities.*;
import com.d288.vdasi11.dao.CustomerRepository;
import com.d288.vdasi11.dao.CartRepository;
import com.d288.vdasi11.dao.CartItemRepository;
import com.d288.vdasi11.dao.DivisionRepository;
import com.d288.vdasi11.dao.ExcursionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.HashSet; // import for HashSet

@Service
public class CheckoutServiceImpl implements CheckoutService {

    private final CustomerRepository customerRepository;
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final DivisionRepository divisionRepository;
    private final ExcursionRepository excursionRepository;

    public CheckoutServiceImpl(CustomerRepository customerRepository,
                               CartRepository cartRepository,
                               CartItemRepository cartItemRepository,
                               DivisionRepository divisionRepository,
                               ExcursionRepository excursionRepository) {
        this.customerRepository = customerRepository;
        this.cartRepository = cartRepository;
        this.cartItemRepository = cartItemRepository;
        this.divisionRepository = divisionRepository;
        this.excursionRepository = excursionRepository;
    }

    @Override
    @Transactional
    public PurchaseResponse placeOrder(Purchase purchase) {

        Customer customer = purchase.getCustomer();
        Cart cart = purchase.getCart();
        Set<CartItem> cartItems = purchase.getCartItems();

        // Assign division if not already set
        if (customer.getDivision() == null) {
            Long divisionId = customer.getDivisionId();
            if (divisionId != null) {
                Division division = divisionRepository.findById(divisionId)
                        .orElseThrow(() -> new RuntimeException("Invalid division ID: " + divisionId));
                customer.setDivision(division);
            } else {
                Optional<Division> defaultDivision = divisionRepository.findAll().stream().findFirst();
                defaultDivision.ifPresent(customer::setDivision);
            }
        }

        // Process cart items
        for (CartItem item : cartItems) {
            // Sync relationship with cart
            item.setCart(cart);
            cart.addItem(item);

            // Load excursions from DB and assign
            Set<Excursion> excursions = item.getExcursions();
            Set<Excursion> fixedExcursions = new HashSet<>();
            if (excursions != null) {
                for (Excursion ex : excursions) {
                    if (ex.getId() != null) {
                        Excursion loadedExcursion = excursionRepository.findById(ex.getId())
                                .orElseThrow(() -> new RuntimeException("Excursion not found with ID: " + ex.getId()));
                        fixedExcursions.add(loadedExcursion);
                    }
                }
            }
            item.setExcursions(fixedExcursions);
        }

        // Finish setting cart
        cart.setCustomer(customer);
        cart.setOrderTrackingNumber(generateOrderTrackingNumber());
        cart.setCreateDate(new Date());
        cart.setLastUpdate(new Date());
        cart.setStatus(StatusType.ordered);

        // Default party size
        if (cart.getPartySize() <= 0) {
            System.out.println("️ Invalid party size provided. Defaulting to 1.");
            cart.setPartySize(1);
        }

        // Save all
        for (CartItem item : cartItems) {
            item.setCart(cart);
            item.setCreateDate(new Date());
            item.setLastUpdate(new Date());
        }
        customerRepository.save(customer);
        cartRepository.save(cart);

        return new PurchaseResponse(cart.getOrderTrackingNumber());
    }

    private String generateOrderTrackingNumber() {
        return UUID.randomUUID().toString();
    }
}
