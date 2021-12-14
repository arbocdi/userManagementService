package net.sf.userManagementService.api.commands;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import net.sf.userManagementService.entity.User;
import org.springframework.security.crypto.password.PasswordEncoder;

@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@ToString(callSuper = true)
public class ChangePasswordCommand extends UserCommand {
    private String newPassword;

    public User update(User user, PasswordEncoder passwordEncoder) {
        return user.setPassword(passwordEncoder.encode(newPassword));
    }
}
