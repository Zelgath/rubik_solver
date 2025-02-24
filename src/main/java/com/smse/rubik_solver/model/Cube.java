package com.smse.rubik_solver.model;

import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class Cube {
    static char[][] W = { { 'W', 'W', 'W' }, { 'W', 'W', 'W' }, { 'W', 'W', 'W' } };
    static char[][] R = { { 'R', 'R', 'R' }, { 'R', 'R', 'R' }, { 'R', 'R', 'R' } };
    static char[][] B = { { 'B', 'B', 'B' }, { 'B', 'B', 'B' }, { 'B', 'B', 'B' } };
    static char[][] G = { { 'G', 'G', 'G' }, { 'G', 'G', 'G' }, { 'G', 'G', 'G' } };
    static char[][] O = { { 'O', 'O', 'O' }, { 'O', 'O', 'O' }, { 'O', 'O', 'O' } };
    static char[][] Y = { { 'Y', 'Y', 'Y' }, { 'Y', 'Y', 'Y' }, { 'Y', 'Y', 'Y' } };

    public Cube(char[][] W, char[][] R, char[][] B, char[][] G, char[][] O, char[][] Y) {
        this.W = W;
        this.R = R;
        this.B = B;
        this.G = G;
        this.O = O;
        this.Y = Y;
    }

    public static void main(String[] args) {
        Cube cube = new Cube(W, R, B, G, O, Y);
        cube.printCube();
        // cube.makeMovesFromList(cube.solveCrossOnLastLayer());

    }

    private void printCube() {
        System.out.println("Up");
        printFace(W);
        System.out.println("Front");
        printFace(R);
        System.out.println("Right");
        printFace(B);
        System.out.println("Left");
        printFace(G);
        System.out.println("Down");
        printFace(Y);
        System.out.println("Back");
        printFace(O);
    }

    private boolean isCubeCompleted() {
        if (isFaceComplted(W) && isFaceComplted(R) && isFaceComplted(B) && isFaceComplted(G) && isFaceComplted(O)
                && isFaceComplted(Y)) {
            return true;
        }
        return false;
    }

    private static boolean isFaceComplted(char[][] face) {
        for (int i = 0; i < face.length; i++) {
            for (int j = 0; j < face.length; j++) {
                if (face[i][j] != face[1][1]) {
                    return false;
                }
            }
        }
        return true;
    }

    private static void printFace(char[][] face) {
        for (int i = 0; i < face.length; i++) {
            for (int j = 0; j < face.length; j++) {
                System.out.print(face[i][j] + " ");
            }
            System.out.println();
        }
    }

    private static void rotateClockWise(char[][] face) {
        char[][] temp = new char[3][3];
        for (int i = 0; i < face.length; i++) {
            for (int j = 0; j < face.length; j++) {
                temp[j][2 - i] = face[i][j];
            }
        }
        for (int i = 0; i < face.length; i++) {
            for (int j = 0; j < face.length; j++) {
                face[i][j] = temp[i][j];
            }
        }

    }

    private static void rotateAntiClockWise(char[][] face) {
        char[][] temp = new char[3][3];
        for (int i = 0; i < face.length; i++) {
            for (int j = 0; j < face.length; j++) {
                temp[2 - j][i] = face[i][j];
            }
        }
        for (int i = 0; i < face.length; i++) {
            for (int j = 0; j < face.length; j++) {
                face[i][j] = temp[i][j];
            }
        }
    }

    private void moveR() {
        rotateClockWise(B);
        char[] temp = new char[3];
        for (int i = 0; i < 3; i++) {
            temp[i] = W[i][2];
            W[i][2] = R[i][2];
            R[i][2] = Y[i][2];
            Y[i][2] = O[2 - i][0];
            O[2 - i][0] = temp[i];
        }
        printCube();
    }

    private void moveRprim() {
        rotateAntiClockWise(B);
        char[] temp = new char[3];
        for (int i = 0; i < 3; i++) {
            temp[i] = W[i][2];
            W[i][2] = O[2 - i][0];
            O[2 - i][0] = Y[i][2];
            Y[i][2] = R[i][2];
            R[i][2] = temp[i];
        }
        printCube();
    }

    private void moveL() {
        rotateClockWise(G);
        char[] temp = new char[3];
        for (int i = 0; i < 3; i++) {
            temp[i] = W[i][0];
            W[i][0] = O[2 - i][2];
            O[2 - i][2] = Y[i][0];
            Y[i][0] = R[i][0];
            R[i][0] = temp[i];
        }
        printCube();
    }

    private void moveLprim() {
        rotateAntiClockWise(G);
        char[] temp = new char[3];
        for (int i = 0; i < 3; i++) {
            temp[i] = W[i][0];
            W[i][0] = R[i][0];
            R[i][0] = Y[i][0];
            Y[i][0] = O[2 - i][2];
            O[2 - i][2] = temp[i];
        }
        printCube();
    }

    private void moveU() {
        rotateClockWise(W);
        char[] temp = new char[3];
        for (int i = 0; i < 3; i++) {
            temp[i] = R[0][i];
            R[0][i] = B[0][i];
            B[0][i] = O[0][i];
            O[0][i] = G[0][i];
            G[0][i] = temp[i];
        }
        printCube();
    }

    private void moveUprim() {
        rotateAntiClockWise(W);
        char[] temp = new char[3];
        for (int i = 0; i < 3; i++) {
            temp[i] = R[0][i];
            R[0][i] = G[0][i];
            G[0][i] = O[0][i];
            O[0][i] = B[0][i];
            B[0][i] = temp[i];
        }
        printCube();
    }

    private void moveF() {
        rotateClockWise(R);
        char[] temp = new char[3];
        for (int i = 0; i < 3; i++) {
            temp[i] = W[2][i];
            W[2][i] = G[i][2];
            G[i][2] = Y[0][i];
            Y[0][i] = B[i][0];
            B[i][0] = temp[i];
        }
        printCube();
    }

    private void moveFprim() {
        rotateAntiClockWise(R);
        char[] temp = new char[3];
        for (int i = 0; i < 3; i++) {
            temp[i] = W[2][i];
            W[2][i] = B[i][0];
            B[i][0] = Y[0][i];
            Y[0][i] = G[i][2];
            G[i][2] = temp[i];
        }
        printCube();
    }

    private void moveD() {
        rotateClockWise(Y);
        char[] temp = new char[3];
        for (int i = 0; i < 3; i++) {
            temp[i] = R[2][i];
            R[2][i] = G[2][i];
            G[2][i] = O[2][i];
            O[2][i] = B[2][i];
            B[2][i] = temp[i];
        }
        printCube();
    }

    private void moveDprim() {
        rotateAntiClockWise(Y);
        char[] temp = new char[3];
        for (int i = 0; i < 3; i++) {
            temp[i] = R[2][i];
            R[2][i] = B[2][i];
            B[2][i] = O[2][i];
            O[2][i] = G[2][i];
            G[2][i] = temp[i];
        }
        printCube();
    }

    private void moveB() {
        rotateClockWise(O);
        char[] temp = new char[3];
        for (int i = 0; i < 3; i++) {
            temp[i] = W[0][i];
            W[0][i] = B[i][2];
            B[i][2] = Y[2][2 - i];
            Y[2][i] = G[i][0];
            G[2 - i][0] = temp[i];
        }
        printCube();
    }

    private void moveBprim() {
        rotateAntiClockWise(O);
        char[] temp = new char[3];
        for (int i = 0; i < 3; i++) {
            temp[i] = W[0][i];
            W[0][i] = G[i][0];
            G[i][0] = Y[2][i];
            Y[2][i] = B[2 - i][2];
            B[i][2] = temp[i];
        }
        printCube();
    }

    private void sexyMove() {
        moveR();
        moveU();
        moveRprim();
        moveUprim();
    }

    private String[] solveCrossOnLastLayer() {
        char center = Y[1][1];
        char upper = Y[0][1];
        char left = Y[1][0];
        char right = Y[1][2];
        char down = Y[2][1];
        if (upper != center && left != center && right != center && down != center) {
            return new String[] { "F", "L", "D", "L'", "D'", "F'", "B", "R", "D", "R'", "D'", "R", "D", "R'", "D'",
                    "B'" };
        } else if (upper == center && left == center) {
            return new String[] { "B", "R", "D", "R'", "D'", "R", "D", "R'", "D'", "B'" };
        } else if (upper == center && right == center) {
            return new String[] { "L", "B", "U", "B'", "U'", "B", "U", "B'", "U'", "L'" };
        } else if (down == center && left == center) {
            return new String[] { "R", "F", "D", "F'", "D'", "F", "D", "F'", "D'", "R'" };
        } else if (down == center && right == center) {
            return new String[] { "F", "L", "D", "L'", "D'", "L", "D", "L'", "D'", "F'" };
        } else if (down == center && upper == center) {
            return new String[] { "L", "B", "D", "B'", "D'", "L'" };
        } else if (left == center && right == center) {
            return new String[] { "F", "L", "D", "L'", "D'", "F'" };
        } else {
            return null;
        }

    }

    private String[] solveLastLayerPart2() {
        List<String> moves = new ArrayList<>();
        int count = howManyStickersInPlaceForPart2();

        while (count < 2) {
            moveD();
            moves.add("D");
            count = howManyStickersInPlaceForPart2();
        }

        char frontSticker = R[2][1];
        char frontCenter = R[1][1];
        char rightSticker = B[2][1];
        char rightCenter = B[1][1];
        char leftSticker = G[2][1];
        char leftCenter = G[1][1];
        char backSticker = O[2][1];
        char backCenter = O[1][1];

        String[] additionalMoves;
        if (frontSticker == frontCenter && rightSticker == rightCenter && leftSticker == leftCenter
                && backSticker == backCenter) {
            return moves.toArray(new String[0]);
        } else if (rightSticker == rightCenter && backSticker == backCenter) {
            additionalMoves = new String[] { "D'", "B", "D'", "D'", "B'", "D'", "B", "D'", "B'" };
        } else if (rightSticker == rightCenter && frontSticker == frontCenter) {
            additionalMoves = new String[] { "D'", "R", "D'", "D'", "R'", "D'", "R", "D'", "R'" };
        } else if (leftSticker == leftCenter && backSticker == backCenter) {
            additionalMoves = new String[] { "D'", "L", "D'", "D'", "L'", "D'", "L", "D'", "L'" };
        } else if (leftSticker == leftCenter && frontSticker == frontCenter) {
            additionalMoves = new String[] { "D'", "F", "D'", "D'", "F'", "D'", "F", "D'", "F'" };
        } else if (rightSticker == rightCenter && leftSticker == leftCenter) {
            additionalMoves = new String[] { "D'", "L", "D'", "D'", "L'", "D'", "L", "D'", "L'", "B", "D'",
                    "D'", "B'", "D'", "B", "D'", "B'" };
        } else if (frontSticker == frontCenter && backSticker == backCenter) {
            additionalMoves = new String[] { "D'", "B", "D'", "D'", "B'", "D'", "B", "D'", "B'", "R", "D'",
                    "D'", "R'", "D'", "R", "D'", "R'" };
        } else {
            additionalMoves = new String[] {};
        }

        moves.addAll(Arrays.asList(additionalMoves));

        return moves.toArray(new String[0]);
    }

    private int howManyStickersInPlaceForPart2() {
        int count = 0;
        char frontSticker = R[2][1];
        char frontCenter = R[1][1];
        char rightSticker = B[2][1];
        char rightCenter = B[1][1];
        char leftSticker = G[2][1];
        char leftCenter = G[1][1];
        char backSticker = O[2][1];
        char backCenter = O[1][1];
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

    private void makeMovesFromList(String[] moves) {
        for (int i = 0; i < moves.length; i++) {
            switch (moves[i]) {
                case "R":
                    moveR();
                    break;
                case "R'":
                    moveRprim();
                    break;
                case "L":
                    moveL();
                    break;
                case "L'":
                    moveLprim();
                    break;
                case "U":
                    moveU();
                    break;
                case "U'":
                    moveUprim();
                    break;
                case "D":
                    moveD();
                    break;
                case "D'":
                    moveDprim();
                    break;
                case "F":
                    moveF();
                    break;
                case "F'":
                    moveFprim();
                    break;
                case "B":
                    moveB();
                    break;
                case "B'":
                    moveBprim();
                    break;
                default:
                    System.out.println("Unknown move: " + moves[i]);
            }
        }
    }

}
