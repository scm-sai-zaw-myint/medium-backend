package scm.api.restapi.medium.forms;

import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ProfileUpdateForm {

    @NotNull
    @Size(min = 6, max = 30)
    private String name;
    
    private String bio;
    
    private MultipartFile profile;
    
}
