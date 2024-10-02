package com.remedios.first.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.remedios.first.remedio.DadosAtualizarRemedios;
import com.remedios.first.remedio.DadosCadastroRemedios;
import com.remedios.first.remedio.DadosDetalhamentoRemedio;
import com.remedios.first.remedio.DadosListagemRemedios;
import com.remedios.first.remedio.Remedio;
import com.remedios.first.remedio.RemedioRepository;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/remedios")
public class RemediosController {
	
	@Autowired
	private RemedioRepository repository;
	
	@PostMapping
	@Transactional
	public ResponseEntity<DadosDetalhamentoRemedio> cadastrar(@RequestBody @Valid DadosCadastroRemedios dados, UriComponentsBuilder uriBuilder) {
		var remedio = new Remedio(dados);
		
		repository.save(remedio);
		
		var uri = uriBuilder.path("/remedios/{id}").buildAndExpand(remedio.getId()).toUri();
		
		return ResponseEntity.created(uri).body(new DadosDetalhamentoRemedio(remedio));
	}
	
	@GetMapping
	public ResponseEntity<List<DadosListagemRemedios>> listar (){
		var lista = repository.findAllByAtivoTrue().stream().map(DadosListagemRemedios::new).toList();
		
		return ResponseEntity.ok(lista);
	}
	
	@PutMapping
	@Transactional
	public ResponseEntity<DadosDetalhamentoRemedio> atualizar (@RequestBody @Valid DadosAtualizarRemedios dados) {
		var remedio = repository.getReferenceById(dados.id());
		remedio.atualizarInformacoes(dados);
		
		return ResponseEntity.ok(new DadosDetalhamentoRemedio(remedio));
	}
	
	@DeleteMapping("/{id}")
	@Transactional
	public ResponseEntity<Void> excluir(@PathVariable Long id) {
		repository.deleteById(id);
		
		return ResponseEntity.noContent().build();
	}
	
	@DeleteMapping("/inativar/{id}")
	@Transactional
	public ResponseEntity<Void> inativar(@PathVariable Long id) {
		var remedio = repository.getReferenceById(id);
		remedio.inativar();
		
		return ResponseEntity.noContent().build();
	}
	
	@PutMapping("/reativar/{id}")
	@Transactional
	public ResponseEntity<Void> reativar(@PathVariable Long id) {
		var remedio = repository.getReferenceById(id);
		remedio.reativar();
		
		return ResponseEntity.noContent().build();
	}
	@GetMapping("/{id}")
	public ResponseEntity<DadosDetalhamentoRemedio> detalhar(@PathVariable Long id) {
		var remedio = repository.getReferenceById(id);
		
		return ResponseEntity.ok(new DadosDetalhamentoRemedio(remedio));
	}
}
