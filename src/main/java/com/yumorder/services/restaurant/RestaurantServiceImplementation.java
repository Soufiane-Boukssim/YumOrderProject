package com.yumorder.services.restaurant;

import com.yumorder.dtos.address.AddressInputDto;
import com.yumorder.dtos.restaurant.RestaurantInputDtoWithAddress;
import com.yumorder.dtos.restaurant.RestaurantOutputDtoWithAddress;
import com.yumorder.entities.Restaurant;
import com.yumorder.mappers.RestaurantMapper;
import com.yumorder.repositories.RestaurantRepository;
import com.yumorder.services.address.AddressService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service @RequiredArgsConstructor
public class RestaurantServiceImplementation implements RestaurantService {

    private final RestaurantMapper restaurantMapper;
    private final RestaurantRepository restaurantRepository;
    private final AddressService addressService;


    @Override
    public RestaurantOutputDtoWithAddress addRestaurant(RestaurantInputDtoWithAddress restaurantInputDtoWithAddress) {
        List<String> errorMessages = validateRestaurantDuplicates(restaurantInputDtoWithAddress);
        if (!errorMessages.isEmpty()) {
            throw new IllegalArgumentException(String.join(", ", errorMessages));
        }

        Restaurant restaurant = restaurantMapper.toEntity(restaurantInputDtoWithAddress);
        Restaurant savedRestaurant = restaurantRepository.save(restaurant);
        return restaurantMapper.toDto(savedRestaurant);
    }

    @Override
    public List<RestaurantOutputDtoWithAddress> getAllRestaurants() {
        List<Restaurant> restaurants = restaurantRepository.findAll();
        return restaurants.stream()
                .map(restaurantMapper::toDto)
                .collect(Collectors.toList());
    }



    private List<String> validateRestaurantDuplicates(RestaurantInputDtoWithAddress restaurantInputDtoWithAddress) {
        List<String> errorMessages = new ArrayList<>();

        AddressInputDto address = restaurantInputDtoWithAddress.getAddress();
        if (addressService.addressExists(address)       ) {
            errorMessages.add("Cette adresse est déjà prise.");
        }

        String email = restaurantInputDtoWithAddress.getEmail();
        if (restaurantRepository.existsByEmail(email)) {
            errorMessages.add("L'email '" + email + "' est déjà pris.");
        }

        String phone = restaurantInputDtoWithAddress.getPhone();
        if (restaurantRepository.existsByPhone(phone)) {
            errorMessages.add("Le téléphone '" + phone + "' est déjà pris.");
        }

        String name = restaurantInputDtoWithAddress.getName();
        if (restaurantRepository.existsByName(name)) {
            errorMessages.add("Le nom '" + name + "' est déjà pris.");
        }

        //website aussi
        String website = restaurantInputDtoWithAddress.getWebsite();
        if (restaurantRepository.existsByWebsite(website)) {
            errorMessages.add("Le website '" + website + "' est déjà pris.");
        }

        return errorMessages;
    }

}
