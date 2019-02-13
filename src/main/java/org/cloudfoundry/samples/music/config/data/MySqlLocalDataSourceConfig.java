package org.cloudfoundry.samples.music.config.data;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import javax.sql.DataSource;


@Configuration
@Profile("mysql-local")
public class MySqlLocalDataSourceConfig extends AbstractLocalDataSourceConfig {

    @Bean
    public DataSource dataSource() {
    	
        System.out.println("using mysql-local!!!!");
        

    	String DB_URL = System.getenv("DB_URL");
    	String DB_DRIVER = System.getenv("DB_DRIVER");
    	String DB_USERNAME = System.getenv("DB_USERNAME");
    	String DB_PASSWORD = System.getenv("DB_PASSWORD");

        //return createDataSource("jdbc:mysql://localhost/music", "com.mysql.jdbc.Driver", "", "");
        return createDataSource(DB_URL,DB_DRIVER, DB_USERNAME, DB_PASSWORD);
    }

}
