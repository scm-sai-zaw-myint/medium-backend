package scm.api.restapi.medium.forms.reponse;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PasswordResponse {
    private String access_token;
    private Date expiredAt;
}
