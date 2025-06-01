import model.Produtos;
import service.Ler;
import service.ListaEncadeada;
import service.Ordenacoes;
import java.util.Scanner;

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
                    System.out.println("Opção inválida!");
                    continue;
            }
            System.out.println("\nArquivo selecionado: " + arquivo);
        
            int opcaoOrdenacao=0;
            while(opcaoOrdenacao!=5){
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
                System.out.print("Escolha o método: ");

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
    
    private static void executarShellSort(String arquivo) {
        Produtos[] produtos = Ler.preencherVetor(arquivo);
        Ordenacoes.ShellSort(produtos);
        System.out.println("\n=== RESULTADO SHELL SORT ===");
        imprimir(produtos);
    }
    
    private static void executarQuickSort(String arquivo) {
        Produtos[] produtos = Ler.preencherVetor(arquivo);
        Ordenacoes.QuickSort(produtos, 0, produtos.length-1);
        System.out.println("\n=== RESULTADO QUICK SORT ===");
        imprimir(produtos);
    }
    
    private static void executarHeapSort(String arquivo) {
        Produtos[] produtos = Ler.preencherVetor(arquivo);
        Ordenacoes.HeapSort(produtos);
        System.out.println("\n=== RESULTADO HEAP SORT ===");
        imprimir(produtos);
    }
    
    private static void executarInsertionSortComLista(String arquivo) {
        Produtos[] produtos = Ler.preencherVetor(arquivo);
        ListaEncadeada lista = new ListaEncadeada();
        
        for (Produtos p : produtos) {
            lista.adiciona(p);
        }
        
        lista.insertionSort();
        System.out.println("\n=== RESULTADO INSERTION SORT (LISTA ENCADEADA) ===");
        lista.imprime();
    }
    
    private static void imprimir(Produtos[] produtos) {
        for (Produtos p : produtos) {
            System.out.println(p);
        }
    }
}