package kz.project1.eLibrary.services;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kz.project1.eLibrary.models.Book;
import kz.project1.eLibrary.models.Person;
import kz.project1.eLibrary.repositories.BooksRepository;


@Service
@Transactional(readOnly=true)
public class BooksService {
	
	private final BooksRepository booksRepository;

	@Autowired
	public BooksService(BooksRepository booksRepository) {
		this.booksRepository = booksRepository;
	}
	
	public List<Book> findAll(){
		return booksRepository.findAll();
	}
	
	public Book findOne(int id) {
		Optional <Book> foundBook = booksRepository.findById(id); // OPTIONAL???
		
		return foundBook.orElse(null);
	}
	
	@Transactional
	public void save(Book book) {
		booksRepository.save(book);
	}
	
	@Transactional
	public void update(int id,Book updatedBook) {
		Book bookToBeUpdated = booksRepository.findById(id).get();
		updatedBook.setId(id); // Po drugomy na view
		updatedBook.setOwner(bookToBeUpdated.getOwner());
		booksRepository.save(updatedBook);
	}

	@Transactional
	public void delete(int id) {
		booksRepository.deleteById(id);
	}
	
	public void test() {
		System.out.println("testing with debug,inside hibernite Transaction");
	}
	
	@Transactional
	public void appointBook(Person person,int book_id) {
		booksRepository.findById(book_id).get().setBookTakenAt(new Date());
		booksRepository.findById(book_id).get().setOwner(person);
	}
	
	@Transactional
	public void deleteUser(int book_id) {
		booksRepository.findById(book_id).get().setBookTakenAt(null);
		booksRepository.findById(book_id).get().setOwner(null);
	}
	
	@Transactional
	public List<Book> getPaginatedBooksPage(int page,int books_per_page){
		return booksRepository.findAll(PageRequest.of(page,books_per_page)).getContent();
	}
	
	@Transactional
	public List<Book> getPaginatedAndSortedBooksPage(int page,int books_per_page){
		return booksRepository.findAll(PageRequest.of(page, books_per_page,Sort.by("year"))).getContent();
	}
	
	@Transactional
	public List<Book> getSortedBooksPage(){
		return booksRepository.findAll(Sort.by("year"));
	}
	
	@Transactional
	public List<Book> getFoundBooksBySearch(String bookTitle){
		return booksRepository.findByTitleContaining(bookTitle);
	}
	
	@Transactional 
	public void checkBookExpiration(Book book) {
		
	}
}
