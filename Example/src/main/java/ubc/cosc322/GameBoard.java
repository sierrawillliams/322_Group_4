package ubc.cosc322;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class GameBoard implements Cloneable {
    public static final int ROWS = 10;
    public static final int COLS = 10;

    public static final int EMPTY = 0;
    public static final int WHITE = 1;
    public static final int BLACK = 2;
    public static final int ARROW = 3;

    public int[][] GameBoard = new int[ROWS][COLS];
    ArrayList<Position> WhitePos = new ArrayList<>();
    ArrayList<Position> BlackPos = new ArrayList<>();

    public GameBoard() {
        GameBoard[0][3] = BLACK;
        GameBoard[0][6] = BLACK;
        GameBoard[3][0] = BLACK;
        GameBoard[3][9] = BLACK;

        GameBoard[6][0] = WHITE;
        GameBoard[9][3] = WHITE;
        GameBoard[9][6] = WHITE;
        GameBoard[6][9] = WHITE;

        initialPos();
    }

    public void initialPos() {
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                if (GameBoard[i][j] == WHITE) {
                    WhitePos.add(new Position(i, j));
                } else if (GameBoard[i][j] == BLACK) {
                    BlackPos.add(new Position(i, j));
                }
            }
        }
    }

    public void printGameBoard() {
        for (int i = 0; i < ROWS; i++) {
            System.out.print("| ");
            for (int j = 0; j < COLS; j++) {
                if (GameBoard[i][j] == WHITE) {
                    System.out.print(" W ");
                } else if (GameBoard[i][j] == BLACK) {
                    System.out.print(" B ");
                } else if (GameBoard[i][j] == ARROW) {
                    System.out.print(" A ");
                } else {
                    System.out.print(" . ");
                }
            }
            System.out.println(" |");
        }
        System.out.println(" ");
        System.out.println(" ");
    }

    public GameBoard updateGameBoard(GameBoard current, ArrayList<Integer> QueenPosCurEnemey,
                                 ArrayList<Integer> QueenPosNewEnemey, ArrayList<Integer> ArrowPosEnemey, boolean conversionNeeded) {
        if (conversionNeeded) {

            HashMap<ArrayList<Integer>, ArrayList<Integer>> map = this.makeHashTable();
            int WorB = current.GameBoard[map.get(QueenPosCurEnemey).get(0)][map.get(QueenPosCurEnemey).get(1)];

            // replace old location of piece with 0
            current.GameBoard[map.get(QueenPosCurEnemey).get(0)][map.get(QueenPosCurEnemey).get(1)] = 0;

            // if moving piece is white put 1 at coord else put 2
            current.GameBoard[map.get(QueenPosNewEnemey).get(0)][map.get(QueenPosNewEnemey).get(1)] = (WorB == 1) ? 1 : 2;

            // Update arrow location
            current.GameBoard[map.get(ArrowPosEnemey).get(0)][map.get(ArrowPosEnemey).get(1)] = 3;
        } else {
            int WorB = this.GameBoard[QueenPosCurEnemey.get(0)][QueenPosCurEnemey.get(1)];

            // replace old location of piece with 0
            current.GameBoard[QueenPosCurEnemey.get(0)][QueenPosCurEnemey.get(1)] = 0;

            // if moving piece is white put 1 at coord else put 2
            current.GameBoard[QueenPosNewEnemey.get(0)][QueenPosNewEnemey.get(1)] = (WorB == 1) ? 1 : 2;

            // Update arrow location
            current.GameBoard[ArrowPosEnemey.get(0)][ArrowPosEnemey.get(1)] = 3;
        }
        return current;
    }

    public GameBoard updateGameBoard(GameBoard GameBoardForArrowGeneration, ArrayList<Integer> QueenCur,
                                 ArrayList<Integer> QueenNew, boolean conversionNeeded) {
        if (conversionNeeded) {

            HashMap<ArrayList<Integer>, ArrayList<Integer>> map = this.makeHashTable();
            int WorB = GameBoardForArrowGeneration.GameBoard[map.get(QueenCur).get(0)][map.get(QueenCur).get(1)];

            // replace old location of piece with 0
            GameBoardForArrowGeneration.GameBoard[map.get(QueenCur).get(0)][map.get(QueenCur).get(1)] = 0;

            // if moving piece is white put 1 at coord else put 2
            GameBoardForArrowGeneration.GameBoard[map.get(QueenNew).get(0)][map.get(QueenNew).get(1)] = (WorB == 1) ? 1 : 2;

        } else {
            int WorB = GameBoardForArrowGeneration.GameBoard[QueenCur.get(0)][QueenCur.get(1)];

            // replace old location of piece with 0
            GameBoardForArrowGeneration.GameBoard[QueenCur.get(0)][QueenCur.get(1)] = 0;

            // if moving piece is white put 1 at coord else put 2
            GameBoardForArrowGeneration.GameBoard[QueenNew.get(0)][QueenNew.get(1)] = (WorB == 1) ? 1 : 2;

        }
        return GameBoardForArrowGeneration;
    }

    public static HashMap<ArrayList<Integer>, ArrayList<Integer>> makeHashTable() {
        HashMap<ArrayList<Integer>, ArrayList<Integer>> GameBoardConversion = new HashMap<>();

        ArrayList<Integer> keys = new ArrayList<>();
        for (int row = 10; row > 0; row--) {
            for (int col = 1; col < 11; col++) {
                keys.add(row);
                keys.add(col);

            }
        }

        ArrayList<Integer> values = new ArrayList<>();
        for (int row = 0; row < 10; row++) {
            for (int col = 0; col < 10; col++) {
                values.add(row);
                values.add(col);

            }
        }

        int counter = 0, keyIndex = -1, valueIndex = -1;
        boolean done = false;
        while (!done) {
            ArrayList<Integer> keyTemp = new ArrayList<>();
            ArrayList<Integer> valueTemp = new ArrayList<>();
            keyTemp.add(keys.get(++keyIndex));
            keyTemp.add(keys.get(++keyIndex));

            valueTemp.add(values.get(++valueIndex));
            valueTemp.add(values.get(++valueIndex));

            GameBoardConversion.put(keyTemp, valueTemp);
            counter++;
            if (counter == 100) {
                done = true;
            }

        }
        return GameBoardConversion;
    }

    public Object clone() {
        GameBoard clone = new GameBoard();
        clone.GameBoard = new int[10][10];
        for (int i = 0; i < GameBoard.length; i++) {
            for (int j = 0; j < GameBoard.length; j++) {
                clone.GameBoard[i][j] = this.GameBoard[i][j];
            }
        }

        clone.WhitePos = new ArrayList<Position>();
        int counter = 0;
        for (Position position : this.WhitePos) {
            Position temp = new Position(this.WhitePos.get(counter).getX(), this.WhitePos.get(counter).getY());
            clone.WhitePos.add(temp);
            counter++;
        }
        counter = 0;
        clone.BlackPos = new ArrayList<Position>();
        for (Position position : this.BlackPos) {
            Position temp = new Position(this.BlackPos.get(counter).getX(), this.BlackPos.get(counter).getY());
            clone.BlackPos.add(temp);
            counter++;
        }
        return clone;
    }

    public static HashMap<ArrayList<Integer>, ArrayList<Integer>> makeGaoTable() {
        HashMap<ArrayList<Integer>, ArrayList<Integer>> gaoTable = new HashMap<>();
        ArrayList<Integer> keys = new ArrayList<>();
        for (int row = 0; row < 10; row++) {
            for (int col = 0; col < 10; col++) {
                keys.add(row);
                keys.add(col);

            }
        }

        ArrayList<Integer> values = new ArrayList<>();
        for (int row = 10; row > 0; row--) {
            for (int col = 1; col < 11; col++) {
                values.add(row);
                values.add(col);

            }
        }

        int counter = 0, keyIndex = -1, valueIndex = -1;
        boolean done = false;
        while (!done) {
            ArrayList<Integer> keyTemp = new ArrayList<>();
            ArrayList<Integer> valueTemp = new ArrayList<>();
            keyTemp.add(keys.get(++keyIndex));
            keyTemp.add(keys.get(++keyIndex));

            valueTemp.add(values.get(++valueIndex));
            valueTemp.add(values.get(++valueIndex));

            gaoTable.put(keyTemp, valueTemp);
            counter++;
            if (counter == 100) {
                done = true;
            }

        }

        return gaoTable;
    }

    // getter and setter
    public int getGameBoardPos(int x, int y) {
        return GameBoard[x][y];
    }

    public ArrayList<Position> getWhitePos() {
        return WhitePos;
    }

    public ArrayList<Position> getBlackPos() {
        return BlackPos;
    }

    public ArrayList<Position> setWhitePos() {
        return this.WhitePos = WhitePos;
    }

    public ArrayList<Position> setBlackPos() {
        return this.BlackPos = BlackPos;
    }

}