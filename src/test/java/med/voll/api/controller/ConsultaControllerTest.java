package med.voll.api.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import med.voll.api.controller.consulta.DadosDetalhamentoConsulta;
import med.voll.api.domain.consulta.AgendaConsultas;
import med.voll.api.domain.consulta.DadosAgendamentoConsulta;
import med.voll.api.domain.medico.EspecialidadeEnum;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import java.time.LocalDateTime;

import org.assertj.core.api.Assertions;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
class ConsultaControllerTest {

	@Autowired
	private MockMvc mvc;
	
	@Autowired
	private JacksonTester<DadosAgendamentoConsulta> dadosAgendamentoConsultaJson;
	
	@Autowired
	private JacksonTester<DadosDetalhamentoConsulta> dadosDetalhamentoConsultaJson;
	
	@MockBean
	private AgendaConsultas agendaConsultas;
	
	@Test
	@DisplayName("Retorna codigo 400 informacoes invalidas")
	@WithMockUser
	void testConsultas400() throws Exception {
		MockHttpServletResponse response = mvc.perform(post("/consultas"))
				.andReturn().getResponse();
		
		Assertions.assertThat(response.getStatus()).isEqualByComparingTo(HttpStatus.BAD_REQUEST.value());
	}
	
	@Test
	@DisplayName("Retorna codigo 200")
	@WithMockUser
	void testConsultas200() throws Exception {
		LocalDateTime data = LocalDateTime.now().plusHours(1);
		
		DadosDetalhamentoConsulta dadosDetalhamentoConsulta = new DadosDetalhamentoConsulta(null, 1l, 1l, data);
		
		when(agendaConsultas.agendar(any())).thenReturn(dadosDetalhamentoConsulta);
		
		MockHttpServletResponse response = mvc
				.perform(
						post("/consultas")
						.contentType(MediaType.APPLICATION_JSON)
						.content(dadosAgendamentoConsultaJson.write(
								new DadosAgendamentoConsulta(1l, 1l, data, EspecialidadeEnum.CARDIOLOGIA)
						).getJson())
				)
				.andReturn().getResponse();
		
		Assertions.assertThat(response.getStatus()).isEqualByComparingTo(HttpStatus.OK.value());
		
		String jsonRetorno = dadosDetalhamentoConsultaJson.write(dadosDetalhamentoConsulta).getJson();		
		Assertions.assertThat(response.getContentAsString()).isEqualTo(jsonRetorno);
	}	
}
