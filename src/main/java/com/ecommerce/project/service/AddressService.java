package com.ecommerce.project.service;

import com.ecommerce.project.Model.User;
import com.ecommerce.project.payload.AddressDTO;

import java.util.List;

public interface AddressService {

    AddressDTO addAddresses(AddressDTO addAddress,User user);

    List<AddressDTO> getAllAddresses();

    AddressDTO getAddressesById(Long addressId);

    List<AddressDTO> getAllUserAddresses(User user);

    AddressDTO updateAddressesById(Long addressId, AddressDTO addressDTO);

    String deleteAddressesById(Long addressId);
}
