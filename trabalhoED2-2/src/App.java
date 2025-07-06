import model.Produtos;
import service.Ler;
import service.ListaEncadeada;
import service.Ordenacoes;
import java.util.Scanner;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

//-------------------------------
//-------------------------------------------
//IMPORTANTE : ANTES DE EXECUTAR O APP.JAVA, DEVE SER EXECUTADO O PROGRAMA GERADORDEPRODUTOS.JAVA
//-------------------------------------------
//-------------------------------

public class App {
    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);
        String arquivo = null;

        while (arquivo == null) {
            System.out.println("\n==== SELECIONE O ARQUIVO ====");
            System.out.println("1. produtos_1000.txt");
            System.out.println("2. produtos_10000.txt");
            System.out.println("3. produtos_100000.txt");
            System.out.println("4. produtos_1000000.txt");
            System.out.println("5. Sair");
            System.out.print("Escolha o arquivo: ");

            int escolhaArquivo = scanner.nextInt();

            switch(escolhaArquivo) {
                case 1: arquivo = "produtos_1000.txt"; break;
                case 2: arquivo = "produtos_10000.txt"; break;
                case 3: arquivo = "produtos_100000.txt"; break;
                case 4: arquivo = "produtos_1000000.txt"; break;
                case 5:
                    System.out.println("Saindo do programa...");
                    scanner.close();
                    return;
                default:
                    System.out.println("Opcao invalida!");
                    continue;
            }

            int opcaoOrdenacao = 0;
            while(opcaoOrdenacao != 5){
                System.out.println("\n==== SELECIONE O METODO DE ORDENACAO ====");
                System.out.println("1. Shell Sort");
                //Ordena por nome
                System.out.println("2. Quick Sort");
                //Ordena por preço
                System.out.println("3. Heap Sort");
                //Ordena por data
                System.out.println("4. Insertion Sort com Lista Encadeada");
                //Ordena por nome
                System.out.println("5. Voltar para selecao de arquivo");
                System.out.print("Escolha o metodo: ");

                opcaoOrdenacao = scanner.nextInt();

                switch(opcaoOrdenacao) {
                    case 1:
                        executarShellSort(arquivo);
                        break;
                    case 2:
                        executarQuickSort(arquivo);
                        break;
                    case 3:
                        executarHeapSort(arquivo);
                        break;
                    case 4:
                        executarInsertionSortComLista(arquivo);
                        break;
                    case 5:
                        arquivo = null;
                        break;
                    default:
                        System.out.println("Opcao invalida!");
                }
            }
        }

        scanner.close();
    }

    private static void executarShellSort(String arquivo) throws IOException {
        Produtos[] produtos = Ler.preencherVetor(arquivo);
        Ordenacoes.ShellSort(produtos);
        salvarEmArquivo(produtos, "resultado_shell_sort.txt");
    }

    private static void executarQuickSort(String arquivo) throws IOException {
        Produtos[] produtos = Ler.preencherVetor(arquivo);
        Ordenacoes.QuickSort(produtos, 0, produtos.length-1);
        salvarEmArquivo(produtos, "resultado_quick_sort.txt");
    }

    private static void executarHeapSort(String arquivo) throws IOException {
        Produtos[] produtos = Ler.preencherVetor(arquivo);
        Ordenacoes.HeapSort(produtos);
        salvarEmArquivo(produtos, "resultado_heap_sort.txt");
    }

    private static void executarInsertionSortComLista(String arquivo) throws IOException {
        Produtos[] produtos = Ler.preencherVetor(arquivo);
        ListaEncadeada lista = new ListaEncadeada();

        for (Produtos p : produtos) {
            lista.adiciona(p);
        }

        lista.insertionSort();
        salvarListaEmArquivo(lista, "resultado_insertion_lista.txt");
    }

    private static void salvarEmArquivo(Produtos[] produtos, String nomeArquivo) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(nomeArquivo))) {
            for (Produtos p : produtos) {
                writer.write(p.toString());
                writer.newLine();
            }
        }
        System.out.println("Resultado salvo em: " + nomeArquivo);
    }

    private static void salvarListaEmArquivo(ListaEncadeada lista, String nomeArquivo) throws IOException {
        lista.salvarEmArquivo(nomeArquivo);
        System.out.println("Resultado salvo em: " + nomeArquivo);
    }

}
