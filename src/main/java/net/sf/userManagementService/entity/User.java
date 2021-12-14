package net.sf.userManagementService.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.domain.Persistable;
import org.springframework.data.relational.core.mapping.Table;

import java.util.Objects;

@Table("users")
@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
public class User implements Persistable<String> {
    @Id
    private String email;
    private String firstName;
    private String lastName;
    private String password;

    @Transient
    @JsonIgnore
    private boolean newUser = true;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(email, user.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email);
    }

    @Override
    public String getId() {
        return email;
    }

    @Override
    public boolean isNew() {
        return newUser;
    }
}
