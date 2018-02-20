package com.notas.core.service;

import org.springframework.stereotype.Service;

import com.mysql.jdbc.log.Log;
import com.mysql.jdbc.log.LogFactory;
import com.notas.core.converter.Convertidor;
import com.notas.core.entity.Nota;
import com.notas.core.model.MNota;
import com.notas.core.repository.NotaRepositorio;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Pageable;

@Service("servicio")
public class NotaService 
{
	@Autowired
	@Qualifier("repositorio")
	private NotaRepositorio repositorio;
	
	@Autowired
	@Qualifier("convertidor")
	private Convertidor convertidor;
	
	private final Logger logger = LoggerFactory.getLogger(NotaService.class);
	
	public boolean crear(Nota nota)
	{
		try{
			repositorio.save(nota);
			logger.info("Nota creada");
			return true;
		}catch(Exception e){
			logger.error("HUBO UN ERROR");
			return false;
		}
	}
	
	public boolean actualizar(Nota nota)
	{
		try{
			repositorio.save(nota);
			return true;
		}catch(Exception e){
			return false;
		}
	}
	
	public boolean borrar(String nombre, long id)
	{
		try{
			Nota nota = repositorio.findByNombreAndId(nombre, id);
			repositorio.delete(nota);
			return true;
		}catch(Exception e){
			return false;
		}
	}
	
	public List<MNota> obtener()
	{
		return convertidor.convertirLista(repositorio.findAll());
	}
	
	public MNota ObtenerPorNombreTitulo(String nombre, String titulo)
	{
		return new MNota(repositorio.findByNombreAndTitulo(nombre, titulo));
	}
	
	public List<MNota> obtenerTitulo(String titulo)
	{
		return convertidor.convertirLista(repositorio.findByTitulo(titulo));
	}
	
	public List<MNota> obtenerPorPaginacion(Pageable pageable)
	{
		return convertidor.convertirLista(repositorio.findAll(pageable).getContent());
	}
}
