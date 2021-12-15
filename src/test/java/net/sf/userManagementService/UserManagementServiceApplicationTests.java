package net.sf.userManagementService;

import net.sf.userManagementService.api.UserWithoutPassword;
import net.sf.userManagementService.api.commands.ChangePasswordCommand;
import net.sf.userManagementService.api.commands.DeleteUserCommand;
import net.sf.userManagementService.api.commands.UpdateUserCommand;
import net.sf.userManagementService.entity.User;
import net.sf.userManagementService.entity.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpMethod;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.util.Assert;
import reactor.core.publisher.Mono;

@SpringBootTest
class UserManagementServiceApplicationTests {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ApplicationContext applicationContext;
    private WebTestClient webTestClient;


    User arboc;
    User maya;


    @BeforeEach
    public void before() {
        webTestClient = WebTestClient.bindToApplicationContext(applicationContext)
                .build();
        userRepository.deleteAll().block();
        arboc = new User().setEmail("arbocdi@gmail.com").setFirstName("arboc").setLastName("di").setPassword("apw");
        maya = new User().setEmail("maya@gmail.com").setFirstName("maya").setLastName("lightbringer").setPassword("mpw");
    }


    @Test
    void registerNewUserOk() {
        webTestClient.post()
                .uri("/users")
                .body(Mono.just(arboc), User.class)
                .exchange()
                .expectBody(UserWithoutPassword.class)
                .isEqualTo(UserWithoutPassword.from(arboc));
    }

    @Test
    void registerNewUserNoPassword() {
        arboc.setPassword(null);
        webTestClient.post()
                .uri("/users")
                .body(Mono.just(arboc), User.class)
                .exchange()
                .expectStatus().is5xxServerError();
    }

    @Test
    void registerSameUserAgain() {
        registerNewUserOk();
        webTestClient.post()
                .uri("/users")
                .body(Mono.just(arboc), User.class)
                .exchange()
                .expectStatus().is5xxServerError();
    }

    @Test
    void registerTwoUsersAndRetrieveThem() {
        registerNewUserOk();
        webTestClient.post()
                .uri("/users")
                .body(Mono.just(maya), User.class)
                .exchange()
                .expectBody(UserWithoutPassword.class)
                .isEqualTo(UserWithoutPassword.from(maya));
        webTestClient.get()
                .uri("/managers/users")
                .exchange()
                .expectBodyList(UserWithoutPassword.class)
                .contains(UserWithoutPassword.from(arboc), UserWithoutPassword.from(maya));
    }

    @Test
    void changeNamesNoAuth() {
        registerNewUserOk();
        webTestClient.put()
                .uri("/users/me")
                .body(Mono.just(new UpdateUserCommand().setFirstName("arboc").setLastName("anakreon")), UpdateUserCommand.class)
                .exchange()
                .expectStatus().is4xxClientError();
    }

    @Test
    void changeNames() {
        registerNewUserOk();
        arboc.setLastName("anakreon");
        webTestClient.
                put()
                .uri("/users/me")
                .body(Mono.just(new UpdateUserCommand()
                        .setFirstName("arboc")
                        .setLastName("anakreon")), UpdateUserCommand.class)
                .headers(headers -> headers.setBasicAuth(arboc.getEmail(), arboc.getPassword()))
                .exchange()
                .expectBody(UserWithoutPassword.class)
                .isEqualTo(UserWithoutPassword.from(arboc));
    }

    @Test
    void changePassword() {
        registerNewUserOk();
        webTestClient.
                put()
                .uri("/users/me/changePassword")
                .body(Mono.just(new ChangePasswordCommand().setNewPassword("newpw")), ChangePasswordCommand.class)
                .headers(headers -> headers.setBasicAuth(arboc.getEmail(), arboc.getPassword()))
                .exchange()
                .expectBody(UserWithoutPassword.class)
                .isEqualTo(UserWithoutPassword.from(arboc));
    }
    @Test
    void deleteUser() {
        registerNewUserOk();
        webTestClient.
                method(HttpMethod.DELETE)
                .uri("/users/me")
                .body(Mono.just(new DeleteUserCommand()), DeleteUserCommand.class)
                .headers(headers -> headers.setBasicAuth(arboc.getEmail(), arboc.getPassword()))
                .exchange()
                .expectBody(Void.class);
        Assertions.assertFalse(userRepository.findAll().toIterable().iterator().hasNext());
    }

}
