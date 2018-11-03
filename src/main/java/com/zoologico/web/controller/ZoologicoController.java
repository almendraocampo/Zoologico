package com.zoologico.web.controller;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.zoologico.web.entity.Animal;
import com.zoologico.web.DAO.AnimalDAO;
import com.zoologico.web.DAO.SectorDAO;
import com.zoologico.web.DAO.TipoDAO;
import com.zoologico.web.DAO.UsuarioDAO;
import com.zoologico.web.entity.Sector;
import com.zoologico.web.entity.Tipo;
import com.zoologico.web.entity.Usuario;

@Controller
public class ZoologicoController {

	@Autowired
	private AnimalDAO aDAO;

	@Autowired
	private TipoDAO tDAO;

	@Autowired
	private SectorDAO sDAO;
	
	@Autowired
	private UsuarioDAO uDAO;
	
	@GetMapping("/listar")
	public String listar(Model model) {

		model.addAttribute("animales", aDAO.crud().findAll());
		return "listar.html";
	}


	@GetMapping("/crear")
	public String crear(Model model) {

		model.addAttribute("tipos", tDAO.crud().findAll());
		model.addAttribute("sectores", sDAO.crud().findAll());

		return "agregar.html";
	}

	@PostMapping("/almacenar")
	public String almacenar(Model model, RedirectAttributes ra, 
			@RequestParam("txtNombre") String nombre,
			@RequestParam("txtPeso") float peso, 
			@RequestParam("cboTipo") int tipoCodigo,
			@RequestParam("txtGenero") String genero,
			@RequestParam("txtFechaNacimiento") @DateTimeFormat(pattern = "yyyy-MM-dd") Date fechaNacimiento,
			@RequestParam("cboSector") int sectorCodigo,
			@RequestParam("txtFechaIngreso") @DateTimeFormat(pattern = "yyyy-MM-dd") Date fechaIngreso,
			@RequestParam("txtFechaDefuncion") @DateTimeFormat(pattern = "yyyy-MM-dd") Date fechaDefuncion) {

		Tipo tipo = new Tipo();
		tipo.setCodigo(tipoCodigo);
		Sector sector = new Sector();
		sector.setCodigo(sectorCodigo);

		Animal animal = new Animal();
		animal.setNombre(nombre);
		animal.setPeso(peso);
		animal.setTipo(tipo);
		animal.setGenero(genero);
		animal.setFechaNacimiento(fechaNacimiento);
		animal.setSector(sector);
		animal.setFechaIngreso(fechaIngreso);
		animal.setFechaDefuncion(fechaDefuncion);


		// guardamos el animal y comprobamos que se haya
		// insertado correctamente
		Animal animalAgregado = aDAO.crud().save(animal);
		String mensaje = "Error al agregar";
		if (animalAgregado != null) {
			mensaje = "Guardado correctamente";
		}

		ra.addFlashAttribute("mensaje", mensaje);

		return "redirect:crear";
	}

