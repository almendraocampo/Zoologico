package com.zoologico.web.DAO;

import org.springframework.data.repository.CrudRepository;

import com.zoologico.web.entity.Animal;

public interface IAnimalDAO extends CrudRepository<Animal, Integer> {

}
