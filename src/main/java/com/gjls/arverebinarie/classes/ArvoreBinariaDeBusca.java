package com.gjls.arverebinarie.classes;

import java.text.Normalizer;
import java.util.Map;
import java.util.TreeMap;

public class ArvoreBinariaDeBusca {
    private Node raiz;
    private int comparacoes;

    public ArvoreBinariaDeBusca() {
        raiz = null;
        comparacoes = 0;
    }

    public void inserir(String palavra) {
        raiz = inserirRec(raiz, palavra);
    }

    private Node inserirRec(Node no, String palavra) {
        if (no == null) {
            return new Node(palavra);
        }

        comparacoes++;
        int comparison = palavra.compareTo(no.palavra);

        if (comparison < 0) {
            no.esq = inserirRec(no.esq, palavra);
        } else if (comparison > 0) {
            no.dir = inserirRec(no.dir, palavra);
        } else {
            no.frequencia++;
        }

        return no;
    }

    public Map<String, Integer> getFrequencias() {
        Map<String, Integer> frequencias = new TreeMap<>();
        inOrderTraversal(raiz, frequencias);
        return frequencias;
    }

    private void inOrderTraversal(Node node, Map<String, Integer> frequencies) {
        if (node != null) {
            inOrderTraversal(node.esq, frequencies);
            frequencies.put(node.palavra, node.frequencia);
            inOrderTraversal(node.dir, frequencies);
        }
    }

    public int getComparacoes() {
        return comparacoes;
    }

    public Node getRaiz() {
        return raiz;
    }

    public int getAltura() {
        return getAltura(raiz);
    }

    private int getAltura(Node node) {
        if (node == null) {
            return 0;
        }
        return 1 + Math.max(getAltura(node.esq), getAltura(node.dir));
    }
}
