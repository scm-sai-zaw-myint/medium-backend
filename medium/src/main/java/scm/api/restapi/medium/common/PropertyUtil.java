package scm.api.restapi.medium.common;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.ocpsoft.prettytime.PrettyTime;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import scm.api.restapi.config.FileConfiguration;

@Component
public class PropertyUtil {

    @Value("${image.upload-dir}")
    private String imageStorageDIR;
    
    @Value("${profile.upload-dir}")
    private String profileStorageDIR;
    
    private static final PrettyTime pretty = new PrettyTime();
    
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
    
    public List<?> convertStringToList(String str, String regex){
        return Arrays.asList(str.split(regex));
    }
    
    public String uploadPhotorequest(MultipartFile file,String prefix,boolean profile) throws IOException {
        String orgname = file.getOriginalFilename();
        String orgType = orgname.substring(orgname.lastIndexOf(".")+1,orgname.length());
        String filename = FileConfiguration.nameFile(prefix)+"."+orgType;
        File f = new File(profile ? profileStorageDIR : imageStorageDIR);
        if(!f.exists()) {
            f.mkdir();
        }
        Path upload = Paths.get(f.getAbsolutePath()).toAbsolutePath().normalize().resolve(filename);
        Files.copy(file.getInputStream(), upload,StandardCopyOption.REPLACE_EXISTING);
        return filename;
    }
    
    public static String diffforhuman(Date date) {
        return pretty.format(date);
    }
    
}
