package ru.klopskiy.usersbook.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.klopskiy.usersbook.dao.PersonDAO;
import ru.klopskiy.usersbook.model.Person;
import ru.klopskiy.usersbook.util.PersonValidator;

import javax.validation.Valid;

@Controller
@RequestMapping("/persons")
public class PersonController {

    private final PersonDAO personDAO;
    private final PersonValidator personValidator;

    @Autowired
    public PersonController(PersonDAO personDAO, PersonValidator personValidator) {
        this.personDAO = personDAO;
        this.personValidator = personValidator;
    }

    @GetMapping()
    public String getAll(Model model) {
        model.addAttribute("persons", personDAO.getAll());
        return "persons/index";
    }

    @GetMapping("/{id}")
    public String get(@PathVariable("id") int id,
                      Model model) {
        model.addAttribute("person", personDAO.get(id));
        model.addAttribute("books", personDAO.getBooksByPersonId(id));
        return "persons/get";
    }

    @GetMapping("/new")
    public String newPerson(@ModelAttribute("person") Person person) {
        return "persons/create";
    }

    @PostMapping()
    public String create(@ModelAttribute("person") @Valid Person person,
                         BindingResult bindingResult) {
        personValidator.validate(person, bindingResult);
        if (bindingResult.hasErrors()) {
            return "persons/create";
        }
        personDAO.save(person);
        return "redirect:/persons";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model,
                       @PathVariable("id") int id) {
        model.addAttribute("person", personDAO.get(id));
        return "/persons/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("person") @Valid Person person,
                         BindingResult bindingResult,
                         @PathVariable("id") int id) {
        if (bindingResult.hasErrors()) {
            return "persons/edit";
        }
        personDAO.update(id, person);
        return "redirect:/persons";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        personDAO.delete(id);
        return "redirect:/persons";
    }
}
