package ru.relex.park.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import ru.relex.park.entity.User;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Component
public class UserDao implements Dao<Integer, User> {

    private static final String FIND_BY_ID_SQL = """
                SELECT id, name, login, password, role
                FROM users
                WHERE id = ?
            """;

    private static final String SAVE_SQL = """
            INSERT INTO users (name, login, password, role)
            VALUES (?, ?, ?, ?)
            """;

    private static final String FIND_BY_LOGIN_SQL = """
                SELECT id, name, login, password, role
                FROM users
                WHERE login = ?
            """;

    private final JdbcTemplate jdbcTemplate;

    @Override
    public Integer save(User entity) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(
                connection -> {

                    PreparedStatement ps = connection.prepareStatement(
                            SAVE_SQL,
                            Statement.RETURN_GENERATED_KEYS
                    );
                    ps.setString(1, entity.getName());
                    ps.setString(2, entity.getLogin());
                    ps.setString(3, entity.getPassword());
                    ps.setString(4, entity.getRole().name());
                    return ps;
                },
                keyHolder
        );

        return getGeneratedValue(keyHolder);
    }

    @Override
    public Optional<User> findById(Integer id) {
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(
                    FIND_BY_ID_SQL,
                    new BeanPropertyRowMapper<>(User.class),
                    id));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<User> findAll() {
        return List.of();
    }

    @Override
    public void update(User entity) {

    }

    @Override
    public boolean delete(Integer id) {
        return false;
    }

    public Optional<User> findByLogin(String login) {
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(
                    FIND_BY_LOGIN_SQL,
                    new BeanPropertyRowMapper<>(User.class),
                    login));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }


    private Integer getGeneratedValue(KeyHolder keyHolder) {
        return ((Integer) (keyHolder.getKeyList().get(0).get("id"))).intValue();
    }
}
