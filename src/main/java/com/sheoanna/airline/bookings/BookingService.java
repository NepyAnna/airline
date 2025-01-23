package com.sheoanna.airline.bookings;

import org.springframework.stereotype.Service;

import com.sheoanna.airline.profile.ProfileDto;
import com.sheoanna.airline.users.User;
import com.sheoanna.airline.users.UserDto;

import java.util.List;

@Service
public class BookingService {
    private BookingRepository repository;

    public BookingService(BookingRepository repository) {
        this.repository = repository;
    }

     public List<BookingDto> getAll() {
        List<Booking> bookings = repository.findAll();
        return bookings.stream().map(booking -> new BookingDto(booking.getIdBooking(),
        toUserDto(booking.getUser()),
        booking.getFlight(),
        booking.getDateBooking(),
        booking.getBookedSeats())).toList();
    }

    public BookingDto getById(Long id){
        Booking booking = repository.findById(id).orElseThrow();

        BookingDto bookingDto = new BookingDto(
            booking.getIdBooking(),
            toUserDto(booking.getUser()),
            booking.getFlight(),
            booking.getDateBooking(),
            booking.getBookedSeats());

            return bookingDto;
    }

    

    private UserDto toUserDto(User user) {
        ProfileDto profileDto = user.getProfile() != null
                ? new ProfileDto(user.getProfile().getAddress())
                : null;
        return new UserDto(user.getIdUser(), user.getUsername(), profileDto);
    }
}
