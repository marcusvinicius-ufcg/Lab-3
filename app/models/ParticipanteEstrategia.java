package models;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;

/**
 * Classe Representa Estrategia Padrao
 * @author Marcus Vinicius
 *
 */
@Entity
@Inheritance
public class ParticipanteEstrategia {

	@Id 
	@GeneratedValue
	private Long id;
	
	public ParticipanteEstrategia() {
		
	}
	
	public String getDescricao() {
		return "As vagas serão preenchidas de acordo com a ordem de inscrição.";
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	/**
	 * Metodo retorna os participantes por orden de inscricao
	 * @param participantes
	 * @param capacidade
	 * @return
	 */
	public List<Participante> getAceitos(List<Participante> participantes, Integer capacidade) {
		// retorna os primeiros a serem inscritos
		if (participantes.size() <= capacidade) {
			return participantes;
		} else {
			return participantes.subList(0, capacidade);
		}
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
		if (!(obj instanceof ParticipanteEstrategia)) {
			return false;
		}
		ParticipanteEstrategia other = (ParticipanteEstrategia) obj;
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
