/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ru.java_inside.lift_ui.users.names;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcDaoSupport;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author 6PATyCb
 */
@Repository
public class NamesDaoImpl extends NamedParameterJdbcDaoSupport implements NamesDao {

    public NamesDaoImpl() {
       // System.out.println("!!!NamesDaoImpl " + this);
    }

    @Override
    public String getRandomName() {
        return getJdbcTemplate().queryForObject("select name from lift.rus_names order by random() limit 1", String.class);
    }

    @Autowired
    public void setLiftJdbcTemplate(JdbcTemplate jdbcTemplate) {
        setJdbcTemplate(jdbcTemplate);
    }

    @Override
    public int createName(Name newName) {
        StringBuilder sql = new StringBuilder();
        sql.append("INSERT INTO lift.rus_names (name,is_female) VALUES (");
        sql.append(":name,");
        sql.append(":is_female");
        sql.append(")");
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("name", newName.getName());
        mapSqlParameterSource.addValue("is_female", newName.isFemale());
        KeyHolder keyHolderReposit = new GeneratedKeyHolder();
        getNamedParameterJdbcTemplate().update(sql.toString(), mapSqlParameterSource, keyHolderReposit);
        int generatedId = (Integer) keyHolderReposit.getKeys().get("ID");
        return generatedId;
    }

    @Override
    public void updateName(Name changedName) {
        StringBuilder sql = new StringBuilder();
        sql.append("UPDATE lift.rus_names SET");
        sql.append(" name = :name,");
        sql.append(" is_female = :is_female");
        sql.append(" where id = :id");
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("name", changedName.getName());
        mapSqlParameterSource.addValue("is_female", changedName.isFemale());
        mapSqlParameterSource.addValue("id", changedName.getId());
        int affectedRows = getNamedParameterJdbcTemplate().update(sql.toString(), mapSqlParameterSource);
        if (affectedRows > 1) {
            throw new RuntimeException("Изменено более одной строки при обновлении имени для пользователей");
        }
        if (affectedRows == 0) {
            throw new RuntimeException("Не удалось найти запись при обновлении имени для пользователей");
        }
    }

    @Transactional(readOnly = false)
    @Override
    public void deleteName(Name name) {
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("id", name.getId());
        int affectedRows = getNamedParameterJdbcTemplate().update("delete from lift.rus_names where id = :id", mapSqlParameterSource);
        if (affectedRows > 1) {
            throw new RuntimeException("Изменено более одной строки при удалении имени для пользователей");
        }
        int count = getJdbcTemplate().queryForObject("select count(*) from lift.rus_names limit 1", Integer.class);
        if (count == 0) {
            throw new RuntimeException("Нельзя удалять последнюю запись из таблицы");
        }
    }

    @Override
    public List<Name> getAllNames() {
        return getJdbcTemplate().query("select * from lift.rus_names", new NameRowMapper());
    }

    @Override
    public Name getNameById(int id) {
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("id", id);
        try {
            return getNamedParameterJdbcTemplate().queryForObject("select * from lift.rus_names where id = :id", mapSqlParameterSource, new NameRowMapper());
        } catch (EmptyResultDataAccessException ignored) {
            return null;
        }

    }

    private class NameRowMapper implements RowMapper<Name> {

        @Override
        public Name mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new Name(rs.getInt("id"), rs.getString("name"), rs.getBoolean("is_female"));
        }

    }

}
