package com.yumorder.dtos.restaurant;

import com.yumorder.dtos.address.AddressOutputDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class RestaurantOutputDtoWithAddress {
    private Long id;
    private String name;
    private String description;
    private String phone;
    private String email;
    private String website;
    private AddressOutputDto address;

}
