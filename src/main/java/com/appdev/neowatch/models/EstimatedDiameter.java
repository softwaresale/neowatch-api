package com.appdev.neowatch.models;

import com.appdev.neowatch.serializers.EstimatedDiameterSerializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.util.HashMap;
import java.util.Map;

@JsonSerialize(using = EstimatedDiameterSerializer.class)
public class EstimatedDiameter {

    public static class MinMaxPair {
        private Double min;
        private Double max;

        public MinMaxPair(Double min, Double max) {
            this.min = min;
            this.max = max;
        }

        public Double getMin() {
            return min;
        }

        public void setMin(Double min) {
            this.min = min;
        }

        public Double getMax() {
            return max;
        }

        public void setMax(Double max) {
            this.max = max;
        }
    }

    public enum MeasureUnits {
        KILOMETERS {
            @Override
            public String toString() {
                return "kilometers";
            }
        },

        METERS {
            @Override
            public String toString() {
                return "meters";
            }
        },

        FEET {
            @Override
            public String toString() {
                return "feet";
            }
        },

        MILES {
            @Override
            public String toString() {
                return "miles";
            }
        }
    }

    @MongoId
    private String id;

    private Map<String, MinMaxPair> diameters;

    public static EstimatedDiameter fromJson(JsonNode node) {

        ObjectMapper mapper = new ObjectMapper();
        EstimatedDiameter estimatedDiameter = new EstimatedDiameter();

        // Parse kilometers
        node.fields().forEachRemaining(field -> {
            String measure = field.getKey();
            double min = field.getValue().get("estimated_diameter_min").asDouble();
            double max = field.getValue().get("estimated_diameter_max").asDouble();
            MinMaxPair minMaxPair = new MinMaxPair(min, max);
            estimatedDiameter.pushDiameter(measure, minMaxPair);
        });

        return estimatedDiameter;
    }

    public EstimatedDiameter() {
        diameters = new HashMap<>();
    }

    public void pushDiameter(String key, MinMaxPair pair) {
        this.diameters.put(key, pair);
    }

    public MinMaxPair getMinMaxForMeasure(MeasureUnits unit) {
        return this.diameters.get(unit.toString());
    }

    public Map<String, MinMaxPair> getDiameters() {
        return diameters;
    }

    public void setDiameters(Map<String, MinMaxPair> diameters) {
        this.diameters = diameters;
    }
}
