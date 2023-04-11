package com.app.phonebooking.controller;

import com.app.phonebooking.dto.MobileDto;
import com.app.phonebooking.service.MobileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/mobiles")
public class MobileController {

    @Autowired
    private MobileService mobileService;

    @GetMapping(produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<MobileDto> getMobiles(){
        return mobileService.getMobiles();
    }

    @GetMapping("/{id}")
    public Mono<MobileDto> getMobile(@PathVariable Integer id){
        return mobileService.getMobile(id);
    }

    @GetMapping("/name/{name}")
    public Flux<MobileDto> getMobile(@PathVariable String name){
        return mobileService.getMobiles(name);
    }

    @PostMapping("/book/{id}/{userid}")
    public Mono<MobileDto> bookMobile(@PathVariable Integer id, @PathVariable Integer userid){
        return mobileService.bookMobile(id,userid);
    }

    @PostMapping("/return/{id}")
    public Mono<MobileDto> returnMobile(@PathVariable Integer id){
        return mobileService.returnMobile(id);
    }


}
