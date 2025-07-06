package model;

public class AVLTree<T extends Comparable<T>> implements BalancedTree<T> {
    private NodeAVL<T> root;
    private final NodeAVL<T> util = new NodeAVL<>(null); // usado para chamar os métodos

    @Override
    public void insert(T value) {
        root = util.insert(value, root);
    }

    @Override
    public boolean remove(T value) {
        // Implementação futura (opcional)
        return false;
    }

    @Override
    public boolean find(T value) {
        return util.find(root, value);
    }

    @Override
    public int getHeight() {
        return util.getTreeHeight(root);
    }

    @Override
    public void printInOrder() {
        util.printInOrder(root);
        System.out.println();
    }
}

