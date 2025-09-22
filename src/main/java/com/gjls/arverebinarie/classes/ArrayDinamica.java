package com.gjls.arverebinarie.classes;

import java.util.Map;
import java.util.TreeMap;

public class ArrayDinamica {
    private FrequenciaDePalavra[] array;
    private int tamanho;
    private int capacidade;
    private int comparacoes;

    public ArrayDinamica() {
        capacidade = 10;
        array = new FrequenciaDePalavra[capacidade];
        tamanho = 0;
        comparacoes = 0;
    }

    public void inserir(String word) {
        int index = buscaBinaria(word);

        if (index >= 0) {
            array[index].incrementarFrequencia();
        } else {
            index = -index - 1;

            if (tamanho == capacidade) {
                resize();
            }

            for (int i = tamanho; i > index; i--) {
                array[i] = array[i - 1];
            }

            array[index] = new FrequenciaDePalavra(word);
            tamanho++;
        }
    }

    private int buscaBinaria(String palavra) {
        int low = 0;
        int high = tamanho - 1;

        while (low <= high) {
            int mid = low + (high - low) / 2;
            comparacoes++;

            int comparison = palavra.compareTo(array[mid].getPalavra());

            if (comparison == 0) {
                return mid;
            } else if (comparison < 0) {
                high = mid - 1;
            } else {
                low = mid + 1;
            }
        }

        return -(low + 1);
    }

    private void resize() {
        capacidade *= 2;
        FrequenciaDePalavra[] newArray = new FrequenciaDePalavra[capacidade];

        if (tamanho >= 0) System.arraycopy(array, 0, newArray, 0, tamanho);

        array = newArray;
    }

    public Map<String, Integer> getFrequencias() {
        Map<String, Integer> frequencias = new TreeMap<>();

        for (int i = 0; i < tamanho; i++) {
            frequencias.put(array[i].getPalavra(), array[i].getFrequencia());
        }

        return frequencias;
    }

    public int getComparacoes() {
        return comparacoes;
    }

    static class FrequenciaDePalavra {
        private final String palavra;
        private int frequencia;

        public FrequenciaDePalavra(String palavra) {
            this.palavra = palavra;
            this.frequencia = 1;
        }

        public String getPalavra() {
            return palavra;
        }

        public int getFrequencia() {
            return frequencia;
        }

        public void incrementarFrequencia() {
            frequencia++;
        }
    }
}
