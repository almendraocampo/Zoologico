package com.zoologico.web.entity;
// Generated 03-11-2018 11:42:22 by Hibernate Tools 5.2.11.Final

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Usuario generated by hbm2java
 */
@Entity
@Table(name = "usuario")
public class Usuario implements java.io.Serializable {

	private Integer codigo;
	private String user;
	private String pass;

	public Usuario() {
	}

	public Usuario(String user, String pass) {
		this.user = user;
		this.pass = pass;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)

	@Column(name = "codigo", unique = true, nullable = false)
	public Integer getCodigo() {
		return this.codigo;
	}

	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}

	@Column(name = "user", nullable = false, length = 50)
	public String getUser() {
		return this.user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	@Column(name = "pass", nullable = false, length = 50)
	public String getPass() {
		return this.pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}

}
