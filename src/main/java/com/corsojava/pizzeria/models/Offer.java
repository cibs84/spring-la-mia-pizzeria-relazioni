package com.corsojava.pizzeria.models;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class Offer {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
@Override
	public String toString() {
		return "Offer [id=" + id + ", title=" + title + ", startDate=" + startDate + ", endDate=" + endDate + ", pizza="
				+ pizza + ", getId()=" + getId() + ", getTitle()=" + getTitle() + ", getStartDate()=" + getStartDate()
				+ ", getEndDate()=" + getEndDate() + ", getPizza()=" + getPizza() + ", getClass()=" + getClass()
				+ ", hashCode()=" + hashCode() + ", toString()=" + super.toString() + "]";
	}

	//	@NotNull(message = "Deve essere inserito il titolo dell'offerta")
	private String title;
	
//	@NotNull(message = "Deve essere inserita una data d'inizio offerta")
	private LocalDate startDate;
	
//	@NotNull(message = "Deve essere inserita una data d'inizio offerta")
	private LocalDate endDate;
	
//	@NotNull
	@ManyToOne
	private Pizza pizza;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public LocalDate getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}

	public LocalDate getEndDate() {
		return endDate;
	}

	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}

	public Pizza getPizza() {
		return pizza;
	}

	public void setPizza(Pizza pizza) {
		this.pizza = pizza;
	}
}
