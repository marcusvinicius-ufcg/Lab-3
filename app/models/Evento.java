package models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import models.exceptions.EventoInvalidoException;
import play.data.validation.Constraints.MaxLength;
import play.data.validation.Constraints.Required;

@Entity
public class Evento {

	@Transient
	private static final long TITULO_MAX_LENGTH = 40L;

	@Transient
	private static final long DESC_MAX_LENGTH = 450L;

	@Id
	@GeneratedValue
	private Long id;

	@Required
	@MaxLength(value = TITULO_MAX_LENGTH)
	private String titulo;

	@Required
	@MaxLength(value = DESC_MAX_LENGTH)
	private String descricao;

	@Required
	private String administrador;

	@Temporal(TemporalType.DATE)
	@Required
	private Date data;

	@ManyToMany
	private List<Participante> participantes = new ArrayList<Participante>();

	@ElementCollection
	@Enumerated(value = EnumType.ORDINAL)
	@NotNull
	private List<Tema> temas = new ArrayList<Tema>();

	@OneToOne
	private Local local;

	@OneToOne
	private ParticipanteEstrategia strategia;

	public Evento() {
	}

	public Evento(String titulo, String descricao, Date data, List<Tema> temas,
			String administrador, Local local, ParticipanteEstrategia strategia)
			throws EventoInvalidoException {
		isSetTitulo(titulo);
		isSetDescricao(descricao);
		isSetData(data);
		isSetTemas(temas);
		isSetLocal(local);
		isSetStrategia(strategia);
		isSetAdministrador(administrador);
	}


	private Integer getCapacidade() {
		return local.getCapacidade();
	}

	public String getTitulo() {
		return titulo;
	}

	public String getDescricao() {
		return descricao;
	}

	public Date getData() {
		return data;
	}

	public Long getId() {
		return id;
	}

	public List<Tema> getTemas() {
		return temas;
	}

	public Integer getTotalDeParticipantes() {
		return participantes.size();
	}

	public List<Participante> getParticipantes() {
		return participantes;
	}

	public void setParticipantes(List<Participante> participantes) {
		isSetParticipantes(participantes);
	}
	
	private void isSetParticipantes(List<Participante> participantes) {
		this.participantes = participantes;
	}

	public boolean addParticipante(Participante participante){
		
		if( participantes.contains(participante)){
			return false;
		}else{
			if(participantes.add(participante)){
				return true;
			}else{
				return false;
			}
		}
	}

	public Local getLocal() {
		return local;
	}

	public void setLocal(Local local) {
		isSetLocal(local);
	}

	private void isSetLocal(Local local) {
		this.local = local;
	}
	
	public ParticipanteEstrategia getStrategia() {
		return strategia;
	}

	public void setStrategia(ParticipanteEstrategia strategia) {
		isSetStrategia(strategia);
	}
	
	private void isSetStrategia(ParticipanteEstrategia strategia) {
		this.strategia = strategia;
	}

	public List<Participante> getParticipantesAceitos() {
		return strategia.getAceitos(participantes, getCapacidade());
	}

	public String getAdministrador() {
		return administrador;
	}

	public void setAdministrador(String administrador) {
		isSetAdministrador(administrador);
	}
	
	private void isSetAdministrador(String administrador) {
		this.administrador = administrador;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setTitulo(String titulo) throws EventoInvalidoException {
		isSetTitulo(titulo);
	}
	
	private void isSetTitulo(String titulo) throws EventoInvalidoException {
		if (titulo == null){
			throw new EventoInvalidoException("Parametro nulo");
		}
		if (titulo.length() > TITULO_MAX_LENGTH){
			throw new EventoInvalidoException("Título longo");
		}
		this.titulo = titulo;
	}
	
	

	public void setDescricao(String descricao) throws EventoInvalidoException {
		isSetDescricao(descricao);
	}
	
	private void isSetDescricao(String descricao) throws EventoInvalidoException {
		if (descricao == null){
			throw new EventoInvalidoException("Parametro nulo");
		}
		if (descricao.length() > DESC_MAX_LENGTH){
			throw new EventoInvalidoException("Descrição longa");
		}
		this.descricao = descricao;
	}

	public void setData(Date data) throws EventoInvalidoException {
		isSetData(data);
	}
	
	private void isSetData(Date data) throws EventoInvalidoException {
		if (data == null){
			throw new EventoInvalidoException("Parametro nulo");
		}
		if (data.compareTo(new Date()) < 0){
			throw new EventoInvalidoException("Data inválida");
		}
		this.data = data;
	}

	public void setTemas(List<Tema> temas) throws EventoInvalidoException {
		isSetTemas(temas);
	}
	
	private void isSetTemas(List<Tema> temas) throws EventoInvalidoException {
		if (temas == null){
			throw new EventoInvalidoException("Parametro nulo");
		}
		if (temas.size() == 0){
			throw new EventoInvalidoException("Nenhum tema");
		}
		this.temas = temas;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((administrador == null) ? 0 : administrador.hashCode());
		result = prime * result + ((titulo == null) ? 0 : titulo.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null || !(obj instanceof Evento)) {
			return false;
		}

		Evento other = (Evento) obj;
		if (administrador == null) {
			if (other.administrador != null) {
				return false;
			}
		} else if (!administrador.equalsIgnoreCase(other.administrador)) {
			return false;
		}
		if (titulo == null) {
			if (other.titulo != null) {
				return false;
			}
		} else if (!titulo.equalsIgnoreCase(other.titulo)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "Evento: " + titulo + ", Local: " + local.getNome();
	}
}
