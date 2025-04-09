package com.smse.rubik_solver.service;

import org.springframework.stereotype.Service;

import com.smse.rubik_solver.model.Color;
import com.smse.rubik_solver.model.Cube;

@Service
public class ValidationService {
    public boolean isCubeValid(Cube cube) {
        return (cube.getFront().get(1).get(1) == Color.R && cube.getUp().get(1).get(1) == Color.W
                && cube.getRight().get(1).get(1) == Color.B && cube.getLeft().get(1).get(1) == Color.G
                && cube.getDown().get(1).get(1) == Color.Y && cube.getBack().get(1).get(1) == Color.O);
    }
}
