package kz.project1.eLibrary.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kz.project1.eLibrary.models.Book;
import kz.project1.eLibrary.models.Person;
import kz.project1.eLibrary.repositories.PeopleRepository;

@Service
@Transactional(readOnly=true)
public class PeopleService {
	private final PeopleRepository peopleRepository;

	@Autowired
	public PeopleService(PeopleRepository peopleRepository) {
		this.peopleRepository = peopleRepository;
	}
	
	public List<Person> findAll(){
		return peopleRepository.findAll();
	}
	
	public Person findOne(int id) {
		Optional <Person> foundPerson = peopleRepository.findById(id);
		return foundPerson.orElse(null);
	}
	
	@Transactional
	public void save(Person person) {
		peopleRepository.save(person);
	}
	
	@Transactional
	public void update(int id,Person updatedPerson) {
		updatedPerson.setId(id);
		peopleRepository.save(updatedPerson);
	}
	
	@Transactional
	public void delete(int id) {
		peopleRepository.deleteById(id);
	}
	
	@Transactional
	public Optional<Person> checkUniqeFullName(String fullName){ //mb error
		
		return peopleRepository.findByFullName(fullName);
	} 
	
	public void Test() {
		System.out.println("testing with debug, inside hibernate Transaction");
	}
	
	@Transactional
	public List<Book> personBooks(int id){
		Optional<Person> person = peopleRepository.findById(id);
		if(person.isPresent()) {
			Hibernate.initialize(person.get().getBooks());
			
			person.get().getBooks().forEach(book-> {
				long diffInMillies = Math.abs(book.getBookTakenAt().getTime() - new Date().getTime());
				if(diffInMillies > 60000) // Time for expired for book
					book.setExpired(true);
			}
			
					);
			
			return person.get().getBooks();
		}
		return new ArrayList<>();
	}
}
	

