package scm.api.restapi.medium.forms.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthRequestForm {

    private String email;
    private String password;
    
}
