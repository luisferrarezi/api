package med.voll.api.domain.consulta;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import med.voll.api.domain.medico.MedicoRepository;
import med.voll.api.controller.consulta.DadosDetalhamentoConsulta;
import med.voll.api.domain.consulta.validacoes.ValidadorAgendamentoConsulta;
import med.voll.api.domain.medico.Medico;
import med.voll.api.domain.paciente.PacienteRepository;
import med.voll.api.infra.exception.ValidacaoException;
import med.voll.api.domain.paciente.Paciente;

@Service
public class AgendaConsultas {

	@Autowired
	private ConsultaRepository consultaRepository;
	
	@Autowired
	private MedicoRepository medicoRepository;
	
	@Autowired
	private PacienteRepository pacienteRepository;
	
	@Autowired
	private List<ValidadorAgendamentoConsulta> validadores;
	
	public DadosDetalhamentoConsulta agendar(DadosAgendamentoConsulta dados) {
		if (!pacienteRepository.existsById(dados.idPaciente())) {
			throw new ValidacaoException("Paciente informado não existe!");
		}
		
		if (dados.idMedico() != null && !medicoRepository.existsById(dados.idMedico())) {
			throw new ValidacaoException("Médico informado não existe!");
		}
		
		validadores.forEach(validador -> validador.validar(dados));
		
		Paciente paciente = pacienteRepository.getReferenceById(dados.idPaciente());
		Medico medico = escolherMedico(dados);
		Consulta consulta = new Consulta(null, medico, paciente, dados.data());
		
		consultaRepository.save(consulta);
		
		return new DadosDetalhamentoConsulta(consulta);
	}

	private Medico escolherMedico(DadosAgendamentoConsulta dados) {
		if (dados.idMedico() != null) {
			return medicoRepository.getReferenceById(dados.idMedico());
		}		
		
		if (dados.especialidade() == null) {
			throw new ValidacaoException("Especialidade não informada!");
		}		
		
		return medicoRepository.escolherMedicoAleatorioLivre(dados.especialidade(), dados.data());
	}
}
