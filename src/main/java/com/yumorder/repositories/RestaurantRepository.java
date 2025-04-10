package com.yumorder.repositories;

import com.yumorder.entities.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {
    boolean existsByEmail(String email);
    boolean existsByPhone(String phone);
    boolean existsByName(String name);
    boolean existsByWebsite(String website);


    boolean existsByEmailAndIdNot(String email, Long id);
    boolean existsByPhoneAndIdNot(String phone, Long id);
    boolean existsByNameAndIdNot(String name, Long id);
    boolean existsByWebsiteAndIdNot(String website, Long id);

}
