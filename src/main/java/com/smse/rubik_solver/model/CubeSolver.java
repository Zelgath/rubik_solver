package com.smse.rubik_solver.model;

import java.util.*;

public class CubeSolver {

    public List<String> solveCube(Cube cube) {
        cube.initArrays();

        List<String> allMoves = new ArrayList<>();

        allMoves.addAll(solveMiddleLayer(cube));
        cube.syncToLists();

        allMoves.addAll(solveLastLayer(cube));
        cube.syncToLists();
        return optimizeMoves(allMoves);
    }

    public List<String> solveLastLayer(Cube cube) {
        cube.initArrays();

        List<String> allMoves = new ArrayList<>();

        allMoves.addAll(solveCrossOnLastLayer(cube));
        cube.syncToLists();

        allMoves.addAll(solveLastLayerPart2(cube));
        cube.syncToLists();

        allMoves.addAll(solveLastLayerPart3(cube));
        cube.syncToLists();

        allMoves.addAll(rotateEdges(cube));
        cube.syncToLists();

        return optimizeMoves(allMoves);

    }

    public List<String> solveMiddleLayer(Cube cube) {
        cube.initArrays();

        List<String> allMoves = new ArrayList<>();

        allMoves.addAll(prepareForSolvingMiddleLayer(cube));
        cube.syncToLists();

        allMoves.addAll(solveMiddleLayer1(cube));
        cube.syncToLists();

        return optimizeMoves(allMoves);

    }

    private List<String> optimizeMoves(List<String> moves) {
        Stack<String> stack = new Stack<>();

        for (String move : moves) {
            if (!stack.isEmpty()) {
                String top = stack.peek();
                if (isInverse(top, move)) {
                    stack.pop(); // Cancel inverse moves
                } else if (top.equals(move)) {
                    stack.pop();
                    if (!stack.isEmpty() && stack.peek().equals(move)) {
                        // Three same moves -> cancel all three and add inverse
                        stack.pop();
                        stack.push(getInverse(move));
                    } else {
                        // Two same moves -> store them temporarily
                        stack.push(move);
                        stack.push(move);
                    }
                } else {
                    stack.push(move);
                }
            } else {
                stack.push(move);
            }
        }

        return new ArrayList<>(stack);
    }

    private boolean isInverse(String move1, String move2) {
        return move1.equals(getInverse(move2));
    }

    private String getInverse(String move) {
        if (move.endsWith("'")) {
            return move.substring(0, move.length() - 1);
        } else {
            return move + "'";
        }
    }

    private List<String> solveCrossOnLastLayer(Cube cube) {
        Color center = cube.getDown().get(1).get(1);
        String state = ""
                + (cube.getDown().get(0).get(1) == center ? "U" : "")
                + (cube.getDown().get(1).get(0) == center ? "L" : "")
                + (cube.getDown().get(1).get(2) == center ? "R" : "")
                + (cube.getDown().get(2).get(1) == center ? "D" : "");

        List<String> moves;

        switch (state) {
            case "":
                moves = Arrays.asList("F", "L", "D", "L'", "D'", "F'", "B", "R", "D", "R'", "D'", "R", "D", "R'", "D'",
                        "B'");
                break;
            case "UL":
                moves = Arrays.asList("B", "R", "D", "R'", "D'", "R", "D", "R'", "D'", "B'");
                break;
            case "UR":
                moves = Arrays.asList("L", "B", "D", "B'", "D'", "B", "D", "B'", "D'", "L'");
                break;
            case "LD":
                moves = Arrays.asList("R", "F", "D", "F'", "D'", "F", "D", "F'", "D'", "R'");
                break;
            case "RD":
                moves = Arrays.asList("F", "L", "D", "L'", "D'", "L", "D", "L'", "D'", "F'");
                break;
            case "UD":
                moves = Arrays.asList("L", "B", "D", "B'", "D'", "L'");
                break;
            case "LR":
                moves = Arrays.asList("F", "L", "D", "L'", "D'", "F'");
                break;
            default:
                moves = new ArrayList<>();
        }

        cube.makeMovesFromList(moves);
        return moves;
    }

