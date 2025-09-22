package com.gjls.arverebinarie.classes;

import java.text.Normalizer;
import java.util.Map;
import java.util.TreeMap;

public class ArvoreAVL {
    private Node raiz;
    private int comparacoes;

    public ArvoreAVL() {
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
        int comparacao = palavra.compareTo(no.palavra);

        if (comparacao < 0) {
            no.esq = inserirRec(no.esq, palavra);
        } else if (comparacao > 0) {
            no.dir = inserirRec(no.dir, palavra);
        } else {
            no.frequencia++;
            return no;
        }

        no.altura = 1 + Math.max(getAltura(no.esq), getAltura(no.dir));

        int balanceamento = getBalanceamento(no);

        if (balanceamento > 1 && palavra.compareTo(no.esq.palavra) < 0) {
            return rotacionarADireita(no);
        }

        if (balanceamento < -1 && palavra.compareTo(no.dir.palavra) > 0) {
            return rotacionarAEsquerda(no);
        }

        if (balanceamento > 1 && palavra.compareTo(no.esq.palavra) > 0) {
            no.esq = rotacionarAEsquerda(no.esq);
            return rotacionarADireita(no);
        }

        if (balanceamento < -1 && palavra.compareTo(no.dir.palavra) < 0) {
            no.dir = rotacionarADireita(no.dir);
            return rotacionarAEsquerda(no);
        }

        return no;
    }

    private Node rotacionarADireita(Node no) {
        Node noEsq = no.esq;
        Node noDir = noEsq.dir;

        noEsq.dir = no;
        no.esq = noDir;

        no.altura = Math.max(getAltura(no.esq), getAltura(no.dir)) + 1;
        noEsq.altura = Math.max(getAltura(noEsq.esq), getAltura(noEsq.dir)) + 1;

        return noEsq;
    }

    private Node rotacionarAEsquerda(Node no) {
        Node noDir = no.dir;
        Node noEsq = noDir.esq;

        noDir.esq = no;
        no.dir = noEsq;

        no.altura = Math.max(getAltura(no.esq), getAltura(no.dir)) + 1;
        noDir.altura = Math.max(getAltura(noDir.esq), getAltura(noDir.dir)) + 1;

        return noDir;
    }

    private int getAltura(Node node) {
        if (node == null) {
            return 0;
        }
        return node.altura;
    }

    private int getBalanceamento(Node node) {
        if (node == null) {
            return 0;
        }
        return getAltura(node.esq) - getAltura(node.dir);
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

    public int getcomparacoes() {
        return comparacoes;
    }

    public Node getRaiz() {
        return raiz;
    }

    public int getAltura() {
        return getAltura(raiz);
    }
}
