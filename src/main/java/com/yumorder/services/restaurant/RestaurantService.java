package com.yumorder.services.restaurant;

import com.yumorder.dtos.restaurant.RestaurantInputDtoWithAddress;
import com.yumorder.dtos.restaurant.RestaurantOutputDtoWithAddress;

import java.util.List;

public interface RestaurantService {
    RestaurantOutputDtoWithAddress addRestaurant(RestaurantInputDtoWithAddress restaurantInputDtoWithAddress);
    List<RestaurantOutputDtoWithAddress> getAllRestaurants();

}
