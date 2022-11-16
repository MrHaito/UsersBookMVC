package ru.klopskiy.usersbook.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.klopskiy.usersbook.dao.BooksDAO;
import ru.klopskiy.usersbook.dao.PersonDAO;
import ru.klopskiy.usersbook.model.Book;
import ru.klopskiy.usersbook.model.Person;

import javax.validation.Valid;
import java.util.Optional;

@Controller
@RequestMapping("/books")
public class BookController {

    private final BooksDAO booksDAO;
    private final PersonDAO personDAO;

    @Autowired
    public BookController(BooksDAO booksDAO, PersonDAO personDAO) {
        this.booksDAO = booksDAO;
        this.personDAO = personDAO;
    }

    @GetMapping()
    public String getAll(Model model) {
        model.addAttribute("books", booksDAO.getAll());
        return "books/index";
    }

    @GetMapping("/{id}")
    public String get(@PathVariable("id") int id,
                      @ModelAttribute("owner") Person person,
                      Model model) {
        Optional<Person> bookOwner = booksDAO.getOwner(id);

        model.addAttribute("book", booksDAO.get(id));
        if (bookOwner.isPresent()) {
            model.addAttribute("person", bookOwner.get());
        } else {
            model.addAttribute("persons", personDAO.getAll());
        }
        return "books/get";
    }

    @PostMapping("/{id}/clear")
    public String clearOwner(@PathVariable("id") int id) {
        booksDAO.clear(id);
        return "redirect:/books/{id}";
    }

    @PatchMapping("/{id}/add_owner")
    public String addOwner(@ModelAttribute("owner") Person person,
                           @PathVariable("id") int bookId) {
        booksDAO.addOwner(bookId, person.getPerson_id());
        return "redirect:/books/{id}";
    }


    @GetMapping("/new")
    public String newBook(@ModelAttribute("book") Book book) {
        return "books/create";
    }

    @PostMapping()
    public String create(@ModelAttribute("book") @Valid Book book,
                         BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "books/create";
        }
        booksDAO.save(book);
        return "redirect:/books";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model,
                       @PathVariable("id") int id) {
        model.addAttribute("book", booksDAO.get(id));
        return "/books/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("book") @Valid Book book,
                         BindingResult bindingResult,
                         @PathVariable("id") int id) {
        if (bindingResult.hasErrors()) {
            return "books/edit";
        }
        booksDAO.update(id, book);
        return "redirect:/books";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        booksDAO.delete(id);
        return "redirect:/books";
    }
}
