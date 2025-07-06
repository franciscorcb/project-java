package service;

import model.Transacao;

public class HashEncadeamento {

    private static class Node {
        String chave;           // aqui será o campo `id` da transação
        Transacao transacao;    // valor armazenado
        Node proximo;           // próximo nó da lista encadeada

        Node(String chave, Transacao transacao, Node proximo) {
            this.chave = chave;
            this.transacao = transacao;
            this.proximo = proximo;
        }
    }

    private Node[] tabela;
    private int M = 512; // tamanho fixo da tabela

    public HashEncadeamento() {
        tabela = new Node[M];
    }

    private int hash(String chave) {
        return (chave.hashCode() & 0x7fffffff) % M;
    }

    public void put(String chave, Transacao transacao) {
        int i = hash(chave);
        Node atual = tabela[i];

        // Se a chave já existir, atualiza
        while (atual != null) {
            if (atual.chave.equals(chave)) {
                atual.transacao = transacao;
                return;
            }
            atual = atual.proximo;
        }

        // Se não existir, insere novo no início da lista
        tabela[i] = new Node(chave, transacao, tabela[i]);
    }

    public Transacao get(String chave) {
        int i = hash(chave);
        Node atual = tabela[i];

        while (atual != null) {
            if (atual.chave.equals(chave)) {
                return atual.transacao;
            }
            atual = atual.proximo;
        }

        return null; // não encontrado
    }

    public void delete(String chave) {
        int i = hash(chave);
        Node atual = tabela[i];
        Node anterior = null;

        while (atual != null) {
            if (atual.chave.equals(chave)) {
                if (anterior == null) {
                    tabela[i] = atual.proximo; // remove primeiro da lista
                } else {
                    anterior.proximo = atual.proximo; // remove do meio ou final
                }
                return;
            }
            anterior = atual;
            atual = atual.proximo;
        }
    }

    // Método opcional para contar colisões por posição (útil para o passo de promover à AVL)
    public int contarColisoes(String chave) {
        int i = hash(chave);
        Node atual = tabela[i];
        int count = 0;

        while (atual != null) {
            count++;
            atual = atual.proximo;
        }

        return count;
    }
}
