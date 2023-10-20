package com.eclipseclothes.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eclipseclothes.model.Producto;
import com.eclipseclothes.repository.IProductoRepository;

@Service
public class ProductoService implements IProductoService {

	@Autowired
	private IProductoRepository productoRepository;
	
	@Override
	public Producto crear(Producto producto) {
		return productoRepository.save(producto);
	}

	@Override
	public Producto actualizar(Producto producto) {
		return productoRepository.save(producto);
	}

	@Override
	public void eliminar(Integer id) {
		productoRepository.deleteById(id);
		
	}

	@Override
	public Producto obtener(Integer id) {
		Optional<Producto> optional = productoRepository.findById(id);
		return (optional.isPresent()) ? optional.get() : null;
	}

	@Override
	public List<Producto> listar() {
		return (List<Producto>) productoRepository.findAll();
	}

	
	
}
