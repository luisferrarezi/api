package med.voll.api.domain.medico;

import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import med.voll.api.domain.endereco.Endereco;

@Table(name = "medicos")
@Entity(name = "Medico")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Medico {
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String nome;
	private String email;
	private String telefone;
	private String crm;
	
	@Enumerated(EnumType.STRING)
	private EspecialidadeEnum especialidade;
	
	@Embedded
	private Endereco endereco;
	
	public Medico(CadastroMedico medico) {
		this.nome = medico.nome();
		this.email = medico.email();
		this.telefone = medico.telefone();
		this.crm = medico.crm();
		this.especialidade = medico.especialidade();
		this.endereco = new Endereco(medico.endereco());
	}

	public void atualizar(@Valid AtualizaMedico atualizaMedico) {
	   if (atualizaMedico.nome() != null) {
           this.nome = atualizaMedico.nome();
       }
       if (atualizaMedico.telefone() != null) {
           this.telefone = atualizaMedico.telefone();
       }
       if (atualizaMedico.endereco() != null) {
    	   this.endereco.atualizarEndereco(atualizaMedico.endereco());
       }		
	}
}