    private List<String> solveLastLayerPart2(Cube cube) {
        List<String> moves = new ArrayList<>();
        int count = howManyEdgesInPlaceForPart2(cube);

        int rotations = 0;
        while (count < 2 && rotations < 4) {
            cube.moveD();
            cube.syncToLists();
            moves.add("D");
            rotations++;
            count = howManyEdgesInPlaceForPart2(cube);
        }

        if (count < 2) {
            throw new IllegalStateException("Invalid cube state: unable to position at least two last layer edges.");
        }

        if (count == 4)
            return moves;

        Color frontSticker = cube.getFront().get(2).get(1);
        Color frontCenter = cube.getFront().get(1).get(1);
        Color rightSticker = cube.getRight().get(2).get(1);
        Color rightCenter = cube.getRight().get(1).get(1);
        Color leftSticker = cube.getLeft().get(2).get(1);
        Color leftCenter = cube.getLeft().get(1).get(1);
        Color backSticker = cube.getBack().get(2).get(1);
        Color backCenter = cube.getBack().get(1).get(1);

        String state = ""
                + (frontSticker == frontCenter ? "F" : "")
                + (rightSticker == rightCenter ? "R" : "")
                + (leftSticker == leftCenter ? "L" : "")
                + (backSticker == backCenter ? "B" : "");

        List<String> additionalMoves;
        switch (state) {
            case "RB":
                additionalMoves = Arrays.asList("D'", "B", "D'", "D'", "B'", "D'", "B", "D'", "B'");
                break;
            case "FR":
                additionalMoves = Arrays.asList("D'", "R", "D'", "D'", "R'", "D'", "R", "D'", "R'");
                break;
            case "LB":
                additionalMoves = Arrays.asList("D'", "L", "D'", "D'", "L'", "D'", "L", "D'", "L'");
                break;
            case "FL":
                additionalMoves = Arrays.asList("D'", "F", "D'", "D'", "F'", "D'", "F", "D'", "F'");
                break;
            case "RL":
                additionalMoves = Arrays.asList("D'", "L", "D'", "D'", "L'", "D'", "L", "D'", "L'",
                        "B", "D'", "D'", "B'", "D'", "B", "D'", "B'");
                break;
            case "FB":
                additionalMoves = Arrays.asList("D'", "B", "D'", "D'", "B'", "D'", "B", "D'", "B'",
                        "R", "D'", "D'", "R'", "D'", "R", "D'", "R'");
                break;
            default:
                additionalMoves = new ArrayList<>();
        }

        cube.makeMovesFromList(additionalMoves);
        cube.syncToLists();
        moves.addAll(additionalMoves);
        return moves;
    }

    private List<String> solveLastLayerPart3(Cube cube) {
        List<String> moves = new ArrayList<>();
        List<String> additionalMoves;
        int count = whichCornersAreInPlace(cube).size();

        if (count == 4)
            return moves;

        int tries = 0;
        while (count == 0 && tries < 4) {
            additionalMoves = Arrays.asList("D", "L", "D'", "R'", "D", "L'", "D'", "R");
            cube.makeMovesFromList(additionalMoves);
            cube.syncToLists();
            moves.addAll(additionalMoves);
            count = whichCornersAreInPlace(cube).size();
            tries++;
        }

        if (count == 0)
            throw new IllegalStateException("No corners could be placed after 4 attempts.");

        while (count == 1) {
            String correctCorner = whichCornersAreInPlace(cube).get(0);
            switch (correctCorner) {
                case "DLF":
                    additionalMoves = Arrays.asList("D", "L", "D'", "R'", "D", "L'", "D'", "R");
                    break;
                case "DFR":
                    additionalMoves = Arrays.asList("D", "F", "D'", "B'", "D", "F'", "D'", "B");
                    break;
                case "DRB":
                    additionalMoves = Arrays.asList("D", "R", "D'", "L'", "D", "R'", "D'", "L");
                    break;
                case "DBL":
                    additionalMoves = Arrays.asList("D", "B", "D'", "F'", "D", "B'", "D'", "F");
                    break;
                default:
                    throw new IllegalStateException("Unknown correct corner: " + correctCorner);
            }

            cube.makeMovesFromList(additionalMoves);
            cube.syncToLists();
            moves.addAll(additionalMoves);
            count = whichCornersAreInPlace(cube).size();
        }

        return moves;
    }

    private List<String> rotateEdges(Cube cube) {
        List<String> moves = new ArrayList<>();

        if (whichCornersAreInPlace(cube).size() == 4) {
            for (int i = 0; i < 4; i++) {
                Color color = cube.getDown().get(0).get(2);
                while (!color.equals(Color.Y)) {
                    cube.sexyMove();
                    cube.syncToLists();
                    moves.addAll(Arrays.asList("R", "U", "R'", "U'"));
                    color = cube.getDown().get(0).get(2);
                }
                cube.moveD();
                cube.syncToLists();
                moves.add("D");
            }
        }

        return moves;
    }

