package kz.project1.eLibrary.models;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;


@Entity
@Table(name="Book")
public class Book {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private int id;
	

	@Column(name="title")
	@NotEmpty(message="Name should not be empty")
	@Size(min=1,max=200,message="Name should be between 1 and 200 characters")
	private String title;
	
	@Column(name="author")
	@Size(min=3,max=149,message="Name should be between 3 and 149 characters")
	@Pattern(regexp="([A-Z]\\w+\\s[A-Z]\\w+\\s[A-Z]\\w+)|([А-Я][а-я]+\\s[А-Я][а-я]+\\s[А-Я][а-я]+)|([A-Z]\\w+\\s[A-Z]\\w+)|([А-Я][а-я]+\\s[А-Я][а-я]+)|([A-Z]\\w+)|([А-Я][а-я]+)",message = "Имя автора должно начинатся с заглавной буквой и не должен содержать ничего кроме букв")
	private String author;
	
	@Column(name="year")
	private int year;
	
	@ManyToOne
	@JoinColumn(name="person_id",referencedColumnName="id")
	private Person owner;
	
	@Column(name="book_taken_at")
	@Temporal(TemporalType.TIMESTAMP)
	private Date bookTakenAt;
	
	@Transient
	private boolean expired;
	public Book() {}
	
	public Book(String title, String author, int year) {
		this.title = title;
		this.author = author;
		this.year = year;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public Person getOwner() {
		return owner;
	}

	public void setOwner(Person owner) {
		this.owner = owner;
	}

	
	public Date getBookTakenAt() {
		return bookTakenAt;
	}

	public void setBookTakenAt(Date bookTakenAt) {
		this.bookTakenAt = bookTakenAt;
	}
	

	public boolean isExpired() {
		return expired;
	}

	public void setExpired(boolean expired) {
		this.expired = expired;
	}

	@Override
	public String toString() {
		return title + " " + author + " " + year + " " + owner.getFullName();
	} 
	
}
