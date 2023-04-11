package com.app.phonebooking.service;

import com.app.phonebooking.dto.UserDto;
import com.app.phonebooking.repository.UserRepository;
import com.app.phonebooking.utils.AppUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public Flux<UserDto> getUsers(){
        return userRepository.findAll().map(AppUtils::userToUserDto);
    }

    public Mono<UserDto> getUser(Integer id){
        return userRepository.findById(id)
                .map(AppUtils::userToUserDto)
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND,"User not found")));
    }

    public Mono<UserDto> create(Mono<UserDto> userDtoMono){
        return userDtoMono.map(AppUtils::userDtoToUser)
                .flatMap(userRepository::save)
                .map(AppUtils::userToUserDto);
    }

    public Mono<Void> delete(Integer id){
        return userRepository.deleteById(id);
    }
}