    private List<String> solveMiddleLayer1(Cube cube) {
        List<String> moves = new ArrayList<>();

        Set<Color> br = Set.of(Color.B, Color.R);
        Set<Color> bo = Set.of(Color.B, Color.O);
        Set<Color> gr = Set.of(Color.G, Color.R);
        Set<Color> go = Set.of(Color.G, Color.O);

        while (!isMiddleLayerSolved(cube)) {
            boolean moved = false;

            Set<Color> r = Set.of(cube.getR()[2][1], cube.getY()[0][1]);
            if (isEdgeOnCorrectPlaceForAlgForMiddleLayer(cube, Color.R)) {
                if (r.equals(br)) {
                    List<String> additional = Arrays.asList("D'", "R'", "D", "R", "D", "F", "D'", "F'");
                    moves.addAll(additional);
                    cube.makeMovesFromList(additional);
                    cube.syncToLists();
                    moved = true;
                }
                if (r.equals(gr)) {
                    List<String> additional = Arrays.asList("D", "L", "D'", "L'", "D'", "F'", "D", "F");
                    moves.addAll(additional);
                    cube.makeMovesFromList(additional);
                    cube.syncToLists();
                    moved = true;
                }
            }

            Set<Color> b = Set.of(cube.getB()[2][1], cube.getY()[1][2]);
            if (isEdgeOnCorrectPlaceForAlgForMiddleLayer(cube, Color.B)) {
                if (b.equals(bo)) {
                    List<String> additional = Arrays.asList("D'", "B'", "D", "B", "D", "R", "D'", "R'");
                    moves.addAll(additional);
                    cube.makeMovesFromList(additional);
                    cube.syncToLists();
                    moved = true;
                }
                if (b.equals(br)) {
                    List<String> additional = Arrays.asList("D", "F", "D'", "F'", "D'", "R'", "D", "R");
                    moves.addAll(additional);
                    cube.makeMovesFromList(additional);
                    cube.syncToLists();
                    moved = true;
                }
            }

            Set<Color> g = Set.of(cube.getG()[2][1], cube.getY()[1][0]);
            if (isEdgeOnCorrectPlaceForAlgForMiddleLayer(cube, Color.G)) {
                if (g.equals(gr)) {
                    List<String> additional = Arrays.asList("D'", "F'", "D", "F", "D", "L", "D'", "L'");
                    moves.addAll(additional);
                    cube.makeMovesFromList(additional);
                    cube.syncToLists();
                    moved = true;
                }
                if (g.equals(go)) {
                    List<String> additional = Arrays.asList("D", "B", "D'", "B'", "D'", "L'", "D", "L");
                    moves.addAll(additional);
                    cube.makeMovesFromList(additional);
                    cube.syncToLists();
                    moved = true;
                }
            }

            Set<Color> o = Set.of(cube.getO()[2][1], cube.getY()[2][1]);
            if (isEdgeOnCorrectPlaceForAlgForMiddleLayer(cube, Color.O)) {
                if (o.equals(go)) {
                    List<String> additional = Arrays.asList("D'", "L'", "D", "L", "D", "B", "D'", "B'");
                    moves.addAll(additional);
                    cube.makeMovesFromList(additional);
                    cube.syncToLists();
                    moved = true;
                }
                if (o.equals(bo)) {
                    List<String> additional = Arrays.asList("D", "R", "D'", "R'", "D'", "B'", "D", "B");
                    moves.addAll(additional);
                    cube.makeMovesFromList(additional);
                    cube.syncToLists();
                    moved = true;
                }
            }

            if (!moved) {
                moves.add("D");
                cube.moveD();
                cube.syncToLists();
            }
        }

        return moves;
    }

