package ru.yandex.practicum.filmorate.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

/**
 * Класс DbConfig представляет собой конфигурацию для базы данных в приложении Filmorate.
 * Используется для настройки подключения к базе данных и создания шаблона JdbcTemplate.
 */
@Configuration
public class DbConfig {

    @Value("${spring.datasource.url}")
    private String url;

    @Value("${spring.datasource.username}")
    private String username;

    @Value("${spring.datasource.password}")
    private String password;

    @Value("${spring.datasource.driverClassName}")
    private String driver;

    /**
     * Создает и настраивает источник данных для подключения к базе данных.
     *
     * @return объект DataSource для подключения к базе данных
     */
    @Bean
    public DataSource dataSource() {
        final DriverManagerDataSource dataSource = new DriverManagerDataSource(url, username, password);
        dataSource.setDriverClassName(driver);
        return dataSource;
    }

    /**
     * Создает и настраивает шаблон JdbcTemplate для выполнения запросов к базе данных.
     *
     * @param dataSource объект DataSource для подключения к базе данных
     * @return объект JdbcTemplate для выполнения запросов к базе данных
     */
    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }
}