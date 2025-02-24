package com.smse.rubik_solver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import com.smse.rubik_solver.model.Cube;

@SpringBootApplication
public class RubikSolverApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext ctx = SpringApplication.run(RubikSolverApplication.class, args);

		Cube cube = ctx.getBean(Cube.class);
		System.out.println(cube);
	}

}
