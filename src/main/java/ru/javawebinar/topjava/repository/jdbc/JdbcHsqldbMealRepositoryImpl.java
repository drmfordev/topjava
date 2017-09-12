package ru.javawebinar.topjava.repository.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
@Profile("hsqldb")
@Repository
public class JdbcHsqldbMealRepositoryImpl extends JdbcMealRepository {

    @Autowired
    public JdbcHsqldbMealRepositoryImpl(DataSource dataSource, JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        super(dataSource, jdbcTemplate, namedParameterJdbcTemplate);
    }

    @Override
    public Date getParticularDate(LocalDateTime date) {

        Date out = Date.from(date.atZone(ZoneId.systemDefault()).toInstant());
        return out;
    }
}
