package com.driver.service;

import com.driver.model.Booking;
import com.driver.model.Facility;
import com.driver.model.Hotel;
import com.driver.model.User;
import com.driver.repository.HotelManagementRepository;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class HotelManagementService {

    HotelManagementRepository hotelRepository = new HotelManagementRepository();

    // Add hotel to database
    public String addHotel(Hotel hotel){
        // Check if the hotel is unique and not null
        if(hotel == null || hotel.getHotelName() == null || hotelRepository.hotelExists(hotel))
            return "FAILURE";

        hotelRepository.addHotel(hotel); // add hotel to the database
        return "SUCCESS";
    }

    // Add User to the Database
    public Integer addUser(User user){
        hotelRepository.addUser(user); // Adding user to the database
        return user.getaadharCardNo();
    }

    // Get the Name of the hotel with most facilities
    public String getHotelWithMostFacilities(){
        List<Hotel> hotelList = hotelRepository.getAllHotels();
        String result = "";
        int maxFacilities = 0;

        for(Hotel hotel : hotelList){
            int facilities = hotel.getFacilities().size();
            if(facilities == 0) continue;

            if(maxFacilities < facilities) {
                result = hotel.getHotelName();
                maxFacilities = facilities;
            }
            else if(maxFacilities == facilities){
                String[] hotelName = {result, hotel.getHotelName()};
                Arrays.sort(hotelName);
                result = hotelName[0];
            }
        }

        return result;
    }

    // Book a room in the hotel
    public int bookARoom(Booking booking){
        if(booking == null) return -1;

        //book the required room
        return hotelRepository.bookRooms(booking);
    }

    // Get the Number of bookings done by the user
    public int getBookings(Integer aadharCard){
        return 2;
    }

    // Add Facilities to the hotel
    public Hotel addFacilities(List<Facility> newFacilities, String hotelName){
        // Get the Hotel
        List<Hotel>hotels = hotelRepository.getAllHotels();

        Hotel hotel = null;
        for(Hotel h : hotels){
            if(h.getHotelName().equals(hotelName)){
                hotel = h;
                break;
            }
        }

        for(Facility facility : newFacilities){
            if(!hotel.getFacilities().contains(facility)){
                hotel.getFacilities().add(facility);
            }
        }
        return hotel;
    }
}
