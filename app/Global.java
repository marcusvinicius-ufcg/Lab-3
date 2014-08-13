import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import models.Evento;
import models.Local;
import models.Participante;
import models.ParticipanteEstrategia;
import models.ParticipanteMaisExperiente;
import models.Tema;
import models.User;
import models.dao.GenericDAO;
import models.dao.GenericDAOImpl;
import models.exceptions.EventoInvalidoException;
import models.exceptions.PessoaInvalidaException;
import play.Application;
import play.GlobalSettings;
import play.db.jpa.JPA;
import play.db.jpa.Transactional;

/**
 * Classe Global Instancia Objetos e Salva no Banco de Dados
 * @author Marcus Vinicius
 *
 */
public class Global extends GlobalSettings {

	private GenericDAO dao = new GenericDAOImpl();
	private static final int CAPACIDADE_10 = 10;
	private static final int CAPACIDADE_12 = 12;
	private static final int CAPACIDADE_20 = 20;
	
	private static final int SABADO = 7;
	private static final int TERCA = 3;
	private static final int DOMINGO = 1;
	private static final int DEZEMBRO = 12;
	private static final int FEVEREIRO = 2;
	private static final int DIA = 17;
	private static final int QUINTA = 5;
	private static final int TOTAL_PARTICIPANTES_DEFAULT = 12;
	
	@Override
	public void onStart(Application arg0) {

		JPA.withTransaction(new play.libs.F.Callback0() {

			@Override
			public void invoke() throws Throwable {
				criarEventosFakes();
			}
		});
	}

	@Override
	public void onStop(Application arg0) {

	}

	@Transactional
	private void criarEventosFakes() throws EventoInvalidoException, PessoaInvalidaException {
		
		criarParticipantesFakes();
		List<Participante> participantes = dao.findAllByClassName("Participante");
		
		if(participantes.size() > TOTAL_PARTICIPANTES_DEFAULT){
			return;
		}
		criarUsers();
		
		ParticipanteEstrategia ordem = new ParticipanteEstrategia();
		ParticipanteMaisExperiente experiente = new ParticipanteMaisExperiente();
		persist(ordem);
		persist(experiente);

		Calendar calendar;

		List<Tema> temas1 = new ArrayList<Tema>();
		temas1.add(Tema.DESAFIOS);
		temas1.add(Tema.PROGRAMACAO);

		calendar = Calendar.getInstance();
		calendar.add(Calendar.DAY_OF_WEEK, SABADO);

		Local local1 = new Local("Centro de Convenção", "Av. Canal", CAPACIDADE_20);
		persist(local1);
		Evento evento1 = new Evento(
				"Python na mente e coração",
				"Neste evento iremos debater e propor soluções para novas releases.",
				calendar.getTime(), temas1, "celia@mail.com", local1,
				experiente);
		evento1.setParticipantes(participantes);
		persist(evento1);

		List<Tema> temas2 = new ArrayList<Tema>();
		temas2.add(Tema.ARDUINO);
		temas2.add(Tema.ELETRONICA);

		calendar = Calendar.getInstance();
		calendar.add(Calendar.DAY_OF_WEEK, TERCA);

		Evento evento2 = new Evento(
				"Luta de robôs",
				"Traga seu robô feito em arduino e traga para competir com outros.",
				calendar.getTime(), temas2, "celia@mail.com", local1,
				experiente);
		evento2.setParticipantes(participantes);
		persist(evento2);

		List<Tema> temas3 = new ArrayList<Tema>();
		temas3.add(Tema.DESAFIOS);
		temas3.add(Tema.PROGRAMACAO);

		calendar = Calendar.getInstance();
		calendar.add(Calendar.MONTH, DOMINGO);

		Local local2 = new Local("Auditorio", "Facisa", CAPACIDADE_10);
		persist(local2);

		Evento evento3 = new Evento(
				"IV Olímpiadas de programação da UFCG",
				"Traga sua equipe e venha competir nessa maratona de programação.",
				calendar.getTime(), temas3, "celia@mail.com", local2,
				experiente);
		evento3.setParticipantes(participantes);
		persist(evento3);

		List<Tema> temas4 = new ArrayList<Tema>();
		temas4.add(Tema.DESAFIOS);
		temas4.add(Tema.PROGRAMACAO);

		calendar = Calendar.getInstance();
		calendar.add(Calendar.DAY_OF_WEEK, DEZEMBRO);

		Local local3 = new Local("Hotel JK", "Proximo ao Shopping", CAPACIDADE_12);
		persist(local3);

		Evento evento4 = new Evento(
				"II Encontro para programadores de Python",
				"O encontro contará com a participação de um de seus fundadores, inúmeras palestras e maratonas. Não percam!!",
				calendar.getTime(), temas4, "alberto@mail.com", local3,
				ordem);
		evento4.setParticipantes(participantes);
		persist(evento4);

		List<Tema> temas5 = new ArrayList<Tema>();
		temas5.add(Tema.PROGRAMACAO);
		temas5.add(Tema.DESAFIOS);

		calendar = Calendar.getInstance();
		calendar.add(Calendar.MONTH, FEVEREIRO);
		calendar.add(Calendar.DAY_OF_WEEK, TERCA);

		Evento evento5 = new Evento(
				"III Semana da Computação Verde",
				"Exiba sua proposta para uma computação mais verde e concorra a diversos prêmios",
				calendar.getTime(), temas5, "alberto@mail.com", local3,
				ordem);
		evento5.setParticipantes(participantes);
		persist(evento5);

		List<Tema> temas6 = new ArrayList<Tema>();
		temas6.add(Tema.PROGRAMACAO);
		temas6.add(Tema.WEB);

		calendar = Calendar.getInstance();
		calendar.add(Calendar.DAY_OF_WEEK, DIA);

		Local local4 = new Local("Auditorio Jose Faria Nobrega",
				"Universidade Federal de Campina Grande", CAPACIDADE_12);
		persist(local4);

		Evento evento6 = new Evento(
				"Web em foco",
				"Este evento contará com a participação de um dos fundadores da Web, e juntos iremos compartilhar diversas dicas e boas práticas nessa vasta área.",
				calendar.getTime(), temas6, "linares@mail.com",
				local4, ordem);
		evento6.setParticipantes(participantes);
		persist(evento6);

		List<Tema> temas7 = new ArrayList<Tema>();
		temas7.add(Tema.ELETRONICA);
		temas7.add(Tema.ARDUINO);

		calendar = Calendar.getInstance();
		calendar.add(Calendar.DAY_OF_WEEK, QUINTA);

		Evento evento7 = new Evento(
				"Minicurso Arduino",
				"Evento destinado a alunos de LOAC, caso sobre vagas iremos disponibilizar em breve",
				calendar.getTime(), temas7, "palhares@mail.com",
				local4, ordem);
		evento7.setParticipantes(participantes);
		persist(evento7);
		criarParticipantesFakes();
	}

