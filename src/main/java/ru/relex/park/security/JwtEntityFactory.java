package ru.relex.park.security;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import ru.relex.park.entity.User;

import java.util.Collection;
import java.util.Collections;


@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class JwtEntityFactory {

    public static JwtEntity create(User user) {
        return JwtEntity
                .builder()
                .id(user.getId())
                .username(user.getLogin())
                .password(user.getPassword())
                .authorities(getAuthorities(user))
                .build();
    }

    private static Collection<? extends GrantedAuthority> getAuthorities(User user) {
        return Collections.singletonList(new SimpleGrantedAuthority(user.getRole().name()));
    }
}
