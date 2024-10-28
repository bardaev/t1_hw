package org.t1.hw.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.t1.hw.sevice.PersonService;

@RestController
@RequiredArgsConstructor
@Slf4j
public class MainController {

    private final PersonService personService;

    @GetMapping(value = "/generate")
    public void generateData(@RequestParam int count) {
        if (count == 0) count = 1000;
        personService.savePersonsOutbox(count);
    }
}
