package med.voll.api.domain.medico;

import java.time.LocalDateTime;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface MedicoRepository extends JpaRepository<Medico, Long>{

	@Query("""
			select m from Medico m
			 where m.especialidade = :especialidade
			   and m.id not in(select c.medico.id from Consulta c
			   					where c.data = :data)
			 order by rand()
			 limit 1			 
			""")
	Medico escolherMedicoAleatorioLivre(EspecialidadeEnum especialidade, LocalDateTime data);

}
