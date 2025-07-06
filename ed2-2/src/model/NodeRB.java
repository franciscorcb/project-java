package model;

public class NodeRB<T extends Comparable<T>> {
    private T element;
    private NodeRB<T> parent, left, right;
    private Color color;
    private int N;

    public static enum Color {
        RED, BLACK
    }

    public NodeRB(T element) {
        this(element, null, null, null, Color.RED);
    }

    NodeRB(T element, NodeRB<T> left, NodeRB<T> right, NodeRB<T> parent, Color color) {
        this.element = element;
        this.left = left;
        this.right = right;
        this.parent = parent;
        this.color = color;
    }

    // Método público de inserção
    public static <T extends Comparable<T>> NodeRB<T> rbInsert(NodeRB<T> root, NodeRB<T> newNode) {
        NodeRB<T> y = null;
        NodeRB<T> x = root;

        while (x != null) {
            y = x;
            if (newNode.element.compareTo(x.element) < 0) {
                x = x.left;
            } else {
                x = x.right;
            }
        }

        newNode.parent = y;

        if (y == null) {
            root = newNode;
        } else if (newNode.element.compareTo(y.element) < 0) {
            y.left = newNode;
        } else {
            y.right = newNode;
        }

        newNode.left = null;
        newNode.right = null;
        newNode.color = Color.RED;

        return rbInsertFixup(root, newNode);
    }

    // Métodos auxiliares privados

    private static <T extends Comparable<T>> NodeRB<T> leftRotate(NodeRB<T> root, NodeRB<T> x) {
        NodeRB<T> y = x.right;

        x.right = y.left;
        if (y.left != null) {
            y.left.parent = x;
        }

        y.parent = x.parent;

        if (x.parent == null) {
            root = y;
        } else if (x == x.parent.left) {
            x.parent.left = y;
        } else {
            x.parent.right = y;
        }

        y.left = x;
        x.parent = y;

        return root;
    }

    private static <T extends Comparable<T>> NodeRB<T> rightRotate(NodeRB<T> root, NodeRB<T> y) {
        NodeRB<T> x = y.left;

        y.left = x.right;
        if (x.right != null) {
            x.right.parent = y;
        }

        x.parent = y.parent;

        if (y.parent == null) {
            root = x;
        } else if (y == y.parent.right) {
            y.parent.right = x;
        } else {
            y.parent.left = x;
        }

        x.right = y;
        y.parent = x;

        return root;
    }

    private static <T extends Comparable<T>> NodeRB<T> rbInsertFixup(NodeRB<T> root, NodeRB<T> z) {
        while (z != root && z.parent.color == Color.RED) {
            if (z.parent == z.parent.parent.left) {
                NodeRB<T> y = z.parent.parent.right;

                if (y != null && y.color == Color.RED) {
                    z.parent.color = Color.BLACK;
                    y.color = Color.BLACK;
                    z.parent.parent.color = Color.RED;
                    z = z.parent.parent;
                } else {
                    if (z == z.parent.right) {
                        z = z.parent;
                        root = leftRotate(root, z);
                    }
                    z.parent.color = Color.BLACK;
                    z.parent.parent.color = Color.RED;
                    root = rightRotate(root, z.parent.parent);
                }
            } else {
                NodeRB<T> y = z.parent.parent.left;

                if (y != null && y.color == Color.RED) {
                    z.parent.color = Color.BLACK;
                    y.color = Color.BLACK;
                    z.parent.parent.color = Color.RED;
                    z = z.parent.parent;
                } else {
                    if (z == z.parent.left) {
                        z = z.parent;
                        root = rightRotate(root, z);
                    }
                    z.parent.color = Color.BLACK;
                    z.parent.parent.color = Color.RED;
                    root = leftRotate(root, z.parent.parent);
                }
            }
        }

        root.color = Color.BLACK;
        return root;
    }
    
    // Getters e setters
    public T getElement() {
        return element;
    }

    public void setElement(T element) {
        this.element = element;
    }

    public NodeRB<T> getLeft() {
        return left;
    }

    public void setLeft(NodeRB<T> left) {
        this.left = left;
    }

    public NodeRB<T> getRight() {
        return right;
    }

    public void setRight(NodeRB<T> right) {
        this.right = right;
    }

    public NodeRB<T> getParent() {
        return parent;
    }

    public void setParent(NodeRB<T> parent) {
        this.parent = parent;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public int getN() {
        return N;
    }

    public void setN(int n) {
        this.N = n;
    }
}
