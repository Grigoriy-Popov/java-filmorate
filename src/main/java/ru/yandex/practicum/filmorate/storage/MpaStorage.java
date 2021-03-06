package ru.yandex.practicum.filmorate.storage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.MpaRating;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Component
public class MpaStorage {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public MpaStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Optional<MpaRating> getRatingById(int mpaRatingId) {
        MpaRating mpa = jdbcTemplate.queryForObject("SELECT * FROM mpa_rating WHERE mpa_id = ?",
                this::makeMpa, mpaRatingId);
        return Optional.ofNullable(mpa);
    }

    public List<MpaRating> getAllMpaRatings() {
        String sql = "SELECT * FROM mpa_rating";
        return jdbcTemplate.query(sql, this::makeMpa);
    }

    private MpaRating makeMpa(ResultSet rs, int rowNum) throws SQLException {
        return new MpaRating(rs.getInt("mpa_id"), rs.getString("name"));
    }
}