	@Transactional
	private void criarParticipantesFakes() throws PessoaInvalidaException {
		persist(new Participante("Alberto Leca", 		"alberto@mail.com"));
		persist(new Participante("Belmifer Linares",	"linares@mail.com"));
		persist(new Participante("Celia Rua", 			"celia@mail.com"));
		persist(new Participante("Tairine Reis", 		"tairine@mail.com"));
		persist(new Participante("Erico Albuquerque",	"erico@mail.com"));
		persist(new Participante("Sonia Gabeira", 		"sonia@mail.com"));
		persist(new Participante("Rosa Varejao", 		"rosa@mail.com"));
		persist(new Participante("Paula Lousado", 		"paula@mail.com"));
		persist(new Participante("Quiterio Galindo",	"quiterio@mail.com"));
		persist(new Participante("Deolindo Castello",	"deolindo@mail.com"));
		persist(new Participante("Doroteia Pasos", 		"doroteia@mail.com"));
		persist(new Participante("Eugenio Palhares",	"palhares@mail.com"));
	}

	@Transactional
	private void criarUsers(){
		List<Participante> participantes = dao.findAllByClassName("Participante");
		for(Participante participante : participantes){
			User user = new User();
			user.setName(participante.getNome());
			user.setEmail(participante.getEmail());
			user.setPassword("fest");
			persist(user);
		}
	}

	@Transactional
	private <T> boolean persist(Object object) {
		List<T> result = dao.findAllByClassName(object.getClass().getSimpleName());
		if (!result.contains(object)) {
			dao.persist(object);
			dao.flush();
			return true;
		} else {
			return false;
		}
	}
}
