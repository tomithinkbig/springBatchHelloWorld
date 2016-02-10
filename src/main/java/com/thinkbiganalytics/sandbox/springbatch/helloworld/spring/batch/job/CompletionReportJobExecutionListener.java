package com.thinkbiganalytics.sandbox.springbatch.helloworld.spring.batch.job;

import com.thinkbiganalytics.sandbox.springbatch.helloworld.dataobjects.Person;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Component
public class CompletionReportJobExecutionListener implements JobExecutionListener {

    private static final Logger log = LoggerFactory.getLogger(CompletionReportJobExecutionListener.class);


    private JdbcTemplate jdbcTemplate;

    @Autowired
    public CompletionReportJobExecutionListener(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void beforeJob(JobExecution jobExecution) {
        log.info(" jobExecution: \n"+jobExecution);
    }

    @Override
    public void afterJob(JobExecution jobExecution) {
        log.info("after jobExecution: \n"+jobExecution);

        List<Person> personList = jdbcTemplate.query("SELECT first_name,last_name FROM people", new RowMapper<Person>() {
            @Override
            public Person mapRow(ResultSet resultSet, int i) throws SQLException {
                return new Person(resultSet.getNString("first_name"), resultSet.getNString("last_name"));
            }
        });

        for (Person person : personList) {
            log.info("found "+person+" in database");
        }

    }
}
