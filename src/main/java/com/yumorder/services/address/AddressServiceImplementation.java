package com.yumorder.services.address;

import com.yumorder.dtos.address.AddressInputDto;
import com.yumorder.repositories.AddressRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service @RequiredArgsConstructor
public class AddressServiceImplementation implements AddressService {
    private final AddressRepository addressRepository;

    public boolean addressExists(AddressInputDto address) {
        return addressRepository.existsByCountryAndStateAndCityAndStreetAndZipCodeAndLatitudeAndLongitude(
                address.getCountry(),
                address.getState(),
                address.getCity(),
                address.getStreet(),
                address.getZipCode(),
                address.getLatitude(),
                address.getLongitude()
        );
    }

    public boolean addressExistsAndIdNot(AddressInputDto address, Long id) {
        return addressRepository.existsByCountryAndStateAndCityAndStreetAndZipCodeAndLatitudeAndLongitudeAndIdNot(
                address.getCountry(),
                address.getState(),
                address.getCity(),
                address.getStreet(),
                address.getZipCode(),
                address.getLatitude(),
                address.getLongitude(),
                id  // Exclure l'ID
        );
    }
}
