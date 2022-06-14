package ubc.cosc322;

import java.util.ArrayList;
import java.util.HashMap;

public class Agent {
    // Attributes
    private boolean white;
    private ArrayList<Integer> QueenPosCurEnemy;
    private ArrayList<Integer> QueenPosNewEnemy;
    private ArrayList<Integer> ArrowPosEnemy;
    private GameBoard GameBoard;
    private ArrayList<Integer> QueenPosCurSend;
    private ArrayList<Integer> QueenPosNewSend;
    private ArrayList<Integer> ArrowPosSend;
    private boolean outOfMoves;

    public boolean isOutOfMoves() {
        return outOfMoves;
    }

    public void setOutOfMoves(boolean outOfMoves) {
        this.outOfMoves = outOfMoves;
    }

    public ArrayList<Integer> getQueenPosCurSend() {
        return QueenPosCurSend;
    }

    public void setQueenPosCurSend(ArrayList<Integer> queenPosCurSend) {
        QueenPosCurSend = queenPosCurSend;
    }

    public ArrayList<Integer> getQueenPosNewSend() {
        return QueenPosNewSend;
    }

    public void setQueenPosNewSend(ArrayList<Integer> queenPosNewSend) {
        QueenPosNewSend = queenPosNewSend;
    }

    public ArrayList<Integer> getArrowPosSend() {
        return ArrowPosSend;
    }

    public void setArrowPosSend(ArrayList<Integer> arrowPosSend) {
        ArrowPosSend = arrowPosSend;
    }

    // Constructors
    public Agent(ArrayList<Integer> QueenPosCurEnemy, ArrayList<Integer> QueenPosNewEnemy,
                 ArrayList<Integer> ArrowPosEnemy, GameBoard GameBoard, boolean white) {
        this.setArrowPosEnemy(ArrowPosEnemy);
        this.setGameBoard(GameBoard);
        this.setQueenPosCurEnemy(QueenPosCurEnemy);
        this.setQueenPosNewEnemy(QueenPosNewEnemy);
        this.setWhite(white);
        this.QueenPosCurSend = new ArrayList<>();
        this.QueenPosNewSend = new ArrayList<>();
        this.ArrowPosSend = new ArrayList<>();

    }

    public Agent(GameBoard GameBoard, boolean white) {
        this.setGameBoard(GameBoard);
        this.setWhite(white);
        this.QueenPosCurSend = new ArrayList<>();
        this.QueenPosNewSend = new ArrayList<>();
        this.ArrowPosSend = new ArrayList<>();
    }

    public GameBoard getGameBoard() {
        return GameBoard;
    }

    public void setGameBoard(GameBoard GameBoard) {
        this.GameBoard = GameBoard;
    }

    // Getters and Setters
    public boolean isWhite() {
        return white;
    }

    public void setWhite(boolean white) {
        this.white = white;
    }

    public ArrayList<Integer> getQueenPosCurEnemy() {
        return QueenPosCurEnemy;
    }

    public void setQueenPosCurEnemy(ArrayList<Integer> queenPosCurEnemy) {
        QueenPosCurEnemy = queenPosCurEnemy;
    }

    public ArrayList<Integer> getQueenPosNewEnemy() {
        return QueenPosNewEnemy;
    }

    public void setQueenPosNewEnemy(ArrayList<Integer> queenPosNewEnemy) {
        QueenPosNewEnemy = queenPosNewEnemy;
    }

    public ArrayList<Integer> getArrowPosEnemy() {
        return ArrowPosEnemy;
    }

    public void setArrowPosEnemy(ArrayList<Integer> arrowPosEnemy) {
        ArrowPosEnemy = arrowPosEnemy;
    }

