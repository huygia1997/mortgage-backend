package com.morgage.service;



import com.morgage.model.Address;
import com.morgage.model.ShopData;
import com.morgage.repository.AddressRepository;
import org.springframework.stereotype.Service;

@Service
public class AddressService {
    private final AddressRepository addressRepository;

    public AddressService(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

    public ShopData addAddressToShopData(int id, ShopData shopData) {
        Address address = addressRepository.findAddressById(id);
        shopData.setFullAddress(address.getFullAddress());
        shopData.setLatitude(address.getLatitude());
        shopData.setLongtitude(address.getLongtitude());

        return shopData;

    }
}
