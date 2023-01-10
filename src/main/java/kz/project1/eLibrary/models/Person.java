package kz.project1.eLibrary.models;


import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.annotations.Cascade;
import org.springframework.format.annotation.DateTimeFormat; 

@Entity
@Table(name="Person")
public class Person {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private int  id;
	
	@Column(name="full_name")
	@Size(min=3,max=149,message="Name should be between 3 and 149 characters")
	@Pattern(regexp="([A-Z]\\w+\\s[A-Z]\\w+\\s[A-Z]\\w+)|([А-Я][а-я]+\\s[А-Я][а-я]+\\s[А-Я][а-я]+)|([A-Z]\\w+\\s[A-Z]\\w+)|([А-Я][а-я]+\\s[А-Я][а-я]+)",message = "Имя человека должно начинатся с заглавной буквой и не должен содержать ничего кроме букв")
	private String fullName;
	
	@Column(name="date_of_birth")
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern="dd/MM/yyyy") //dd//MM//yyyy  mm-minutes MM-months
	private Date dateOfBirth;
	
	@OneToMany(mappedBy="owner",fetch=FetchType.LAZY)
	@Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
	public List<Book> Books;
	public Person() {}
	
	
	public Person(int id,String fullName,Date dateOfBirth) {
		this.id = id;
		this.fullName = fullName;
		this.dateOfBirth = dateOfBirth;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}


	public Date getDateOfBirth() {
		return dateOfBirth;
	}


	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}


	public List<Book> getBooks() {
		return Books;
	}


	public void setBooks(List<Book> books) {
		Books = books;
	}

	@Override
	public String toString() {
		return fullName + " " + dateOfBirth;
	}
	
}
