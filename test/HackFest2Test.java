import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

import models.Evento;
import models.Local;
import models.Participante;
import models.ParticipanteMaisExperiente;
import models.Tema;
import models.dao.GenericDAO;
import models.dao.GenericDAOImpl;

import org.junit.Before;
import org.junit.Test;


public class HackFest2Test {

	private Evento evento;
	private Local local;
	private GenericDAO dao;

	@Before
	public void setUp() throws Exception {
		try {
			dao = new GenericDAOImpl();
			List<Tema> temas = new ArrayList<Tema>();
			temas.add(Tema.DESAFIOS);
			temas.add(Tema.PROGRAMACAO);
			local = new Local("Hotel JK", "Centro", 2);
			evento = new Evento("Java", "JUnit", new GregorianCalendar(2014, 9,
					02).getTime(), temas, "teste@teste.com", local,
					new ParticipanteMaisExperiente());
		} catch (Exception e) {
			fail();
		}
	}

	@Test
	public void devePermitirAdiconarParticipanteAlemDaCapacidade() {
		try {
			Participante p1 = new Participante("João José da Silva", "joao_jose@mail.com");
			assertTrue(evento.addParticipante(p1));
			//Nao deve permitir cadastrar participantes iguais
			assertFalse(evento.addParticipante(p1));
			
			Participante p2 = new Participante("Quiterio Galindo", "quiterio_galindo@mail.com");
			
			assertTrue(evento.addParticipante(p2));
			assertEquals((Integer)2, evento.getTotalDeParticipantes());
			
			Participante p3 = new Participante("Deolindo Castello", "deolindo_castello@mail.com");
			//Deve Permitir Adiconar Participantes Alem Da Capacidade
			assertTrue(evento.addParticipante(p3));
		} catch (Exception e) {
			fail();
		}

	}
	
	@Test
	public void devePermitirCriarEventoParaParticipanteExperientes() {
		try {
			
			dao.persist(new Participante("João José da Silva", 	"joao_jose@mail.com"));
			dao.persist(new Participante("Quiterio Galindo", 		"quiterio_galindo@mail.com"));
			dao.persist(new Participante("Deolindo Castello","deolindo_castello@mail.com"));
			dao.flush();
			
			Participante p1 = (Participante) dao.findAllByClassName("Participante").get(0);
			Participante p2 = (Participante) dao.findAllByClassName("Participante").get(1);
			Participante p3 = (Participante) dao.findAllByClassName("Participante").get(2);
			
			List<Tema> temas = new ArrayList<Tema>();
			temas.add(Tema.DESAFIOS);
			temas.add(Tema.PROGRAMACAO);
			
			evento = new Evento("Java", "JUnit", new GregorianCalendar(2014, 9,
					02).getTime(), temas, "joao_jose@mail.com", local,
					new ParticipanteMaisExperiente());			
			evento.setParticipantes(dao.findAllByClassName("Participante"));
			dao.persist(evento);
						
			Evento evento2 = new Evento("Java", "JUnit", new GregorianCalendar(2014, 9,
					02).getTime(), temas, "teste@teste.com", local,
					new ParticipanteMaisExperiente());
			evento2.addParticipante(p2);
			
			dao.persist(evento2);
			dao.flush();
			
			//Participante p1 tem um evento criado
			//Participante p2 nao tem evento criado mas foi aceito em dois eventos
			//Participante p3 nao tem evento criado mas foi aceito em 1 evento
			
			//Evento tem que aceita os participante 
			assertTrue(evento.getParticipantesAceitos().contains(p1));
			//Participante p2 esta empatado com p3 quanto a quantidade
			//de eventos criados mas o Participante p2 foi aceito em dois eventos
			assertTrue(evento.getParticipantesAceitos().contains(p2));
			//Nao deve aceitar Participante p3
			assertFalse(evento.getParticipantesAceitos().contains(p3));
			
		} catch (Exception e) {
			
		}
	}
}
