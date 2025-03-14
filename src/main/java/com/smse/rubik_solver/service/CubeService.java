package com.smse.rubik_solver.service;

import org.springframework.stereotype.Service;

import com.smse.rubik_solver.dto.CubeDto;
import com.smse.rubik_solver.model.Cube;

@Service
public class CubeService {

    public void initializeCube(Cube cube, CubeDto cubeDto) {
        cube.setY(cubeDto.getFront());
        cube.setB(cubeDto.getBack());
        cube.setG(cubeDto.getBottom());
        cube.setO(cubeDto.getLeft());
        cube.setR(cubeDto.getRight());
        cube.setW(cubeDto.getTop());
    }

}
