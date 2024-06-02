package ru.relex.park.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.relex.park.entity.Vehicle;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Repository
public class VehicleDao implements Dao<Integer, Vehicle> {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public Integer save(Vehicle entity) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(
                connection -> {
                    PreparedStatement ps = connection.prepareStatement(
                            "INSERT INTO vehicle (name, year) VALUES (?, ?)",
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
        String sql = """
                SELECT * 
                FROM vehicle
                WHERE id = ?;                             
                """;
        var rowMapper = getVehicleRowMapper();
        Vehicle vehicle = jdbcTemplate.queryForObject(sql, rowMapper, id);
        return Optional.ofNullable(vehicle);
    }

    @Override
    public List<Vehicle> findAll() {
        return null;
    }

    @Override
    public void update(Vehicle entity) {

    }

    @Override
    public boolean delete(Integer id) {
        return false;
    }

    private static RowMapper<Vehicle> getVehicleRowMapper() {
        return (rs, rowNum) -> Vehicle.builder()
                .id(rs.getInt("id"))
                .name(rs.getString("name"))
                .year(rs.getInt("year"))
                .build();
    }

    private static int getGeneratedValue(KeyHolder keyHolder) {
        return ((Integer) (keyHolder.getKeyList().get(0).get("id"))).intValue();
    }
}
