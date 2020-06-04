package com.appdev.neowatch.models;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.util.HashSet;
import java.util.Set;

public class Neo {

    @MongoId
    private String id;

    private String neoReferenceId;
    private String name;
    private String nasaJpaUrl;
    private Boolean isPotentiallyHazardousAsteroid;
    private Boolean isSentryObject;
    private EstimatedDiameter estimatedDiameter;
    private Set<CloseApproachData> closeApproachDataSet;

    public static Neo fromJson(JsonNode node) {
        String id = node.get("id").asText();
        String neoReferenceId = node.get("neo_reference_id").asText();
        String name = node.get("name").asText();
        String nasaJpaUrl = node.get("nasa_jpl_url").asText();
        Boolean isPotentiallyHazardous = node.get("is_potentially_hazardous_asteroid").asBoolean();
        Boolean isSentry = node.get("is_sentry_object").asBoolean();

        JsonNode estimatedDiameterNode = node.get("estimated_diameter");
        EstimatedDiameter estimatedDiameter = EstimatedDiameter.fromJson(estimatedDiameterNode);

        JsonNode closeApproachDataNode = node.get("close_approach_data");
        Set<CloseApproachData> closeApproachData = new HashSet<>();
        closeApproachDataNode.elements().forEachRemaining(element -> {
            CloseApproachData data = CloseApproachData.fromJson(element);
            closeApproachData.add(data);
        });

        return new Neo(id, neoReferenceId, name, nasaJpaUrl, isPotentiallyHazardous, isSentry, estimatedDiameter, closeApproachData);
    }

    public Neo(String id, String neoReferenceId, String name, String nasaJpaUrl, Boolean isPotentiallyHazardousAsteroid, Boolean isSentryObject, EstimatedDiameter estimatedDiameter, Set<CloseApproachData> closeApproachDataSet) {
        this.id = id;
        this.neoReferenceId = neoReferenceId;
        this.name = name;
        this.nasaJpaUrl = nasaJpaUrl;
        this.isPotentiallyHazardousAsteroid = isPotentiallyHazardousAsteroid;
        this.isSentryObject = isSentryObject;
        this.estimatedDiameter = estimatedDiameter;
        this.closeApproachDataSet = closeApproachDataSet;
    }

    public Neo() {
        this(null, null, null, null, false, false, null, null);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNeoReferenceId() {
        return neoReferenceId;
    }

    public void setNeoReferenceId(String neoReferenceId) {
        this.neoReferenceId = neoReferenceId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNasaJpaUrl() {
        return nasaJpaUrl;
    }

    public void setNasaJpaUrl(String nasaJpaUrl) {
        this.nasaJpaUrl = nasaJpaUrl;
    }

    public Boolean getPotentiallyHazardousAsteroid() {
        return isPotentiallyHazardousAsteroid;
    }

    public void setPotentiallyHazardousAsteroid(Boolean potentiallyHazardousAsteroid) {
        isPotentiallyHazardousAsteroid = potentiallyHazardousAsteroid;
    }

    public Boolean getSentryObject() {
        return isSentryObject;
    }

    public void setSentryObject(Boolean sentryObject) {
        isSentryObject = sentryObject;
    }

    public EstimatedDiameter getEstimatedDiameter() {
        return estimatedDiameter;
    }

    public void setEstimatedDiameter(EstimatedDiameter estimatedDiameter) {
        this.estimatedDiameter = estimatedDiameter;
    }

    public Set<CloseApproachData> getCloseApproachDataSet() {
        return closeApproachDataSet;
    }

    public void setCloseApproachDataSet(Set<CloseApproachData> closeApproachDataSet) {
        this.closeApproachDataSet = closeApproachDataSet;
    }
}
