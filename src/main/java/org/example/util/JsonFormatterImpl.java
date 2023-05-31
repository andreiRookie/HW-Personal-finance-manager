package org.example.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.net.Request;
import java.io.IOException;

public class JsonFormatterImpl implements JsonFormatter<Request>{
    private static final ObjectMapper mapper = new ObjectMapper();

    @Override
    public String toJsonString(Request request) {
        try {
            return mapper.writeValueAsString(request);
        }catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Request fromJsonFormat(String string) {
        try {
            return mapper.readValue(string, new TypeReference<>() {

            });
        } catch (IOException e){
            e.printStackTrace();
        }
        return null;
    }
}
