package ru.relex.park.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import ru.relex.park.entity.Role;
import ru.relex.park.entity.User;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Component
public class UserDao implements Dao<Integer, User> {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public Integer save(User entity) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        var sql = """
                  INSERT INTO users (name, login, password, role)" +
                  VALUES (?, ?, ?, ?)
                  """;
        jdbcTemplate.update(
                connection -> {

                    PreparedStatement ps = connection.prepareStatement(
                            sql,
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
        return Optional.empty();
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
        String sql = """
                SELECT id, name, role, login, password
                FROM users
                WHERE login = ?;
                """;
        var rowMapper = getRowMapper();
        return getUserOptional(login, sql, rowMapper);
    }

    private Optional<User> getUserOptional(String login, String sql, RowMapper<User> rowMapper) {
        try {
            User user = jdbcTemplate.queryForObject(sql, rowMapper, login);
            return Optional.of(user);
        }catch (DataAccessException e ){
            return Optional.empty();
        }
    }

    private RowMapper<User> getRowMapper() {
        return (rs, rowNum) -> User.builder()
                .id(rs.getInt("id"))
                .name(rs.getString("name"))
                .login(rs.getString("login"))
                .password(rs.getString("password"))
                .role(Role.valueOf(rs.getString("role")))
                .build();
    }

    private Integer getGeneratedValue(KeyHolder keyHolder) {
        return ((Integer) (keyHolder.getKeyList().get(0).get("id"))).intValue();
    }
}
