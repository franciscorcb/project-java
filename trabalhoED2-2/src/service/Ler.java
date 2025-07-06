package service;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import model.Produtos;

public class Ler {
    //classe responsavel por ler e carregar dados em um vetor
    public static Produtos[] preencherVetor(String camArq){

        List<Produtos> lista = new ArrayList<>();


        try(BufferedReader in = new BufferedReader(new FileReader(camArq))){
            String linha;
            while((linha = in.readLine())!=null){
                String[] dado = linha.split("#");
                //separa os dados utilizando split, a cada split ele armazena o dado diferente
                if(dado.length==5){

                    String nome = dado[0];
                    String fabricante = dado[1];
                    Float preco = Float.parseFloat(dado[2].replace(",", "."));
                    String validade = dado[3];
                    String tipo = dado[4];

                    //cria um objeto e adiciona na lista
                    Produtos novoProduto = new Produtos(nome, fabricante, preco, validade, tipo);
                    lista.add(novoProduto);
                }
            }
        }
        catch(IOException e){
             System.out.println("Erro ao ler o arquivo: " + e.getMessage());
        }

        //converte a lista para o array
        return lista.toArray(new Produtos[0]);

        
    }
}
