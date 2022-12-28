package scm.api.restapi.medium.forms.reponse;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthResponseForm {
    @JsonInclude(Include.NON_NULL)
    private String email;
    @JsonInclude(Include.NON_NULL)
    private String accessToken;
    @JsonInclude(Include.NON_NULL)
    private Date expiredIn;
}
