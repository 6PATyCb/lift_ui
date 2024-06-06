/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ru.java_inside.lift_ui.users.names;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcDaoSupport;
import org.springframework.stereotype.Repository;

/**
 *
 * @author 6PATyCb
 */
@Repository
public class NamesDaoImpl extends NamedParameterJdbcDaoSupport implements NamesDao {

    @Autowired
    public void setLiftJdbcTemplate(JdbcTemplate jdbcTemplate) {
        setJdbcTemplate(jdbcTemplate);
    }

    @Override
    public String getRandomName() {
        return getJdbcTemplate().queryForObject("select name from lift.rus_names order by random() limit 1", String.class);
    }

}
