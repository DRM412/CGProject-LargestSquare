package com.example.square;

import java.util.List;

public class KDTree {
    private Node root;

    public KDTree(List<Point> points) {
        for( Point point : points)
        {
            root = insert(root, point ,0);
        }
    }

    public KDTree() {

    }

    private Node insert(Node node, Point point, int depth) {

            if(node == null)
            {
                return new Node(point);
            }

            int currentAxis = depth % 2;

            if (point.get(currentAxis) < node.getPoint().get(currentAxis))
            {
               node.left = insert(node.left, point, depth + 1);
            }
            else
            {
                node.right = insert(node.right, point, depth + 1);
            }

            return node;
    }

    public void addPoint(Point point)
    {
        root = insert(root, point, 0);
    }

    public int computeDistance(Point a, Point b)
    {
        return (a.getX() - b.getX()) * (a.getX() -b.getX()) + (a.getY() - b.getY()) * (a.getY() - b.getY());
    }

    public Point findClosestPoint(Point point)
    {
        return findClosestPoint(root, point, 0, null);
    }

    private Point findClosestPoint(Node node, Point targetPoint, int depth, Point bestOption)
    {
        if(node == null)
            return bestOption;

        int currentAxis = depth % 2;

        if(bestOption == null || computeDistance(targetPoint, node.getPoint()) <  computeDistance(targetPoint, bestOption))
            bestOption = node.getPoint();
        Node nextBranch = (targetPoint.get(currentAxis) < node.getPoint().get(currentAxis)) ? node.left : node.right;
        Node oppositeBranch = (targetPoint.get(currentAxis) < node.getPoint().get(currentAxis)) ? node.right : node.left;

        bestOption = findClosestPoint(nextBranch, targetPoint, depth + 1, bestOption);

        if (Math.abs(targetPoint.get(currentAxis) - node.getPoint().get(currentAxis)) < computeDistance(targetPoint, bestOption)) {
            bestOption = findClosestPoint(oppositeBranch, targetPoint, depth + 1, bestOption);
        }

        return bestOption;
    }
}
