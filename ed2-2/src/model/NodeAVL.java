package model;

public class NodeAVL<T extends Comparable<T>> {
    public T element;
    public NodeAVL<T> left;
    public NodeAVL<T> right;
    private int height;

    public NodeAVL(T e) {
        this(e, null, null);
    }

    public NodeAVL(T e, NodeAVL<T> left, NodeAVL<T> right) {
        this.element = e;
        this.left = left;
        this.right = right;
        this.height = 0;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    private int height(NodeAVL<T> n) {
        if (n == null) {
            return -1;
        } else {
            return n.height;
        }
    }

    private int max(int a, int b) {
        if (a > b) {
            return a;
        } else {
            return b;
        }
    }

    private NodeAVL<T> rotacaoDireita(NodeAVL<T> k2) {
        NodeAVL<T> k1 = k2.left;
        k2.left = k1.right;
        k1.right = k2;

        k2.height = max(height(k2.left), height(k2.right)) + 1;
        k1.height = max(height(k1.left), height(k1.right)) + 1;

        return k1;
    }

    private NodeAVL<T> rotacaoEsquerda(NodeAVL<T> k1) {
        NodeAVL<T> k2 = k1.right;
        k1.right = k2.left;
        k2.left = k1;

        k1.height = max(height(k1.left), height(k1.right)) + 1;
        k2.height = max(height(k2.left), height(k2.right)) + 1;

        return k2;
    }

    private NodeAVL<T> rotacaoDuplaDireita(NodeAVL<T> k3) {
        k3.left = rotacaoEsquerda(k3.left);
        return rotacaoDireita(k3);
    }

    private NodeAVL<T> rotacaoDuplaEsquerda(NodeAVL<T> k1) {
        k1.right = rotacaoDireita(k1.right);
        return rotacaoEsquerda(k1);
    }

    // método principal de inserção recursiva, do jeito que você usou
    public NodeAVL<T> insert(T x, NodeAVL<T> t) {
        if (t == null)
            t = new NodeAVL<>(x);

        else if (x.compareTo(t.element) < 0) {
            t.left = insert(x, t.left);

            if (height(t.left) - height(t.right) == 2) {
                if (x.compareTo(t.left.element) < 0)
                    t = rotacaoDireita(t);
                else
                    t = rotacaoDuplaDireita(t);
            }

        } else if (x.compareTo(t.element) > 0) {
            t.right = insert(x, t.right);

            if (height(t.right) - height(t.left) == 2) {
                if (x.compareTo(t.right.element) > 0)
                    t = rotacaoEsquerda(t);
                else
                    t = rotacaoDuplaEsquerda(t);
            }
        }

        t.height = max(height(t.left), height(t.right)) + 1;
        return t;
    }

    public void printInOrder(NodeAVL<T> node) {
        if (node != null) {
            printInOrder(node.left);
            System.out.print(node.element + " ");
            printInOrder(node.right);
        }
    }

    public boolean find(NodeAVL<T> node, T x) {
        if (node == null) return false;

        int cmp = x.compareTo(node.element);
        if (cmp < 0) return find(node.left, x);
        else if (cmp > 0) return find(node.right, x);
        else return true;
    }

    public int getTreeHeight(NodeAVL<T> node) {
        return height(node);
    }


}
