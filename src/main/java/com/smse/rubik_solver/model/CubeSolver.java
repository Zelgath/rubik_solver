package com.smse.rubik_solver.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CubeSolver {
    private String[] solveCrossOnLastLayer(Cube cube) {
        Color center = cube.getDown().get(1).get(1);
        String state = "" + (cube.getDown().get(0).get(1) == center ? "U" : "")
                + (cube.getDown().get(1).get(0) == center ? "L" : "") +
                (cube.getDown().get(1).get(2) == center ? "R" : "")
                + (cube.getDown().get(2).get(1) == center ? "D" : "");

        switch (state) {
            case "":
                return new String[] { "F", "L", "D", "L'", "D'", "F'", "B", "R", "D", "R'", "D'", "R", "D", "R'", "D'",
                        "B'" };
            case "UL":
                return new String[] { "B", "R", "D", "R'", "D'", "R", "D", "R'", "D'", "B'" };
            case "UR":
                return new String[] { "L", "B", "U", "B'", "U'", "B", "U", "B'", "U'", "L'" };
            case "LD":
                return new String[] { "R", "F", "D", "F'", "D'", "F", "D", "F'", "D'", "R'" };
            case "RD":
                return new String[] { "F", "L", "D", "L'", "D'", "L", "D", "L'", "D'", "F'" };
            case "UD":
                return new String[] { "L", "B", "D", "B'", "D'", "L'" };
            case "LR":
                return new String[] { "F", "L", "D", "L'", "D'", "F'" };
            default:
                return null;
        }
    }

    private String[] solveLastLayerPart2(Cube cube) {
        List<String> moves = new ArrayList<>();
        int count = howManyStickersInPlaceForPart2(cube);
        int c = 0;

        while (count < 2) {
            cube.moveD();
            c++;
            moves.add("D");
            count = howManyStickersInPlaceForPart2(cube);
        }

        Color frontSticker = cube.getFront().get(2).get(1);
        Color frontCenter = cube.getFront().get(1).get(1);
        Color rightSticker = cube.getRight().get(2).get(1);
        Color rightCenter = cube.getRight().get(1).get(1);
        Color leftSticker = cube.getLeft().get(2).get(1);
        Color leftCenter = cube.getLeft().get(1).get(1);
        Color backSticker = cube.getBack().get(2).get(1);
        Color backCenter = cube.getBack().get(1).get(1);
        String state = "" + (frontSticker == frontCenter ? "F" : "") + (rightSticker == rightCenter ? "R" : "") +
                (leftSticker == leftCenter ? "L" : "") + (backSticker == backCenter ? "B" : "");

        for (int i = 0; i < c; i++) {
            cube.moveDprim();
        }

        String[] additionalMoves;
        switch (state) {
            case ("RB"):
                additionalMoves = new String[] { "D'", "B", "D'", "D'", "B'", "D'", "B", "D'", "B'" };
                break;
            case ("FR"):
                additionalMoves = new String[] { "D'", "R", "D'", "D'", "R'", "D'", "R", "D'", "R'" };
                break;
            case ("LB"):
                additionalMoves = new String[] { "D'", "L", "D'", "D'", "L'", "D'", "L", "D'", "L'" };
                break;
            case ("FL"):
                additionalMoves = new String[] { "D'", "F", "D'", "D'", "F'", "D'", "F", "D'", "F'" };
                break;
            case ("RL"):
                additionalMoves = new String[] { "D'", "L", "D'", "D'", "L'", "D'", "L", "D'", "L'", "B", "D'",
                        "D'", "B'", "D'", "B", "D'", "B'" };
                break;
            case ("FB"):
                additionalMoves = new String[] { "D'", "B", "D'", "D'", "B'", "D'", "B", "D'", "B'", "R", "D'",
                        "D'", "R'", "D'", "R", "D'", "R'" };
                break;
            default:
                additionalMoves = new String[] {};
        }

        moves.addAll(Arrays.asList(additionalMoves));

        return moves.toArray(new String[0]);
    }

    private int howManyStickersInPlaceForPart2(Cube cube) {
        int count = 0;
        Color frontSticker = cube.getFront().get(2).get(1);
        Color frontCenter = cube.getFront().get(1).get(1);
        Color rightSticker = cube.getRight().get(2).get(1);
        Color rightCenter = cube.getRight().get(1).get(1);
        Color leftSticker = cube.getLeft().get(2).get(1);
        Color leftCenter = cube.getLeft().get(1).get(1);
        Color backSticker = cube.getBack().get(2).get(1);
        Color backCenter = cube.getBack().get(1).get(1);
        if (frontSticker == frontCenter) {
            count++;
        }
        if (rightSticker == rightCenter) {
            count++;
        }
        if (leftSticker == leftCenter) {
            count++;
        }
        if (backSticker == backCenter) {
            count++;
        }
        return count;
    }
}
