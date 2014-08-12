package models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import models.exceptions.PessoaInvalidaException;
import play.data.validation.Constraints.Email;
import play.data.validation.Constraints.MaxLength;
import play.data.validation.Constraints.Required;

@Entity
@Table(name="TABLE_PARTICIPANTE")
public class Participante {

	@Transient 
	private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

	@Transient
	private static final long MAX_LENGTH = 70L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long id;

	@NotNull
	@MaxLength(value = MAX_LENGTH)
	private String nome;

	@Email
	@Required
	@NotNull
	@MaxLength(value = MAX_LENGTH)
	private String email;
	
	public Participante() {
	}
	
	public Participante(String nome, String email)
			throws PessoaInvalidaException {
		setNome(nome);
		setEmail(email);
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) throws PessoaInvalidaException {
		if (nome == null) {
			throw new PessoaInvalidaException("Parametro nulo");
		}
		if (nome.length() > MAX_LENGTH) {
			throw new PessoaInvalidaException("Nome longo");
		}
		this.nome = nome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) throws PessoaInvalidaException {
		if (email == null) {
			throw new PessoaInvalidaException("Parametro nulo");
		}
		if (!email.matches(EMAIL_PATTERN)) {
			throw new PessoaInvalidaException("Email inválido");
		}
		if (email.length() > MAX_LENGTH) {
			throw new PessoaInvalidaException("Email longo");
		}
		this.email = email;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((email == null) ? 0 : email.hashCode());
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
		if (!(obj instanceof Participante)) {
			return false;
		}
		Participante other = (Participante) obj;
		if (email == null) {
			if (other.email != null) {
				return false;
			}
		} else if (!email.equalsIgnoreCase(other.email)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "Participante: " + nome + ", Email: " + email + "]";
	}
}
