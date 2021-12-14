package net.sf.userManagementService.config;

import lombok.extern.slf4j.Slf4j;
import net.sf.userManagementService.api.UserMessage;
import net.sf.userManagementService.entity.User;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.core.annotation.Order;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.Objects;

@Aspect
@Component
//way-in:совет с наименьшим приоритетом выполняется первым
//way-out:совет с наименьшим приоритетом выполняется последним
@Order(20)
@Slf4j
public class SetPrincipalRestAdvice {

    @Before("@within(org.springframework.web.bind.annotation.RestController) && within(net.sf.userManagementService..*)")
    public void before(JoinPoint joinPoint) {
        Mono<User> userMono = ReactiveSecurityContextHolder.getContext()
                .map(securityContext -> securityContext.getAuthentication().getPrincipal())
                .filter(principal -> principal != null && ManagementUserDetails.class.isAssignableFrom(principal.getClass()))
                .map(principal -> ((ManagementUserDetails) principal).getUser());
        Arrays.stream(joinPoint.getArgs())
                .filter(Objects::nonNull)
                .filter(param -> UserMessage.class.isAssignableFrom(param.getClass()))
                .map(param -> (UserMessage) param)
                .forEach(userMessage -> userMessage.setUserMono(userMono));
    }


}
