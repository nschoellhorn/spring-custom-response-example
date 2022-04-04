package com.example.springcustomresponse;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;

public class CustomResponseSerializer extends StdSerializer<CustomResponseType<?>> {

    protected CustomResponseSerializer() {
        this(null);
    }

    protected CustomResponseSerializer(Class<CustomResponseType<?>> clazz) {
        super(clazz);
    }

    @Override
    public void serialize(CustomResponseType<?> customResponseType, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        if (customResponseType.isError()) {
            this.serializeError(customResponseType, jsonGenerator);
        } else {
            this.serializeSuccess(customResponseType, jsonGenerator, serializerProvider);
        }
    }

    private void serializeError(CustomResponseType<?> customResponseType, JsonGenerator jsonGenerator) throws IOException {
        // in case of error, we generate an errorCode field
        jsonGenerator.writeStartObject();
        jsonGenerator.writeStringField("errorCode", customResponseType.getErrorCode().toString());
        jsonGenerator.writeEndObject();
    }

    private void serializeSuccess(CustomResponseType<?> customResponseType, JsonGenerator jsonGenerator, SerializerProvider provider) throws IOException {
        // In case of success, we delegate serialization of the wrapped value to the default serializer for the wrapped type
        provider.defaultSerializeValue(customResponseType.get(), jsonGenerator);
    }
}