    public void legalityCheck(GameBoard previous) {
        // check legaility of move
        HashMap<ArrayList<Integer>, ArrayList<Integer>> table = GameBoard.makeHashTable();
        LegalMove moveGetter = new LegalMove();
        boolean illegalQueenMove = true;
        ArrayList<Position> moves = moveGetter.getLegalMove(
                new Queen(new Position(table.get(QueenPosCurEnemy).get(0), table.get(QueenPosCurEnemy).get(1))),
                previous);
        for (Position position : moves) {
            if (position.getX() == table.get(QueenPosNewEnemy).get(0)
                    && position.getY() == table.get(QueenPosNewEnemy).get(1)) {
                illegalQueenMove = false;
                break;
            }
        }

        boolean illegalArrowMove = true;
        LegalArrow arrowGetter = new LegalArrow();
        GameBoard temp = (GameBoard) previous.clone();
        temp.updateGameBoard(temp, QueenPosCurEnemy, QueenPosNewEnemy, true);
        ArrayList<Position> arrowMoves = arrowGetter.getLegalArrow(table.get(QueenPosNewEnemy).get(0),
                table.get(QueenPosNewEnemy).get(1), temp);

        for (Position position2 : arrowMoves) {
            if (position2.getX() == table.get(ArrowPosEnemy).get(0)
                    && position2.getY() == table.get(ArrowPosEnemy).get(1)) {
                illegalArrowMove = false;
                break;
            }
        }

        if (illegalQueenMove || illegalArrowMove) {
            System.out.println("opponenet made an illegal move");
            System.out.println(
                    "*************************************************************************************************************************************************************************************************************");

            System.out.println(
                    "Enmey Queen's old position: " + QueenPosCurEnemy.get(0) + "," + QueenPosCurEnemy.get(1));
            System.out.println(
                    "Enmey Queen's New position: " + QueenPosNewEnemy.get(0) + "," + QueenPosNewEnemy.get(1));
            System.out.println("Enmey Arrow's New position: " + ArrowPosEnemy.get(0) + "," + ArrowPosEnemy.get(1));
            System.out.println("GameBoard before move");
            this.GameBoard.printGameBoard();
            System.out.println("opponent's queen move");
            GameBoard temp4 = (GameBoard) this.GameBoard.clone();
            temp4 = temp4.updateGameBoard(temp4, QueenPosCurEnemy, QueenPosNewEnemy, ArrowPosEnemy, true);
            temp4.printGameBoard();
        }

    }



    public int calculateDepth() {
        int ply = 1;
        int emptySpotCounter = 0;
        for (int i = 0; i < this.getGameBoard().GameBoard.length; i++) {

            for (int j = 0; j < this.getGameBoard().GameBoard.length; j++) {
                if (this.getGameBoard().GameBoard[i][j] == 0)
                    emptySpotCounter++;

            }
        }
        int possible = emptySpotCounter * emptySpotCounter;
        if (possible > 3000)
            ply = 2;
        else if (possible <= 3000)
            ply = 3;


        System.out.println("ply chosen and possible count: (" + ply + "," + possible + ")");
        return ply;
    }

