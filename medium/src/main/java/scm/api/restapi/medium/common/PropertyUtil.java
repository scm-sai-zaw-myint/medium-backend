package scm.api.restapi.medium.common;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class PropertyUtil {

    public static String eToJson(Throwable e,int code) {
        Map<String, Object> error = new HashMap<>();
        error.put("code", code);
        error.put("ok", false);
        error.put("message", "Unauthorized!");
        error.put("error", e.getMessage());
        String json = "";
        try {
            json = new ObjectMapper().writeValueAsString(error);
        } catch (JsonProcessingException e1) {
            e1.printStackTrace();
            json = e1.getMessage();
        }
        return json;
    }
    
}
