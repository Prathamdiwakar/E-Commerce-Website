package com.ecommerce.project.Controller;

import com.ecommerce.project.Model.User;
import com.ecommerce.project.Util.AuthUtils;
import com.ecommerce.project.payload.AddressDTO;
import com.ecommerce.project.service.AddressService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class AddressController {

    @Autowired
    private AddressService addressService;

    @Autowired
    private AuthUtils authUtils;

    @PostMapping("/addresses")
    public ResponseEntity<AddressDTO> CreateAddress(@Valid @RequestBody AddressDTO addAddress) {
        User user = authUtils.loggedInUser();
        AddressDTO saveAddress = addressService.addAddresses(addAddress,user);
        return new ResponseEntity<>(saveAddress, HttpStatus.CREATED);
    }

    @GetMapping("/addresses")
    public ResponseEntity<List<AddressDTO>> GetAddress() {
        List<AddressDTO> saveAddress = addressService.getAllAddresses();
        return new ResponseEntity<List<AddressDTO>>(saveAddress, HttpStatus.OK);
    }

    @GetMapping("/addresses/{addressId}")
    public ResponseEntity<AddressDTO> CreateAddressById(@PathVariable Long addressId) {
        AddressDTO saveAddress = addressService.getAddressesById(addressId);
        return new ResponseEntity<AddressDTO>(saveAddress, HttpStatus.OK);
    }

    @GetMapping("/user/addresses")
    public ResponseEntity<List<AddressDTO>> GetUserAddress() {
        User user = authUtils.loggedInUser();
        List<AddressDTO> saveAddress = addressService.getAllUserAddresses(user);
        return new ResponseEntity<List<AddressDTO>>(saveAddress, HttpStatus.OK);
    }

    @PutMapping("/addresses/{addressId}")
    public ResponseEntity<AddressDTO> updateAddressById(@PathVariable Long addressId,@RequestBody AddressDTO addressDTO) {
        AddressDTO saveAddress = addressService.updateAddressesById(addressId,addressDTO);
        return new ResponseEntity<AddressDTO>(saveAddress, HttpStatus.OK);
    }

    @DeleteMapping("/addresses/{addressId}")
    public ResponseEntity<String> deleteAddressById(@PathVariable Long addressId) {
        String status = addressService.deleteAddressesById(addressId);
        return new ResponseEntity<String>(status, HttpStatus.OK);
    }


}
