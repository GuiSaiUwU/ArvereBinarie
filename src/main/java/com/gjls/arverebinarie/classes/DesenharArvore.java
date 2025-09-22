package com.gjls.arverebinarie.classes;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;


public class DesenharArvore extends Canvas {
    private final Node root;
    private static final int NODE_SIZE = 30;
    private static final int LEVEL_HEIGHT = 80;
    private static final int H_SPACING = 40;
    private final Color cor;

    public DesenharArvore(Node root, Color cor) {
        this.root = root;
        this.cor = cor;

        int altura = getAltura(root);
        int largura = getLargura(root);

        double canvasWidth = Math.max(800, largura * (NODE_SIZE + H_SPACING));
        double canvasHeight = Math.max(600, altura * LEVEL_HEIGHT + 100);

        setWidth(canvasWidth);
        setHeight(canvasHeight);

        renderizarArvore();
    }

    private void renderizarArvore() {
        GraphicsContext gc = getGraphicsContext2D();
        gc.clearRect(0, 0, getWidth(), getHeight());
        gc.setFont(new Font(12));
        gc.setFill(Color.BLACK);
        gc.setStroke(Color.BLACK);
        gc.setLineWidth(2);

        // o nó raiz tem que ser centralizado
        drawNode(gc, root, getWidth() / 2, 40, getWidth() / 4);
    }

    private void drawNode(GraphicsContext gc, Node node, double x, double y, double offset) {
        if (node == null) return;

        // filhos
        if (node.esq != null) {
            gc.strokeLine(x, y, x - offset, y + LEVEL_HEIGHT);
            drawNode(gc, node.esq, x - offset, y + LEVEL_HEIGHT, offset / 2);
        }
        if (node.dir != null) {
            gc.strokeLine(x, y, x + offset, y + LEVEL_HEIGHT);
            drawNode(gc, node.dir, x + offset, y + LEVEL_HEIGHT, offset / 2);
        }

        // nó em si
        gc.setFill(cor);
        gc.fillOval(x - NODE_SIZE / 2, y - NODE_SIZE / 2, NODE_SIZE, NODE_SIZE);
        gc.setStroke(Color.BLACK);
        gc.strokeOval(x - NODE_SIZE / 2, y - NODE_SIZE / 2, NODE_SIZE, NODE_SIZE);

        // texto
        gc.setFont(gc.getFont());
        Text tempText = new Text(node.palavra);
        tempText.setFont(gc.getFont());
        double textWidth = tempText.getLayoutBounds().getWidth();
        double textHeight = tempText.getLayoutBounds().getHeight();
        double textX = x - textWidth / 2;
        double textY = y + textHeight / 4;

        gc.setStroke(Color.BLACK);
        gc.setLineWidth(2);
        gc.strokeText(node.palavra, textX, textY);

        gc.setFill(Color.WHITE);
        gc.fillText(node.palavra, textX, textY);
    }

    private int getAltura(Node node) {
        if (node == null) return 0;
        return 1 + Math.max(getAltura(node.esq), getAltura(node.dir));
    }

    private int getLargura(Node node) {
        if (node == null) return 0;
        if (node.esq == null && node.dir == null) return 1;
        return getLargura(node.esq) + getLargura(node.dir);
    }
}
