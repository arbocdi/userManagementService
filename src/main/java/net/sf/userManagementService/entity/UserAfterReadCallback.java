package net.sf.userManagementService.entity;

import org.reactivestreams.Publisher;
import org.springframework.data.r2dbc.mapping.event.AfterConvertCallback;
import org.springframework.data.relational.core.sql.SqlIdentifier;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class UserAfterReadCallback implements AfterConvertCallback<User> {
    @Override
    public Publisher<User> onAfterConvert(User entity, SqlIdentifier table) {
        return Mono.just(entity.setNewUser(false));
    }
}
