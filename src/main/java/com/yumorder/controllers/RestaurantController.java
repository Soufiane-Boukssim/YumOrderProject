package com.yumorder.controllers;

import com.yumorder.dtos.restaurant.RestaurantInputDtoWithAddress;
import com.yumorder.dtos.restaurant.RestaurantOutputDtoWithAddress;
import com.yumorder.services.restaurant.RestaurantService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController @RequestMapping("/restaurants") @RequiredArgsConstructor
public class RestaurantController {

    private final RestaurantService restaurantService;

    @PostMapping("/add") @ResponseStatus(HttpStatus.CREATED)
    public RestaurantOutputDtoWithAddress addRestaurant(@RequestBody RestaurantInputDtoWithAddress restaurantInputDtoWithAddress) {
        return restaurantService.addRestaurant(restaurantInputDtoWithAddress);
    }

    @GetMapping("/get/all") @ResponseStatus(HttpStatus.OK)
    public List<RestaurantOutputDtoWithAddress> getAllRestaurants() {
        return restaurantService.getAllRestaurants();
    }

}
