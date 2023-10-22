package com.eclipseclothes.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.eclipseclothes.model.Producto;
import com.eclipseclothes.service.ICategoriaService;
import com.eclipseclothes.service.IProductoService;

@Controller
@RequestMapping("/")
public class HomeController {
	
	@Autowired
	private IProductoService productoService;
	
	@Autowired
	private ICategoriaService categoriaService;

	@GetMapping("")
	public String home(Model model) {

		model.addAttribute("lstProductos", productoService.listar());
		
		return "home/index";
	}
	
	@GetMapping("/register")
	public String register() {
		return "home/register";
	}
	
	@GetMapping("/login")
	public String login() {
		return "home/login";
	}
	
	@GetMapping("/categorias")
	public String categorias(Model model, @RequestParam(name = "nombre", defaultValue = "Camisas") String nombre) {
		
		String categoria = (nombre != null) ? nombre : "Camisas";
		
		
		model.addAttribute("lstCategorias", categoriaService.listar());
		
		List<Producto> lista = productoService.listar()
								.stream()
								.filter(p -> p.getCategoria().getNombre().equals(categoria))
								.toList();
		
		model.addAttribute("lstProductos", lista);
		model.addAttribute("currentCategoria", categoria);
		
		return "home/categorias";
	}
	
	@PostMapping("/buscar")
	public String buscarProducto(@RequestParam(name = "nombre", defaultValue = "Todos") String nombre, Model model) {
		
		if(nombre.equals("Todos")) {
			model.addAttribute("lstProductos", productoService.listar());
		
		} else {
			
			List<Producto> lista = productoService.listar() 
									.stream()
									.filter(p -> p.getNombre().toLowerCase().contains(nombre.toLowerCase()))
									.toList();
			
			model.addAttribute("lstProductos", lista);
		}
		
		return "home/index";
	}
	
	
}
