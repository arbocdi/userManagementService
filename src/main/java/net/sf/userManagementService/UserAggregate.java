package net.sf.userManagementService;

import net.sf.userManagementService.api.commands.ChangePasswordCommand;
import net.sf.userManagementService.api.commands.CreateUserCommand;
import net.sf.userManagementService.api.commands.DeleteUserCommand;
import net.sf.userManagementService.api.commands.UpdateUserCommand;
import net.sf.userManagementService.api.UserWithoutPassword;
import net.sf.userManagementService.api.queries.FindAllUsersQuery;
import net.sf.userManagementService.api.queries.FindUserByEmailQuery;
import net.sf.userManagementService.entity.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class UserAggregate {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public Mono<UserWithoutPassword> on(CreateUserCommand cmd) {
        return Mono.just(cmd.convertToUser())
                .doOnNext(user -> user.setPassword(passwordEncoder.encode(user.getPassword())))
                .flatMap(user -> userRepository.save(user))
                .map(UserWithoutPassword::from);
    }

    public Mono<UserWithoutPassword> on(UpdateUserCommand cmd) {
        return cmd.getUserMono()
                .flatMap(user -> userRepository.save(cmd.update(user)))
                .map(UserWithoutPassword::from);
    }

    public Mono<Void> on(DeleteUserCommand cmd) {
        return cmd.getUserMono()
                .flatMap(user -> userRepository.deleteById(user.getEmail()));
    }

    public Mono<UserWithoutPassword> on(ChangePasswordCommand cmd) {
        return cmd.getUserMono()
                .flatMap(user -> userRepository.save(cmd.update(user,passwordEncoder)))
                .map(UserWithoutPassword::from);
    }

    public Mono<UserWithoutPassword> on(FindUserByEmailQuery query) {
        return userRepository.findById(query.getEmail())
                .map(UserWithoutPassword::from);
    }

    public Flux<UserWithoutPassword> on(FindAllUsersQuery query) {
        return userRepository.findAll()
                .map(UserWithoutPassword::from);
    }

}
