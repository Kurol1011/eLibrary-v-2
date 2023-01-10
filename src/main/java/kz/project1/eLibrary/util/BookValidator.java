package kz.project1.eLibrary.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import kz.project1.eLibrary.models.Book;
import kz.project1.eLibrary.services.BooksService;



@Component
public class BookValidator implements Validator {
	
	private final BooksService booksService;
	
	@Autowired
	public BookValidator(BooksService booksService) {
		this.booksService = booksService;
	}

	@Override
	public boolean supports(Class<?> clazz2) {
		// TODO Auto-generated method 
		return Book.class.equals(clazz2);
	}

	@Override
	public void validate(Object target, Errors errors) {
		// TODO Auto-generated method stub
		Book book = (Book) target;
		/*if(bookDAO.show(book.getName()).isPresent()) {
			errors.rejectValue("Name", "","This name is already taken");
		}*/
	}

}

