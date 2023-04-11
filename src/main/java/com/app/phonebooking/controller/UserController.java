package com.app.phonebooking.controller;

import com.app.phonebooking.dto.UserDto;
import com.app.phonebooking.error.PhoneBookingException;
import com.app.phonebooking.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping(produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<UserDto> getUsers(){
        return userService.getUsers();
    }

    @GetMapping("/{id}")
    public Mono<UserDto> getUser(@PathVariable Integer id){
        return userService.getUser(id)
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, "User no=t found")));
    }

    @PostMapping
    public Mono<UserDto> saveUser(@RequestBody Mono<UserDto> userDtoMono){
        return userService.create(userDtoMono);
    }

    @DeleteMapping("/{id}")
    public Mono<Void> deleteUser(@PathVariable Integer id){
        return userService.delete(id);
    }
}
