package service;

import model.NodeAVL;
import model.NodeRB;
import model.Transacao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HashHibrido {

    private final HashQuadratico<String, Transacao> hashQuad;
    private final Map<String, NodeAVL<Transacao>> avlMap;
    private final Map<String, NodeRB<Transacao>> rbMap;
    private static final int LIMITE_AVL = 3;
    private static final int LIMITE_RB = 10;

    public HashHibrido(int capacidade) {
        this.hashQuad = new HashQuadratico<>(capacidade);
        this.avlMap = new HashMap<>();
        this.rbMap = new HashMap<>();
    }

    public void putPorOrigem(String origem, Transacao t) {
        if (rbMap.containsKey(origem)) {
            NodeRB<Transacao> rootRB = rbMap.get(origem);
            NodeRB<Transacao> newNode = new NodeRB<>(t);
            rootRB = NodeRB.rbInsert(rootRB, newNode);
            rbMap.put(origem, rootRB);

        } else if (avlMap.containsKey(origem)) {
            NodeAVL<Transacao> rootAVL = avlMap.get(origem);
            rootAVL = rootAVL.insert(t, rootAVL);
            avlMap.put(origem, rootAVL);

            if (rootAVL.getTreeHeight(rootAVL) > LIMITE_RB) {
                System.out.println("Migrando origem '" + origem + "' de AVL para Rubro-Negra");
                NodeRB<Transacao> rootRB = convertAVLtoRB(rootAVL);
                avlMap.remove(origem);
                rbMap.put(origem, rootRB);
            }

        } else {
            int colisoes = hashQuad.putWithCount(origem, t);
            if (colisoes > LIMITE_AVL) {
                System.out.println("Migrando origem '" + origem + "' de Hash para AVL");
                List<Transacao> todas = hashQuad.listarPorChave(origem);
                NodeAVL<Transacao> rootAVL = null;
                for (Transacao trans : todas) {
                    if (rootAVL == null) {
                        rootAVL = new NodeAVL<>(trans);
                    } else {
                        rootAVL = rootAVL.insert(trans, rootAVL);
                    }
                }
                avlMap.put(origem, rootAVL);
            }
        }
    }

    private NodeRB<Transacao> convertAVLtoRB(NodeAVL<Transacao> avlRoot) {
        List<Transacao> inOrder = new ArrayList<>();
        fillInOrder(avlRoot, inOrder);

        NodeRB<Transacao> rbRoot = null;
        for (Transacao t : inOrder) {
            NodeRB<Transacao> newNode = new NodeRB<>(t);
            if (rbRoot == null) {
                rbRoot = newNode;
            } else {
                rbRoot = NodeRB.rbInsert(rbRoot, newNode);
            }
        }
        return rbRoot;
    }

    private void fillInOrder(NodeAVL<Transacao> node, List<Transacao> out) {
        if (node == null) return;
        fillInOrder(node.left, out);
        out.add(node.element);
        fillInOrder(node.right, out);
    }

    private void fillInOrderRB(NodeRB<Transacao> node, List<Transacao> out) {
        if (node == null) return;
        fillInOrderRB(node.getLeft(), out);
        out.add(node.getElement());
        fillInOrderRB(node.getRight(), out);
    }

    public List<Transacao> buscarPorOrigemEIntervalo(String origem, String dataInicio, String dataFim) {
        List<Transacao> resultado = new ArrayList<>();
        List<Transacao> fonte;

        if (rbMap.containsKey(origem)) {
            fonte = new ArrayList<>();
            fillInOrderRB(rbMap.get(origem), fonte);
        } else if (avlMap.containsKey(origem)) {
            fonte = new ArrayList<>();
            fillInOrder(avlMap.get(origem), fonte);
        } else {
            fonte = hashQuad.listarPorChave(origem);
        }

        for (Transacao t : fonte) {
            String ts = t.getTimestamp();
            if (ts.compareTo(dataInicio) >= 0 && ts.compareTo(dataFim) <= 0) {
                resultado.add(t);
            }
        }

        return resultado;
    }
}
