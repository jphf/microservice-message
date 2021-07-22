package com.cloud.jphf.controller;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cloud.jphf.util.entity.User;
import com.cloud.jphf.util.pojo.RegistrationForm;
import com.cloud.jphf.util.repository.UserRepository;

@Controller
@RequestMapping("register")
public class RegisterController {

	private UserRepository userRepository;
	private PasswordEncoder passwordEncoder;

	public RegisterController(UserRepository userRepository, PasswordEncoder passwordEncoder) {
		super();
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
	}

	@GetMapping
	public String register(Model model) {
		model.addAttribute("form", new RegistrationForm());
		return "user/registration";
	}

	@PostMapping
	public String processRegistration(RegistrationForm form, Model model, Errors errors) throws Exception{

		User user = userRepository.findByUsername(form.getUsername());
		if (user != null) {
			model.addAttribute("form", form);
			model.addAttribute("error", "Username exist");
			return "user/registration";
		} else {
			userRepository.save(form.toUser(passwordEncoder));
			return "redirect:/login";
		}
	}
}
