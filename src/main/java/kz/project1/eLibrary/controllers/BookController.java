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
import org.springframework.web.bind.annotation.RequestParam;

import kz.project1.eLibrary.models.Book;
import kz.project1.eLibrary.models.Person;
import kz.project1.eLibrary.services.BooksService;
import kz.project1.eLibrary.services.PeopleService;
import kz.project1.eLibrary.util.BookValidator;

@Controller
@RequestMapping("/books")
public class BookController {
	private BooksService booksService;
	private PeopleService peopleService;
	private BookValidator bookValidator;
	
	@Autowired
	public BookController(BooksService booksService,BookValidator bookValidator,PeopleService peopleService) {
		this.booksService = booksService;
		this.bookValidator = bookValidator;
		this.peopleService = peopleService;
	}
	
	@GetMapping()
	public String index(Model model,@RequestParam(value = "page",required=false) String page,@RequestParam(value = "books_per_page",required=false) String books_per_page,@RequestParam(value = "sort_by_year",required = false) String sort_by_year) {
		boolean sort_by_yearToBool = Boolean.parseBoolean(sort_by_year);
		if(page!=null&&books_per_page!=null) {
			int pageToInt = Integer.parseInt(page);
			int books_per_pageToInt = Integer.parseInt(books_per_page);
			
				if(sort_by_yearToBool==true)
					model.addAttribute("books",booksService.getPaginatedAndSortedBooksPage(pageToInt,books_per_pageToInt));
				else
					model.addAttribute("books",booksService.getPaginatedBooksPage(pageToInt,books_per_pageToInt));
		}
		else if(sort_by_yearToBool==true) {
			model.addAttribute("books",booksService.getSortedBooksPage());
		}
		else {
		model.addAttribute("books",booksService.findAll());
		}
		return "books/index";
	}
	
	@GetMapping("/{id}")
	public String show(@PathVariable("id") int id,Model model,Model model2,Model model3,@ModelAttribute("person") Person person) {
		model.addAttribute("book",booksService.findOne(id));
		model2.addAttribute("people", peopleService.findAll());
		model3.addAttribute("personWithBook",booksService.findOne(id).getOwner());
		return "books/show";
	}
	
	@PatchMapping("/{id}")
	public String giveBook(@ModelAttribute("person") Person person,@PathVariable("id") int id) {
		booksService.appointBook(person, id);
		return "redirect:/books";
	}
	
	@PostMapping("/{id}")
	public String deleteUser(@PathVariable("id") int id) {
		booksService.deleteUser(id);
		return "redirect:/books";
	}
	
	@GetMapping("/new")
	public String create(@ModelAttribute("book") Book book) {
		return "books/new";
	}
	
	@PostMapping()
	public String create(@ModelAttribute("book") @Valid Book book, BindingResult bindingResult) {
		bookValidator.validate(book, bindingResult);
		if(bindingResult.hasErrors()) {
			return "books/new";
		}
		booksService.save(book);
		return "redirect:/books";
	}
	
	
	@GetMapping("/{id}/edit")
	public String edit(Model model,@PathVariable("id") int id) {
		model.addAttribute("book", booksService.findOne(id));
		return "books/edit";
	}
	
	@PatchMapping("/{id}/edit")
	public String update(@ModelAttribute("book") @Valid Book book,BindingResult bindingResult,@PathVariable("id") int id) {
		bookValidator.validate(book, bindingResult);
		if(bindingResult.hasErrors())
			return "books/edit";
		booksService.update(id,book);
		return "redirect:/books";
	}
	
	@DeleteMapping("/{id}/edit")
	public String delete(@PathVariable("id") int id) {
		booksService.delete(id);
		return "redirect:/books";
	}
	
	@GetMapping("/search")
	public String searchPage()  {
		return "books/search";
	}
	
	@PostMapping("/search")
	public String searchBooks(Model model,@RequestParam("bookTitle") String bookTitle) {
		model.addAttribute("foundBooks",booksService.getFoundBooksBySearch(bookTitle));
		return "books/search";
	}
	
}
