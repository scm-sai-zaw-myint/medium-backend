package scm.api.restapi.medium.forms;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PasswordForm {
    @Size(min = 6, max = 16)
    private String currentPassword;
    @Size(min = 6, max = 16)
    private String newPassword;
    @Size(min = 6, max = 16)
    private String confirmPassword;
}
