package com.eclipseclothes.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eclipseclothes.model.Usuario;
import com.eclipseclothes.repository.IUsuarioRepository;

@Service
public class UsuarioService implements IUsuarioService {

	@Autowired
	private IUsuarioRepository usuarioRepository;
	
	@Override
	public Usuario obtener(Integer id) {
		Optional<Usuario> optional = usuarioRepository.findById(id);
		
		return (optional.isPresent()) ? optional.get() : null;
	}

}
