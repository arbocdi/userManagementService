package net.sf.userManagementService.api;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import net.sf.userManagementService.entity.User;

@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
public class UserWithoutPassword {
    private String email;
    private String firstName;
    private String lastName;

    public static UserWithoutPassword from(User user) {
        return new UserWithoutPassword()
                .setEmail(user.getEmail())
                .setFirstName(user.getFirstName())
                .setLastName(user.getLastName());
    }
}
