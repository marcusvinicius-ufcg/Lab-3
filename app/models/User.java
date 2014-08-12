package models;

import javax.persistence.Entity;
import javax.persistence.Id;

import play.data.format.Formats.NonEmpty;
import play.data.validation.Constraints.Required;

import com.google.common.base.Objects;
/**
 * Classe Representa um Usuario do Sistema
 * @author Vinicius
 *
 */
@Entity
public class User {

	@Id
	@Required
	@NonEmpty
	private String	email;

	@Required
	private String	name;

	@Required
	private String	password;

	/**
	 * @return o email do usuario
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * Altera o email do usuario
	 * 
	 * @param email
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return nome do usuario
	 */
	public String getName() {
		return name;
	}

	/**
	 * Altera nome do usuario
	 * 
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return a senha do usuario
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Altera a senha do usuario
	 * 
	 * @param password
	 */
	public void setPassword(String password) {
		this.password = password;
		this.password = String.valueOf(this.hashCode());
	}

	@Override
	public String toString() {
		return String.format("|%-40s|%-40s|", name, email);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof User))
			return false;
		User other = (User) obj;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equalsIgnoreCase(other.email))
			return false;
		return true;
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(email, password);
	}
}
