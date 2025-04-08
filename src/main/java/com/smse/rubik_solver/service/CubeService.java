package com.smse.rubik_solver.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.smse.rubik_solver.model.Color;
import com.smse.rubik_solver.model.Cube;

@Service
public class CubeService {

    public Cube initializeCube(Cube cube) {
        cube.initArrays();
        return cube;
    }

    public Cube applyMoves(Cube cube, List<String> moves) {
        cube.makeMovesFromList(moves);
        cube.syncToLists();
        return cube;
    }

    public Cube getRandomScramble(Cube cube, int n) {
        cube.randomScramble(n);
        cube.syncToLists();
        return cube;
    }

    public Cube createSolvedCube() {
        Cube cube = new Cube();

        cube.setUp(cube.makeCompletedFace(Color.W));
        cube.setFront(cube.makeCompletedFace(Color.R));
        cube.setRight(cube.makeCompletedFace(Color.B));
        cube.setLeft(cube.makeCompletedFace(Color.G));
        cube.setBack(cube.makeCompletedFace(Color.O));
        cube.setDown(cube.makeCompletedFace(Color.Y));
        cube.initArrays();

        return cube;
    }

}
