package scm.api.restapi.medium.bl.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

import jakarta.servlet.http.HttpServletResponse;
import scm.api.restapi.medium.bl.service.AssetService;
import scm.api.restapi.medium.common.Response;

@Service
public class AssetServiceImpl implements AssetService{

    @Value("${image.upload-dir}")
    private String imageDIR;
    
    @Value("${profile.upload-dir}")
    private String profileDIR;
    
    @Override
    public Object getImageAsset(String name, HttpServletResponse response) {
        File f = new File(this.getImageURL(name));
        if(!f.exists()) {
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            return Response.send(HttpStatus.NOT_FOUND, false, "No image found!", null, null, null);
        }
        try {
        InputStream in = new FileInputStream(f);
        
        String imgType = name.substring(name.lastIndexOf(".")+1,name.length());
        imgType = imgType == "svg"?imgType+"+xml":imgType;
        response.setContentType("image/"+imgType);
        byte[] bytes = IOUtils.toByteArray(in);
        in.close();
        return bytes;
        }catch(Exception e) {
            e.printStackTrace();
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            return Response.send(HttpStatus.INTERNAL_SERVER_ERROR, false, "Unexpect error occoured!", e, null, null);
        }
    }


    @Override
    public Object getProfileAsset(String name, HttpServletResponse response) {
        File f = new File(this.getProfileURL(name));
        if(!f.exists()) {
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            return Response.send(HttpStatus.NOT_FOUND, false, "No image found!", null, null, null);
        }
        try {
        InputStream in = new FileInputStream(f);
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        byte[] bytes = IOUtils.toByteArray(in);
        in.close();
        return bytes;
        }catch(Exception e) {
            e.printStackTrace();
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            return Response.send(HttpStatus.INTERNAL_SERVER_ERROR, false, "Unexpect error occoured!", e, null, null);
        }
    }

    private String getImageURL(String name) {
        return new StringBuffer(imageDIR).append("/").append(name).toString();
    }
    
    private String getProfileURL(String name) {
        return new StringBuffer(profileDIR).append("/").append(name).toString();
    }

}
