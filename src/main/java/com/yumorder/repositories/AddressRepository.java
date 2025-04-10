package com.yumorder.repositories;

import com.yumorder.entities.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {
    boolean existsByCountryAndStateAndCityAndStreetAndZipCodeAndLatitudeAndLongitude(
            String country, String state, String city, String street, String zipCode, double latitude, double longitude);
}
