package com.driver.repository;

import com.driver.model.Booking;
import com.driver.model.Hotel;
import com.driver.model.User;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class HotelManagementRepository {
    HashMap<String, Hotel> hotelHashMap ;
    HashMap<Integer, User> userHashMap ;
    HashMap<String, Booking> bookingHashMap ;

    // Constructor
    public HotelManagementRepository(){
        hotelHashMap = new HashMap<>();
        userHashMap = new HashMap<>();
        bookingHashMap = new HashMap<>();
    }

    // Add Hotel to the Database
    public void addHotel(Hotel hotel){
        hotelHashMap.put(hotel.getHotelName(), hotel);
    }

    // Check if the hotel name exists in database
    public boolean hotelExists(Hotel hotel){
        return hotelHashMap.keySet().stream().
                anyMatch(key -> hotel.getHotelName().equals(key));
    }

    // Add user to the Database
    public void addUser(User user){
        userHashMap.put(user.getaadharCardNo(), user);
    }

    // Return the list of Hotels
    public List<Hotel> getAllHotels(){
        return hotelHashMap.keySet().stream()
                .map(key -> hotelHashMap.get(key)).collect(Collectors.toList());
    }

    // Book the rooms
    public int bookRooms(Booking booking){
        Hotel hotel = hotelHashMap.get(booking.getHotelName());
        if(hotel == null || booking.getNoOfRooms() > hotel.getAvailableRooms()){
            return -1;
        }
        hotel.setAvailableRooms(hotel.getAvailableRooms()- booking.getNoOfRooms());
        //addBooking(booking);

        int cost = hotel.getPricePerNight() * booking.getNoOfRooms();
        booking.setAmountToBePaid(cost);

        bookingHashMap.put(booking.getBookingId(), booking);
        return cost;
    }

    // Add Bookings to the Database


    // Get the Number of bookings done by the user
    public int getBookings(Integer aadharCard){
        int count = 0;
        for(String key : bookingHashMap.keySet()){
            if(bookingHashMap.get(key).getBookingAadharCard() == aadharCard){
                count++;
            }
        }
        return count;
    }
}
