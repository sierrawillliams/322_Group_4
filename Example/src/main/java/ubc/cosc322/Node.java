package ubc.cosc322;

import java.util.ArrayList;

public class Node {
    private Node parentNode;
    private ArrayList<Node> children;
    public double value;
    private GameBoard board;

    public Node(Node parentNode){
        this.parentNode = parentNode;
        this.children = new ArrayList<>();
        this.board = new GameBoard();

    }

    public Node(GameBoard gb, int i) {
    }

    public Node getParentNode() {
        return this.parentNode;
    }
    public double getValue() {
        return this.value;
    }
    public GameBoard getGameBoard() {
        return this.board;
    }

    public ArrayList<Node> getChildren() {
        return this.children;
    }
    public void setValue(double value) {
        this.value = value;
    }
    public void setGameBoard(GameBoard board) {
        this.board = board;
    }
    public void addChild(Node node) {
        this.children.add(node);
    }
    public void setParent(Node node) {
        this.parentNode = node;
    }
    public int childrenCount() {
        int count = 0;
        for (int i=0; i < this.children.size(); i++)
            if (this.children.get(i) != null)
                count++;

        return count;
    }
}