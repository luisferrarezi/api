package med.voll.api.domain.medico;

import jakarta.validation.constraints.NotNull;
import med.voll.api.domain.endereco.CadastroEndereco;

public record AtualizaMedico(
		@NotNull
		Long id, 
		String nome,				
		String telefone,
		CadastroEndereco endereco) {

}
