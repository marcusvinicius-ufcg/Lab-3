import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import models.Evento;
import models.Local;
import models.ParticipanteEstrategia;
import models.Tema;
import models.exceptions.EventoInvalidoException;

import org.junit.Before;
import org.junit.Test;

public class EventoTest {
	
	private List<Tema> temas;
	private Local local;
	private ParticipanteEstrategia estrategia;
	@Before
	public void setUp(){
		temas = new ArrayList<Tema>();
		local = new Local("Centro de Convenção", "Av. Canal", 20);
		estrategia= new ParticipanteEstrategia();
	}
	
	@Test
	public void deveCriarUmEvento() {
		temas.add(Tema.ARDUINO);
		try {
			new Evento("Python na cabeça", "Vamos programar em Python!", new Date(), temas, "celia_rua@mail.com",local, estrategia );
		} catch (EventoInvalidoException e) {
			fail();
		}
	}
	
	@Test
	public void deveDarException() {
		try {
			new Evento(null,
					"Vamos programar em Python!", new Date(), temas, "celia_rua@mail.com",local, estrategia);
			fail();
		} catch (EventoInvalidoException e) {
			assertEquals("Parametro nulo", e.getMessage());
		}
		try {
			new Evento("Python na cabeça",
					null, new Date(), temas, "celia_rua@mail.com",local, estrategia);
			fail();
		} catch (EventoInvalidoException e) {
			assertEquals("Parametro nulo", e.getMessage());
		}
		try {
			new Evento("Python na cabeça",
					"Vamos programar em Python!", null, temas, "celia_rua@mail.com",local, estrategia);
			fail();
		} catch (EventoInvalidoException e) {
			assertEquals("Parametro nulo", e.getMessage());
		}
		try {
			new Evento("Python na cabeça",
					"Vamos programar em Python!", new Date(), null, "celia_rua@mail.com",local, estrategia);
			fail();
		} catch (EventoInvalidoException e) {
			assertEquals("Parametro nulo", e.getMessage());
		}
		try {
			new Evento("Python na cabeça",
					"Vamos programar em Python!", new Date(), new ArrayList<Tema>(), "celia_rua@mail.com",local, estrategia);
			fail();
		} catch (EventoInvalidoException e) {
			assertEquals("Nenhum tema", e.getMessage());
		}
		try {
			String descricaoLonga = "Vamos programar em Python!";
			
			for (int i = 0; i < 5; i++) {
				descricaoLonga += descricaoLonga;
			}
			
			new Evento("Python na cabeça",
					descricaoLonga, new Date(), temas, "celia_rua@mail.com",local, estrategia);
			fail();
		} catch (EventoInvalidoException e) {
			assertEquals("Descrição longa", e.getMessage());
		}
		try {
			new Evento("Python na cabeça na mente e no coração uhuuu",
					"Vamos programar em Python!", new Date(), null, "celia_rua@mail.com",local, estrategia);
			fail();
		} catch (EventoInvalidoException e) {
			assertEquals("Título longo", e.getMessage());
		}
		try {
			Calendar calendar = Calendar.getInstance();
			calendar.add(Calendar.DAY_OF_WEEK, -1);

			new Evento("Python na cabeça",
					"Vamos programar em Python!", calendar.getTime(), temas, "celia_rua@mail.com",local, estrategia);
			fail();
		} catch (EventoInvalidoException e) {
			assertEquals("Data inválida", e.getMessage());
		}
	}
}
