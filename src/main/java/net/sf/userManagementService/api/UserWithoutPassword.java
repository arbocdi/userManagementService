package net.sf.userManagementService.api;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import net.sf.userManagementService.entity.User;

@Data
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
