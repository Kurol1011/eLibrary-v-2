package kz.project1.eLibrary.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import kz.project1.eLibrary.models.Book;
import kz.project1.eLibrary.models.Person;

@Repository
public interface PeopleRepository extends JpaRepository<Person,Integer> {
	
	Optional<Person> findByFullName(String fullName);
}
