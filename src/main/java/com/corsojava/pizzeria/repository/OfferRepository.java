package com.corsojava.pizzeria.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.corsojava.pizzeria.models.Offer;

public interface OfferRepository extends JpaRepository<Offer, Long> {
	
}
