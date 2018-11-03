package com.zoologico.web.DAO;


import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.zoologico.web.entity.Animal;

@Repository
@Transactional
public class AnimalDAO {

	@PersistenceContext
	private EntityManager em;

	@Autowired
	private IAnimalDAO crud;

	public IAnimalDAO crud() {
		return crud;
	}

	public List<Animal> tipoAnimal(int codigoTipo) {
		String hql = "from Animal where tipo.codigo =:codigoTipo";
		return (List<Animal>) em.createQuery(hql, Animal.class).setParameter("codigoTipo", codigoTipo).getResultList();

	}

	public List<Animal> fechaIngresoAnimal(Date fechaIni, Date fechaFin) {
		String hql = "SELECT a FROM Animal a WHERE a.fechaIngreso >=:fechaIni and a.fechaIngreso<=:fechaFin";
		return (List<Animal>) em.createQuery(hql, Animal.class).setParameter("fechaIni", fechaIni)
				.setParameter("fechaFin", fechaFin).getResultList();

	}
}
