package med.voll.api.domain.consulta.validacoes;

import java.time.DayOfWeek;
import java.time.LocalDateTime;

import org.springframework.stereotype.Component;

import med.voll.api.domain.consulta.DadosAgendamentoConsulta;
import med.voll.api.infra.exception.ValidacaoException;

@Component
public class ValidadorHorarioClinica implements ValidadorAgendamentoConsulta {
	
	public void validar(DadosAgendamentoConsulta dadosConsulta) {
		LocalDateTime dataConsulta = dadosConsulta.data();
		
		boolean isDomingo = dataConsulta.getDayOfWeek().equals(DayOfWeek.SUNDAY);
		boolean isAntesClinicaAberta = dataConsulta.getHour() < 7;
		boolean isDepoisClinicaFechada = dataConsulta.getHour() > 18;
		
		if (isDomingo || isAntesClinicaAberta || isDepoisClinicaFechada) {
			throw new ValidacaoException("Clinica fechada!");
		}
	}	
}
