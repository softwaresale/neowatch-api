package com.appdev.neowatch.controllers;

import com.appdev.neowatch.models.Neo;
import com.appdev.neowatch.models.NeoFeed;
import com.appdev.neowatch.services.NeoFeedService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.apache.tomcat.jni.Local;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.net.URISyntaxException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/neos")
public class NeoController {

    private final NeoFeedService neoFeedService;

    @Autowired
    public NeoController(NeoFeedService neoFeedService) {
        this.neoFeedService = neoFeedService;
    }

    @GetMapping
    public Collection<Neo> getNeos(
            @RequestParam(name = "start", required = false) Optional<String> startDateStr,
            @RequestParam(name = "end", required = false) Optional<String> endDateStr
    ) throws URISyntaxException, JsonProcessingException {
        LocalDate startDate = startDateStr.map(str -> LocalDate.parse(str)).orElse(LocalDate.now());
        LocalDate endDate = null;
        if (endDateStr.isPresent()) {
            endDate = LocalDate.parse(endDateStr.get());
        }

        NeoFeed feed = null;
        if (Objects.nonNull(endDate)) {
            feed = this.neoFeedService.getFeed(startDate, endDate);
        } else {
            feed = this.neoFeedService.getFeed(startDate);
        }

        // Combine all dates into a single collection of neos
        Collection<Neo> neos = feed.getNeos().values().stream()
                .flatMap(setOfNeos -> setOfNeos.stream())
                .collect(Collectors.toList());

        return neos;
    }

    @GetMapping("/feed")
    public NeoFeed getNeoFeedForDate(
            @RequestParam(name = "start") String startDateStr,
            @RequestParam(name = "end", required = false) Optional<String> endDateStr
    ) throws URISyntaxException, JsonProcessingException {
        LocalDate startDate = LocalDate.parse(startDateStr);
        LocalDate endDate = null;
        if (endDateStr.isPresent()) {
            endDate = LocalDate.parse(endDateStr.get());
        }

        if (Objects.nonNull(endDate)) {
            return this.neoFeedService.getFeed(startDate, endDate);
        } else {
            return this.neoFeedService.getFeed(startDate);
        }
    }
}
