package med.voll.api.domain.consulta.validacoes;

import java.time.Duration;
import java.time.LocalDateTime;

import org.springframework.stereotype.Component;

import med.voll.api.domain.consulta.DadosAgendamentoConsulta;
import med.voll.api.infra.exception.ValidacaoException;

@Component
public class ValidarHorarioAntecedencia implements ValidadorAgendamentoConsulta {
	
	public void validar(DadosAgendamentoConsulta dadosConsulta) {
		LocalDateTime dataConsulta = dadosConsulta.data();
		LocalDateTime dataAtual = LocalDateTime.now();
		long diferencaMinutos = Duration.between(dataAtual, dataConsulta).toMinutes();		
		
		if (diferencaMinutos < 30) {
			throw new ValidacaoException("Necessário agendar com 30 minutos de antecedência!");
		}
	}
}
