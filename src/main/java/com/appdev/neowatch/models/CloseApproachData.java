package com.appdev.neowatch.models;

import com.fasterxml.jackson.databind.JsonNode;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

public class CloseApproachData {

    private LocalDate closeApproachDate;
    private LocalDateTime closeApproachDateFull;
    private Map<String, Double> relativeVelocities;
    private Map<String, Double> missDistance;
    private String orbitingBody;

    public static CloseApproachData fromJson(JsonNode node) {
        String orbitingBody = node.get("orbiting_body").asText();
        String dateString = node.get("close_approach_date").asText();
        LocalDate closeApproachDate = LocalDate.parse(dateString);

        String dateStringOpt = node.get("close_approach_date_full").asText();
        LocalDateTime closeApproachDateTime = null;
        if (!dateStringOpt.equals("null")) {
            closeApproachDateTime = LocalDateTime.parse(dateStringOpt, DateTimeFormatter.ofPattern("yyyy-MMM-dd HH:mm"));
        }

        HashMap<String, Double> velocities = new HashMap<>();
        JsonNode relativeVelocities = node.get("relative_velocity");
        relativeVelocities.fields().forEachRemaining(field -> {
            String unit = field.getKey();
            String valueStr = field.getValue().asText();
            Double value = Double.parseDouble(valueStr);
            velocities.put(unit, value);
        });

        HashMap<String, Double> missDistances = new HashMap<>();
        node.get("miss_distance").fields().forEachRemaining(field -> {
            String unit = field.getKey();
            String valueStr = field.getValue().asText();
            Double value = Double.parseDouble(valueStr);
            missDistances.put(unit, value);
        });

        return new CloseApproachData(closeApproachDate, closeApproachDateTime, velocities, missDistances, orbitingBody);
    }

    public CloseApproachData(LocalDate closeApproachDate, LocalDateTime closeApproachDateFull, Map<String, Double> relativeVelocities, Map<String, Double> missDistance, String orbitingBody) {
        this.closeApproachDate = closeApproachDate;
        this.closeApproachDateFull = closeApproachDateFull;
        this.relativeVelocities = relativeVelocities;
        this.missDistance = missDistance;
        this.orbitingBody = orbitingBody;
    }

    public CloseApproachData() {
        this(null, null, new HashMap<>(), new HashMap<>(), null);
    }

    public LocalDate getCloseApproachDate() {
        return closeApproachDate;
    }

    public void setCloseApproachDate(LocalDate closeApproachDate) {
        this.closeApproachDate = closeApproachDate;
    }

    public LocalDateTime getCloseApproachDateFull() {
        return closeApproachDateFull;
    }

    public void setCloseApproachDateFull(LocalDateTime closeApproachDateFull) {
        this.closeApproachDateFull = closeApproachDateFull;
    }

    public Map<String, Double> getRelativeVelocities() {
        return relativeVelocities;
    }

    public void setRelativeVelocities(Map<String, Double> relativeVelocities) {
        this.relativeVelocities = relativeVelocities;
    }

    public Map<String, Double> getMissDistance() {
        return missDistance;
    }

    public void setMissDistance(Map<String, Double> missDistance) {
        this.missDistance = missDistance;
    }

    public String getOrbitingBody() {
        return orbitingBody;
    }

    public void setOrbitingBody(String orbitingBody) {
        this.orbitingBody = orbitingBody;
    }
}
