package com.example.KES.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddressDTO {
    private String addressCode;
    private int addressId;
    private String name;
    private String street;
    private String houseNumber;
    private String postalCode;
    private String city;
    private String comment;
    private String careOf;
    private String floor;
    private String door;
    private String area;
    private String state;
    private String countryCode;
    private int countryId;
    private double latitude;
    private double longitude;
    private int stopType;
    private String stopTypeCode;
    private String deliveryEft;
    private String deliveryLtt;
    private String pickupEft;
    private String pickupLtt;
    private String doorKey;
    private String customerCode;
    private String sourceRef;
    private String metadata;
    private int operation; // INSERT, UPDATE, DELETE
}