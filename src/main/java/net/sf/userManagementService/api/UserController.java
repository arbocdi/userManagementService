package net.sf.userManagementService.api;

import net.sf.userManagementService.UserAggregate;
import net.sf.userManagementService.api.commands.ChangePasswordCommand;
import net.sf.userManagementService.api.commands.CreateUserCommand;
import net.sf.userManagementService.api.commands.DeleteUserCommand;
import net.sf.userManagementService.api.commands.UpdateUserCommand;
import net.sf.userManagementService.api.queries.FindAllUsersQuery;
import net.sf.userManagementService.api.queries.FindUserByEmailQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(path = "/")
public class UserController {

    @Autowired
    private UserAggregate userAggregate;

    @PostMapping(path = "/users")
    public Mono<UserWithoutPassword> create(@RequestBody CreateUserCommand cmd) {
        return userAggregate.on(cmd);
    }

    @PutMapping(path = "/users/me")
    public Mono<UserWithoutPassword> update(@RequestBody UpdateUserCommand cmd) {
        return userAggregate.on(cmd);
    }

    @DeleteMapping(path = "/users/me",consumes = MediaType.ALL_VALUE)
    public Mono<Void> delete(@RequestBody DeleteUserCommand cmd) {
        return userAggregate.on(cmd);
    }

    @PutMapping(path = "/users/me/changePassword")
    public Mono<UserWithoutPassword> changePassword(@RequestBody ChangePasswordCommand cmd) {
        return userAggregate.on(cmd);
    }

    @GetMapping(path = "/managers/users/{id}")
    public Mono<UserWithoutPassword> findByEmail(@PathVariable("id") String email) {
        return userAggregate.on(new FindUserByEmailQuery().setEmail(email));
    }

    @GetMapping(path = "/managers/users")
    public Flux<UserWithoutPassword> findAll() {
        return userAggregate.on(new FindAllUsersQuery());
    }
}
