package com.eclipseclothes.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.eclipseclothes.model.Categoria;

@Repository
public interface ICategoriaRepository extends CrudRepository<Categoria, Integer> {

}
