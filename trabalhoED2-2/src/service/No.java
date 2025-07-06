package service;

import model.Produtos;

public class No {
    
    private Produtos node;
    private No next;


    public No(Produtos node) {
        this.node = node;
        this.next = null;
    }

    public No(Produtos node, No next) {
        this.node = node;
        this.next = next;
    }

    public Produtos getNode() {
        return node;
    }
    public void setNode(Produtos node) {
        this.node = node;
    }
    public No getNext() {
        return next;
    }
    public void setNext(No next) {
        this.next = next;
    }

    @Override
    public String toString() {
        return ""+ node ;
    }
    
}
