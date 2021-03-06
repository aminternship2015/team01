package com.endava.twitt;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.endava.twitt.models.GlobalVariables;
import com.endava.twitt.models.Tweets;
import com.endava.twitt.models.User;
import com.endava.twitt.services.TweetServiceInterface;
import com.endava.twitt.services.UserServicesInterface;

@Controller
@Scope("session")
public class LoginController {

	private static final Logger logger = LoggerFactory
			.getLogger(LoginController.class);

	
	@Autowired
	private UserServicesInterface userService;

	@Qualifier(value = "userService")
	public void setUserService(UserServicesInterface userService) {
		this.userService = userService;
	}

	@Autowired
	private TweetServiceInterface tweetService;

	@Qualifier(value = "tweetService")
	public void setTweetService(TweetServiceInterface tweetService) {
		this.tweetService = tweetService;
	}

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String loginShowForm() {
		return "login";
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String verifyLogin(@RequestParam String userEmail,
			@RequestParam String password, HttpSession session, Model model) {

		session.removeAttribute("loadedUser");
		session.removeAttribute("newloadedUser");
		session.removeAttribute("existingUser");
		
		logger.debug("User autentification.");
		User user = userService.loginUser(userEmail, password);
		User user1 = userService.getUserByName(userEmail);

		if (user == null) {
			model.addAttribute("loginError",
					"Please provide correct email and password!");
			return "login";
		} else if (user1.getRole().equals("ROLE_ADMIN")) {			
			session.setAttribute("loadedUser", user);
			session.setAttribute("loadedAdmin", user);
			session.setAttribute("loadedRole", user.getRole());

			session.setAttribute("loadedAdminEmail", user.getEmail());
			session.setAttribute("state", "ROLE_ADMIN");

			return "redirect:/admin";
		}
		logger.debug("User was autentificated successfully.");
		Integer numberOfTweetsOnPage = new GlobalVariables().tweetsOnPage;

		Integer firstrow = 0;
		Integer rowcount = 0;
		List<Tweets> allUsersTweets = user1.getTweet();
		Integer listSize = allUsersTweets.size();
		
		Integer numberOfPages;
		if (listSize % numberOfTweetsOnPage == 0 && numberOfTweetsOnPage>0) {
			numberOfPages=listSize/numberOfTweetsOnPage;
		}else if(numberOfTweetsOnPage==0){
			numberOfPages=0;
		}else{
			numberOfPages=Math.abs(listSize/numberOfTweetsOnPage)+1;
		}
		
		session.setAttribute("numberOfRealPages", numberOfPages);
		
			
		logger.debug("Lise size in login = " + listSize);
		if (listSize == 0) {
			firstrow = 0;
			rowcount = 0;
			session.setAttribute("firstRow", firstrow);
			session.setAttribute("rowCount", rowcount);
		} else if (listSize > 0 && listSize < numberOfTweetsOnPage) {
			firstrow = 0;
			rowcount = listSize;
			session.setAttribute("firstRow", firstrow);
			session.setAttribute("rowCount", rowcount);
		} else if (listSize >= numberOfTweetsOnPage) {
			firstrow = 0;
			rowcount = numberOfTweetsOnPage;
			session.setAttribute("firstRow", firstrow);
			session.setAttribute("rowCount", rowcount);
		}

		session.setAttribute("loadedUser", user);
		session.setAttribute("userID", user.getEmail());
		session.setAttribute("state", "ROLE_USER");

		return "redirect:/home";
	}

	@RequestMapping(value = "/home", method = RequestMethod.GET)
	public String home(HttpSession session, Model model) {
		if (session.getAttribute("loadedUser") == null) {
			return "redirect:/login";
		}

		session.setAttribute("newloadedUser",
				session.getAttribute("loadedUser"));
		session.setAttribute("existingUser",
				session.getAttribute("sessionUser"));

		User user1 = userService.getUserByName((String) session
				.getAttribute("userID"));
		logger.debug("In home user name=" + user1.getFirstName());
		List<Tweets> allUsersTweets = user1.getTweet();
		Integer listSize = allUsersTweets.size();
		logger.debug("List size in home= " + listSize);
		session.setAttribute("sizeUserTweets", listSize);

		Integer firstrow = (Integer) session.getAttribute("firstRow");
		Integer rowcount = (Integer) session.getAttribute("rowCount");
		logger.debug("Size = " + listSize + " In Home = "
				+ (Integer) session.getAttribute("firstRow") + " "
				+ (Integer) session.getAttribute("rowCount"));
		
		try {
			logger.debug("Try to retrive sublist of users");
		List<Tweets> userSubTweets = allUsersTweets.subList(firstrow, rowcount);
		session.setAttribute("userTweetsSublist", userSubTweets);
		} catch (IndexOutOfBoundsException e) {
			logger.error("Error to fulfill request with indexes "
					+ firstrow + " " + rowcount);
			return "redirect:/home";
		}
		
		Integer numberOfTweetsOnPage1 = new GlobalVariables().tweetsOnPage;
		Integer numberOfPages;
		Integer selectedPage;
		if (listSize % numberOfTweetsOnPage1 == 0 && numberOfTweetsOnPage1>0) {
			numberOfPages=listSize/numberOfTweetsOnPage1;
		}else if(numberOfTweetsOnPage1==0){
			numberOfPages=0;
		}else{
			numberOfPages=Math.abs(listSize/numberOfTweetsOnPage1)+1;
		}
		
		if(rowcount%numberOfTweetsOnPage1==0 ){
			selectedPage=rowcount/numberOfTweetsOnPage1;
		}else if(rowcount%numberOfTweetsOnPage1!=0){
			selectedPage=Math.abs(rowcount/numberOfTweetsOnPage1)+1;
		}else{
			selectedPage=0;
		}
		
		session.setAttribute("loadedRole", user1.getRole());
		session.setAttribute("selectedRealPage", selectedPage);
		session.setAttribute("numberOfRealPages", numberOfPages);
		return "home";
	}

	@RequestMapping(value = "/paginateTweets", method = RequestMethod.GET)
	public String paginating(HttpSession session,
			@RequestParam Integer firstrow, @RequestParam Integer rowcount,
			@RequestParam String user_email, @RequestParam String page,
			Model model) {
		logger.debug("Try to paginate tweets");
		if (session.getAttribute("loadedUser") == null) {
			return "redirect:/login";
		}

		Integer numberOfTweetsOnPage = new GlobalVariables().tweetsOnPage;

		if (page.equals("Next")) {
			Integer sizeTweetsList = (Integer) session
					.getAttribute("sizeUserTweets");
			if ((sizeTweetsList % numberOfTweetsOnPage == 0 && rowcount >= numberOfTweetsOnPage)) {
				firstrow += numberOfTweetsOnPage;
				rowcount += numberOfTweetsOnPage;

				if (rowcount > sizeTweetsList) {
					firstrow -= numberOfTweetsOnPage;
					rowcount = sizeTweetsList;
					session.setAttribute("firstRow", firstrow);
					session.setAttribute("rowCount", rowcount);
					logger.debug("Size = " + sizeTweetsList
							+ " In Next 1 = "
							+ (Integer) session.getAttribute("firstRow") + " "
							+ (Integer) session.getAttribute("rowCount"));
				} else {
					session.setAttribute("firstRow", firstrow);
					session.setAttribute("rowCount", rowcount);
					logger.debug("Size = " + sizeTweetsList
							+ " In Next 1 ELSE = "
							+ (Integer) session.getAttribute("firstRow") + " "
							+ (Integer) session.getAttribute("rowCount"));
				}

			} else if (sizeTweetsList % numberOfTweetsOnPage == 0
					&& (rowcount < numberOfTweetsOnPage)) {

				logger.debug("Size = " + sizeTweetsList + " In Next 2 = "
						+ (Integer) session.getAttribute("firstRow") + " "
						+ (Integer) session.getAttribute("rowCount"));
				return "redirect:/home";

			} else if ((sizeTweetsList % numberOfTweetsOnPage != 0 && rowcount >= numberOfTweetsOnPage)
					&& (sizeTweetsList - rowcount) < numberOfTweetsOnPage) {
				Integer lastTweets = sizeTweetsList % numberOfTweetsOnPage;
				firstrow += numberOfTweetsOnPage;
				rowcount += lastTweets;

				if (rowcount > sizeTweetsList) {
					firstrow -= numberOfTweetsOnPage;
					rowcount = sizeTweetsList;
					session.setAttribute("firstRow", firstrow);
					session.setAttribute("rowCount", rowcount);
					logger.debug("Size = " + sizeTweetsList
							+ " In Next 3 = "
							+ (Integer) session.getAttribute("firstRow") + " "
							+ (Integer) session.getAttribute("rowCount"));
				} else {
					session.setAttribute("firstRow", firstrow);
					session.setAttribute("rowCount", rowcount);
					logger.debug("Size = " + sizeTweetsList
							+ " In Next 3 ELSE = "
							+ (Integer) session.getAttribute("firstRow") + " "
							+ (Integer) session.getAttribute("rowCount"));
				}

			} else if (sizeTweetsList % numberOfTweetsOnPage != 0
					&& rowcount >= numberOfTweetsOnPage
					&& (sizeTweetsList - rowcount) > numberOfTweetsOnPage) {
				firstrow += numberOfTweetsOnPage;
				rowcount += numberOfTweetsOnPage;
				session.setAttribute("firstRow", firstrow);
				session.setAttribute("rowCount", rowcount);
				logger.debug("Size = " + sizeTweetsList + " In Next 4 = "
						+ (Integer) session.getAttribute("firstRow") + " "
						+ (Integer) session.getAttribute("rowCount"));

			} else if (sizeTweetsList % numberOfTweetsOnPage != 0
					&& (rowcount < numberOfTweetsOnPage)) {
				Integer lastTweets = sizeTweetsList % numberOfTweetsOnPage;
				firstrow = 0;
				rowcount = lastTweets;
				session.setAttribute("firstRow", firstrow);
				session.setAttribute("rowCount", rowcount);
				logger.debug("Size = " + sizeTweetsList + " In Next 5 = "
						+ (Integer) session.getAttribute("firstRow") + " "
						+ (Integer) session.getAttribute("rowCount"));
			}
		}

		if (page.equals("Previous")) {
			Integer sizeTweetsList = (Integer) session
					.getAttribute("sizeUserTweets");
			if (sizeTweetsList % numberOfTweetsOnPage != 0
					&& (firstrow >= numberOfTweetsOnPage && (rowcount
							% numberOfTweetsOnPage != 0))) {
				Integer lastTweets = sizeTweetsList % numberOfTweetsOnPage;
				firstrow -= numberOfTweetsOnPage;
				rowcount -= lastTweets;
				session.setAttribute("firstRow", firstrow);
				session.setAttribute("rowCount", rowcount);
				logger.debug("In Previous 1 = "
						+ (Integer) session.getAttribute("firstRow") + " "
						+ (Integer) session.getAttribute("rowCount"));

			} else if (sizeTweetsList % numberOfTweetsOnPage != 0
					&& (firstrow >= numberOfTweetsOnPage && (rowcount
							% numberOfTweetsOnPage == 0))) {

				firstrow -= numberOfTweetsOnPage;
				rowcount -= numberOfTweetsOnPage;
				session.setAttribute("firstRow", firstrow);
				session.setAttribute("rowCount", rowcount);
				logger.debug("In Previous 1_2 = "
						+ (Integer) session.getAttribute("firstRow") + " "
						+ (Integer) session.getAttribute("rowCount"));

			} else if (sizeTweetsList % numberOfTweetsOnPage != 0
					&& (firstrow < numberOfTweetsOnPage && sizeTweetsList >= numberOfTweetsOnPage)) {

				firstrow = 0;
				rowcount = numberOfTweetsOnPage;
				session.setAttribute("firstRow", firstrow);
				session.setAttribute("rowCount", rowcount);
				logger.debug("In Previous 1_3 = "
						+ (Integer) session.getAttribute("firstRow") + " "
						+ (Integer) session.getAttribute("rowCount"));

			} else if (sizeTweetsList % numberOfTweetsOnPage != 0
					&& (firstrow < numberOfTweetsOnPage && rowcount < numberOfTweetsOnPage)) {
				Integer lastTweets = sizeTweetsList % numberOfTweetsOnPage;
				firstrow = 0;
				rowcount = lastTweets;
				session.setAttribute("firstRow", firstrow);
				session.setAttribute("rowCount", rowcount);
				logger.debug("In Previous 2 = "
						+ (Integer) session.getAttribute("firstRow") + " "
						+ (Integer) session.getAttribute("rowCount"));

			} else if ((firstrow >= numberOfTweetsOnPage && rowcount > numberOfTweetsOnPage)
					&& sizeTweetsList % numberOfTweetsOnPage == 0) {
				firstrow -= numberOfTweetsOnPage;
				rowcount -= numberOfTweetsOnPage;
				session.setAttribute("firstRow", firstrow);
				session.setAttribute("rowCount", rowcount);
				logger.debug("In Previous 3 = "
						+ (Integer) session.getAttribute("firstRow") + " "
						+ (Integer) session.getAttribute("rowCount"));
			} else if ((firstrow < numberOfTweetsOnPage && sizeTweetsList < numberOfTweetsOnPage)
					&& sizeTweetsList % numberOfTweetsOnPage == 0) {
				Integer lastTweets = sizeTweetsList % numberOfTweetsOnPage;
				firstrow = 0;
				rowcount = lastTweets;
				session.setAttribute("firstRow", firstrow);
				session.setAttribute("rowCount", rowcount);
				logger.debug("In Previous 4 = "
						+ (Integer) session.getAttribute("firstRow") + " "
						+ (Integer) session.getAttribute("rowCount"));
			}
		}

		return "redirect:/home";
	}

	

	@RequestMapping(value = "/loginTest", method = RequestMethod.POST)
	public String loginTest() {
		return "loginTest";
	}

	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String logout(HttpSession session) {
		session.removeAttribute("loadedUser");
		session.removeAttribute("newloadedUser");
		session.removeAttribute("existingUser");
		session.removeAttribute("firstRow");
		session.removeAttribute("rowCount");
		session.removeAttribute("specialUser");
		session.removeAttribute("firstRowUser");
		session.removeAttribute("rowCountUser");
		session.removeAttribute("sizeUserTweetsUser");
		session.removeAttribute("specialUser");
		session.removeAttribute("userID");
		session.removeAttribute("currentUserData");
		session.removeAttribute("currentUser");
		session.removeAttribute("sessionUser");
		session.removeAttribute("followedUsers");
		session.removeAttribute("selectedRealPage");
		session.removeAttribute("selectedRealPageUser");
		session.removeAttribute("numberOfRealPagesUser");
		session.removeAttribute("numberOfRealPages");
		logger.info("User log out succesfully");
		return "login";
	}
}
