package models;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.persistence.Entity;

import play.db.jpa.Transactional;
import models.dao.GenericDAO;
import models.dao.GenericDAOImpl;

/**
 * Classe Representa a Estrategia para Usuario mais Experiente
 * @author Marcus Vinicius
 *
 */
@Entity
public class ParticipanteMaisExperiente extends ParticipanteEstrategia {
	
	public ParticipanteMaisExperiente() {
		super();
	}
	
	@Override
	public List<Participante> getAceitos(List<Participante> participantes, Integer capacidade) {

		List<Participante> result = participantes;

		Comparator<Participante> comparator = new Comparator<Participante>() {

			@Override
			public int compare(Participante p1, Participante p2) {
				//Compara se os participante tem o mesmo numero de eventos criados
				if(eventosProprietarios(p2).compareTo(eventosProprietarios(p1)) == 0){
					return eventosAceitos(p2).compareTo(eventosAceitos(p1));
				//Caso os participantes nao tenha o mesmo numero de eventos 
				}else{
					return eventosProprietarios(p2).compareTo(eventosProprietarios(p1));
				}
			}
		};

		Collections.sort(result, comparator);

		return participantes.size() <= capacidade ? participantes : result.subList(0, capacidade);
	}

	@Override
	public String getDescricao() {
		return "As vagas serão preenchidas de acordo com a experiencia dos hackres.";
	}
	
	private Integer eventosAceitos(Participante participante) {
		Integer eventosAceitos = 0;

		for (Evento evento : getEventos()) {
			eventosAceitos += evento.getParticipantes().contains(participante) ? 1: 0;
		}
		return eventosAceitos;
	}

	private Integer eventosProprietarios(Participante participante) {
		Integer contador = 0;
		for (Evento evento : getEventos()) {
			contador += participante.getEmail().equalsIgnoreCase(evento.getAdministrador()) ? 1 : 0;
		}
		return contador;
	}

	@Transactional
	private List<Evento> getEventos() {
		GenericDAO dao = new GenericDAOImpl();
		List<Evento> eventos = dao.findAllByClassName("Evento");
		return eventos;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((getDescricao() == null) ? 0 : getDescricao().hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof ParticipanteMaisExperiente)) {
			return false;
		}
		ParticipanteMaisExperiente other = (ParticipanteMaisExperiente) obj;
		if (getDescricao() == null) {
			if (other.getDescricao() != null) {
				return false;
			}
		} else if (!getDescricao().equalsIgnoreCase(other.getDescricao())) {
			return false;
		}
		return true;
	}
}
