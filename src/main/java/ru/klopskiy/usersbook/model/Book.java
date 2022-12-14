package ru.klopskiy.usersbook.model;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class Book {
    int book_id;

    @NotEmpty(message = "Название книги не должно быть пустым")
    @Size(min = 2, max = 100, message = "Название книги должно быть длиной от 2 до 100 символов")
    String title;

    @NotEmpty(message = "Имя автора не должно быть пустым")
    @Size(min = 2, max = 30, message = "Имя автора должно быть длиной от 2 до 30 символов")
    String author;

    int year;

    public Book() {
    }

    public Book(int person_id, String name, String author, int year) {
        this.title = name;
        this.author = author;
        this.year = year;
    }

    public int getBook_id() {
        return book_id;
    }

    public void setBook_id(int book_id) {
        this.book_id = book_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }
}
