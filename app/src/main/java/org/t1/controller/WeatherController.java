package org.t1.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WeatherController {

    @GetMapping(path = "/weather")
    public String getWeather() {
        return "Moscow +15";
    }
}
