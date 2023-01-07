package scm.api.restapi.medium.forms.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthRequestForm {
    @Email
    private String email;
    @Size(min = 6, max = 16)
    private String password;
    
}
