package scm.api.restapi.medium.forms;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PasswordForm {
    private String currentPassword;
    private String newPassword;
    private String confirmPassword;
}
