package com.corsojava.pizzeria.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.corsojava.pizzeria.models.Offer;
import com.corsojava.pizzeria.models.Pizza;
import com.corsojava.pizzeria.repository.OfferRepository;
import com.corsojava.pizzeria.repository.PizzaRepository;

import jakarta.persistence.EntityNotFoundException;

@Controller
@RequestMapping("/offerte")
public class OfferController {
	
	@Autowired
	private PizzaRepository pizzaRepository;
	
	@Autowired
	private OfferRepository offerRepository;
	
	@GetMapping() 
	public String index(Model model) {
		List<Offer> offers = offerRepository.findAll();
		model.addAttribute("offers", offers);
		
		return "offers/index";
	}
	
	@GetMapping("/create")
	public String create(
		@RequestParam(name="pizzaId", required = true) Long pizzaId,
		Model model) throws Exception {
		
		Offer offer = new Offer();	//non esiste ancora sul DB
		
		try {
			Pizza pizza = pizzaRepository.getReferenceById(pizzaId);
			offer.setPizza(pizza);
		} catch (EntityNotFoundException e) {
			throw new Exception("Pizza not present. Id="+pizzaId);
		}
		
		model.addAttribute("offer", offer);
		
		return "offers/create";
	}
	
	@PostMapping("/store")
	public String store(
//		@Valid @ModelAttribute("offer") Offer formOffer, 
//		BindingResult bindingResult,
//		Model model){
//		
//		if (bindingResult.hasErrors()) {
//			System.out.println(bindingResult);
//			return "offers/create";
//		}
			
		@ModelAttribute("offer") Offer formOffer, 
		Model model){
			
		offerRepository.save(formOffer);
		
//		model.addAttribute("pizza", formOffer.getPizza());
		
		return "redirect:/pizze/" + formOffer.getPizza().getId(); //genera un altro get
	}
	
	@GetMapping("/edit/{id}")
	public String edit(@PathVariable("id") Long id, Model model) {
		Offer offer = offerRepository.getReferenceById(id);
		model.addAttribute("offer", offer);
		
		return "offers/edit";
	}
	
	@PostMapping("/update/{id}")
	public String update(
//			@Valid @ModelAttribute Offer formOffer,
//			BindingResult bindingResult,
//			Model model) {
//		
//		if (bindingResult.hasErrors()) {
//			return "offers/edit";
//		}
		@ModelAttribute Offer formOffer,
		Model model) {
			
		offerRepository.save(formOffer);
		
		return "redirect:/pizze/" + formOffer.getPizza().getId();
	}
	
	@PostMapping("/delete/{id}")
	public String delete(@PathVariable("id") Long id) {
		
		Long pizzaId = offerRepository.getReferenceById(id).getPizza().getId();

		offerRepository.deleteById(id);
		
		return "redirect:/pizze/" + pizzaId;
	}
}
