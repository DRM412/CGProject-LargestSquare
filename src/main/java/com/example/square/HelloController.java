package com.example.square;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class HelloController {
    @FXML
    private Label welcomeText;

    @FXML
    private GridPane gridPane;

    @FXML
    private TextField coordinateX;

    @FXML
    private TextField coordinateY;

    @FXML
    private TextField coordinateX1;

    @FXML
    private TextField coordinateY1;

    KDTree kdTree;

    List<Point> points = new ArrayList<>();

    Point targetPoint;

    public Node getNodeByRowColumnIndex(final int row, final int column, GridPane gridPane) {
        Node result = null;
        ObservableList<Node> childrens = gridPane.getChildren();

        for (Node node : childrens) {
            if (GridPane.getRowIndex(node) != null
                    && GridPane.getColumnIndex(node) != null
                    && GridPane.getRowIndex(node) == row
                    && GridPane.getColumnIndex(node) == column) {
                result = node;
                break;
            }
        }

        return result;
    }

    @FXML
    protected void onHelloButtonClick() {

        int x = Integer.parseInt(String.valueOf(coordinateX.getText()));
        int y = Integer.parseInt(String.valueOf(coordinateY.getText()));
        Label currentLabel = ((Label) getNodeByRowColumnIndex(x, y, gridPane));
        currentLabel.setText("X");
        currentLabel.setStyle("-fx-background-color: yellow");
        points.add(new Point(x,y));
    }

    @FXML
    protected void mainPointButtonClick() {
        int x = Integer.parseInt(String.valueOf(coordinateX1.getText()));
        int y = Integer.parseInt(String.valueOf(coordinateY1.getText()));
        Label currentLabel = ((Label) getNodeByRowColumnIndex(x, y, gridPane));
        currentLabel.setText("X");
        currentLabel.setStyle("-fx-background-color: green");
        targetPoint = new Point(x,y);

    }

    public void insertPointsInKDTree(int left, int right, int depth)
    {
        if(left > right);
        else {

            int middle = left + (right - left) / 2;
            if (depth % 2 == 0) {
                List<Point> sortedByX = new ArrayList<>();
                for(int i = left; i <= right; i++)
                {
                    sortedByX.add(points.get(i));
                }
                sortedByX.sort(Comparator.comparing(Point::getX));
                int k = 0;
                for(int i = left; i <= right; i++)
                {
                    points.set(i, sortedByX.get(k));
                    k++;
                }
                kdTree.addPoint(points.get(middle));
            }
            else {
                List<Point> sortedByY = new ArrayList<>();
                for(int i = left; i <= right; i++)
                {
                    sortedByY.add(points.get(i));
                }
                sortedByY.sort(Comparator.comparing(Point::getX));
                int k = 0;
                for(int i = left; i <= right; i++)
                {
                    points.set(i, sortedByY.get(k));
                    k++;
                }
                kdTree.addPoint(points.get(middle));
            }

            insertPointsInKDTree(left, middle - 1, depth + 1);
            insertPointsInKDTree(middle + 1, right, depth + 1);
        }
    }


    @FXML
    protected void computeButtonClick() {
        if (points != null && points.size() > 0) {
            kdTree = new KDTree();
            insertPointsInKDTree(0, points.size() - 1, 0);
            Point result = kdTree.findClosestPoint(targetPoint);

            System.out.println("X = " + result.getX() + "   Y = " + result.getY());
            int lengthX = Math.abs(targetPoint.getX() - result.getX());
            int lengthY = Math.abs(targetPoint.getY() - result.getY());
            int length = Math.max(lengthX, lengthY);

            for (int i = targetPoint.getX() - length; i <= targetPoint.getX() + length; i++) {
                for (int j = targetPoint.getY() - length; j <= targetPoint.getY() + length; j++) {
                    Label currentLabel = ((Label) getNodeByRowColumnIndex(i, j, gridPane));
                    currentLabel.setStyle("-fx-background-color: red");
                }
            }
            Label currentLabel = ((Label) getNodeByRowColumnIndex(targetPoint.getX(), targetPoint.getY(), gridPane));
            currentLabel.setText("X");
            currentLabel.setStyle("-fx-background-color: green");

            currentLabel = ((Label) getNodeByRowColumnIndex(result.getX(), result.getY(), gridPane));
            currentLabel.setText("X");
            currentLabel.setStyle("-fx-background-color: yellow");
        }
    }
}