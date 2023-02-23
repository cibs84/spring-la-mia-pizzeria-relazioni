package com.corsojava.pizzeria.controllers;

import java.util.List;
import java.util.Optional;

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
import org.springframework.web.bind.annotation.RequestParam;

import com.corsojava.pizzeria.models.Ingrediente;
import com.corsojava.pizzeria.models.Pizza;
import com.corsojava.pizzeria.repository.IngredienteRepository;
import com.corsojava.pizzeria.repository.PizzaRepository;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/pizze")
public class PizzaController {
	
	@Autowired
	private PizzaRepository pizzaRepository;

	@Autowired
	private IngredienteRepository ingredienteRepository;
	
	@GetMapping()
	public String index(
			@RequestParam(name = "keyword", required = false) String keyword, 
			Model model) {	// GET /pizze  OPPURE  /pizze?keyword=xxx
		
		List<Pizza> elencoPizze;
		
		if (keyword!=null && !keyword.isEmpty()) {
			elencoPizze = pizzaRepository.findByNameLike(keyword + "%");
		} else {
			elencoPizze = pizzaRepository.findAll(Sort.by("name"));
		}
		
		// List<Pizza> elencoPizze = pizzaRepository.findByNameLike("Ma%"); // ritorna tutte le pizze che iniziano con 'Ma'
		// List<Pizza> elencoPizze = pizzaRepository.findByDescriptionLike("%mozz%"); // Ritorna tutte le pizze con la mozzarella
		model.addAttribute("pizze", elencoPizze);
		return "pizze/index";
	}
	
	@GetMapping("/{id}")
	public String show(@PathVariable("id") Long id, Model model) {
		
		Optional<Pizza> pizza = pizzaRepository.findById(id);
		if (pizza.isEmpty()) {
			return "redirect:/error";
		}
		Pizza pizza2 = pizza.get();
		
		model.addAttribute("pizza", pizza2);
		
		return "pizze/show";
	}
	
	@GetMapping("/create")
	public String create(Model model) {
		Pizza pizza = new Pizza();
		model.addAttribute("pizza", pizza);
		
		List<Ingrediente> ingredienti = ingredienteRepository.findAll();
		model.addAttribute("ingredienti", ingredienti);
		
		pizza.setPhoto("https://rusticslice.ca/wp-content/uploads/2015/08/placeholder-pizza.jpg");
		
		return "pizze/create";
	}
	
	@PostMapping("/store")
	public String store(
			@Valid @ModelAttribute("pizza") Pizza formPizza,
			BindingResult bindingResult, Model model) {
		
		if (bindingResult.hasErrors()) {
			return "/pizze/create";
		}
		
		pizzaRepository.save(formPizza);
		
		return "redirect:/pizze";
	}
	
	@GetMapping("/edit/{id}")
	public String edit(@PathVariable("id") Long id, Model model) {
		Pizza pizza = pizzaRepository.getReferenceById(id);
		model.addAttribute("pizza", pizza);
		
		List<Ingrediente> ingredienti = ingredienteRepository.findAll();
		model.addAttribute("ingredienti", ingredienti);
		
		return "pizze/edit";
	}
	
	@PostMapping("/update/{id}")
	public String update(
			@Valid @ModelAttribute Pizza formPizza,
			BindingResult bindingResult,
			Model model) {
		
		if (bindingResult.hasErrors()) {
			return "pizze/edit";
		}
		
		pizzaRepository.save(formPizza);
		
		return "redirect:/pizze/" + formPizza.getId();
	}
	
	@PostMapping("/delete/{id}")
	public String delete(@PathVariable("id") Long id) {
		
		pizzaRepository.deleteById(id);
		
		return "redirect:/pizze";
	}
}
