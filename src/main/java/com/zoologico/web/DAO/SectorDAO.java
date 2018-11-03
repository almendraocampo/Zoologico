package com.zoologico.web.DAO;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class SectorDAO {

	@PersistenceContext
	private EntityManager em;
	
	@Autowired
	private ISectorDAO crud;

	public ISectorDAO crud() {
		return crud;
	}
}
