package com.appdev.neowatch.models;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class NeoFeed {

    private Long elements;
    private Map<String, String> adjacentLinks;
    private Map<LocalDate, Set<Neo>> neos;

    public static NeoFeed fromJson(JsonNode node) {
        Long elementCount = node.get("element_count").asLong();
        Map<String, String> links = new HashMap<>();
        node.get("links").fields().forEachRemaining(field -> {
            links.put(field.getKey(), field.getValue().textValue());
        });

        Map<LocalDate, Set<Neo>> neos = new HashMap<>();
        node.get("near_earth_objects").fields().forEachRemaining(field -> {
            String dateStr = field.getKey();
            LocalDate date = LocalDate.parse(dateStr);
            Set<Neo> dateNeos = new HashSet<>();
            field.getValue().elements().forEachRemaining(element -> {
                Neo newNeo = Neo.fromJson(element);
                dateNeos.add(newNeo);
            });

            neos.put(date, dateNeos);
        });

        return new NeoFeed(elementCount, links, neos);
    }

    public static NeoFeed fromJson(String requestBody) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode rootNode = mapper.readTree(requestBody);
        // Once node is parsed, call the previous method
        return fromJson(rootNode);
    }

    public NeoFeed(Long elements, Map<String, String> adjacentLinks, Map<LocalDate, Set<Neo>> neos) {
        this.elements = elements;
        this.adjacentLinks = adjacentLinks;
        this.neos = neos;
    }

    public NeoFeed() {
        this(0l, null, null);
    }

    public Long getElements() {
        return elements;
    }

    public void setElements(Long elements) {
        this.elements = elements;
    }

    public Map<String, String> getAdjacentLinks() {
        return adjacentLinks;
    }

    public void setAdjacentLinks(Map<String, String> adjacentLinks) {
        this.adjacentLinks = adjacentLinks;
    }

    public Map<LocalDate, Set<Neo>> getNeos() {
        return neos;
    }

    public void setNeos(Map<LocalDate, Set<Neo>> neos) {
        this.neos = neos;
    }
}
