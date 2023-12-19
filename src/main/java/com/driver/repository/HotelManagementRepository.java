package com.driver.repository;

import com.driver.model.Booking;
import com.driver.model.Hotel;
import com.driver.model.User;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
public class HotelManagementRepository {
    HashMap<String, Hotel> hotelHashMap;
    HashMap<Integer, User> userHashMap;
    HashMap<String, Booking> bookingHashMap;
    HashMap<Integer, List<Booking>> userBookingHashMap;

    // Constructor
    public HotelManagementRepository(){
        hotelHashMap = new HashMap<>();
        userHashMap = new HashMap<>();
        bookingHashMap = new HashMap<>();
        userBookingHashMap = new HashMap<>();
    }

    // Add Hotel to the Database
    public void addHotel(Hotel hotel){
        hotelHashMap.put(hotel.getHotelName(), hotel);
    }

    // Check if the hotel name exists in database
    public boolean hotelExists(Hotel hotel){
        return hotelHashMap.keySet().stream().
                filter(key -> hotel.getHotelName().equals(key)).findFirst().isPresent();
    }

    // Add user to the Database
    public void addUser(User user){
        userHashMap.put(user.getaadharCardNo(), user);
        userBookingHashMap.put(user.getaadharCardNo(), new ArrayList<>());
    }

    // Return the list of Hotels
    public List<Hotel> getAllHotels(){
        return hotelHashMap.keySet().stream()
                .map(key -> hotelHashMap.get(key)).collect(Collectors.toList());
    }

    // Book the rooms
    public boolean bookRooms(Booking booking){
        Hotel hotel = hotelHashMap.get(booking.getHotelName());
        if(booking.getNoOfRooms() > hotel.getAvailableRooms()){
            return false;
        }
        hotel.setAvailableRooms(hotel.getAvailableRooms()- booking.getNoOfRooms());
        return true;
    }

    // Add Bookings to the Database
    public void addBooking(Booking booking){
        bookingHashMap.put(booking.getBookingId(), booking);
        userBookingHashMap.get(booking.getBookingAadharCard()).add(booking);
    }

    // Get hotel of the given hotelName
    public Hotel getHotel(String hotelName){
        return hotelHashMap.get(hotelName);
    }

    // Get the Number of bookings done by the user
    public int getBookings(Integer aadharCard){
        return userBookingHashMap.get(aadharCard).size();
    }
}
