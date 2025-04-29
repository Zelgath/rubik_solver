package com.smse.rubik_solver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class RubikSolverApplication {

	public static void main(String[] args) {
		var app = SpringApplication.run(RubikSolverApplication.class, args);
		emitSwaggerLink(app);
	}

	private static void emitSwaggerLink(ConfigurableApplicationContext app) {
		if (java.lang.management.ManagementFactory.getRuntimeMXBean().getInputArguments().toString().contains("jdwp")) {
			String port = app.getEnvironment().getProperty("server.port", "8080");
			System.out.println("Swagger UI: http://localhost:" + port + "/swagger-ui/index.html");
		}
	}

}
