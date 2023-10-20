package com.eclipseclothes.service;

import java.util.List;

import com.eclipseclothes.model.Categoria;

public interface ICategoriaService {

	Categoria crear(Categoria categoria);
	
	Categoria actualizar(Categoria categoria);
	
	void eliminar(Integer id);
	
	Categoria obtener(Integer id);
	
	List<Categoria> listar();
	
	
	
	
}
