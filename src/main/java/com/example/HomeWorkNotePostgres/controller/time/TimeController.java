package com.example.HomeWorkNotePostgres.controller.time;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDateTime;

@Controller
public class TimeController {
    @GetMapping("/time")
    public ModelAndView getTime() {
        ModelAndView result = new ModelAndView("time/current");
        result.addObject("now", LocalDateTime.now());
        return result;
    }
}

