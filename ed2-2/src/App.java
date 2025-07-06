import model.Transacao;
import service.HashEncadeamento;
import service.HashHibrido;
import service.LerTransacoes;

import java.util.List;
import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        String caminho = "transacoes.csv";
        Transacao[] transacoes = LerTransacoes.preencherVetor(caminho);
        System.out.println("Total de transações lidas: " + transacoes.length);

        HashEncadeamento hashPorId = new HashEncadeamento();
        HashHibrido hashPorOrigem = new HashHibrido(512);

        for (Transacao t : transacoes) {
            hashPorId.put(t.getId(), t);
            hashPorOrigem.putPorOrigem(t.getOrigem(), t);
        }

        Scanner sc = new Scanner(System.in);
        boolean flag=true;
        while (flag) {
            System.out.println("\nMenu:");
            System.out.println("1. Buscar transacao por ID");
            System.out.println("2. Buscar por origem e intervalo (aaaa-mm-dd)");
            System.out.println("3. Sair");
            System.out.print("Escolha: ");
            String op = sc.nextLine();

            switch (op) {
                case "1":
                    System.out.print("ID: ");
                    String id = sc.nextLine();
                    Transacao t = hashPorId.get(id);
                    if (t != null) {
                        System.out.println(t);
                    } else {
                        System.out.println("Transação não encontrada.");
                    }
                    break;

                case "2":
                    System.out.print("Origem: ");
                    String origem = sc.nextLine();
                    System.out.print("Data início (aaaa-mm-dd): ");
                    String inicio = sc.nextLine();
                    System.out.print("Data fim (aaaa-mm-dd): ");
                    String fim = sc.nextLine();
                    List<Transacao> lista = hashPorOrigem.buscarPorOrigemEIntervalo(origem, inicio, fim);
                    if (lista.isEmpty()) {
                        System.out.println("Nenhuma transacao encontrada.");
                    } else {
                        for (Transacao tx : lista) {
                            System.out.println(tx);
                        }
                    }
                    break;

                case "3":
                    System.out.println("Encerrando...");
                    flag = false;
                    sc.close();
                    return;

                default:
                    System.out.println("Opção invalida.");
            }
        }
    }
}
