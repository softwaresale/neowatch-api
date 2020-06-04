package com.appdev.neowatch.serializers;

import com.appdev.neowatch.models.EstimatedDiameter;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;
import java.util.Map;

public class EstimatedDiameterSerializer extends StdSerializer<EstimatedDiameter> {

    public EstimatedDiameterSerializer(Class<EstimatedDiameter> type) {
        super(type);
    }

    public EstimatedDiameterSerializer() {
        this(null);
    }

    @Override
    public void serialize(EstimatedDiameter estimatedDiameter, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartObject();
        for (Map.Entry<String, EstimatedDiameter.MinMaxPair> entry : estimatedDiameter.getDiameters().entrySet()) {
            jsonGenerator.writeFieldName(entry.getKey());
            jsonGenerator.writeStartObject();
            jsonGenerator.writeNumberField("min", entry.getValue().getMin());
            jsonGenerator.writeNumberField("max", entry.getValue().getMax());
            jsonGenerator.writeEndObject();
        }
        jsonGenerator.writeEndObject();
    }
}
