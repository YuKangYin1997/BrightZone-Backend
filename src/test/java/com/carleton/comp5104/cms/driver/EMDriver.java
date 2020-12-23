package com.carleton.comp5104.cms.driver;

import com.carleton.comp5104.cms.CmsApplication;
import com.p6spy.engine.spy.P6SpyDriver;
import org.evomaster.client.java.controller.EmbeddedSutController;
import org.evomaster.client.java.controller.InstrumentedSutStarter;
import org.evomaster.client.java.controller.api.dto.AuthenticationDto;
import org.evomaster.client.java.controller.api.dto.SutInfoDto;
import org.evomaster.client.java.controller.db.DbCleaner;
import org.evomaster.client.java.controller.internal.SutController;
import org.evomaster.client.java.controller.problem.ProblemInfo;
import org.evomaster.client.java.controller.problem.RestProblem;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class EMDriver extends EmbeddedSutController {

    public static void main(String[] args) {
        SutController controller = new EMDriver();
        InstrumentedSutStarter starter = new InstrumentedSutStarter(controller);
        starter.start();
    }

    private ConfigurableApplicationContext ctx;
    private Connection connection;

    @Override
    public boolean isSutRunning() {
        return ctx != null && ctx.isRunning();
    }

    @Override
    public String getPackagePrefixesToCover() {
        return "com.carleton.comp5104.cms";
    }

    @Override
    public List<AuthenticationDto> getInfoForAuthentication() {
        return null;
    }

    @Override
    public Connection getConnection() {
        return connection;
    }

    @Override
    public String getDatabaseDriverName() {
        return "org.h2.Driver";
        //return "com.mysql.cj.jdbc.Driver";
    }

    @Override
    public ProblemInfo getProblemInfo() {
        return new RestProblem("http://localhost:8080/v3/api-docs", null);
    }

    @Override
    public SutInfoDto.OutputFormat getPreferredOutputFormat() {
        return SutInfoDto.OutputFormat.JAVA_JUNIT_5;
    }

    @Override
    public String startSut() {
        ctx = SpringApplication.run(CmsApplication.class, new String[]{
//                "--spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver",
//                "--spring.datasource.url=jdbc:mysql://${MYSQL_HOST:localhost}:3306/mybatis?serverTimezone=UTC&userUnicode=true&characterEncoding=utf-8",
//                "--spring.datasource.username=root",
//                "--spring.datasource.password=password",
//                "--server.port=8181",
//                "--spring.jpa.database-platform=org.hibernate.dialect.MySQL5InnoDBDialect",
//                "--spring.jpa.show-sql=true",
//                "--spring.jpa.hibernate.ddl-auto=create",

                "--spring.datasource.url=jdbc:p6spy:h2:mem:testdb;DB_CLOSE_DELAY=-1;",
                "--spring.datasource.driver-class-name=" + P6SpyDriver.class.getName()
        });
        System.out.println(P6SpyDriver.class.getName());
        JdbcTemplate jdbc = ctx.getBean(JdbcTemplate.class);

        try {
            connection = jdbc.getDataSource().getConnection();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        System.out.println(connection);
        return "http://localhost:8080";
    }

    @Override
    public void stopSut() {
        ctx.stop();
    }

    @Override
    public void resetStateOfSUT() {
        DbCleaner.clearDatabase_H2(connection);
    }
}
