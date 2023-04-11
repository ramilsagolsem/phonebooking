package com.app.phonebooking.service;

import com.app.phonebooking.dto.MobileDto;
import com.app.phonebooking.error.PhoneBookingException;
import com.app.phonebooking.repository.MobileRepository;
import com.app.phonebooking.utils.AppUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class MobileService {

    @Autowired
    private MobileRepository mobileRepository;

    @Autowired
    private UserService userService;

    public Flux<MobileDto> getMobiles(){
        return mobileRepository.findAll().map(AppUtils::mobileToMobileDto);
    }

    public Mono<MobileDto> getMobile(Integer id){
        return mobileRepository.findById(id).map(AppUtils::mobileToMobileDto)
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND,"Mobile not found")));
    }

    public Flux<MobileDto> getMobiles(String name){
        return mobileRepository.findByNameContaining(name).map(AppUtils::mobileToMobileDto);
    }

    public Mono<MobileDto> save(Mono<MobileDto> mobileDtoMono){
        return mobileDtoMono.map(AppUtils::mobileDtoToMobile)
                .flatMap(mobileRepository::save)
                .map(AppUtils::mobileToMobileDto);
    }

    public Mono<Void> delete(Integer id){
        return mobileRepository.deleteById(id);
    }

    public Mono<MobileDto> bookMobile(Integer id, Integer userid){
        return userService.getUser(userid)
                .flatMap(userDto -> getMobile(id)
                        .flatMap(mobileDto -> {
                            if(mobileDto.getAvailable()!=null && mobileDto.getAvailable().equals("No")){
                                return Mono.error(new PhoneBookingException("The phone is not available"));
                            } else {
                                mobileDto.setWhoBooked(userid);
                                mobileDto.setAvailable("No");
                                Date date = new Date();
                                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                                String dateString = dateFormat.format(date);
                                mobileDto.setBookedOn(dateString);
                                return mobileRepository.save(AppUtils.mobileDtoToMobile(mobileDto))
                                        .map(AppUtils::mobileToMobileDto);
                            }
                        }));
    }

    public Mono<MobileDto> returnMobile(Integer id){
        return getMobile(id)
                .flatMap(mobileDto -> {
                    mobileDto.setWhoBooked(null);
                    mobileDto.setAvailable("Yes");
                    mobileDto.setBookedOn(null);
                    return mobileRepository.save(AppUtils.mobileDtoToMobile(mobileDto))
                            .map(AppUtils::mobileToMobileDto);
                });
    }
}
