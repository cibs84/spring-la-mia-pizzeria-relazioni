package com.corsojava.pizzeria.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.corsojava.pizzeria.models.Ingrediente;
import com.corsojava.pizzeria.models.Pizza;
import com.corsojava.pizzeria.repository.IngredienteRepository;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/ingredienti")
public class IngredienteController {

	@Autowired
	private IngredienteRepository ingredienteRepository;
	
	@GetMapping()
	public String index(Model model) {	// GET /pizze  OPPURE  /pizze?keyword=xxx
		
		List<Ingrediente> ingredienti;
		
		ingredienti = ingredienteRepository.findAll(Sort.by("name"));
		
		// List<Pizza> elencoPizze = pizzaRepository.findByNameLike("Ma%"); // ritorna tutte le pizze che iniziano con 'Ma'
		// List<Pizza> elencoPizze = pizzaRepository.findByDescriptionLike("%mozz%"); // Ritorna tutte le pizze con la mozzarella
		model.addAttribute("ingredienti", ingredienti);
		return "ingredienti/index";
	}
	
	@GetMapping("/create")
	public String create(Model model) {
		Ingrediente ingrediente = new Ingrediente();
		model.addAttribute("ingrediente", ingrediente);
		
		return "ingredienti/create";
	}
	
	@PostMapping("/store")
	public String store(
			@Valid @ModelAttribute("ingrediente") Ingrediente formIngrediente,
			BindingResult bindingResult, Model model) {
		
		if (bindingResult.hasErrors()) {
			return "/ingredienti/edit";
		}
		
		ingredienteRepository.save(formIngrediente);
		
		return "redirect:/ingredienti";
	}
	
	@GetMapping("/edit/{id}")
	public String edit(@PathVariable("id") Long id, Model model) {
		Ingrediente ingrediente = ingredienteRepository.getReferenceById(id);
		model.addAttribute("ingrediente", ingrediente);
		
		return "ingredienti/edit";
	}
	
	@PostMapping("/update/{id}")
	public String update(
//			@Valid @ModelAttribute Ingrediente formIngrediente,
//			BindingResult bindingResult,
//			Model model) {
//		
//		if (bindingResult.hasErrors()) {
//			return "ingredienti/edit";
//		}
		@ModelAttribute Ingrediente formIngrediente,
		Model model) {
		
		ingredienteRepository.save(formIngrediente);
		
		return "redirect:/ingredienti";
	}
	
	@PostMapping("/delete/{id}")
	public String delete(@PathVariable("id") Long id) {
		
		ingredienteRepository.deleteById(id);
		
		return "redirect:/ingredienti";
	}
}
