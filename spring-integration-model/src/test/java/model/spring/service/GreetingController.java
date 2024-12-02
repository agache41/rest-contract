package model.spring.service;

import io.github.agache41.rest.contract.dataAccess.DataAccess;
import io.github.agache41.rest.contract.entities.Modell;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.atomic.AtomicLong;

@RestController
public class GreetingController {

	private static final String template = "Hello, %s!";
	private final AtomicLong counter = new AtomicLong();


	@Autowired
	private DataAccess<Modell,Long> modellLongDataAccess;

	@GetMapping("/greeting")
	public Greeting greeting(@RequestParam(value = "name", defaultValue = "World") String name) {
		//Modell byId = modellLongDataAccess.findById(0L);
		return new Greeting(counter.incrementAndGet(), String.format(template, name));
	}
}
