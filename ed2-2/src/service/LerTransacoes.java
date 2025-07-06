package service;

import model.Transacao;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class LerTransacoes {

    public static Transacao[] preencherVetor(String caminhoArquivo) {
        ArrayList<Transacao> lista = new ArrayList<>();

        try (BufferedReader in = new BufferedReader(new FileReader(caminhoArquivo))) {
            String linha;

            while ((linha = in.readLine()) != null) {
                // Supondo que o separador seja ";"
                String[] dados = linha.split(";");
                if (dados.length == 5 && !dados[0].equalsIgnoreCase("id")) {
                    String id = dados[0];
                    String origem = dados[1];
                    String destino = dados[2];
                    float valor = Float.parseFloat(dados[3].replace(",", "."));
                    String timestamp = dados[4];
                
                    Transacao t = new Transacao(id,origem, destino,valor, timestamp);
                    lista.add(t);
                }
            }

        } catch (IOException e) {
            System.out.println("Erro ao ler o arquivo: " + e.getMessage());
        }

        return lista.toArray(new Transacao[0]);
    }
}
