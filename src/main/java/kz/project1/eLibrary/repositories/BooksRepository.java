package kz.project1.eLibrary.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import kz.project1.eLibrary.models.Book;

@Repository
public interface BooksRepository extends JpaRepository<Book,Integer> {
	List<Book> findByTitleContaining(String bookTitle);
}
