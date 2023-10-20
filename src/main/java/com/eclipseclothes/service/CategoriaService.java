package com.eclipseclothes.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eclipseclothes.model.Categoria;
import com.eclipseclothes.repository.ICategoriaRepository;

@Service
public class CategoriaService implements ICategoriaService {

	@Autowired
	private ICategoriaRepository categoriaRepository;
	
	@Override
	public Categoria crear(Categoria categoria) {
		return categoriaRepository.save(categoria);
	}

	@Override
	public Categoria actualizar(Categoria categoria) {
		return categoriaRepository.save(categoria);
	}

	@Override
	public void eliminar(Integer id) {
		categoriaRepository.deleteById(id);
	}

	@Override
	public Categoria obtener(Integer id) {
		Optional<Categoria> optional = categoriaRepository.findById(id);
		
		return (optional.isPresent()) ? optional.get() : null;
	}

	@Override
	public List<Categoria> listar() {
		return (List<Categoria>) categoriaRepository.findAll();
	}

	
	
}
