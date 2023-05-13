package med.voll.api.domain.medico;

import org.assertj.core.api.Assertions;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import med.voll.api.domain.consulta.Consulta;
import med.voll.api.domain.endereco.CadastroEndereco;
import med.voll.api.domain.paciente.DadosCadastroPaciente;
import med.voll.api.domain.paciente.Paciente;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
class MedicoRepositoryTest {

	@Autowired
	private MedicoRepository medicoRepository;
	
	@Autowired
	private TestEntityManager em;
	
	@Test
	@DisplayName("Devolver null unico medico disponivel")
	void testEscolherMedicoAleatorioLivreMedicoDisponivel() {
		LocalDateTime proximaSegunda10 = LocalDateTime.now()
				.with(TemporalAdjusters.next(DayOfWeek.MONDAY))
				.withHour(10).withMinute(0);
		
		Medico medico = cadastrarMedico("medico", "medico@email", "123456", EspecialidadeEnum.CARDIOLOGIA);
		Paciente paciente = cadastrarPaciente("paciente", "paciente@email", "12345678910");
		cadastrarConsulta(medico, paciente, proximaSegunda10);
		
		Medico medicoLivre = medicoRepository.escolherMedicoAleatorioLivre(EspecialidadeEnum.CARDIOLOGIA, proximaSegunda10);
		
		Assertions.assertThat(medicoLivre).isNotNull();
	}
	
	private void cadastrarConsulta(Medico medico, Paciente paciente, LocalDateTime data) {
	    em.persist(new Consulta(null, medico, paciente, data));
	}

	private Medico cadastrarMedico(String nome, String email, String crm, EspecialidadeEnum especialidade) {
	    var medico = new Medico(dadosMedico(nome, email, crm, especialidade));
	    em.persist(medico);
	    return medico;
	}

	private Paciente cadastrarPaciente(String nome, String email, String cpf) {
	    var paciente = new Paciente(dadosPaciente(nome, email, cpf));
	    em.persist(paciente);
	    return paciente;
	}

	private CadastroMedico dadosMedico(String nome, String email, String crm, EspecialidadeEnum especialidade) {
	    return new CadastroMedico(
	            nome,
	            email,
	            "61999999999",
	            crm,
	            especialidade,
	            dadosEndereco()
	    );
	}

	private DadosCadastroPaciente dadosPaciente(String nome, String email, String cpf) {
	    return new DadosCadastroPaciente(
	            nome,
	            email,
	            "61999999999",
	            cpf,
	            dadosEndereco()
	    );
	}

	private CadastroEndereco dadosEndereco() {
	    return new CadastroEndereco(
	            "rua xpto",
	            "bairro",
	            "00000000",
	            "Brasilia",
	            "DF",
	            null,
	            null
	    );
	}	
}