    private List<String> prepareForSolvingMiddleLayer(Cube cube) {
        List<String> moves = new ArrayList<>();

        while (!isMiddleLayerReadyForSolving(cube)) {
            boolean moved = false;
            if (!((cube.getB()[1][0] == Color.B && cube.getR()[1][2] == Color.R)
                    || (cube.getB()[1][0] == Color.Y || cube.getR()[1][2] == Color.Y))) {
                if (isEdgeOnCorrectPlaceForPreparingForMiddleLayer(cube, Color.R)) {
                    List<String> additional = Arrays.asList("D'", "R'", "D", "R", "D", "F", "D'", "F'");
                    moves.addAll(additional);
                    cube.makeMovesFromList(additional);
                    cube.syncToLists();
                    moved = true;
                } else if (isEdgeOnCorrectPlaceForPreparingForMiddleLayer(cube, Color.B)) {
                    List<String> additional = Arrays.asList("D", "F", "D'", "F'", "D'", "R'", "D", "R");
                    moves.addAll(additional);
                    cube.makeMovesFromList(additional);
                    cube.syncToLists();
                    moved = true;
                }
            }
            if (!((cube.getB()[1][2] == Color.B && cube.getO()[1][0] == Color.O)
                    || (cube.getB()[1][2] == Color.Y || cube.getO()[1][0] == Color.Y))) {
                if (isEdgeOnCorrectPlaceForPreparingForMiddleLayer(cube, Color.B)) {
                    List<String> additional = Arrays.asList("D'", "B'", "D", "B", "D", "R", "D'", "R'");
                    moves.addAll(additional);
                    cube.makeMovesFromList(additional);
                    cube.syncToLists();
                    moved = true;
                } else if (isEdgeOnCorrectPlaceForPreparingForMiddleLayer(cube, Color.O)) {
                    List<String> additional = Arrays.asList("D", "R", "D'", "R'", "D'", "B'", "D", "B");
                    moves.addAll(additional);
                    cube.makeMovesFromList(additional);
                    cube.syncToLists();
                    moved = true;
                }
            }
            if (!((cube.getO()[1][2] == Color.O && cube.getG()[1][0] == Color.G)
                    || (cube.getO()[1][2] == Color.Y || cube.getG()[1][0] == Color.Y))) {
                if (isEdgeOnCorrectPlaceForPreparingForMiddleLayer(cube, Color.O)) {
                    List<String> additional = Arrays.asList("D'", "L'", "D", "L", "D", "B", "D'", "B'");
                    moves.addAll(additional);
                    cube.makeMovesFromList(additional);
                    cube.syncToLists();
                    moved = true;
                } else if (isEdgeOnCorrectPlaceForPreparingForMiddleLayer(cube, Color.G)) {
                    List<String> additional = Arrays.asList("D", "B", "D'", "B'", "D'", "L'", "D", "L");
                    moves.addAll(additional);
                    cube.makeMovesFromList(additional);
                    cube.syncToLists();
                    moved = true;
                }
            }
            if (!((cube.getR()[1][0] == Color.R && cube.getG()[1][2] == Color.G)
                    || (cube.getR()[1][0] == Color.Y || cube.getG()[1][2] == Color.Y))) {
                if (isEdgeOnCorrectPlaceForPreparingForMiddleLayer(cube, Color.R)) {
                    List<String> additional = Arrays.asList("D", "L", "D'", "L'", "D'", "F'", "D", "F");
                    moves.addAll(additional);
                    cube.makeMovesFromList(additional);
                    cube.syncToLists();
                    moved = true;
                } else if (isEdgeOnCorrectPlaceForPreparingForMiddleLayer(cube, Color.G)) {
                    List<String> additional = Arrays.asList("D'", "F'", "D", "F", "D", "L", "D'", "L'");
                    moves.addAll(additional);
                    cube.makeMovesFromList(additional);
                    cube.syncToLists();
                    moved = true;
                }
            }
            if (!moved) {
                moves.add("D");
                cube.moveD();
                cube.syncToLists();
            }
        }
        return moves;

    }

    private boolean isMiddleLayerReadyForSolving(Cube cube) {
        // BR
        if (!((cube.getB()[1][0] == Color.B && cube.getR()[1][2] == Color.R)
                || (cube.getB()[1][0] == Color.Y || cube.getR()[1][2] == Color.Y)))
            return false;

        // BO
        if (!((cube.getB()[1][2] == Color.B && cube.getO()[1][0] == Color.O)
                || (cube.getB()[1][2] == Color.Y || cube.getO()[1][0] == Color.Y)))
            return false;

        // GR
        if (!((cube.getG()[1][2] == Color.G && cube.getR()[1][0] == Color.R)
                || (cube.getG()[1][2] == Color.Y || cube.getR()[1][0] == Color.Y)))
            return false;

        // GO
        if (!((cube.getG()[1][0] == Color.G && cube.getO()[1][2] == Color.O)
                || (cube.getG()[1][0] == Color.Y || cube.getO()[1][2] == Color.Y)))
            return false;

        return true;
    }

