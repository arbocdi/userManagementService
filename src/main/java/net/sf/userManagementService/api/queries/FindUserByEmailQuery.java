package net.sf.userManagementService.api.queries;

import lombok.*;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@ToString(callSuper = true)
public class FindUserByEmailQuery extends UserQuery {
    private String email;
}
