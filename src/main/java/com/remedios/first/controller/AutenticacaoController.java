package com.remedios.first.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.remedios.first.infra.DadosTokenJWT;
import com.remedios.first.infra.TokenService;
import com.remedios.first.usuarios.DadosAutenticacao;
import com.remedios.first.usuarios.Usuario;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/login")
public class AutenticacaoController {

	@Autowired
	private AuthenticationManager manager;
	
	@Autowired
	private TokenService tokenService;

	@PostMapping
	public ResponseEntity<?> fazerLogin (@RequestBody @Valid DadosAutenticacao dados) {

		var token = new UsernamePasswordAuthenticationToken(dados.login(), dados.senha());
		var autenticacao = manager.authenticate(token);

		var tokenJWT = tokenService.gerarToken((Usuario) autenticacao.getPrincipal());
		
		return ResponseEntity.ok(new DadosTokenJWT (tokenJWT));
	}
}
