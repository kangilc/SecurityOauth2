package net.javaf.SecurityOauth2.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.javaf.SecurityOauth2.config.auth.dto.SessionUser;

@RequiredArgsConstructor
@Controller
@Slf4j
public class IndexController {
//	private final PostService postService;
	private final HttpSession httpSession;

	@GetMapping(value = "/")
	public String index(Model model) {
//		model.addAttribute("posts", postService.findAllDesc());

		SessionUser user = (SessionUser) httpSession.getAttribute("user");

		if (user != null) {
			model.addAttribute("userName", user.getName());
		}

		return "index";
	}
	
	@GetMapping(value = "/test/SsoCallBack")
	public String ssoCallBack(Model model) {
//		model.addAttribute("posts", postService.findAllDesc());
		
		SessionUser user = (SessionUser) httpSession.getAttribute("user");
		
		log.info("[Model] :: params :: {}", model);
		
		if (user != null) {
			model.addAttribute("userName", user.getName());
		}
		
		return "/login/oauth2/code/lmp";
	}
}
