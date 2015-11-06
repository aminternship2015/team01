package RestWebServices;

import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.endava.twitt.models.User;
import com.endava.twitt.services.UserServicesInterface;

@RestController
public class UserRestController {

	private static final Logger logger = LoggerFactory
			.getLogger(UserRestController.class);

	private UserServicesInterface userService;

	@Autowired(required = true)
	@Qualifier(value = "userService")
	public void setUserService(UserServicesInterface userService) {
		this.userService = userService;
	}

	// Done and works.
	//List all users.
	@RequestMapping(value = "userapi", method = RequestMethod.GET)
	public ResponseEntity<List<User>> listAllUsers() {
		List<User> users = userService.getUser();
		System.out.println(users.size());

		if (users.isEmpty()) {
			return new ResponseEntity<List<User>>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<User>>(users, HttpStatus.OK);
	}

	// Done and works.
	//List one user by id.
	@RequestMapping(value = "userapi/{id}", method = RequestMethod.GET)
	public ResponseEntity<User> listUsers(@PathVariable("id") Integer id) {

		User users = userService.getUserById(id);
		if (users == null) {
			logger.info("User with id " + id + " not found or not exists");
			return new ResponseEntity<User>(HttpStatus.NO_CONTENT);
		}
		logger.info("User with id " + id + " was found.");
		return new ResponseEntity<User>(users, HttpStatus.OK);
	}

	@RequestMapping(value = "userapi/delete/{id}", method = RequestMethod.GET)
	public ResponseEntity<User> deleteUsers(
			@PathVariable("useremail") String useremail) {
		System.out.println(useremail);

		User users = userService.getUserByName(useremail);
		System.out.println(users.getEmail());

		if (users != null) {
			logger.info("User with id " + id + " not found or not exists");
			return new ResponseEntity<User>(users, HttpStatus.OK);
		}

		return null;
	}

	// Done needs test
	@SuppressWarnings("unused")
	@RequestMapping(value = "userapi/create", method = RequestMethod.POST)
	public ResponseEntity<Void> createUsers(@Valid @RequestBody User user,
			BindingResult result, UriComponentsBuilder ucBuilder) {

		System.out.println("Creating User " + user.getEmail());

		if (userService.getUserByName(user.getEmail()) == null) {
			System.out.println("A User with name " + user.getEmail()
					+ " already exist");
			return new ResponseEntity<Void>(HttpStatus.CONFLICT);
		}

		userService.insertUser(user);

		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(ucBuilder.path("/user/{useremail:.+}")
				.buildAndExpand(user.getEmail()).toUri());
		return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
	}

	@SuppressWarnings("unused")
	@RequestMapping(value = "userapi/update/", method = RequestMethod.PUT)
	public ResponseEntity<User> updateUsers(@RequestParam String useremail,
			@RequestBody User user) {
		System.out.println("Updating User " + useremail);

		User currentUser = userService.getUserByName(useremail);

		if (currentUser == null) {
			System.out.println("User with id " + useremail + " not found");
			return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
		}

		currentUser.setFirstName(firstName);
		currentUser.setLastName(lastname);
		currentUser.setPassword(password);

		userService.updateUser(currentUser);
		return new ResponseEntity<User>(currentUser, HttpStatus.OK);
	}

}