    public void makeMove() {
        GameBoard GameBoardBeforeMove = (GameBoard) this.getGameBoard().clone();
        NodeSearchTree partial = new NodeSearchTree();
        partial.getRoot().setGameBoard(this.getGameBoard());
        int ply = this.calculateDepth();
        partial.generatePartialGameTree(GameBoardBeforeMove, this.isWhite(), ply, partial.getRoot());

        GameBoard moveToMake;
        if (partial.getRoot().getChildren().size() == 0) {
            this.setOutOfMoves(true);
            return;
        }
        moveToMake = minimax(partial.getRoot(), ply);
        this.setGameBoard(moveToMake);
        // Get Move Coords

        int[] oldCoord = new int[2];
        int[] newCoord = new int[2];
        int[] newArrowcoord = new int[2];

        if (this.isWhite()) {
            for (int i = 0; i < GameBoardBeforeMove.GameBoard.length; i++) {
                for (int j = 0; j < GameBoardBeforeMove.GameBoard.length; j++) {

                    if (moveToMake.GameBoard[i][j] == 3 && GameBoardBeforeMove.GameBoard[i][j] != 3) {
                        newArrowcoord[0] = i;
                        newArrowcoord[1] = j;

                    }
                    if (GameBoardBeforeMove.GameBoard[i][j] == 0 && moveToMake.GameBoard[i][j] == 1) {
                        newCoord[0] = i;
                        newCoord[1] = j;

                    }

                    if (moveToMake.GameBoard[i][j] != 1 && GameBoardBeforeMove.GameBoard[i][j] == 1) {

                        oldCoord[0] = i;
                        oldCoord[1] = j;
                    }

                }
            }
        } else if(!this.isWhite()) {

            for (int i = 0; i < GameBoardBeforeMove.GameBoard.length; i++) {
                for (int j = 0; j < GameBoardBeforeMove.GameBoard.length; j++) {

                    if (moveToMake.GameBoard[i][j] == 3 && GameBoardBeforeMove.GameBoard[i][j] != 3) {
                        newArrowcoord[0] = i;
                        newArrowcoord[1] = j;

                    }
                    if (GameBoardBeforeMove.GameBoard[i][j] == 0 && moveToMake.GameBoard[i][j] == 2) {
                        newCoord[0] = i;
                        newCoord[1] = j;

                    }

                    if (moveToMake.GameBoard[i][j] != 2 && GameBoardBeforeMove.GameBoard[i][j] == 2) {

                        oldCoord[0] = i;
                        oldCoord[1] = j;
                    }

                }
            }
        }
        ArrayList<Integer> QueenNew = new ArrayList<Integer>();
        QueenNew.add(newCoord[0]);
        QueenNew.add(newCoord[1]);
        ArrayList<Integer> Arrow = new ArrayList<Integer>();
        Arrow.add(newArrowcoord[0]);
        Arrow.add(newArrowcoord[1]);
        ArrayList<Integer> QueenOld = new ArrayList<>();
        QueenOld.add(oldCoord[0]);
        QueenOld.add(oldCoord[1]);

        // Convert To sendable coords
        HashMap<ArrayList<Integer>, ArrayList<Integer>> gaoTable = ubc.cosc322.GameBoard.makeGaoTable();

        // Set move coordinates
        this.setQueenPosCurSend(gaoTable.get(QueenOld));
        this.setQueenPosNewSend((gaoTable.get(QueenNew)));
        this.setArrowPosSend(gaoTable.get(Arrow));


    }

    public GameBoard minimax(Node current, int depth) {
        Node move = MaxValue(current, depth, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY);
        return move.getGameBoard();
    }

    public Node MaxValue(Node current, int depth, double alpha, double beta) {
        if (depth == 0) {
            current.value = value(current.getGameBoard(), this.white);

            return current;
        }
        Node v = new Node(null);
        v.value = Double.NEGATIVE_INFINITY;
        for (Node node : current.getChildren()) {
            Node v2 = MinValue(node, depth - 1, alpha, beta);
            if (v2.value > v.value) {
                v.value = v2.value;
                v.setGameBoard(node.getGameBoard());
                alpha = Math.max(alpha, v.value);
            }
            if (v.value >= beta) {
                return v;
            }

        }

        return v;

    }

    public Node MinValue(Node current, int depth, double alpha, double beta) {
        if (depth == 0) {
            current.value = value(current.getGameBoard(), this.white);

            return current;
        }
        Node v = new Node(null);
        v.value = Double.POSITIVE_INFINITY;
        for (Node node : current.getChildren()) {
            Node v2 = MaxValue(node, depth - 1, alpha, beta);
            if (v2.value < v.value) {
                v.value = v2.value;
                v.setGameBoard(node.getGameBoard());
                beta = Math.min(beta, v.value);
            }
            if (v.value <= alpha) {
                return v;
            }

        }

        return v;

    }

    public double value(GameBoard GameBoard, boolean max) {
        Heuristic heur = new Heuristic();
        int[][] scoreGameBoard = heur.closestQueen(GameBoard);

        double sum = 0;
        for (int[] ints : scoreGameBoard) {
            for (int anInt : ints) {
                if (anInt == 1) {
                    if (max) {
                        sum += 1;
                    } else {
                        sum -= 1;
                    }
                } else if (anInt == 2) {
                    if (max) {
                        sum -= 1;
                    } else {
                        sum += 1;
                    }
                }
            }
        }
        return sum;
    }

}