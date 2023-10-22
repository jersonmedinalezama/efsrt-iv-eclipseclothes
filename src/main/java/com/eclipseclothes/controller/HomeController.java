package com.eclipseclothes.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.eclipseclothes.model.DetalleOrden;
import com.eclipseclothes.model.Orden;
import com.eclipseclothes.model.Producto;
import com.eclipseclothes.model.Usuario;
import com.eclipseclothes.service.ICategoriaService;
import com.eclipseclothes.service.IProductoService;
import com.eclipseclothes.service.IUsuarioService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/")
public class HomeController {
	
	@Autowired
	private IProductoService productoService;
	
	@Autowired
	private ICategoriaService categoriaService;
	
	@Autowired
	private IUsuarioService usuarioService;
	
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
	
	@GetMapping("/detalles/{id}")
	public String detallesProducto(@PathVariable int id, Model model) {
		
		model.addAttribute("producto", productoService.obtener(id));
		
		return "home/detalles";
	}
	
	
	@PostMapping("/carrito/agregar")
	public String agregarCarrito(int id, int cantidad, Model model, HttpSession session) {
		
		Orden orden;
		List<DetalleOrden> detalles;
		
		if(session.getAttribute("orden") == null) {
			orden = new Orden();
			detalles = new ArrayList<>();
		} else {
			orden = (Orden) session.getAttribute("orden");
			detalles = orden.getDetalles();
		}
		
		Producto p = productoService.obtener(id);

		DetalleOrden detalle = new DetalleOrden();

		detalle.setCantidad(cantidad);
		detalle.setPrecio(p.getPrecio());
		detalle.setProducto(p);
		detalle.setSubtotal(cantidad * p.getPrecio());
		
		detalles.add(detalle);
		
		double total = detalles.stream().mapToDouble(dt -> dt.getSubtotal()).sum();
		
		orden.setDetalles(detalles);
		
		session.setAttribute("orden", orden);
		
		model.addAttribute("orden", orden);
		model.addAttribute("total", total);
		
		return "home/orden";
	}
	
	@GetMapping("/carrito/eliminar/{id}")
	public String eliminarProductoCarrito(@PathVariable int id, Model model, HttpSession session) {
		
		Orden orden = (Orden) session.getAttribute("orden");
		
		List<DetalleOrden> nuevaLista = new ArrayList<>();
		
		for(DetalleOrden dt : orden.getDetalles()) {
			if(dt.getProducto().getId() != id) {
				nuevaLista.add(dt);
			}
		}
		
		orden.setDetalles(nuevaLista);
		
		session.setAttribute("orden", orden);
		
		return "redirect:/carrito";
	}
	
	@GetMapping("/carrito")
	public String carrito(Model model, HttpSession session) {
		
		Orden orden = (Orden) session.getAttribute("orden");
		
		double total = (orden != null) 
						? orden.getDetalles().stream().mapToDouble(dt -> dt.getSubtotal()).sum()
						: 0.0;
		
		model.addAttribute("orden", orden);
		model.addAttribute("total", total);
		
		return "home/orden";
	}
	
	@GetMapping("/orden") 
	public String orden(Model model, HttpSession session) {
		
		Usuario usuario = usuarioService.obtener(1);
		Orden orden = (Orden) session.getAttribute("orden");
		
		double total = orden.getDetalles().stream().mapToDouble(dt -> dt.getSubtotal()).sum();
		
		model.addAttribute("orden", orden);
		model.addAttribute("total", total);
		model.addAttribute("usuario", usuario);
		
		return "home/resumenorden";
	}
	
}
