package scm.api.restapi.medium.common;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class Response {

    public static ResponseEntity<?> send(HttpStatus status,boolean ok,String message, Object body){
        Map<String, Object> response = new HashMap<>();
        response.put("code", status.value());
        response.put("ok", ok);
        response.put("message",message);
        if(body != null)
        response.put("data", body);
        return ResponseEntity.status(status).body(response);
    }
    
}