	@GetMapping("/eliminar")
	public String eliminar(Model model,
			RedirectAttributes ra,
			@RequestParam("codigo") int codigo) {
		
		String mensaje = "";
		
		try {
			//eliminamos al animal
			aDAO.crud().deleteById(codigo);
			mensaje = "Eliminado correctamente";
		} catch(Exception ex) {
			mensaje = "No se ha podido eliminar";
		}
		
		ra.addFlashAttribute("mensaje", mensaje);
		
		return "redirect:listar";
	}
	
	
	@GetMapping("/modificar")
	public String modificar(Model model,
			RedirectAttributes ra,
			@RequestParam("codigo") int codigo) {
		
		//buscamos al animal
		Animal animal = null;
		
		try {
			
			animal = aDAO.crud().findById(codigo).get();
			
		} catch (Exception e) {
			
			//si el animal no existe en la BBDD
			//lo redirigimos de vuelta con un mensaje de error
			ra.addFlashAttribute("mensaje", "El animal no existe");
			return "redirect:listar";
		}
		
		//si encuentra el animal lo enviamos a la vista
		model.addAttribute("animal", animal);
		
		//mandamos las razas y familias para llenar los combobox
		model.addAttribute("tipos", tDAO.crud().findAll());
		model.addAttribute("sectores", sDAO.crud().findAll());
		
		return "modificar.html";
	}
	
	
	@PostMapping("/actualizar")
	public String actualizar(Model model,
			RedirectAttributes ra,
			@RequestParam("txtId") int codigo,
			@RequestParam("txtNombre") String nombre,
			@RequestParam("txtPeso") float peso, 
			@RequestParam("cboTipo") int tipoCodigo,
			@RequestParam("txtGenero") String genero,
			@RequestParam("txtFechaNacimiento") @DateTimeFormat(pattern = "yyyy-MM-dd") Date fechaNacimiento,
			@RequestParam("cboSector") int sectorCodigo,
			@RequestParam("txtFechaIngreso") @DateTimeFormat(pattern = "yyyy-MM-dd") Date fechaIngreso,
			@RequestParam("txtFechaDefuncion") @DateTimeFormat(pattern = "yyyy-MM-dd") Date fechaDefuncion) {
			
		Tipo tipo = new Tipo();
		tipo.setCodigo(tipoCodigo);
		Sector sector = new Sector();
		sector.setCodigo(sectorCodigo);

		Animal animal = new Animal();
		animal.setCodigo(codigo);
		animal.setNombre(nombre);
		animal.setPeso(peso);
		animal.setTipo(tipo);
		animal.setGenero(genero);
		animal.setFechaNacimiento(fechaNacimiento);
		animal.setSector(sector);
		animal.setFechaIngreso(fechaIngreso);
		animal.setFechaDefuncion(fechaDefuncion);
		
		//guardamos el animal y comprobamos que se haya
		//insertado correctamente
		Animal animalAgregado = aDAO.crud().save(animal);
		String mensaje = "Error al modificar";
		if(animalAgregado!= null) {
			mensaje = "Modificado correctamente";
		}
		
		ra.addFlashAttribute("mensaje", mensaje);
		
		return "redirect:listar";
	}
	
	@GetMapping("/listarSector")
	public String listarSector(Model model) {

		model.addAttribute("sectores", sDAO.crud().findAll());
		return "listarSector.html";
	}

	@GetMapping("/crearSector")
	public String crearSector(Model model) {

		//model.addAttribute("tipos", tDAO.crud().findAll());
		//model.addAttribute("sectores", sDAO.crud().findAll());

		return "agregarSector.html";
	}

	@PostMapping("/almacenarSector")
	public String almacenarSector(Model model, RedirectAttributes ra, 
			@RequestParam("txtNombre") String nombre,
			@RequestParam("txtDescripcion") String descripcion) {



		Sector sector = new Sector();
		sector.setNombre(nombre);
		sector.setDescripcion(descripcion);

		Sector sectorAgregado = sDAO.crud().save(sector);
		String mensaje = "Error al agregar";
		if (sectorAgregado != null) {
			mensaje = "Guardado correctamente";
		}

		ra.addFlashAttribute("mensaje", mensaje);

		return "redirect:crearSector";
	}

