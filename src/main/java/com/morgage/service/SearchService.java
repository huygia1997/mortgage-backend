package com.morgage.service;

import com.morgage.model.*;
import com.morgage.model.data.ShopData;
import com.morgage.repository.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SearchService {
    private final AddressRepository addressRepository;
    private final ShopRepository shopRepository;
    private final CityRepository cityRepository;
    private final DistrictRepository districtRepository;
    private final CategoryRepository categoryRepository;

    public SearchService(AddressRepository addressRepository, ShopRepository shopRepository, CityRepository cityRepository, DistrictRepository districtRepository, CategoryRepository categoryRepository) {
        this.addressRepository = addressRepository;
        this.shopRepository = shopRepository;
        this.cityRepository = cityRepository;
        this.districtRepository = districtRepository;
        this.categoryRepository = categoryRepository;
    }

    //Add Shop to Shop Data.
    public ShopData addShopToShopData(Shop shop, ShopData shopData) {
        shopData.setId(shop.getId());
        shopData.setShopName(shop.getShopName());
        shopData.setEmail(shop.getEmail());
        shopData.setFacebook(shop.getFacebook());
        shopData.setPhoneNumber(shop.getPhoneNumber());
        shopData.setPolicy(shop.getPolicy());
        shopData.setRating(shop.getRating());
        shopData.setStatus(shop.getStatus());
        shopData.setAvaURL(shop.getAvatarUrl());
        return shopData;
    }

    public ShopData addShopToShopData(int addressId, ShopData shopData) {
        Shop shop = shopRepository.findShopByAddressId(addressId);
        shopData.setId(shop.getId());
        shopData.setShopName(shop.getShopName());
        shopData.setEmail(shop.getEmail());
        shopData.setFacebook(shop.getFacebook());
        shopData.setPhoneNumber(shop.getPhoneNumber());
        shopData.setPolicy(shop.getPolicy());
        shopData.setRating(shop.getRating());
        shopData.setStatus(shop.getStatus());
        shopData.setAvaURL(shop.getAvatarUrl());
        return shopData;
    }

    // add address to shop data
    public ShopData addAddressToShopData(int id, ShopData shopData) {
        Address address = addressRepository.findAddressById(id);
        shopData.setFullAddress(address.getFullAddress());
        shopData.setLatitude(address.getLatitude());
        shopData.setLongtitude(address.getLongtitude());
        return shopData;

    }

    public ShopData addAddressToShopData(Address address, ShopData shopData) {
        shopData.setFullAddress(address.getFullAddress());
        shopData.setLatitude(address.getLatitude());
        shopData.setLongtitude(address.getLongtitude());
        return shopData;

    }

    public Map<String, List<District>> searchCityDistrict() {
        Map<String, List<District>> listMap = new HashMap<>();

//        generateDataIntoCityAndDistrict();
        List<City> listCity = cityRepository.findAll();
        for (int i = 0; i < listCity.size(); i++) {
            if (listMap.containsKey(listCity.get(i).getCityName())) {
            } else {
                List<District> listDistrict = districtRepository.findAllByCityId(listCity.get(i).getId());
                listMap.put(listCity.get(i).getCityName(), listDistrict);
            }
        }
        return listMap;
    }

    public List<City> findAllCities() {
        return cityRepository.findAll();
    }
    public List<District> findAllDistrict() {
        return districtRepository.findAll();
    }

    public List<Category> findAllCategory() {
        return categoryRepository.findAll();
    }

    public void generateDataIntoCityAndDistrict() {
        String data = "Bình Dương,Thủ Dầu Một\n" +
                "Bình Dương,Bàu Bàng\n" +
                "Bình Dương,Dầu Tiếng\n" +
                "Bình Dương,Thị xã Bến Cát\n" +
                "Bình Dương,Phú Giáo\n" +
                "Bình Dương,Thị xã Tân Uyên\n" +
                "Bình Dương,Thị xã Dĩ An\n" +
                "Bình Dương,Thị xã Thuận An\n" +
                "Bình Dương,Bắc Tân Uyên\n" +
                "Đồng Nai,Biên Hòa\n" +
                "Đồng Nai,Thị xã Long Khánh\n" +
                "Đồng Nai,Tân Phú\n" +
                "Đồng Nai,Vĩnh Cửu\n" +
                "Đồng Nai,Định Quán\n" +
                "Đồng Nai,Trảng Bom\n" +
                "Đồng Nai,Thống Nhất\n" +
                "Đồng Nai,Cẩm Mỹ\n" +
                "Đồng Nai,Long Thành\n" +
                "Đồng Nai,Xuân Lộc\n" +
                "Đồng Nai,Nhơn Trạch\n" +
                "Bà Rịa - Vũng Tàu,Vũng Tàu\n" +
                "Bà Rịa - Vũng Tàu,Bà Rịa\n" +
                "Bà Rịa - Vũng Tàu,Châu Đức\n" +
                "Bà Rịa - Vũng Tàu,Xuyên Mộc\n" +
                "Bà Rịa - Vũng Tàu,Long Điền\n" +
                "Bà Rịa - Vũng Tàu,Đất Đỏ\n" +
                "Bà Rịa - Vũng Tàu,Tân Thành\n" +
                "Hồ Chí Minh,1\n" +
                "Hồ Chí Minh,12\n" +
                "Hồ Chí Minh,Thủ Đức\n" +
                "Hồ Chí Minh,9\n" +
                "Hồ Chí Minh,Gò Vấp\n" +
                "Hồ Chí Minh,Bình Thạnh\n" +
                "Hồ Chí Minh,Tân Bình\n" +
                "Hồ Chí Minh,Tân Phú\n" +
                "Hồ Chí Minh,Phú Nhuận\n" +
                "Hồ Chí Minh,2\n" +
                "Hồ Chí Minh,3\n" +
                "Hồ Chí Minh,10\n" +
                "Hồ Chí Minh,11\n" +
                "Hồ Chí Minh,4\n" +
                "Hồ Chí Minh,5\n" +
                "Hồ Chí Minh,6\n" +
                "Hồ Chí Minh,8\n" +
                "Hồ Chí Minh,Bình Tân\n" +
                "Hồ Chí Minh,7\n" +
                "Hồ Chí Minh,Củ Chi\n" +
                "Hồ Chí Minh,Hóc Môn\n" +
                "Hồ Chí Minh,Bình Chánh\n" +
                "Hồ Chí Minh,Nhà Bè\n" +
                "Hồ Chí Minh,Cần Giờ";

        Map<String, List<String>> listMap = new HashMap<>();
        String[] dataArray = data.split("\n");
        for (int i=0; i<dataArray.length; i++) {

            String city = dataArray[i].split(",")[0].trim();
            String dist = dataArray[i].split(",")[1].trim();
            if (listMap.containsKey(city)) {
                listMap.get(city).add(dist);
            } else {
                List<String> districtList = new ArrayList<>();
                districtList.add(dist);
                listMap.put(city, districtList);
            }
        }

        for (Map.Entry entry : listMap.entrySet()) {
            List<String> list = (List<String>) entry.getValue();
//            System.out.println(entry.getKey().toString() + ":");
//            for (int i=0; i<list.size(); i++) {
//                System.out.println(list.get(i).toString());
//            }
            City city = new City();
            city.setCityName(entry.getKey().toString());
            cityRepository.saveAndFlush(city);
            City cityItem = cityRepository.findCityByCityName(city.getCityName());

            for (int i=0; i<list.size(); i++) {
                District district = new District();
                district.setCityId(cityItem.getId());
                district.setDistrictName(list.get(i));
                districtRepository.saveAndFlush(district);
            }


        }
    }




}
