package net.sf.userManagementService.api.commands;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import net.sf.userManagementService.entity.User;

@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@ToString(callSuper = true)
public class UpdateUserCommand extends UserCommand {
    private String firstName;
    private String lastName;

    public User update(User user){
        user.setFirstName(firstName);
        user.setLastName(lastName);
        return user;
    }
}