	@GetMapping("/eliminarSector")
	public String eliminarSector(Model model,
			RedirectAttributes ra,
			@RequestParam("codigo") int codigo) {
		
		String mensaje = "";
		
		try {
			//eliminamos al animal
			sDAO.crud().deleteById(codigo);
			mensaje = "Eliminado correctamente";
		} catch(Exception ex) {
			mensaje = "No se ha podido eliminar";
		}
		
		ra.addFlashAttribute("mensaje", mensaje);
		
		return "redirect:listarSector";
	}
	
	
	@GetMapping("/modificarSector")
	public String modificarSector(Model model,
			RedirectAttributes ra,
			@RequestParam("codigo") int codigo) {

		Sector sector = null;
		
		try {
			
			sector = sDAO.crud().findById(codigo).get();
			
		} catch (Exception e) {
			

			ra.addFlashAttribute("mensaje", "El sector no existe");
			return "redirect:sector";
		}
		

		model.addAttribute("sector", sector);		
		return "modificarSector.html";
	}
	
	
	@PostMapping("/actualizarSector")
	public String actualizarSector(Model model,
			RedirectAttributes ra,
			@RequestParam("txtId") int codigo,
			@RequestParam("txtNombre") String nombre,
			@RequestParam("txtDescripcion") String descripcion) {
			
		Sector sector = new Sector();
		sector.setCodigo(codigo);
		sector.setNombre(nombre);
		sector.setDescripcion(descripcion);

		Sector sectorAgregado = sDAO.crud().save(sector);
		String mensaje = "Error al modificar";
		if(sectorAgregado!= null) {
			mensaje = "Modificado correctamente";
		}
		
		ra.addFlashAttribute("mensaje", mensaje);
		
		return "redirect:listarSector";
	}
	
	@GetMapping("/listarTipo")
	public String listarTipo(Model model) {

		model.addAttribute("tipos", tDAO.crud().findAll());
		return "listarTipo.html";
	}

	@GetMapping("/crearTipo")
	public String crearTipo(Model model) {

		return "agregarTipo.html";
	}
	
	@PostMapping("/almacenarTipo")
	public String almacenarTipo(Model model, RedirectAttributes ra, @RequestParam("txtNombre") String nombre,
			@RequestParam("txtDescripcion") String descripcion) {

		Tipo tipo = new Tipo();
		tipo.setNombre(nombre);
		tipo.setDescripcion(descripcion);

		Tipo tipoAgregado = tDAO.crud().save(tipo);
		String mensaje = "Error al agregar";
		if (tipoAgregado != null) {
			mensaje = "Guardado correctamente";
		}

		ra.addFlashAttribute("mensaje", mensaje);

		return "redirect:crearTipo";
	}
	
	@GetMapping("/eliminarTipo")
	public String eliminarTipo(Model model, RedirectAttributes ra, @RequestParam("codigo") int codigo) {

		String mensaje = "";

		try {
			tDAO.crud().deleteById(codigo);
			mensaje = "Eliminado correctamente";
		} catch (Exception ex) {
			mensaje = "No se ha podido eliminar";
		}

		ra.addFlashAttribute("mensaje", mensaje);

		return "redirect:listarTipo";
	}

	@GetMapping("/modificarTipo")
	public String modificarTipo(Model model, RedirectAttributes ra, @RequestParam("codigo") int codigo) {

		Tipo tipo = null;

		try {

			tipo = tDAO.crud().findById(codigo).get();

		} catch (Exception e) {

			ra.addFlashAttribute("mensaje", "El tipo no existe");
			return "redirect:tipo";
		}

		model.addAttribute("tipo", tipo);
		return "modificarTipo.html";
	}

	@PostMapping("/actualizarTipo")
	public String actualizarTipo(Model model, RedirectAttributes ra, @RequestParam("txtId") int codigo,
			@RequestParam("txtNombre") String nombre, @RequestParam("txtDescripcion") String descripcion) {

		Tipo tipo = new Tipo();
		tipo.setCodigo(codigo);
		tipo.setNombre(nombre);
		tipo.setDescripcion(descripcion);

		Tipo tipoAgregado = tDAO.crud().save(tipo);
		String mensaje = "Error al modificar";
		if (tipoAgregado != null) {
			mensaje = "Modificado correctamente";
		}

		ra.addFlashAttribute("mensaje", mensaje);

		return "redirect:listarTipo";
	}
	
