package com.sunglowsys.web;

import com.sunglowsys.domain.Hotel;
import com.sunglowsys.service.HotelService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

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
        return new ModelAndView("index", "hotels", page.getContent());
    }

    @GetMapping("/hotels/create")
    public ModelAndView createHotelForm() {
        log.debug("Web request to lod create Hotel form");
        return new ModelAndView("add-hotel","hotel", new Hotel());
    }

    @PostMapping("/hotels/create")
    public ModelAndView createHotel(@ModelAttribute @Valid Hotel hotel, BindingResult result) {
        log.debug("Web request to create Hotel : {}", hotel);
        if (result.hasErrors()) {
            return new ModelAndView("add-hotel");
        }
        hotelService.save(hotel);
        return new ModelAndView("redirect:/");
    }

    @GetMapping("/hotels/update/{id}")
    public ModelAndView updateHotel(@ModelAttribute Hotel hotel, @PathVariable Long id) {
        log.debug("Web request to update Hotel : {}", id);
        hotel = hotelService.findById(id).get();
        return new ModelAndView("add-hotel", "hotel", hotel);
    }

    @GetMapping("/_search/hotels")
    public ModelAndView searchHotel(@RequestParam String searchText) {
        log.debug("Web request to search Hotel : {}", searchText);
        return new ModelAndView("index", "hotels", hotelService.search(searchText));
    }

    @GetMapping("/hotels/delete/{id}")
    public ModelAndView deleteHotel(@PathVariable Long id) {
        log.debug("Web request to delete Hotel : {}", id);
        hotelService.delete(id);
        return new ModelAndView("redirect:/");
    }
}
