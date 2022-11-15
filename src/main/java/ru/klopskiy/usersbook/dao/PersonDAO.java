package ru.klopskiy.usersbook.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.klopskiy.usersbook.model.Book;
import ru.klopskiy.usersbook.model.Person;

import java.util.ArrayList;
import java.util.List;

@Component
public class PersonDAO {
    private final JdbcTemplate jdbcTemplate;
    private final BeanPropertyRowMapper<Person> rowMapper = new BeanPropertyRowMapper<>(Person.class);

    @Autowired
    public PersonDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Person> getAll() {
        return jdbcTemplate.query("select * from persons", rowMapper);
    }

    public Person get(int id) {
        return jdbcTemplate.query("select * from persons where persons.person_id=?", new Object[]{id}, rowMapper).stream()
                .findAny().orElse(null);
    }

    public void save(Person person) {
        jdbcTemplate.update("insert into persons (name, year_of_birth) VALUES (?, ?)", person.getName(), person.getYearOfBirth());
    }

    public void update(int id, Person person) {
        jdbcTemplate.update("update persons set name=?, year_of_birth=? where person_id=?", person.getName(), person.getYearOfBirth(), id);
    }

    public void delete(int id) {
        jdbcTemplate.update("delete from persons where person_id=?", id);
    }

    public List<Book> getBooksByPersonId(int id) {
        return jdbcTemplate.query("select * from books where person_id=?", new Object[]{id}, new BeanPropertyRowMapper<>(Book.class));
    }
}