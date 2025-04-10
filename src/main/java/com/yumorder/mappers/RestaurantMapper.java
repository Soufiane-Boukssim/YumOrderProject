package com.yumorder.mappers;

import com.yumorder.dtos.restaurant.RestaurantInputDtoWithAddress;
import com.yumorder.dtos.restaurant.RestaurantOutputDtoWithAddress;
import com.yumorder.entities.Restaurant;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor @Service
public class RestaurantMapper {

    private final ModelMapper modelMapper=new ModelMapper();
    private final AddressMapper addressMapper;

    public Restaurant toEntity(RestaurantInputDtoWithAddress restaurantInputDtoWithAddress) {
        Restaurant restaurant = modelMapper.map(restaurantInputDtoWithAddress, Restaurant.class);
        restaurant.setAddress(addressMapper.toEntity(restaurantInputDtoWithAddress.getAddress()));
        return restaurant;
    }

    public RestaurantOutputDtoWithAddress toDto(Restaurant restaurant) {
        RestaurantOutputDtoWithAddress dto = modelMapper.map(restaurant, RestaurantOutputDtoWithAddress.class);
        dto.setAddress(addressMapper.toDto(restaurant.getAddress()));
        return dto;
    }

}
