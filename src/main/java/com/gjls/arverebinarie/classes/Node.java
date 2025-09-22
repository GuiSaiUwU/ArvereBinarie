package com.gjls.arverebinarie.classes;

public class Node {
    public String palavra;
    public int frequencia;
    public Node esq;
    public Node dir;
    public int altura;

    public Node(String palavra) {
        this.palavra = palavra;
        this.frequencia = 1;
        this.esq = null;
        this.dir = null;
        this.altura = 1;
    }
}
