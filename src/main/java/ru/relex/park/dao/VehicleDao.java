package ru.relex.park.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import ru.relex.park.entity.Vehicle;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Component
public class VehicleDao implements Dao<Integer, Vehicle> {

    private static final String SAVE_SQL = "INSERT INTO vehicle (name, year) VALUES (?, ?)";
    public static final String FIND_BY_ID_SQL = """
                SELECT id, name, year
                FROM vehicle
                WHERE id = ?
            """;
    private final JdbcTemplate jdbcTemplate;

    @Override
    public Integer save(Vehicle entity) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(
                connection -> {
                    PreparedStatement ps = connection.prepareStatement(
                            SAVE_SQL,
                            Statement.RETURN_GENERATED_KEYS
                    );
                    ps.setString(1, entity.getName());
                    ps.setInt(2, entity.getYear());
                    return ps;
                },
                keyHolder
        );

        return getGeneratedValue(keyHolder);
    }

    @Override
    public Optional<Vehicle> findById(Integer id) {
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(
                    FIND_BY_ID_SQL,
                    new BeanPropertyRowMapper<>(Vehicle.class),
                    id));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Vehicle> findAll() {
        return List.of();
    }

    @Override
    public void update(Vehicle entity) {

    }

    @Override
    public boolean delete(Integer id) {
        return false;
    }

    private static int getGeneratedValue(KeyHolder keyHolder) {
        return ((Integer) (keyHolder.getKeyList().get(0).get("id"))).intValue();
    }
}
