package net.sf.userManagementService.api;

import lombok.Getter;
import lombok.Setter;
import net.sf.userManagementService.entity.User;
import reactor.core.publisher.Mono;

@Getter
@Setter
public class UserMessage {
    private Mono<User> userMono;
}
