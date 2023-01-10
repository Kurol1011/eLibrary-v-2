package kz.project1.eLibrary.controllers;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import kz.project1.eLibrary.models.Person;
import kz.project1.eLibrary.services.PeopleService;
import kz.project1.eLibrary.util.PersonValidator;
@Controller
@RequestMapping("/people")
public class PeopleController {
	private PeopleService peopleService;
	private final PersonValidator personValidator;
	@Autowired
	public PeopleController(PeopleService peopleService , PersonValidator personValidator) {
		this.peopleService = peopleService;
		this.personValidator = personValidator;
	}
	@GetMapping()
	public String index(Model model) {
		model.addAttribute("people",peopleService.findAll());
		return "people/index";
	}
	
	@GetMapping("/{id}")
	public String findOne(@PathVariable("id") int id,Model model,Model model2) {
		model.addAttribute("person",peopleService.findOne(id));
		model2.addAttribute("personBooks", peopleService.personBooks(id));
		return "people/show";
	}
	@GetMapping("/new")
	public String create(@ModelAttribute("person") Person person) {
		return "people/new";
	}
	
	@PostMapping()
	public String create(@ModelAttribute("person") @Valid Person person, BindingResult bindingResult) {
		personValidator.validate(person, bindingResult);
		if(bindingResult.hasErrors()) {
			return "people/new";
		}
		peopleService.save(person);
		return "redirect:/people";
	}
	
	
	@GetMapping("/{id}/edit")
	public String edit(Model model,@PathVariable("id") int id) {
		model.addAttribute("person", peopleService.findOne(id));
		return "people/edit";
	}
	
	@PatchMapping("/{id}/edit")
	public String update(@ModelAttribute("person") @Valid Person person,BindingResult bindingResult,@PathVariable("id") int id) {
		personValidator.validate(person, bindingResult);
		if(bindingResult.hasErrors())
			return "people/edit";
		peopleService.update(id,person);
		return "redirect:/people";
	}
	
	@DeleteMapping("/{id}/edit")
	public String delete(@PathVariable("id") int id) {
		peopleService.delete(id);
		return "redirect:/people";
	}
	
}
