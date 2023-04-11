package com.app.phonebooking.controller;

import com.app.phonebooking.PhoneBookingApplication;
import com.app.phonebooking.dto.MobileDto;
import com.app.phonebooking.repository.H2Configuration;
import com.app.phonebooking.service.MobileService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@WebFluxTest(MobileController.class)
@ComponentScan(basePackageClasses = PhoneBookingApplication.class,
        excludeFilters = {
                @ComponentScan.Filter(
                        type = FilterType.ASSIGNABLE_TYPE,
                        value = H2Configuration.class)})
public class MobileControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private MobileService mobileService;


    @Test
    public void getMobileTest(){
        Mono<MobileDto> mobileDtoMono = Mono.just(new MobileDto(1,"Samsung Galaxy S9","4G","Yes",null, null));
        when(mobileService.getMobile(1)).thenReturn(mobileDtoMono);

        Flux<MobileDto> responseBody = webTestClient.get().uri("/mobiles/1")
                .exchange()
                .expectStatus().isOk()
                .returnResult(MobileDto.class)
                .getResponseBody();

        StepVerifier.create(responseBody)
                .expectSubscription()
                .expectNextMatches(p->p.getName().equals("Samsung Galaxy S9"))
                .verifyComplete();
    }

    @Test
    public void bookMobileTest(){
        Mono<MobileDto> mobileDtoMono = Mono.just(new MobileDto(1,"Samsung Galaxy S9","4G","No",null, null));
        when(mobileService.getMobile(1)).thenReturn(mobileDtoMono);

        Flux<MobileDto> responseBody = webTestClient.post().uri("/mobiles/book/1/1")
                .exchange()
                .expectStatus().isOk()
                .returnResult(MobileDto.class)
                .getResponseBody();

        StepVerifier.create(responseBody)
                .expectSubscription()
                .expectNextMatches(p->p.getAvailable().equals("No"))
                .verifyComplete();
    }

    @Test
    public void returnMobileTest(){
        Mono<MobileDto> mobileDtoMono = Mono.just(new MobileDto(1,"Samsung Galaxy S9","4G","Yes",null, null));
        when(mobileService.getMobile(1)).thenReturn(mobileDtoMono);

        Flux<MobileDto> responseBody = webTestClient.post().uri("/mobiles/return/1")
                .exchange()
                .expectStatus().isOk()
                .returnResult(MobileDto.class)
                .getResponseBody();

        StepVerifier.create(responseBody)
                .expectSubscription()
                .expectNextMatches(p->p.getAvailable().equals("Yes"))
                .verifyComplete();
    }
}
