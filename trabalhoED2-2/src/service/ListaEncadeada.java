package service;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import model.Produtos;

public class ListaEncadeada {
    private No inicio;

    public ListaEncadeada() {
        this.inicio = null;
    }

    public No getInicio() {
        return inicio;
    }

    public void setInicio(No inicio) {
        this.inicio = inicio;
    }

    //Funação de adicionar o dado no node
    //Enquanto o o spec tiver dado, ele adiciona na lista encadeada

    public void adiciona(Produtos dado){
        No node = new No(dado);
        if(inicio!=null){
            No spec = inicio;
            while (spec.getNext() != null) {
                spec = spec.getNext();
            }
            spec.setNext(node);
        }
        else{
            this.inicio = node;
        }
    }


    //Função de inserção
    public void insertionSort() {
        No list = null;
        No atual = this.inicio;
        
        while (atual != null) {
            //percorre a lista toda e a coloca na posição certa
            No proximo = atual.getNext();
            atual.setNext(null);
            list = insereOrdenado(list, atual);
            atual = proximo;
        }

        this.inicio = list;
    }

    private No insereOrdenado(No first, No newNode) {
        //Abaixo está a variavel numNew, dentro dela tudo que não for identificado como um numero de 0 a 9 será ignorado e logo em seguida é adicionado na função
        //ex: produto_12 -> a variavel numNew vai armazenar o numero 12
        int numNew = Integer.parseInt(newNode.getNode().getNome().replaceAll("[^0-9]", ""));

        if (first != null) {

            int numFirst = Integer.parseInt(first.getNode().getNome().replaceAll("[^0-9]", ""));
            //mesma logica do numNew, mas logo em seguida ele faz a comparação para ver se o novo numero/palavra é menor que o primeiro, se for, ele vira o novo primeiro
            if (numNew < numFirst) {
                newNode.setNext(first);
                return newNode;
            }

            No spec = first;
            while (spec.getNext() != null) {
                int numNext = Integer.parseInt(spec.getNext().getNode().getNome().replaceAll("[^0-9]", ""));
                //Vai comparando um por um até encontrar um nome/numero menor, se for, o laço é interrompido
                if (numNew < numNext) {
                    break;
                }
                spec = spec.getNext();
            }

            newNode.setNext(spec.getNext());// Conecta o novo no ao próximo
            spec.setNext(newNode);// Conecta o no atual ao novo nó
            return first;
        }

        newNode.setNext(null);
        return newNode;
    }

    public void salvarEmArquivo(String nomeArquivo) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(nomeArquivo))) {
            No atual = inicio;
            while (atual != null) {
                writer.write(atual.getNode().toString()); // Usa o toString de Produtos
                writer.newLine();
                atual = atual.getNext();
            }
            System.out.println("Arquivo gerado com sucesso.");
        } catch (IOException e) {
            System.out.println("Erro ao salvar o arquivo: " + e.getMessage());
        }
    }


}
