package scm.api.restapi.medium.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletResponse;
import scm.api.restapi.medium.service.AssetService;

@RestController
@CrossOrigin
@RequestMapping("/api/assets")
public class AssetController {

    
    @Autowired
    private AssetService service;
    
    @GetMapping(value="/image/{name}",produces = {MediaType.IMAGE_JPEG_VALUE})
    public Object getImageAsset(@PathVariable String name,HttpServletResponse response) {
        
        return this.service.getImageAsset(name,response);
    }
    
    @GetMapping(value="/profile/{name}",produces = {MediaType.IMAGE_JPEG_VALUE})
    public Object getProfileAsset(@PathVariable String name,HttpServletResponse response) {
        return this.service.getProfileAsset(name,response);
    }
}
