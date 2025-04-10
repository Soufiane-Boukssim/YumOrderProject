package com.yumorder.dtos.address;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class AddressOutputDto {
    private Long id;
    private String country;
    private String state;
    private String city;
    private String street;
    private String zipCode;
    private double latitude;
    private double longitude;
}