    private boolean isMiddleLayerSolved(Cube cube) {
        // BR
        if (!(cube.getB()[1][0] == Color.B && cube.getR()[1][2] == Color.R))
            return false;

        // BO
        if (!(cube.getB()[1][2] == Color.B && cube.getO()[1][0] == Color.O))
            return false;

        // GR
        if (!(cube.getG()[1][2] == Color.G && cube.getR()[1][0] == Color.R))
            return false;

        // GO
        if (!(cube.getG()[1][0] == Color.G && cube.getO()[1][2] == Color.O))
            return false;

        return true;
    }

    private boolean isEdgeOnCorrectPlaceForAlgForMiddleLayer(Cube cube, Color color) {
        Set<Color> br = Set.of(Color.B, Color.R);
        Set<Color> bo = Set.of(Color.B, Color.O);
        Set<Color> gr = Set.of(Color.G, Color.R);
        Set<Color> go = Set.of(Color.G, Color.O);

        Set<Color> r = Set.of(cube.getR()[2][1], cube.getY()[0][1]);
        Set<Color> b = Set.of(cube.getB()[2][1], cube.getY()[1][2]);
        Set<Color> g = Set.of(cube.getG()[2][1], cube.getY()[1][0]);
        Set<Color> o = Set.of(cube.getO()[2][1], cube.getY()[2][1]);
        if (color.equals(Color.R) && ((r.equals(br) || r.equals(gr)) && cube.getR()[2][1].equals(Color.R))) {
            return true;
        }
        if (color.equals(Color.B) && ((b.equals(br) || b.equals(bo)) && cube.getB()[2][1].equals(Color.B))) {
            return true;
        }
        if (color.equals(Color.G) && ((g.equals(gr) || g.equals(go)) && cube.getG()[2][1].equals(Color.G))) {
            return true;
        }
        if (color.equals(Color.O) && ((o.equals(bo) || o.equals(go)) && cube.getO()[2][1].equals(Color.O))) {
            return true;
        }
        return false;
    }

    private boolean isEdgeOnCorrectPlaceForPreparingForMiddleLayer(Cube cube, Color color) {

        Set<Color> r = Set.of(cube.getR()[2][1], cube.getY()[0][1]);
        Set<Color> b = Set.of(cube.getB()[2][1], cube.getY()[1][2]);
        Set<Color> g = Set.of(cube.getG()[2][1], cube.getY()[1][0]);
        Set<Color> o = Set.of(cube.getO()[2][1], cube.getY()[2][1]);
        if (color.equals(Color.R) && (r.contains(Color.Y))) {
            return true;
        }
        if (color.equals(Color.B) && (b.contains(Color.Y))) {
            return true;
        }
        if (color.equals(Color.G) && (g.contains(Color.Y))) {
            return true;
        }
        if (color.equals(Color.O) && (o.contains(Color.O))) {
            return true;
        }
        return false;
    }

    private List<String> whichCornersAreInPlace(Cube cube) {
        List<String> correctCorners = new ArrayList<>();

        Color downCenter = cube.getY()[1][1];
        Color frontCenter = cube.getR()[1][1];
        Color rightCenter = cube.getB()[1][1];
        Color backCenter = cube.getO()[1][1];
        Color leftCenter = cube.getG()[1][1];

        Set<Color> dfr = Set.of(cube.getY()[0][2], cube.getR()[2][2], cube.getB()[2][0]);
        if (dfr.equals(Set.of(downCenter, frontCenter, rightCenter)))
            correctCorners.add("DFR");

        Set<Color> drb = Set.of(cube.getY()[2][2], cube.getB()[2][2], cube.getO()[2][0]);
        if (drb.equals(Set.of(downCenter, rightCenter, backCenter)))
            correctCorners.add("DRB");

        Set<Color> dbl = Set.of(cube.getY()[2][0], cube.getO()[2][2], cube.getG()[2][0]);
        if (dbl.equals(Set.of(downCenter, backCenter, leftCenter)))
            correctCorners.add("DBL");

        Set<Color> dlf = Set.of(cube.getY()[0][0], cube.getG()[2][2], cube.getR()[2][0]);
        if (dlf.equals(Set.of(downCenter, leftCenter, frontCenter)))
            correctCorners.add("DLF");

        return correctCorners;
    }

    private int howManyEdgesInPlaceForPart2(Cube cube) {
        int count = 0;
        if (cube.getFront().get(2).get(1) == cube.getFront().get(1).get(1))
            count++;
        if (cube.getRight().get(2).get(1) == cube.getRight().get(1).get(1))
            count++;
        if (cube.getLeft().get(2).get(1) == cube.getLeft().get(1).get(1))
            count++;
        if (cube.getBack().get(2).get(1) == cube.getBack().get(1).get(1))
            count++;
        return count;
    }
}
