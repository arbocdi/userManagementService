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
public class CreateUserCommand extends UserCommand {
    private String email;
    private String firstName;
    private String lastName;
    private String password;

    public User convertToUser(){
        return new User()
                .setEmail(email)
                .setFirstName(firstName)
                .setLastName(lastName)
                .setPassword(password);
    }

}
