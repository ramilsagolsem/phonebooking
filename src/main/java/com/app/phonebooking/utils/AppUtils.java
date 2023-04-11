package com.app.phonebooking.utils;

import com.app.phonebooking.dto.MobileDto;
import com.app.phonebooking.dto.UserDto;
import com.app.phonebooking.entity.Mobile;
import com.app.phonebooking.entity.Users;

public class AppUtils {

    public static UserDto userToUserDto(Users user){
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setName(user.getName());
        return userDto;
    }

    public static Users userDtoToUser(UserDto userDto){
        Users user = new Users();
        user.setName(userDto.getName());
        user.setId(userDto.getId());
        return user;
    }

    public static MobileDto mobileToMobileDto(Mobile mobile){
        MobileDto mobileDto = new MobileDto();
        mobileDto.setId(mobile.getId());
        mobileDto.setName(mobile.getName());
        mobileDto.setTechnology(mobile.getTechnology());
        mobileDto.setAvailable(mobile.getAvailability());
        mobileDto.setBookedOn(mobile.getBookedon());
        mobileDto.setWhoBooked(mobile.getBookedby());
        return mobileDto;
    }

    public static Mobile mobileDtoToMobile(MobileDto mobileDto){
        Mobile mobile = new Mobile();
        mobile.setId(mobileDto.getId());
        mobile.setName(mobileDto.getName());
        mobile.setTechnology(mobileDto.getTechnology());
        mobile.setAvailability(mobileDto.getAvailable());
        mobile.setBookedon(mobileDto.getBookedOn());
        mobile.setBookedby(mobileDto.getWhoBooked());
        return mobile;
    }
}
