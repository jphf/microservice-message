package com.jphf.cloud.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.jphf.cloud.shared.User;
import com.jphf.cloud.util.feign.UserFeignClient;
import com.jphf.cloud.util.pojo.RegistrationForm;

@Controller
@RequestMapping("register")
public class RegisterController {

	private static final Logger logger = LoggerFactory.getLogger(RegisterController.class);

	private UserFeignClient userFeignClient;
	private PasswordEncoder passwordEncoder;

	public RegisterController(UserFeignClient userFeignClient, PasswordEncoder passwordEncoder) {
		this.userFeignClient = userFeignClient;
		this.passwordEncoder = passwordEncoder;
	}

	@GetMapping
	public String register(Model model) {
		model.addAttribute("form", new RegistrationForm());
		return "user/registration";
	}

	@PostMapping
	public String processRegistration(RegistrationForm form, Model model, Errors errors) throws Exception {

		logger.info("{}, {}", form.getUsername(), form.getPassword());

		User user = userFeignClient.findByUsername(form.getUsername());
		if (user != null) {
			model.addAttribute("form", form);
			model.addAttribute("error", "Username exist");
			return "user/registration";
		} else {
			user = form.toUser(passwordEncoder);
			user = userFeignClient.register(user.getUsername(), user.getPassword());
			if (user == null) {
				model.addAttribute("form", form);
				model.addAttribute("error", "Try again later");
				return "user/registration";
			}

			return "redirect:/login";
		}
	}
}
