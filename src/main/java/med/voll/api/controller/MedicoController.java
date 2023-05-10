package med.voll.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import med.voll.api.medico.AtualizaMedico;
import med.voll.api.medico.CadastroMedico;
import med.voll.api.medico.ListagemMedico;
import med.voll.api.medico.Medico;
import med.voll.api.medico.MedicoRepository;

@RestController
@RequestMapping("/medicos")
public class MedicoController {
	
	@Autowired
	private MedicoRepository repository;
	
	@PostMapping
	@Transactional
	public void cadastrar(@RequestBody @Valid CadastroMedico cadastroMedico) {		
		repository.save(new Medico(cadastroMedico));
	}
	
	@GetMapping	
	public Page<ListagemMedico> listar(Pageable paginacao) {		
		return repository.findAll(paginacao).map(ListagemMedico::new);
	}	
	
	@PutMapping
	@Transactional
	public void atualizar(@RequestBody @Valid AtualizaMedico atualizaMedico) {
		Medico medico = repository.getReferenceById(atualizaMedico.id());
		medico.atualizar(atualizaMedico);	
	}
	
	@DeleteMapping("/{id}")
	@Transactional
	public void excluir(@PathVariable Long id) {
		repository.deleteById(id);			
	}	
}
