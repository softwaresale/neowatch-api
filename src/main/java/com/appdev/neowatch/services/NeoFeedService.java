package com.appdev.neowatch.services;

import com.appdev.neowatch.models.NeoFeed;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDate;

@Service
public class NeoFeedService {

    @Value("${neowatch.nasaApiKey}")
    private String apiKey;

    public NeoFeedService() {
    }

    private URI createFeedRequestURI(LocalDate start, LocalDate end) throws URISyntaxException {
        String uriString = String.format(
                "https://api.nasa.gov/neo/rest/v1/feed?start_date=%s&end_date=%s&api_key=%s",
                start.toString(),
                end.toString(),
                this.apiKey
        );

        return new URI(uriString);
    }

    public NeoFeed getFeed(LocalDate start, LocalDate end) throws URISyntaxException, JsonProcessingException {
        URI requestUri = this.createFeedRequestURI(start, end);
        RequestEntity<Void> request = RequestEntity.get(requestUri).accept(MediaType.APPLICATION_JSON).build();

        RestTemplate template = new RestTemplate();
        ResponseEntity<String> feedRawJsonResponse = template.exchange(request, String.class);
        String rawJson = feedRawJsonResponse.getBody();
        return NeoFeed.fromJson(rawJson);
    }

    public NeoFeed getFeed(LocalDate date) throws URISyntaxException, JsonProcessingException {
        return this.getFeed(date, date);
    }

    public NeoFeed getFeed() throws URISyntaxException, JsonProcessingException {
        return this.getFeed(LocalDate.now().minusDays(7), LocalDate.now());
    }
}
