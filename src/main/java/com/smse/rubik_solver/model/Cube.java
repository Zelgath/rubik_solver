package com.smse.rubik_solver.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Cube {

    private List<List<Color>> up;
    private List<List<Color>> front;
    private List<List<Color>> right;
    private List<List<Color>> left;
    private List<List<Color>> back;
    private List<List<Color>> down;

    @JsonIgnore
    private Color[][] W;
    @JsonIgnore
    private Color[][] R;
    @JsonIgnore
    private Color[][] B;
    @JsonIgnore
    private Color[][] G;
    @JsonIgnore
    private Color[][] O;
    @JsonIgnore
    private Color[][] Y;

    public void initArrays() {
        this.W = toArray(up);
        this.R = toArray(front);
        this.B = toArray(right);
        this.G = toArray(left);
        this.O = toArray(back);
        this.Y = toArray(down);
    }

    public static Color[][] toArray(List<List<Color>> list) {
        Color[][] array = new Color[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                array[i][j] = list.get(i).get(j);
            }
        }
        return array;
    }

    public void syncToLists() {
        this.up = fromArray(W);
        this.front = fromArray(R);
        this.right = fromArray(B);
        this.left = fromArray(G);
        this.back = fromArray(O);
        this.down = fromArray(Y);
    }

    public static List<List<Color>> fromArray(Color[][] array) {
        List<List<Color>> list = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            List<Color> row = new ArrayList<>();
            for (int j = 0; j < 3; j++) {
                row.add(array[i][j]);
            }
            list.add(row);
        }
        return list;
    }

    public List<List<Color>> makeCompletedFace(Color color) {
        List<List<Color>> face = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            List<Color> row = new ArrayList<>();
            for (int j = 0; j < 3; j++) {
                row.add(color);
            }
            face.add(row);
        }
        return face;
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

    private static final String[] MOVES = {
            "U", "U'",
            "D", "D'",
            "L", "L'",
            "R", "R'",
            "F", "F'",
            "B", "B'"
    };

    private boolean isCubeCompleted() {
        return isFaceCompleted(W) && isFaceCompleted(R) && isFaceCompleted(B) && isFaceCompleted(G)
                && isFaceCompleted(O)
                && isFaceCompleted(Y);
    }

    private static boolean isFaceCompleted(Color[][] face) {
        for (int i = 0; i < face.length; i++) {
            for (int j = 0; j < face.length; j++) {
                if (face[i][j] != face[1][1]) {
                    return false;
                }
            }
        }
        return true;
    }

    private static void printFace(Color[][] face) {
        for (int i = 0; i < face.length; i++) {
            for (int j = 0; j < face.length; j++) {
                System.out.print(face[i][j] + " ");
            }
            System.out.println();
        }
    }

    private static void rotateClockWise(Color[][] face) {
        Color[][] temp = new Color[3][3];
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

    private static void rotateAntiClockWise(Color[][] face) {
        Color[][] temp = new Color[3][3];
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
        Color[] temp = new Color[3];

        for (int i = 0; i < 3; i++) {
            temp[i] = W[i][2];
        }
        for (int i = 0; i < 3; i++) {
            W[i][2] = R[i][2];
        }
        for (int i = 0; i < 3; i++) {
            R[i][2] = Y[i][2];
        }
        for (int i = 0; i < 3; i++) {
            Y[i][2] = O[2 - i][0];
        }
        for (int i = 0; i < 3; i++) {
            O[2 - i][0] = temp[i];
        }
    }

    private void moveRprim() {
        rotateAntiClockWise(B);
        Color[] temp = new Color[3];

        for (int i = 0; i < 3; i++) {
            temp[i] = W[i][2];
        }
        for (int i = 0; i < 3; i++) {
            W[i][2] = O[2 - i][0];
        }
        for (int i = 0; i < 3; i++) {
            O[2 - i][0] = Y[i][2];
        }
        for (int i = 0; i < 3; i++) {
            Y[i][2] = R[i][2];
        }
        for (int i = 0; i < 3; i++) {
            R[i][2] = temp[i];
        }
    }

    private void moveL() {
        rotateClockWise(G);
        Color[] temp = new Color[3];

        for (int i = 0; i < 3; i++) {
            temp[i] = W[i][0];
        }
        for (int i = 0; i < 3; i++) {
            W[i][0] = O[2 - i][2];
        }
        for (int i = 0; i < 3; i++) {
            O[2 - i][2] = Y[i][0];
        }
        for (int i = 0; i < 3; i++) {
            Y[i][0] = R[i][0];
        }
        for (int i = 0; i < 3; i++) {
            R[i][0] = temp[i];
        }
    }

    private void moveLprim() {
        rotateAntiClockWise(G);
        Color[] temp = new Color[3];

        for (int i = 0; i < 3; i++) {
            temp[i] = W[i][0];
        }
        for (int i = 0; i < 3; i++) {
            W[i][0] = R[i][0];
        }
        for (int i = 0; i < 3; i++) {
            R[i][0] = Y[i][0];
        }
        for (int i = 0; i < 3; i++) {
            Y[i][0] = O[2 - i][2];
        }
        for (int i = 0; i < 3; i++) {
            O[2 - i][2] = temp[i];
        }
    }

    private void moveU() {
        rotateClockWise(W);
        Color[] temp = new Color[3];

        for (int i = 0; i < 3; i++) {
            temp[i] = R[0][i];
        }
        for (int i = 0; i < 3; i++) {
            R[0][i] = B[0][i];
        }
        for (int i = 0; i < 3; i++) {
            B[0][i] = O[0][i];
        }
        for (int i = 0; i < 3; i++) {
            O[0][i] = G[0][i];
        }
        for (int i = 0; i < 3; i++) {
            G[0][i] = temp[i];
        }
    }

    private void moveUprim() {
        rotateAntiClockWise(W);
        Color[] temp = new Color[3];

        for (int i = 0; i < 3; i++) {
            temp[i] = R[0][i];
        }
        for (int i = 0; i < 3; i++) {
            R[0][i] = G[0][i];
        }
        for (int i = 0; i < 3; i++) {
            G[0][i] = O[0][i];
        }
        for (int i = 0; i < 3; i++) {
            O[0][i] = B[0][i];
        }
        for (int i = 0; i < 3; i++) {
            B[0][i] = temp[i];
        }
    }

    private void moveF() {
        rotateClockWise(R);
        Color[] temp = new Color[3];
        for (int i = 0; i < 3; i++) {
            temp[i] = W[2][i];
        }
        for (int i = 0; i < 3; i++) {
            W[2][i] = G[2 - i][2];
        }
        for (int i = 0; i < 3; i++) {
            G[i][2] = Y[0][i];
        }
        for (int i = 0; i < 3; i++) {
            Y[0][i] = B[2 - i][0];
        }
        for (int i = 0; i < 3; i++) {
            B[i][0] = temp[i];
        }
    }

    private void moveFprim() {
        rotateAntiClockWise(R);
        Color[] temp = new Color[3];
        for (int i = 0; i < 3; i++) {
            temp[i] = W[2][i];
        }
        for (int i = 0; i < 3; i++) {
            W[2][i] = B[i][0];
        }
        for (int i = 0; i < 3; i++) {
            B[i][0] = Y[0][2 - i];
        }
        for (int i = 0; i < 3; i++) {
            Y[0][i] = G[i][2];
        }
        for (int i = 0; i < 3; i++) {
            G[i][2] = temp[2 - i];
        }
    }

    private void moveD() {
        rotateClockWise(Y);
        Color[] temp = new Color[3];
        for (int i = 0; i < 3; i++) {
            temp[i] = R[2][i];
        }
        for (int i = 0; i < 3; i++) {
            R[2][i] = G[2][i];
        }
        for (int i = 0; i < 3; i++) {
            G[2][i] = O[2][i];
        }
        for (int i = 0; i < 3; i++) {
            O[2][i] = B[2][i];
        }
        for (int i = 0; i < 3; i++) {
            B[2][i] = temp[i];
        }
    }

    private void moveDprim() {
        rotateAntiClockWise(Y);
        Color[] temp = new Color[3];
        for (int i = 0; i < 3; i++) {
            temp[i] = R[2][i];
        }
        for (int i = 0; i < 3; i++) {
            R[2][i] = B[2][i];
        }
        for (int i = 0; i < 3; i++) {
            B[2][i] = O[2][i];
        }
        for (int i = 0; i < 3; i++) {
            O[2][i] = G[2][i];
        }
        for (int i = 0; i < 3; i++) {
            G[2][i] = temp[i];
        }
    }

    private void moveB() {
        rotateClockWise(O);
        Color[] temp = new Color[3];
        for (int i = 0; i < 3; i++) {
            temp[i] = W[0][i];
        }
        for (int i = 0; i < 3; i++) {
            W[0][i] = B[i][2];
        }
        for (int i = 0; i < 3; i++) {
            B[i][2] = Y[2][2 - i];
        }
        for (int i = 0; i < 3; i++) {
            Y[2][i] = G[i][0];
        }
        for (int i = 0; i < 3; i++) {
            G[2 - i][0] = temp[i];
        }
    }

    private void moveBprim() {
        rotateAntiClockWise(O);
        Color[] temp = new Color[3];
        for (int i = 0; i < 3; i++) {
            temp[i] = W[0][i];
        }
        for (int i = 0; i < 3; i++) {
            W[0][i] = G[2 - i][0];
        }
        for (int i = 0; i < 3; i++) {
            G[i][0] = Y[2][i];
        }
        for (int i = 0; i < 3; i++) {
            Y[2][i] = B[2 - i][2];
        }
        for (int i = 0; i < 3; i++) {
            B[i][2] = temp[i];
        }
    }

    private void sexyMove() {
        moveR();
        moveU();
        moveRprim();
        moveUprim();
    }

    private String[] solveCrossOnLastLayer() {
        Color center = Y[1][1];
        String state = "" + (Y[0][1] == center ? "U" : "") + (Y[1][0] == center ? "L" : "") +
                (Y[1][2] == center ? "R" : "") + (Y[2][1] == center ? "D" : "");

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

    private String[] solveLastLayerPart2() {
        List<String> moves = new ArrayList<>();
        int count = howManyStickersInPlaceForPart2();
        int c = 0;

        while (count < 2) {
            moveD();
            c++;
            moves.add("D");
            count = howManyStickersInPlaceForPart2();
        }

        Color frontSticker = R[2][1];
        Color frontCenter = R[1][1];
        Color rightSticker = B[2][1];
        Color rightCenter = B[1][1];
        Color leftSticker = G[2][1];
        Color leftCenter = G[1][1];
        Color backSticker = O[2][1];
        Color backCenter = O[1][1];
        String state = "" + (frontSticker == frontCenter ? "F" : "") + (rightSticker == rightCenter ? "R" : "") +
                (leftSticker == leftCenter ? "L" : "") + (backSticker == backCenter ? "B" : "");

        for (int i = 0; i < c; i++) {
            moveDprim();
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

    private int howManyStickersInPlaceForPart2() {
        int count = 0;
        Color frontSticker = R[2][1];
        Color frontCenter = R[1][1];
        Color rightSticker = B[2][1];
        Color rightCenter = B[1][1];
        Color leftSticker = G[2][1];
        Color leftCenter = G[1][1];
        Color backSticker = O[2][1];
        Color backCenter = O[1][1];
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

    public void makeMovesFromList(List<String> moves) {
        if (moves == null || moves.isEmpty()) {
            return;
        }

        for (String move : moves) {
            switch (move) {
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
                    System.out.println("Unknown move: " + move);
            }
        }
    }

    private static List<String> getRandomMoves(int n) {
        Random random = new Random();
        List<String> randomMoves = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            String move = MOVES[random.nextInt(MOVES.length)];
            randomMoves.add(move);
        }

        return randomMoves;
    }

    public void randomScramble(int n) {
        makeMovesFromList(getRandomMoves(n));
    }

}
