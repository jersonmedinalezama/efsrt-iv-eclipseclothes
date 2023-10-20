package com.eclipseclothes.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.eclipseclothes.model.Producto;

@Repository
public interface IProductoRepository extends CrudRepository<Producto, Integer>{

	
}
