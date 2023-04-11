package com.app.phonebooking.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Mobile {
    @Id
    private Integer id;
    private String name;
    private String technology;
    private String availability;
    private String bookedon;
    private Integer bookedby;

}
