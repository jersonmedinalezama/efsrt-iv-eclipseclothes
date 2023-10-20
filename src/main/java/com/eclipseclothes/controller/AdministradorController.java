package com.eclipseclothes.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.eclipseclothes.model.Categoria;
import com.eclipseclothes.model.Producto;
import com.eclipseclothes.model.Usuario;
import com.eclipseclothes.service.ICategoriaService;
import com.eclipseclothes.service.IProductoService;
import com.eclipseclothes.service.ISubirArchivo;

@Controller
@RequestMapping("/administrador")
public class AdministradorController {
	
	@Autowired
	private ICategoriaService categoriaService;
	
	@Autowired
	private IProductoService productoService;
	
	@Autowired
	private ISubirArchivo subirArchivo;
	
	@GetMapping("")
	public String home() {
		
		return "administrador/home";
	}
	
	//---- CATEGORIAS
	
	@GetMapping("/categorias")
	public String categorias(Model model) {
		model.addAttribute("categoria", new Categoria());
		model.addAttribute("lstCategorias", categoriaService.listar());
		
		return "categorias/lista";
	}
	
	@PostMapping("/categoria/crear")
	public String crearCategoria(Categoria categoria, Model model) {
		
		System.out.println(categoria);
		
		categoriaService.crear(categoria);
		
		model.addAttribute("categoria", new Categoria());
		model.addAttribute("lstCategorias", categoriaService.listar());
		model.addAttribute("mensaje", "Guardado con éxito.");
		
		return "categorias/lista";
	}
	
	@GetMapping("/categoria/editar/{id}")
	public String editarCategoria(@PathVariable int id, Model model) {
		
		model.addAttribute("categoria", categoriaService.obtener(id));
		model.addAttribute("lstCategorias", categoriaService.listar());
		model.addAttribute("editar", true);
		
		return "categorias/lista";
	}
	
	@PostMapping("/categoria/actualizar")
	public String actualizarCategoria(Categoria categoria, Model model) {
		
		System.out.println(categoria);
		
		categoriaService.actualizar(categoria);
		
		model.addAttribute("categoria", new Categoria());
		model.addAttribute("lstCategorias", categoriaService.listar());
		model.addAttribute("mensaje", "Actualizado con éxito.");
		
		return "categorias/lista";
	}
	
	@GetMapping("/categoria/eliminar/{id}")
	public String eliminarCategoria(@PathVariable int id, Model model) {
		
		categoriaService.eliminar(id);
		
		model.addAttribute("categoria", new Categoria());
		model.addAttribute("lstCategorias", categoriaService.listar());
		model.addAttribute("mensaje", "Eliminado con éxito.");
		
		return "categorias/lista";
	}
	

	//---- PRODUCTOS
	@GetMapping("/productos")
	public String productos(Model model) {
		model.addAttribute("lstProductos", productoService.listar());
		
		return "productos/lista";
	}
	
	@GetMapping("/producto/crear")
	public String crearProducto(Model model) {
		model.addAttribute("producto", new Producto());
		model.addAttribute("lstCategorias", categoriaService.listar());
		
		return "productos/crear";
	}
	
	@PostMapping("/producto/crear")
	public String crearProducto(Producto producto, @RequestParam("file") MultipartFile file,  Model model) {
		
		//TODO ("usuario statico, mejorar buscando el usuario actual de la session") 
		Usuario usuario = new Usuario();
		usuario.setId(1);
		
		String imagen = (!file.isEmpty()) ? subirArchivo.subir(file) : "default.png";
		
		producto.setFechaRegistro(new Date());
		producto.setImagen(imagen);
		producto.setUsuario(usuario);
		
		productoService.crear(producto);
		
		model.addAttribute("producto", new Producto());
		model.addAttribute("mensaje", "Producto guardado con éxito.");
		
		return "productos/crear";
	}
	
	@GetMapping("/producto/editar/{id}")
	public String editarProducto(@PathVariable int id, Model model) {
		
		model.addAttribute("producto", productoService.obtener(id));
		model.addAttribute("lstCategorias", categoriaService.listar());
		
		return "productos/editar";
	}
	
	@PostMapping("/producto/actualizar")
	public String actualizarProducto(Producto producto, @RequestParam("file") MultipartFile file,  Model model) {
		
		Producto p = productoService.obtener(producto.getId());
		
		if(file.isEmpty()) {
			producto.setImagen(p.getImagen());
		} else {
			
			if(!p.getImagen().equals("default.png")) {
				subirArchivo.eliminar(p.getImagen());
			}
			
			String imagen = subirArchivo.subir(file);
			producto.setImagen(imagen);
			
		}
		
		producto.setFechaRegistro(p.getFechaRegistro());
		producto.setUsuario(p.getUsuario());
		
		productoService.actualizar(producto);
		
		model.addAttribute("producto", new Producto());
		model.addAttribute("lstCategorias", categoriaService.listar());
		model.addAttribute("mensaje", "Actualización con éxito.");
		
		return "productos/editar";
	}
	
	@GetMapping("/producto/eliminar/{id}")
	public String eliminarProducto(@PathVariable int id, Model model) {
		
		Producto p = productoService.obtener(id);
		
		if(!p.getImagen().equals("default.png")) {
			subirArchivo.eliminar(p.getImagen());
		}
		
		productoService.eliminar(id);
		
		return "redirect:/administrador/productos";
	}
	
}
