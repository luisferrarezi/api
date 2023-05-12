package med.voll.api.domain.medico;

public record ListagemMedico(Long id, String nome, String email, String crm, EspecialidadeEnum especialidade) {
	public ListagemMedico(Medico medico) {
		this(medico.getId(), medico.getNome(), medico.getEmail(), medico.getCrm(), medico.getEspecialidade());
	}
}
