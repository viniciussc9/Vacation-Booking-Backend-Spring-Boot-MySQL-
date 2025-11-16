package com.d288.vdasi11.services;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PurchaseResponse {
    private String orderTrackingNumber;

    public PurchaseResponse(String orderTrackingNumber) {
        this.orderTrackingNumber = orderTrackingNumber;
    }
}

