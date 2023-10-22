package com.eclipseclothes.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.eclipseclothes.model.Usuario;

@Repository
public interface IUsuarioRepository extends CrudRepository<Usuario, Integer>{

}
