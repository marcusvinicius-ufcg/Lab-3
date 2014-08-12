import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import models.Participante;
import models.exceptions.PessoaInvalidaException;

import org.junit.Test;

public class ParticipanteTest {

	@Test
	public void deveCriarUmParticipante() {
		try {
			new Participante("João José da Silva", "joao_jose@mail.com");
		} catch (PessoaInvalidaException e) {
			fail();
		}
	}

	@Test
	public void deveOcorrerException() {
		try {
			new Participante(
					"João José da Silva Maria da Penha do Ultimo Socorro Pereira Lima Roberto",
					"joao_jose@mail.com");
			fail();
		} catch (PessoaInvalidaException e) {
			assertEquals("Nome longo", e.getMessage());
		}
		try {
			new Participante(
					"João José da Silva",
					"joao_jose_da_silva_maria_da_penha_do_ultimo_socorro_pereira_lima@mail.com");
			fail();
		} catch (PessoaInvalidaException e) {
			assertEquals("Email longo", e.getMessage());
		}
		try {
			new Participante(null, "joao_jose@mail.com");
			fail();
		} catch (PessoaInvalidaException e) {
			assertEquals("Parametro nulo", e.getMessage());
		}
		try {
			new Participante("João José da Silva", null);
			fail();
		} catch (PessoaInvalidaException e) {
			assertEquals("Parametro nulo", e.getMessage());
		}
		try {
			new Participante("João José da Silva", null);
			fail();
		} catch (PessoaInvalidaException e) {
			assertEquals("Parametro nulo", e.getMessage());
		}
		try {
			new Participante("João José da Silva", "joao_jose_mail.com");
			fail();
		} catch (PessoaInvalidaException e) {
			assertEquals("Email inválido", e.getMessage());
		}
	}
}
