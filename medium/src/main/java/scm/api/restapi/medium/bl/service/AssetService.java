package scm.api.restapi.medium.bl.service;

import jakarta.servlet.http.HttpServletResponse;

public interface AssetService {

    public Object getImageAsset(String name, HttpServletResponse response);

    public Object getProfileAsset(String name, HttpServletResponse response);

}
