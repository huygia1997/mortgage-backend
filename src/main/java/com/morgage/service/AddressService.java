package com.morgage.service;


import com.morgage.model.Address;
import com.morgage.model.data.ShopData;
import com.morgage.repository.AddressRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AddressService {
    private final AddressRepository addressRepository;

    public AddressService(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }


    public List<Address> searchNearby(String latString, String lngString) {
        Float lat = Float.parseFloat(latString);
        Float lng = Float.parseFloat(lngString);

        List<Address> listAddress = addressRepository.searchNearby(lat, lng);

        return listAddress;

    }

    public List<Address> searchByDistrictId(String districtString) {
        int districtId = Integer.parseInt(districtString);


        List<Address> listAddress = addressRepository.findAddressesByDistrictId(districtId);

        return listAddress;

    }

    public Address addAddress(String longtitude, String latitude, String fullAddress, int districtId) {
        if (longtitude == null || latitude == null) {
            return null;
        } else {
            Address address = new Address();
            address.setLatitude(latitude);
            address.setLongtitude(longtitude);
            address.setFullAddress(fullAddress);
            address.setDistrictId(districtId);
            return addressRepository.saveAndFlush(address);
        }

    }

    public Address findAddressById(int addressId) {
        return addressRepository.findAddressById(addressId);
    }

    public Address updateAddress(int addressId, String longtitude, String latitude, String fullAddress, int districtId) {
        if (longtitude == null || latitude == null) {
            return null;
        } else {
            Address address = addressRepository.findAddressById(addressId);
            if (address != null) {
                address.setLatitude(latitude);
                address.setLongtitude(longtitude);
                address.setFullAddress(fullAddress);
                address.setDistrictId(districtId);
            }
            return addressRepository.saveAndFlush(address);

        }

    }
}
