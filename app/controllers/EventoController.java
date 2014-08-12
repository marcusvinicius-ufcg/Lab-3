package controllers;

import static play.data.Form.form;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import models.Evento;
import models.EventoComparator;
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
import play.data.Form;
import play.db.jpa.Transactional;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.*;

import com.fasterxml.jackson.databind.ObjectMapper;

import controllers.Application.Login;

public class EventoController extends Controller {

	private final static Form<Evento> EVENTO_FORM = form(Evento.class);
	private final static Form<Participante> PARTICIPANTE_FORM = form(Participante.class);
	private final static Form<Local> LOCAL_FORM = form(Local.class);

	private static GenericDAO dao = new GenericDAOImpl();

	@Transactional
	public static Result eventosPorTema(int id) throws PessoaInvalidaException,EventoInvalidoException {
		List<Evento> todosEventos = dao.findAllByClassName("Evento");
		List<Evento> eventosRequeridos = new ArrayList<Evento>();

		for (Evento ev : todosEventos) {
			if (ev.getTemas().contains(Tema.values()[(int) id])) {
				eventosRequeridos.add(ev);
			}
		}
		Collections.sort(eventosRequeridos, new EventoComparator());
		ObjectMapper mapper = new ObjectMapper();
		String json = "";
		try {
			json = mapper.writeValueAsString(eventosRequeridos);
		} catch (Exception e) {
			return badRequest();
		}
		return ok(json);
	}

	@Transactional
	public static Result novo() throws PessoaInvalidaException,EventoInvalidoException {
		Form<Evento> eventoFormRequest = EVENTO_FORM.bindFromRequest();
		if (EVENTO_FORM.hasErrors()) {
			return badRequest();
		} else {
			Evento novoEvento = eventoFormRequest.get();
			
			Long idLocal = Long.parseLong(form().bindFromRequest().get("locale"));
			Long idStartegia = Long.parseLong(form().bindFromRequest().get("estrategia"));
			Local local = dao.findByEntityId(Local.class, idLocal);
			ParticipanteEstrategia strategia = dao.findByEntityId(ParticipanteEstrategia.class, idStartegia);
			novoEvento.setLocal(local);
			novoEvento.setStrategia(strategia);
			dao.persist(novoEvento);
			dao.flush();
			return redirect(controllers.routes.Application.index());
		}
	}

	@Transactional
	public static Result participar(long id) throws PessoaInvalidaException,
			EventoInvalidoException {

		Form<Participante> participanteFormRequest = PARTICIPANTE_FORM.bindFromRequest();

		if (PARTICIPANTE_FORM.hasErrors()) {
			return badRequest();
		} else {
			Evento evento = dao.findByEntityId(Evento.class, id);
			Participante novoParticipante = participanteFormRequest.get();
			evento.addParticipante(novoParticipante);

			dao.persist(novoParticipante);
			dao.merge(novoParticipante);
			dao.flush();
			return redirect(controllers.routes.Application.index());
		}
	}

	@Transactional
	public static List<Local> getLocais() {
		return dao.findAllByClassName("Local");
	}
	
	@Transactional
	public static List<Evento> getEventos(String email) {
		return dao.findByAttributeName("Evento", "administrador", email);
	}

	@Transactional
	public static Result abrirCadastroDeLocal() {
		
		if (session("email") == null)
			return ok(login.render(Form.form(Login.class)));

		User user = getUser(session("email"));
		
		return ok(cadastroLocal.render(LOCAL_FORM, user));
	}

	@Transactional
	private static User getUser(String email) {
		List<User> result = dao.findByAttributeName("User", "email", email);
		return result.size() == 0 ? null : result.get(0);
	}
	
	@Transactional
	public static Result cadastrarLocal() {

		Form<Local> localFormRequest = LOCAL_FORM.bindFromRequest();

		if (session("email") == null)
			return ok(login.render(form(Login.class)));

		User user = getUser(session("email"));
		
		if (localFormRequest.hasErrors()) {
			return badRequest(cadastroLocal.render(localFormRequest, user));
		} else {
			Local local = localFormRequest.get();
			
			if (dao.findAllByClassName("Local").contains(local)) {
				flash("success", "Local ja cadastrado");
				return badRequest(cadastroLocal.render(localFormRequest, user));
			} else {
				dao.persist(local);
				dao.flush();
			}
			return redirect(routes.Application.index());
		}
	}
	@Transactional
	public static List<ParticipanteEstrategia> getStrategias(){
		List<ParticipanteEstrategia> estrategias = dao.findAllByClassName("ParticipanteEstrategia");
		
		if(!estrategias.contains(new ParticipanteEstrategia())){
			dao.persist(new ParticipanteEstrategia());
		}
		if(!estrategias.contains(new ParticipanteMaisExperiente())){
			dao.persist(new ParticipanteMaisExperiente());
		}
		dao.flush();
		return dao.findAllByClassName("ParticipanteEstrategia");
	}
	@Transactional
	public static Result listaInscritos(String email){
		Long idEvento = Long.parseLong(form().bindFromRequest().get("select-evento"));
		Evento evento = dao.findByEntityId(Evento.class, idEvento);
		return ok(meusEventos.render(getUser(email), evento));
	}
	@Transactional
	public static Result abrirMeusEventos(String email){
		List<Evento> evento = getEventos(email);
		if(evento == null || evento.size() == 0 || evento.size() > 1){
			return Application.index();
		}else{
			return ok(meusEventos.render(getUser(email), evento.get(0)));
		}
		
	}
	@Transactional
	public <T> boolean salvarObjeto(Object object){
		List<T> result = dao.findAllByClassName(object.getClass().toString());
		
		if(result.contains(object))
			return false;
		else{
			if(dao.persist(object)){
				dao.flush();
				return true;
			}else{
				return false;
			}
		}
	}
	
	
}
