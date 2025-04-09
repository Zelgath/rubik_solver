package com.smse.rubik_solver.service;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.smse.rubik_solver.model.Color;
import com.smse.rubik_solver.model.Cube;

@Service
public class ValidationService {
    public boolean isCubeValid(Cube cube) {
        return (areCentersValid(cube) && areCornersValid(cube) && areEdgesValid(cube));
    }

    private boolean areCentersValid(Cube cube) {
        return (cube.getFront().get(1).get(1) == Color.R && cube.getUp().get(1).get(1) == Color.W
                && cube.getRight().get(1).get(1) == Color.B && cube.getLeft().get(1).get(1) == Color.G
                && cube.getDown().get(1).get(1) == Color.Y && cube.getBack().get(1).get(1) == Color.O);
    }

    private boolean areCornersValid(Cube cube) {
        Set<Color> c1 = new HashSet<>(Arrays.asList( // U-F-L
                cube.getFront().get(0).get(0),
                cube.getLeft().get(0).get(2),
                cube.getUp().get(2).get(0)));

        Set<Color> c2 = new HashSet<>(Arrays.asList( // U-F-R
                cube.getFront().get(0).get(2),
                cube.getRight().get(0).get(0),
                cube.getUp().get(2).get(2)));

        Set<Color> c3 = new HashSet<>(Arrays.asList( // U-B-L
                cube.getBack().get(0).get(2),
                cube.getLeft().get(0).get(0),
                cube.getUp().get(0).get(0)));

        Set<Color> c4 = new HashSet<>(Arrays.asList( // U-B-R
                cube.getBack().get(0).get(0),
                cube.getRight().get(0).get(2),
                cube.getUp().get(0).get(2)));

        Set<Color> c5 = new HashSet<>(Arrays.asList( // D-F-L
                cube.getFront().get(2).get(0),
                cube.getLeft().get(2).get(2),
                cube.getDown().get(0).get(0)));

        Set<Color> c6 = new HashSet<>(Arrays.asList( // D-F-R
                cube.getFront().get(2).get(2),
                cube.getRight().get(2).get(0),
                cube.getDown().get(0).get(2)));

        Set<Color> c7 = new HashSet<>(Arrays.asList( // D-B-L
                cube.getBack().get(2).get(2),
                cube.getLeft().get(2).get(0),
                cube.getDown().get(2).get(0)));

        Set<Color> c8 = new HashSet<>(Arrays.asList( // D-B-R
                cube.getBack().get(2).get(0),
                cube.getRight().get(2).get(2),
                cube.getDown().get(2).get(2)));

        return true;
    }

    private boolean areEdgesValid(Cube cube) {
        Set<Color> e1 = new HashSet<>(Arrays.asList( // U-F
                cube.getFront().get(0).get(1),
                cube.getUp().get(2).get(1)));

        Set<Color> e2 = new HashSet<>(Arrays.asList( // U-L
                cube.getLeft().get(0).get(1),
                cube.getUp().get(1).get(0)));

        Set<Color> e3 = new HashSet<>(Arrays.asList( // U-R
                cube.getRight().get(0).get(1),
                cube.getUp().get(1).get(2)));

        Set<Color> e4 = new HashSet<>(Arrays.asList( // U-B
                cube.getBack().get(0).get(1),
                cube.getUp().get(0).get(1)));

        Set<Color> e5 = new HashSet<>(Arrays.asList( // D-F
                cube.getFront().get(2).get(1),
                cube.getDown().get(0).get(1)));

        Set<Color> e6 = new HashSet<>(Arrays.asList( // D-L
                cube.getLeft().get(2).get(1),
                cube.getDown().get(1).get(0)));

        Set<Color> e7 = new HashSet<>(Arrays.asList( // D-R
                cube.getRight().get(2).get(1),
                cube.getDown().get(1).get(2)));

        Set<Color> e8 = new HashSet<>(Arrays.asList( // D-B
                cube.getBack().get(2).get(1),
                cube.getDown().get(2).get(1)));

        Set<Color> e9 = new HashSet<>(Arrays.asList( // F-L
                cube.getFront().get(1).get(0),
                cube.getLeft().get(1).get(2)));

        Set<Color> e10 = new HashSet<>(Arrays.asList( // F-R
                cube.getFront().get(1).get(2),
                cube.getRight().get(1).get(0)));

        Set<Color> e11 = new HashSet<>(Arrays.asList( // B-L
                cube.getBack().get(1).get(2),
                cube.getLeft().get(1).get(0)));

        Set<Color> e12 = new HashSet<>(Arrays.asList( // B-R
                cube.getBack().get(1).get(0),
                cube.getRight().get(1).get(2)));

        return true;
    }

}
