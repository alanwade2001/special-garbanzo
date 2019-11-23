package io.gnucash.cloud;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GoodbyeController {

	public GoodbyeController() {
		// TODO Auto-generated constructor stub
	}
	
	@GetMapping
	public String goodbye() {
		return "goodbye 2";
	}

}
