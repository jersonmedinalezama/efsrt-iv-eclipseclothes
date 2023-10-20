package com.eclipseclothes.service;

import java.util.List;

import com.eclipseclothes.model.Producto;

public interface IProductoService {

	Producto crear(Producto producto);
	
	Producto actualizar(Producto producto);
	
	void eliminar(Integer id);
	
	Producto obtener(Integer id);
	
	List<Producto> listar();
	
}
