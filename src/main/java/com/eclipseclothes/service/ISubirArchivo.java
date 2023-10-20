package com.eclipseclothes.service;


import org.springframework.web.multipart.MultipartFile;

public interface ISubirArchivo {

	void init();
	
	String subir(MultipartFile file);
	
	void eliminar(String fileName);
}
