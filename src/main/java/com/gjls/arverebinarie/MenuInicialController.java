package com.gjls.arverebinarie;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

public class MenuInicialController {
    @FXML
    private Label LabelArquivoSelecionado;

    @FXML
    private Button ButtonSelecionarArquivo;

    @FXML
    private Button ButtonConfirmarSelecao;

    public static String arquivoSelecionado;
    public static boolean arquivoFoiSelecionado;

    @FXML
    public void initialize() {
        if (arquivoFoiSelecionado) {
            ButtonConfirmarSelecao.setDisable(false);

            String[] diretorios = arquivoSelecionado.replace('\\', '/').split(".+?/(?=[^/]+$)");
            if (diretorios.length > 1) {
                LabelArquivoSelecionado.setText(diretorios[diretorios.length - 1]);
            }
        }
    }

    @FXML
    protected void onButtonSelecionarArquivo(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Selecione um arquivo de texto");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Arquivos de Texto", "*.txt"));

        File arquivo = fileChooser.showOpenDialog(ButtonSelecionarArquivo.getScene().getWindow());

        if (arquivo != null) {
            arquivoSelecionado = arquivo.getAbsolutePath();
            arquivoFoiSelecionado = true;
            LabelArquivoSelecionado.setText(arquivo.getName());
        }
        else {
            arquivoFoiSelecionado = false;
            LabelArquivoSelecionado.setText("Nenhum arquivo selecionado");
        }

        ButtonConfirmarSelecao.setDisable(!arquivoFoiSelecionado);
    }

    @FXML
    protected void onButtonConfirmarSelecao(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MenuInicial.class.getResource("MenuVisualizacao.fxml"));
        Scene scene = new Scene(fxmlLoader.load());

        MenuVisualizacaoController controller = fxmlLoader.getController();
        File arquivo = new File(arquivoSelecionado);
        controller.setArquivoParaProcessar(arquivo);

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle("Visualizador De Árvores");
        stage.setScene(scene);
        stage.show();

        stage.setMaximized(true);
    }
}