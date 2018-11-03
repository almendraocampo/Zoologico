package com.zoologico.web.DAO;

import org.springframework.data.repository.CrudRepository;

import com.zoologico.web.entity.Usuario;

public interface IUsuarioDAO extends CrudRepository<Usuario, Integer>{

}
