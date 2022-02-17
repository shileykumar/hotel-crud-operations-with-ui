package com.sunglowsys.repository;

import com.sunglowsys.domain.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Repository
public interface HotelRepository extends JpaRepository<Hotel, Long> {

    @Query(" from Hotel hot where hot.code like :searchText% or hot.name like :searchText% " +
            " or hot.hotelType like :searchText% or hot.email like :searchText% " +
            " or hot.mobile like :searchText% ")
    List<Hotel> search(@RequestParam String searchText);
}
