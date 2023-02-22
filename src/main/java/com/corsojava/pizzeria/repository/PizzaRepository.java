package com.corsojava.pizzeria.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.corsojava.pizzeria.models.Pizza;

public interface PizzaRepository extends JpaRepository<Pizza, Long> {
	// Custom Query
	public List<Pizza> findByNameLike(String keyword);
	public List<Pizza> findByDescriptionLike(String keyword);
}
