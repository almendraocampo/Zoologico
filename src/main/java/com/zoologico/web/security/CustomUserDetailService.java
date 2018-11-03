package com.zoologico.web.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.stereotype.Component;

import com.zoologico.web.DAO.UsuarioDAO;
import com.zoologico.web.entity.Usuario;

@Component
public class CustomUserDetailService implements UserDetailsService {

	@Autowired
	private UsuarioDAO usuarioDAO;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
Usuario usuario=usuarioDAO.buscarPorNombreUsuario(username);
		
		//crearemos un userbuilder para crear un usuario de spring
		
		UserBuilder user=null;
		
		if(usuario!=null) {
			//si existe lo transformo en un usuario de spring
			user= org.springframework.security.core.userdetails.User.withUsername(username);
			user.password(new BCryptPasswordEncoder().encode(usuario.getPass()));
			user.roles("USER");
		}else {
			throw new UsernameNotFoundException("Usuario No encontrado");
		}
		
		return user.build();
	}

}
