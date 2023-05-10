package med.voll.api.medico;

import jakarta.validation.constraints.NotNull;
import med.voll.api.endereco.CadastroEndereco;

public record AtualizaMedico(
		@NotNull
		Long id, 
		String nome,				
		String telefone,
		CadastroEndereco endereco) {

}
