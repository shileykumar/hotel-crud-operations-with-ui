package com.sunglowsys.web;

import com.sunglowsys.domain.Hotel;
import com.sunglowsys.service.HotelService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/")
public class HotelController {

    private final Logger log = LoggerFactory.getLogger(HotelController.class);

    private final HotelService hotelService;

    public HotelController(HotelService hotelService) {
        this.hotelService = hotelService;
    }

    @GetMapping
    public ModelAndView home() {
        log.debug("Web request to get Hotels");
        Page<Hotel> page = hotelService.findAll(PageRequest.of(0,20));
        List<Hotel> hotels = page.getContent();
        return new ModelAndView("index", "hotels", hotels);
    }

    @GetMapping("/hotels/create")
    public ModelAndView createHotelForm(@ModelAttribute Hotel hotel) {
        log.debug("Web request to lod create Hotel form");
        return new ModelAndView("add-hotel","hotel", hotel);
    }

    @PostMapping("/hotels")
    public ModelAndView createHotel(@ModelAttribute Hotel hotel) {
        log.debug("Web request to create Hotel : {}", hotel);
        hotelService.save(hotel);
        return new ModelAndView("redirect:/","hotel",hotel);
    }

    @GetMapping("/hotels/update/{id}")
    public ModelAndView updateHotel(@ModelAttribute Hotel hotel, @PathVariable Long id) {
        log.debug("Web request to update Hotel : {}", id);
        hotel = hotelService.findById(id).get();
        return new ModelAndView("add-hotel", "hotel", hotel);
    }

    @GetMapping("/hotels/delete/{id}")
    public ModelAndView deleteHotel(@PathVariable Long id) {
        log.debug("Web request to delete Hotel : {}", id);
        hotelService.delete(id);
        return new ModelAndView("redirect:/");
    }
}