	@GetMapping("/listarUsuario")
	public String listarUsuario(Model model) {

		model.addAttribute("usuarios", uDAO.crud().findAll());
		return "listarUsuario.html";
	}

	@GetMapping("/crearUsuario")
	public String crearUsuario(Model model) {

		return "agregarUsuario.html";
	}

	@PostMapping("/almacenarUsuario")
	public String almacenarUsuario(Model model, RedirectAttributes ra, @RequestParam("txtUser") String user,
			@RequestParam("txtPass") String pass) {

		Usuario usuario = new Usuario();
		usuario.setUser(user);
		usuario.setPass(pass);

		Usuario usuarioAgregado = uDAO.crud().save(usuario);
		String mensaje = "Error al agregar";
		if (usuarioAgregado != null) {
			mensaje = "Guardado correctamente";
		}

		ra.addFlashAttribute("mensaje", mensaje);

		return "redirect:crearUsuario";
	}

	@GetMapping("/eliminarUsuario")
	public String eliminarUsuario(Model model, RedirectAttributes ra, @RequestParam("codigo") int codigo) {

		String mensaje = "";

		try {
			uDAO.crud().deleteById(codigo);
			mensaje = "Eliminado correctamente";
		} catch (Exception ex) {
			mensaje = "No se ha podido eliminar";
		}

		ra.addFlashAttribute("mensaje", mensaje);

		return "redirect:listarUsuario";
	}

	@GetMapping("/modificarUsuario")
	public String modificarUsuario(Model model, RedirectAttributes ra, @RequestParam("codigo") int codigo) {

		Usuario usuario = null;

		try {

			usuario = uDAO.crud().findById(codigo).get();

		} catch (Exception e) {

			ra.addFlashAttribute("mensaje", "El usuario no existe");
			return "redirect:usuario";
		}

		model.addAttribute("usuario", usuario);
		return "modificarUsuario.html";
	}

	@PostMapping("/actualizarUsuario")
	public String actualizarUsuario(Model model, RedirectAttributes ra, @RequestParam("txtId") int codigo,
			@RequestParam("txtUser") String user, @RequestParam("txtPass") String pass) {

		Usuario usuario = new Usuario();
		usuario.setCodigo(codigo);
		usuario.setUser(user);
		usuario.setPass(pass);

		Usuario usuarioAgregado = uDAO.crud().save(usuario);
		String mensaje = "Error al modificar";
		if (usuarioAgregado != null) {
			mensaje = "Modificado correctamente";
		}

		ra.addFlashAttribute("mensaje", mensaje);

		return "redirect:listarUsuario";
	}
	
	@GetMapping("/tipoAnimal")
	public String tipoAnimal(Model model) {
		
		model.addAttribute("tipos", tDAO.crud().findAll());
		return "listarTipoAnimal.html";
	}

	@PostMapping("/tipoAnimal")
	public String tipoAnimal(Model model,
			RedirectAttributes ra,		
			@RequestParam("cboTipo") int codigoTipo) {
		model.addAttribute("tipos", tDAO.crud().findAll());
		model.addAttribute("animales", aDAO.tipoAnimal(codigoTipo));

		return "listarTipoAnimal.html";
	}
	
	@GetMapping("/fechaIngresoAnimal")
	public String fechaIngresoAnimal(Model model) {
				
		return "listarFechaIngreso.html";
	}
	
	@PostMapping("/fechaIngresoAnimal")
	public String fechaIngresoAnimal(Model model,
			RedirectAttributes ra,		
			@RequestParam("txtFechaIni") @DateTimeFormat(pattern = "yyyy-MM-dd") Date fechaIni,
			@RequestParam("txtFechaFin") @DateTimeFormat(pattern = "yyyy-MM-dd")  Date fechaFin) {
		

		model.addAttribute("animales", aDAO.fechaIngresoAnimal(fechaIni, fechaFin));

		return "listarFechaIngreso.html";
	}
	

}
