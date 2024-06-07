/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ru.java_inside.lift_ui.lift.lift_ride;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Clock;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcDaoSupport;
import org.springframework.stereotype.Repository;

/**
 *
 * @author 6PATyCb
 */
@Repository
public class LiftRideDaoImpl extends NamedParameterJdbcDaoSupport implements LiftRideDao {

    private Clock clock;

    @Override
    public void saveLiftRide(LiftRide liftRide) {
        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue("userId", liftRide.getUserId());
        parameterSource.addValue("ridedFloors", liftRide.getRidedFloors());

        LocalDateTime created = LocalDateTime.ofInstant(Instant.ofEpochMilli(liftRide.getTimestamp()), clock.getZone());
        parameterSource.addValue("created", created);
        StringBuilder sql = new StringBuilder();
        sql.append("INSERT INTO lift.lift_ride (userId,ridedFloors,created) VALUES (");
        sql.append(":userId,");
        sql.append(":ridedFloors,");
        sql.append(":created");
        sql.append(")");

        getNamedParameterJdbcTemplate().update(sql.toString(), parameterSource);
    }

    @Override
    public List<LiftRide> getLast100LiftRides() {
        return getJdbcTemplate().query("select * from lift.lift_ride order by created desc limit 100", new LiftRideRowMapper());
    }

    private class LiftRideRowMapper implements RowMapper<LiftRide> {

        @Override
        public LiftRide mapRow(ResultSet rs, int rowNum) throws SQLException {
            String userId = rs.getString("userId");
            int ridedFloors = rs.getInt("ridedFloors");
            Timestamp timestamp = rs.getTimestamp("created");
            return new LiftRide(ridedFloors, userId, timestamp.getTime());
        }

    }

    @Autowired
    public void setLiftJdbcTemplate(JdbcTemplate jdbcTemplate) {
        setJdbcTemplate(jdbcTemplate);
    }

    @Autowired
    public void setClock(Clock clock) {
        this.clock = clock;
    }

}
