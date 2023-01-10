package kz.project1.eLibrary.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import kz.project1.eLibrary.models.Person;
import kz.project1.eLibrary.services.PeopleService;



@Component
public class PersonValidator implements Validator {
	
	private final PeopleService peopleService;
	
	@Autowired
	public PersonValidator(PeopleService peopleService) {
		this.peopleService = peopleService;
	}

	@Override
	public boolean supports(Class<?> clazz) {
		// TODO Auto-generated method 
		return Person.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		// TODO Auto-generated method stub
		Person person = (Person) target;
		if(peopleService.checkUniqeFullName(person.getFullName()).isPresent()) { //mb error
			errors.rejectValue("fullName", "","Это имя уже занято!");
		}
	}

}
