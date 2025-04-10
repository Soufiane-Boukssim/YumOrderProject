package com.yumorder.services.address;

import com.yumorder.dtos.address.AddressInputDto;

public interface AddressService {
    boolean addressExists(AddressInputDto address);

}
