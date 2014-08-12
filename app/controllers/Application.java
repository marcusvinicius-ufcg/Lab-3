package controllers;

import java.util.List;

import com.google.common.base.Objects;
import models.User;
import models.dao.GenericDAO;
import models.dao.GenericDAOImpl;
import play.data.Form;
import play.db.jpa.Transactional;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.*;

public class Application extends Controller {

	private static GenericDAO	dao	= new GenericDAOImpl();

	@Transactional
	public static Result index() {

		if (session("email") == null)
			return ok(login.render(Form.form(Login.class)));

		User userLogado = getUser(session("email"));

		return ok(index.render(userLogado));
	}

	public static Result cadastroPage() {
		
		return ok(cadastro.render(Form.form(Cadastro.class)));
	}

	@Transactional
	public static Result cadastro() {
		Form<Cadastro> cadastroForm = Form.form(Cadastro.class)
				.bindFromRequest();

		if (cadastroForm.hasErrors()) {
			return badRequest(cadastro.render(cadastroForm));

		} else {

			User user = criarUser(cadastroForm);

			if (usuarioCadastrado(user)) {
				flash("success", "Email já cadastrado");
				return badRequest(cadastro.render(cadastroForm));
			} else {
				salvarNoBD(user);
			}
			return redirect(routes.Application.index());
		}
	}

	@Transactional
	private static boolean usuarioCadastrado(User user) {
		List<User> users = dao.findAllByClassName("User");

		for (User userDao : users) {
			if (userDao.equals(user)){
				return true;
			}
		}
		return false;
	}

	public static Result login() {
		return ok(login.render(Form.form(Login.class)));
	}

	private static User criarUser(Form<Cadastro> cadastroForm) {
		User user = new User();
		user.setEmail(cadastroForm.get().email);
		user.setPassword(cadastroForm.get().senha);
		user.setName(cadastroForm.get().nome);
		return user;
	}

	@Transactional
	private static void salvarNoBD(Object object) {
		dao.persist(object);
		dao.flush();
	}

	@Transactional
	public static Result authenticate() {
		Form<Login> loginForm = Form.form(Login.class).bindFromRequest();

		if (loginForm.hasErrors() || autenticacaoFalhou(loginForm)) {
			return badRequest(login.render(loginForm));
		} else {
			session().clear();
			User user = getUser(loginForm.get().email);

			session("email", user.getEmail());

			return redirect(routes.Application.index());
		}
	}

	@Transactional
	private static User getUser(String email) {
		List<User> result = dao.findByAttributeName("User", "email", email);
		return result.size() == 0 ? null : result.get(0);
	}

	@Transactional
	private static boolean autenticacaoFalhou(Form<Login> loginForm) {

		User user = getUser(loginForm.get().email);

		if (user == null) {
			flash("success", "Email não cadastrado");
			return true;
		}
		if (!isPasswordValido(loginForm, user)) {
			flash("success", "Senha incorreta");
			return true;
		}
		return false;
	}

	private static boolean isPasswordValido(Form<Login> loginForm, User user) {
		Integer hash = Objects.hashCode(user.getEmail(),
				loginForm.get().password);

		String hashString = String.valueOf(hash);

		return hashString.equals(user.getPassword());
	}

	public static Result logout() {
		session().clear();
		flash("success", "Você saiu do sistema!");
		return ok(login.render(Form.form(Login.class)));
	}

	public static class Login {
		public String	email;
		public String	password;
	}

	public static class Cadastro {
		public String	email;
		public String	senha;
		public String	nome;
	}
}
