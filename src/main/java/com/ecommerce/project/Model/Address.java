package com.ecommerce.project.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "addresses")
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long addressId;
    @NotBlank
    @Size(min = 5 , message = "Street must be AtLeast of 5 Characters")
    private String street;

    @NotBlank
    @Size(min = 5 , message = "BuildingName must be AtLeast of 5 Characters")
    private String BuildingName;

    @NotBlank
    @Size(min = 2 , message = "City Name must be AtLeast of 2 Characters")
    private String city;

    @NotBlank
    @Size(min = 2 , message = "State must be AtLeast of 2 Characters")
    private String state;



    @NotBlank
    @Size(min = 6 , message = "PinCode must be AtLeast of 6 Characters")
    private String pinCode;

    @NotBlank
    @Size(min = 2 , message = "Country must be AtLeast of 2 Characters")
    private String country;

    @ToString.Exclude
    @ManyToMany(mappedBy = "addresses")
    private List<User> users =  new ArrayList<>();

    public Address(String buildingName, String city, String country, String pinCode, String state, String street) {
        BuildingName = buildingName;
        this.city = city;
        this.country = country;
        this.pinCode = pinCode;
        this.state = state;
        this.street = street;
    }

}
