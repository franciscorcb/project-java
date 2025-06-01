package service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import model.Produtos;

public class Ordenacoes {
    /*---------------SHELL SORT MODIFICADO---------------*/
    public static Produtos[] ShellSort(Produtos [] A){
        int h,j,min,numK,numMin;
        Produtos temp;

        for(h=1;h<A.length;h = (3*h)+1);

        while (h > 0) {
            if (h > 1) {
                // SelectionSort nas sublistas com espaçamento h
                //for adicionado, muda o vetor de lugar
                for (int i = 0; i < h; i++) {
                    //os dois proximos auxiliam na movimentação e comparação dos itens do vetor
                    for (j = i; j < A.length - h; j += h) {
                        min = j;
                        for (int k = j + h; k < A.length; k += h) {
                            //dentro das variaveis abaixo, tudo que não for identificado como um numero de 0 a 9 será ignorado
                            numK = Integer.parseInt(A[k].getNome().replaceAll("[^0-9]", ""));
                            numMin = Integer.parseInt(A[min].getNome().replaceAll("[^0-9]", ""));
                            if (numK < numMin) {
                                min = k;
                            }
                        }
                        //realiza a troca
                        temp = A[min];
                        A[min] = A[j];
                        A[j] = temp;
                    }
                }

            }
            else {
                //Logica insertion sort, só vai chamar ela quando h=1
                for (int i = h; i < A.length; i++) {
                    temp = A[i];
                    j = i;

                    int numTemp = Integer.parseInt(temp.getNome().replaceAll("[^0-9]", ""));
                
                    while (j >= h && Integer.parseInt(A[j - h].getNome().replaceAll("[^0-9]", "")) > numTemp) {
                        A[j] = A[j - h];
                        j -= h;
                    }
                    A[j]=temp;
                }
            }
            h =(h-1)/3;
        }
        return A;
    }

    /*---------------QUICKSORT---------------*/
    public static void QuickSort(Produtos[] vetor, int inicio, int fim) {
        if (inicio < fim) {
            int q = particiona(vetor, inicio, fim);
            QuickSort(vetor, inicio, q - 1);
            QuickSort(vetor, q + 1, fim);
        }
    }

    private static int particiona(Produtos[] vetor, int inicio, int fim) {
        //(NOTA) Foi utilizado mediana pelos seguinte motivos: Melhor eficiencia e também o codigo apresentava problemas ao tentar realizar a media dos itens inicio, meio e fim
        int meio = (inicio + fim) / 2;
        float a = vetor[inicio].getPreco();
        float b = vetor[meio].getPreco();
        float c = vetor[fim].getPreco();
        
        int mediana;
        //verifica qual é maior a(inicio) b(meio) c(fim)
        if((a>=b&&a<=c)||(a<=b&&a>c)){
            mediana = inicio;
        }
        else if((b>=a&&b<=c)||(b<=a&&b>c)){
            mediana = meio;
        }
        else{
            mediana = fim;
        }

        Produtos aux = vetor[inicio];
        vetor[inicio] = vetor[mediana];
        vetor[mediana] = aux;

        float pivo = vetor[inicio].getPreco();

        int i = inicio+1;
        int j = inicio+1;

        while (j <= fim) {
            //compara o item com o pivo atual
            if (vetor[j].getPreco() < pivo) {
                Produtos temp = vetor[i];
                vetor[i] = vetor[j];
                vetor[j] = temp;
                i++;
            }
            j++;
        }

        aux = vetor[inicio];
        vetor[inicio] = vetor[i - 1];
        vetor[i - 1] = aux;

        return i - 1;
    }


    /* -----------HeapSort----------- */

    public static void HeapSort(Produtos [] A ){
        //chama a função de construção
        buildMaxHeap(A);
        int n = A.length;

        for(int i = A.length-1; i>=1;i--){
            Produtos aux = A[0];
            A[0] = A[i];
            A[i] = aux;
            n--;
            maxHeapify(A, 0,n);
        }
    }

    private static void maxHeapify(Produtos[] A,int i,int n){
        Boolean flag = true;
        DateTimeFormatter data = DateTimeFormatter.ofPattern("ddMMyyyy");
        //heapify interativo, utilizasse uma flag, enquanto for verdadeiro o codigo ficará rodando
        while(flag){
            int menor=i;
            int l = 2*i+1;
            int r = 2*i+2;

            LocalDate dataM = LocalDate.parse(A[menor].getValidade().trim(),data);

            if (l < n) {

                LocalDate dataL = LocalDate.parse(A[l].getValidade().trim(), data);
                
                //compara as data exemplo : 12/03/2025 > 11/03/2025 se for ele atualiza o novo menor
                if (dataL.isAfter(dataM)) {
                    menor = l;
                    dataM = dataL;
                }
            }
            if (r < n) {
                LocalDate dataR = LocalDate.parse(A[r].getValidade().trim(), data);
                //mesma logica do primeiro if so que agora na parte direita
                if (dataR.isAfter(dataM)) {
                    menor = r;
                }
            }

            // faz a troca dos itens
            if(menor != i){
                Produtos aux = A[i];
                A[i] = A[menor];
                A[menor] = aux;

                i = menor;
            }
            else{
                //chegou no fim a flag atualiza
                flag = false;
            }
        }
    }
    //função de contrução da arvore
    private static void buildMaxHeap(Produtos[] A){
        int n = A.length;
        for(int i= (n/2)-1;i>=0;i--){
            maxHeapify(A, i,n);
        }
    }
}
