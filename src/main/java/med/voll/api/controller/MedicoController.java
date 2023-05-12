package med.voll.api.controller;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import med.voll.api.domain.medico.AtualizaMedico;
import med.voll.api.domain.medico.CadastroMedico;
import med.voll.api.domain.medico.DadosDetalheMedico;
import med.voll.api.domain.medico.ListagemMedico;
import med.voll.api.domain.medico.Medico;
import med.voll.api.domain.medico.MedicoRepository;

@RestController
@RequestMapping("/medicos")
public class MedicoController {
	
	@Autowired
	private MedicoRepository repository;
	
	@PostMapping
	@Transactional
	public ResponseEntity<?> cadastrar(@RequestBody @Valid CadastroMedico cadastroMedico, UriComponentsBuilder uriBuilder) {		
		Medico medico = repository.save(new Medico(cadastroMedico));
	
		URI uri = uriBuilder.path("/medicos/{id}").buildAndExpand(medico.getId()).toUri();
		
		return ResponseEntity.created(uri).body(new DadosDetalheMedico(medico));
	}
	
	@GetMapping	
	public ResponseEntity<Page<ListagemMedico>> listar(Pageable paginacao) {		
		Page<ListagemMedico> page = repository.findAll(paginacao).map(ListagemMedico::new);
		
		return ResponseEntity.ok(page);
	}	
	
	@PutMapping
	@Transactional
	public ResponseEntity<?> atualizar(@RequestBody @Valid AtualizaMedico atualizaMedico) {
		Medico medico = repository.getReferenceById(atualizaMedico.id());
		medico.atualizar(atualizaMedico);	
		
		return ResponseEntity.ok(new DadosDetalheMedico(medico));
	}
	
	@DeleteMapping("/{id}")
	@Transactional
	public ResponseEntity<?> excluir(@PathVariable Long id) {
		repository.deleteById(id);		
		
		return ResponseEntity.noContent().build();
	}	
	
	@GetMapping("/{id}")	
	public ResponseEntity<?> detalhar(@PathVariable Long id) {
		Medico medico = repository.getReferenceById(id);			
		
		return ResponseEntity.ok(new DadosDetalheMedico(medico));
	}		
}
