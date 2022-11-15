package ru.klopskiy.usersbook.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.klopskiy.usersbook.model.Book;
import ru.klopskiy.usersbook.model.Person;

import java.util.List;

@Component
public class BooksDAO {
    private final JdbcTemplate jdbcTemplate;
    private final BeanPropertyRowMapper<Book> rowMapper = new BeanPropertyRowMapper<>(Book.class);

    @Autowired
    public BooksDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Book> getAll() {
        return jdbcTemplate.query("select * from books", rowMapper);
    }

    public Book get(int id) {
        return jdbcTemplate.query("select * from books where book_id=?", new Object[]{id}, rowMapper).stream()
                .findAny().orElse(null);
    }

    public void clear(int id) {
        jdbcTemplate.update("UPDATE books SET person_id=null WHERE book_id=?", id);
    }

    public void save(Book book) {
        jdbcTemplate.update("insert into books (title, author, year) VALUES (?, ?, ?)", book.getTitle(), book.getAuthor(), book.getYear());
    }

    public void update(int id, Book book) {
        jdbcTemplate.update("update books set title=?, author=?, year=? where book_id=?", book.getTitle(), book.getAuthor(), book.getYear(), id);
    }

    public void delete(int id) {
        jdbcTemplate.update("delete from books where book_id=?", id);
    }

    public Person getOwner(int id) {
        return jdbcTemplate.query("select * " +
                                          "from persons " +
                                          "join books " +
                                          "on persons.person_id = books.person_id " +
                                          "where book_id=?", new Object[]{id}, new BeanPropertyRowMapper<>(Person.class)).stream()
                .findAny().orElse(null);
    }

    public void addOwner(int bookId, int personId) {
        jdbcTemplate.update("update books set person_id=? where book_id=?", personId, bookId);
    }
}
