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
    HashMap<String, Hotel> hotelHashMap = new HashMap<>();
    HashMap<Integer, User> userHashMap = new HashMap<>();
    HashMap<String, Booking> bookingHashMap = new HashMap<>();
    HashMap<Integer, List<Booking>> userBookingHashMap = new HashMap<>();

    // Constructor
    public HotelManagementRepository(){
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
    public int bookRooms(Booking booking){
        Hotel hotel = hotelHashMap.get(booking.getHotelName());
        if(hotel == null || booking.getNoOfRooms() > hotel.getAvailableRooms()){
            return -1;
        }
        hotel.setAvailableRooms(hotel.getAvailableRooms()- booking.getNoOfRooms());
        addBooking(booking);

        int cost = hotel.getPricePerNight() * booking.getNoOfRooms();
        booking.setAmountToBePaid(cost);

        return cost;
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
        if(!userBookingHashMap.containsKey(aadharCard) || userBookingHashMap.get(aadharCard) == null) {
            return 0;
        }
        else {
            return userBookingHashMap.get(aadharCard).size();
        }
    }
}
