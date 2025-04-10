package com.yumorder.services.restaurant;

import com.yumorder.dtos.address.AddressInputDto;
import com.yumorder.dtos.restaurant.RestaurantInputDtoWithAddress;
import com.yumorder.dtos.restaurant.RestaurantOutputDtoWithAddress;
import com.yumorder.entities.Address;
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
        List<String> errorMessages = validateRestaurantDuplicatesForAdd(restaurantInputDtoWithAddress);
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

    @Override
    public Boolean deleteRestaurantById(Long id) {
        if (restaurantRepository.existsById(id)) {
            restaurantRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public RestaurantOutputDtoWithAddress updateRestaurantById(Long id, RestaurantInputDtoWithAddress restaurantInputDtoWithAddress) {
        Restaurant existingRestaurant = restaurantRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Restaurant avec l'ID " + id + " non trouvé"));

        List<String> errorMessages = validateRestaurantDuplicatesForUpdate(restaurantInputDtoWithAddress, id);

        if (!errorMessages.isEmpty()) {
            throw new IllegalArgumentException(String.join(", ", errorMessages));
        }

        Restaurant updatedRestaurant = restaurantMapper.toEntity(restaurantInputDtoWithAddress);
        existingRestaurant.setName(updatedRestaurant.getName());
        existingRestaurant.setDescription(updatedRestaurant.getDescription());
        existingRestaurant.setPhone(updatedRestaurant.getPhone());
        existingRestaurant.setEmail(updatedRestaurant.getEmail());
        existingRestaurant.setWebsite(updatedRestaurant.getWebsite());

//        if (updatedRestaurant.getAddress() != null) {
//            existingRestaurant.setAddress(updatedRestaurant.getAddress());
//        }


        Address existingAddress = existingRestaurant.getAddress();
        AddressInputDto newAddressDto = restaurantInputDtoWithAddress.getAddress();

        if (newAddressDto != null) {
            existingAddress.setCountry(newAddressDto.getCountry());
            existingAddress.setState(newAddressDto.getState());
            existingAddress.setCity(newAddressDto.getCity());
            existingAddress.setStreet(newAddressDto.getStreet());
            existingAddress.setZipCode(newAddressDto.getZipCode());
            existingAddress.setLatitude(newAddressDto.getLatitude());
            existingAddress.setLongitude(newAddressDto.getLongitude());

        }



        Restaurant savedRestaurant = restaurantRepository.save(existingRestaurant);

        return restaurantMapper.toDto(savedRestaurant);
    }

    private List<String> validateRestaurantDuplicatesForAdd(RestaurantInputDtoWithAddress restaurantInputDtoWithAddress) {
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


    private List<String> validateRestaurantDuplicatesForUpdate(RestaurantInputDtoWithAddress restaurantInputDtoWithAddress, Long restaurantId) {
        List<String> errorMessages = new ArrayList<>();

        AddressInputDto address = restaurantInputDtoWithAddress.getAddress();

        Long addressId = restaurantRepository.findById(restaurantId)
                .map(Restaurant::getAddress)
                .map(Address::getId)
                .orElse(null); // ou -1L si tu veux éviter null

        if (addressService.addressExistsAndIdNot(address, addressId)       ) {
            errorMessages.add("Cette adresse est déjà prise.");
        }

        String email = restaurantInputDtoWithAddress.getEmail();
        if (restaurantRepository.existsByEmailAndIdNot(email, restaurantId)) {
            errorMessages.add("L'email '" + email + "' est déjà pris.");
        }

        String phone = restaurantInputDtoWithAddress.getPhone();
        if (restaurantRepository.existsByPhoneAndIdNot(phone, restaurantId)) {
            errorMessages.add("Le téléphone '" + phone + "' est déjà pris.");
        }

        String name = restaurantInputDtoWithAddress.getName();
        if (restaurantRepository.existsByNameAndIdNot(name, restaurantId)) {
            errorMessages.add("Le nom '" + name + "' est déjà pris.");
        }

        //website aussi
        String website = restaurantInputDtoWithAddress.getWebsite();
        if (restaurantRepository.existsByWebsiteAndIdNot(website, restaurantId)) {
            errorMessages.add("Le website '" + website + "' est déjà pris.");
        }

        return errorMessages;
    }

}
