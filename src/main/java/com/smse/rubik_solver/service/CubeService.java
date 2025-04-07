package com.smse.rubik_solver.service;

import org.springframework.stereotype.Service;

import com.smse.rubik_solver.model.Cube;

@Service
public class CubeService {

    public Cube initializeCube(Cube cube) {
        return cube;
    }

    public Cube applyMoves(Cube cube, String[] moves) {
        cube.makeMovesFromList(moves);
        return cube;
    }

}
