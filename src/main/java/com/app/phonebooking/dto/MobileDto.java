package com.app.phonebooking.dto;

import com.app.phonebooking.entity.Users;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MobileDto {

    private Integer id;
    private String name;
    private String technology;
    private String available;
    private String bookedOn;
    private Integer whoBooked;
}
