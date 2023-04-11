package com.app.phonebooking.controller;

import com.app.phonebooking.PhoneBookingApplication;
import com.app.phonebooking.dto.UserDto;
import com.app.phonebooking.repository.H2Configuration;
import com.app.phonebooking.service.UserService;
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
@WebFluxTest(UserController.class)
@ComponentScan(basePackageClasses = PhoneBookingApplication.class,
        excludeFilters = {
                @ComponentScan.Filter(
                        type = FilterType.ASSIGNABLE_TYPE,
                        value = H2Configuration.class)})
public class UsersControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private UserService userService;

    @Test
    public void createUserTest(){
        UserDto userDto = new UserDto(1,"John Denver");
        Mono<UserDto> userDtoMono = Mono.just(userDto);
        when(userService.create(userDtoMono)).thenReturn(userDtoMono);

        webTestClient.post().uri("/users")
                .body(Mono.just(userDtoMono),UserDto.class)
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    public void getAllUsersTest(){
        Flux<UserDto> userDtoFlux = Flux.just(new UserDto(1, "John Denver"),
                new UserDto(2, "John Mayer"),
                new UserDto(3, "Debby Jones"));
        when(userService.getUsers()).thenReturn(userDtoFlux);

        Flux<UserDto> responseBody = webTestClient.get().uri("/users")
                .exchange()
                .expectStatus().isOk()
                .returnResult(UserDto.class)
                .getResponseBody();

        StepVerifier.create(responseBody)
                .expectSubscription()
                .expectNext(new UserDto(1, "John Denver"))
                .expectNext(new UserDto(2, "John Mayer"))
                .expectNext(new UserDto(3, "Debby Jones"))
                .verifyComplete();
    }

    @Test
    public void getUserTest(){
        Mono<UserDto> userDtoMono = Mono.just(new UserDto(1, "John Denver"));
        when(userService.getUser(1)).thenReturn(userDtoMono);

        Flux<UserDto> responseBody = webTestClient.get().uri("/users/1")
                .exchange()
                .expectStatus().isOk()
                .returnResult(UserDto.class)
                .getResponseBody();

        StepVerifier.create(responseBody)
                .expectSubscription()
                .expectNextMatches(p->p.getName().equals("John Denver"))
                .verifyComplete();
    }
}
