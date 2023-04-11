package com.app.phonebooking.repository;

import com.app.phonebooking.entity.Mobile;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface MobileRepository extends R2dbcRepository<Mobile, Integer> {

    @Query("select * from mobile where name = $1")
    Flux<Mobile> findByNameContaining(String name);
}
