package com.smse.rubik_solver.service;

import org.springframework.stereotype.Service;

import com.smse.rubik_solver.dto.CubeDto;
import com.smse.rubik_solver.model.Cube;

@Service
public class CubeService {

    public void initializeCube(Cube cube, CubeDto cubeDto) {
        cube.setR(cubeDto.getFront());
        cube.setW(cubeDto.getTop());
        cube.setG(cubeDto.getLeft());
        cube.setB(cubeDto.getRight());
        cube.setY(cubeDto.getBottom());
        cube.setO(cubeDto.getBack());

    }

}
