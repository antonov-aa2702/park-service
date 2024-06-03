package ru.relex.park.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import ru.relex.park.dto.filter.TripFilter;
import ru.relex.park.entity.Trip;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Component
public class TripDao implements Dao<Integer, Trip> {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public Integer save(Trip entity) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        var sql = """
                   INSERT INTO trip (user_id, vehicle_id, distance, from_date, to_date)
                   VALUES (?, ?, ?, ?, ?)
                """;
        jdbcTemplate.update(
                connection -> {
                    PreparedStatement ps = connection.prepareStatement(
                            sql,
                            Statement.RETURN_GENERATED_KEYS
                    );
                    ps.setInt(1, entity.getUserId());
                    ps.setInt(2, entity.getVehicleId());
                    ps.setInt(3, entity.getDistance());
                    ps.setDate(4, Date.valueOf(entity.getFromDate()));
                    ps.setDate(5, Date.valueOf(entity.getToDate()));
                    return ps;
                },
                keyHolder
        );

        return getGeneratedValue(keyHolder);
    }

    @Override
    public Optional<Trip> findById(Integer id) {
        return Optional.empty();
    }

    @Override
    public List<Trip> findAll() {
        return List.of();
    }

    @Override
    public void update(Trip entity) {

    }

    @Override
    public boolean delete(Integer id) {
        return false;
    }

    public List<Trip> findByVehicleIdAndDateBetween(Integer vehicleId, LocalDate fromDate, LocalDate toDate) {
        String sql = """
                SELECT id, user_id, vehicle_id, distance, from_date, to_date
                FROM trip
                WHERE vehicle_id = ?
                      AND ? BETWEEN from_date AND to_date
                      OR ? BETWEEN from_date AND to_date;
                """;
        var rowMapper = getRowMapper();
        return jdbcTemplate.query(sql, rowMapper,
                vehicleId,
                fromDate,
                toDate);
    }

    public List<Trip> getAllByFilter(TripFilter tripFilter, Integer userId) {
        String sql = """
                SELECT id, user_id, vehicle_id, distance, from_date, to_date
                FROM trip
                WHERE user_id = ? AND vehicle_id = ? AND ? < from_date AND ? > to_date;
                """;
        var rowMapper = getRowMapper();
        return jdbcTemplate.query(sql, rowMapper,
                userId,
                tripFilter.getVehicleId(),
                tripFilter.getFromDate(),
                tripFilter.getToDate());
    }

    private static int getGeneratedValue(KeyHolder keyHolder) {
        return ((Integer) (keyHolder.getKeyList().get(0).get("id"))).intValue();
    }

    private static RowMapper<Trip> getRowMapper() {
        return (rs, rowNum) -> Trip.builder()
                .id(rs.getInt("id"))
                .userId(rs.getInt("user_id"))
                .vehicleId(rs.getInt("vehicle_id"))
                .distance(rs.getInt("distance"))
                .fromDate(rs.getDate("from_date").toLocalDate())
                .toDate(rs.getDate("to_date").toLocalDate())
                .build();
    }
}


