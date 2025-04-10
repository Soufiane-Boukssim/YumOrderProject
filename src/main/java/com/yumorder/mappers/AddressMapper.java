package com.yumorder.mappers;

import com.yumorder.dtos.address.AddressInputDto;
import com.yumorder.dtos.address.AddressOutputDto;
import com.yumorder.entities.Address;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor @Service
public class AddressMapper {

    private final ModelMapper modelMapper=new ModelMapper();

    public Address toEntity(AddressInputDto addressInputDto) {
        return modelMapper.map(addressInputDto, Address.class);
    }

    public AddressOutputDto toDto(Address address) {
        return modelMapper.map(address, AddressOutputDto.class);
    }

}
