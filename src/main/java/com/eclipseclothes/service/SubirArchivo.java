package com.eclipseclothes.service;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import jakarta.annotation.PostConstruct;

@Service
public class SubirArchivo implements ISubirArchivo {
	
	@Value("${media.location}")
	private String pathMedia;
	
	private Path pathRaiz;

	@Override
	@PostConstruct
	public void init() {
		
		pathRaiz = Paths.get(pathMedia);
		
		if(!Files.exists(pathRaiz)) {
			try {
				Files.createDirectory(pathRaiz);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		
	}

	@Override
	public String subir(MultipartFile file) {
		
		try {
			
			if(file.isEmpty()) {
				throw new Exception();
			}
			
			String nombreArchivo = file.getOriginalFilename();
			Path destino = pathRaiz.resolve(Paths.get(nombreArchivo))
							.normalize().toAbsolutePath();
			
			try(InputStream inputStream = file.getInputStream()) {
				Files.copy(inputStream,destino, StandardCopyOption.REPLACE_EXISTING);
			}
			
			return nombreArchivo;
			
		} catch (Exception e) {
			
		}
		
		return "default.png";
	}

	@Override
	public void eliminar(String fileName) {
		
		File file = new File(pathMedia + File.separatorChar + fileName);
		
		System.out.println(file.getAbsolutePath());
		
		file.delete();
		
	}
	
	

}
