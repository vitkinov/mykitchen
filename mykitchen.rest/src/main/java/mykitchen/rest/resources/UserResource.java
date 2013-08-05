package mykitchen.rest.resources;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.xml.bind.JAXBElement;

import mykitchen.model.User;
import mykitchen.repositories.UserRepository;

@Stateless
@LocalBean
@Path("/users")
public class UserResource {

	@EJB
	private UserRepository userRepository;

	@GET
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public User login(@QueryParam("username") String username,
			@QueryParam("password") String password) {
		User user = userRepository.login(username, password);

		return user;
	}

	@GET
	@Produces({ MediaType.TEXT_PLAIN })
	@Path("exist/{username}")
	public String isExistUsername(@PathParam("username") String username) {
		boolean result = userRepository.isExist(username);

		return String.valueOf(result);
	}

	@PUT
	@Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public void put(JAXBElement<User> input) {
		User user = input.getValue();

		if (user.getId() == 0) {
			userRepository.add(user);
		} else {
			userRepository.edit(user);
		}
	}
}
