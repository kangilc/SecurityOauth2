package net.javaf.SecurityOauth2.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import net.javaf.SecurityOauth2.config.auth.dto.SessionUser;

@RequiredArgsConstructor
@Controller
public class IndexController {
//	private final PostService postService;
	private final HttpSession httpSession;

	@GetMapping("/")
	public String index(Model model) {
//		model.addAttribute("posts", postService.findAllDesc());

		SessionUser user = (SessionUser) httpSession.getAttribute("user");

		if (user != null) {
			model.addAttribute("userName", user.getName());
		}

		return "index";
	}
}
