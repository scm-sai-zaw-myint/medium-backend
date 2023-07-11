package scm.api.restapi.config;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import scm.api.restapi.medium.common.PropertyUtil;
import scm.api.restapi.medium.persistence.entiry.Users;
import scm.api.restapi.medium.persistence.repo.UsersRepo;

@Component
public class DeploymentListener {
    @Autowired
    private UsersRepo usersRepo;
    @Autowired
    PasswordEncoder passwordEncoder;
    @PostConstruct
    public void initData(){
        if(usersRepo.findByEmail("admin@gmail.com").isPresent())return;

        Users admin = new Users();
        admin.setEmail("admin@gmail.com");
        admin.setPassword(passwordEncoder.encode("admin123"));
        admin.setUsername("Admin");
        admin.setIp(PropertyUtil.generateIpAddress());
        usersRepo.save(admin);
    }
}
