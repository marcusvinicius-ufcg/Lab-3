package models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import play.data.format.Formats.NonEmpty;

import com.google.common.base.Objects;
/**
 * Classe Representa um Usuario do Sistema
 * 
 * @author Marcus Vinicius
 *
 */
@Entity
@Table(name="USER_TABLE")
public class User {

	@Id
	@NonEmpty
	@Column(name="EMAIL")
	private String	email;

	@Column(name="NOME")
	private String	name;

	@Column(name="SENHA")
	private String	password;

	public User() {
		
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj){
			return true;
		}
		if (obj == null){
			return false;
		}
		if (!(obj instanceof User)){
			return false;
		}
			
		User other = (User) obj;
		if (email == null) {
			if (other.email != null){
				return false;
			}
		} else if (!email.equalsIgnoreCase(other.email)){
			return false;
		}
		return true;
	}

	/**
	 * @return o email do usuario
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @return nome do usuario
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return a senha do usuario
	 */
	public String getPassword() {
		return password;
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(email, password);
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
	 * Altera nome do usuario
	 * 
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
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
}
