package net.sf.userManagementService.api.commands;

import lombok.*;
import lombok.experimental.Accessors;
import net.sf.userManagementService.api.UserMessage;

@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@ToString(callSuper = true)
public class UserCommand extends UserMessage {
}
