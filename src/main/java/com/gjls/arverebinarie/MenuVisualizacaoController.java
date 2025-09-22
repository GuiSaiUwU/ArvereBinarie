package com.gjls.arverebinarie;

import com.gjls.arverebinarie.classes.ArrayDinamica;
import com.gjls.arverebinarie.classes.ArvoreAVL;
import com.gjls.arverebinarie.classes.ArvoreBinariaDeBusca;
import com.gjls.arverebinarie.classes.DesenharArvore;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.input.ScrollEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.Normalizer;
import java.util.*;

public class MenuVisualizacaoController {
    @FXML
    private TextArea areaResultado;

    @FXML
    private ScrollPane painelArvore;

    @FXML
    private ChoiceBox<String> choiceBoxArvore;

    /* Fonte: https://gist.github.com/alopes/5358189?permalink_comment_id=3477894#gistcomment-3477894 */
    private static final String[] STOP_WORDS = {
            "a", "à", "adeus", "agora", "aí", "ainda", "além", "algo", "alguém", "algum", "alguma", "algumas", "alguns", "ali", "ampla", "amplas", "amplo", "amplos", "ano", "anos", "ante", "antes", "ao", "aos", "apenas", "apoio", "após", "aquela", "aquelas", "aquele", "aqueles", "aqui", "aquilo", "área", "as", "às", "assim", "até", "atrás", "através", "baixo", "bastante", "bem", "boa", "boas", "bom", "bons", "breve", "cá", "cada", "catorze", "cedo", "cento", "certamente", "certeza", "cima", "cinco", "coisa", "coisas", "com", "como", "conselho", "contra", "contudo", "custa", "da", "dá", "dão", "daquela", "daquelas", "daquele", "daqueles", "dar", "das", "de", "debaixo", "dela", "delas", "dele", "deles", "demais", "dentro", "depois", "desde", "dessa", "dessas", "desse", "desses", "desta", "destas", "deste", "destes", "deve", "devem", "devendo", "dever", "deverá", "deverão", "deveria", "deveriam", "devia", "deviam", "dez", "dezanove", "dezasseis", "dezassete", "dezoito", "dia", "diante", "disse", "disso", "disto", "dito", "diz", "dizem", "dizer", "do", "dois", "dos", "doze", "duas", "dúvida", "e", "é", "ela", "elas", "ele", "eles", "em", "embora", "enquanto", "entre", "era", "eram", "éramos", "és", "essa", "essas", "esse", "esses", "esta", "está", "estamos", "estão", "estar", "estas", "estás", "estava", "estavam", "estávamos", "este", "esteja", "estejam", "estejamos", "estes", "esteve", "estive", "estivemos", "estiver", "estivera", "estiveram", "estivéramos", "estiverem", "estivermos", "estivesse", "estivessem", "estivéssemos", "estiveste", "estivestes", "estou", "etc", "eu", "exemplo", "faço", "falta", "favor", "faz", "fazeis", "fazem", "fazemos", "fazendo", "fazer", "fazes", "feita", "feitas", "feito", "feitos", "fez", "fim", "final", "foi", "fomos", "for", "fora", "foram", "fôramos", "forem", "forma", "formos", "fosse", "fossem", "fôssemos", "foste", "fostes", "fui", "geral", "grande", "grandes", "grupo", "há", "haja", "hajam", "hajamos", "hão", "havemos", "havia", "hei", "hoje", "hora", "horas", "houve", "houvemos", "houver", "houvera", "houverá", "houveram", "houvéramos", "houverão", "houverei", "houverem", "houveremos", "houveria", "houveriam", "houveríamos", "houvermos", "houvesse", "houvessem", "houvéssemos", "isso", "isto", "já", "la", "lá", "lado", "lhe", "lhes", "lo", "local", "logo", "longe", "lugar", "maior", "maioria", "mais", "mal", "mas", "máximo", "me", "meio", "menor", "menos", "mês", "meses", "mesma", "mesmas", "mesmo", "mesmos", "meu", "meus", "mil", "minha", "minhas", "momento", "muita", "muitas", "muito", "muitos", "na", "nada", "não", "naquela", "naquelas", "naquele", "naqueles", "nas", "nem", "nenhum", "nenhuma", "nessa", "nessas", "nesse", "nesses", "nesta", "nestas", "neste", "nestes", "ninguém", "nível", "no", "noite", "nome", "nos", "nós", "nossa", "nossas", "nosso", "nossos", "nova", "novas", "nove", "novo", "novos", "num", "numa", "número", "nunca", "o", "obra", "obrigada", "obrigado", "oitava", "oitavo", "oito", "onde", "ontem", "onze", "os", "ou", "outra", "outras", "outro", "outros", "para", "parece", "parte", "partir", "paucas", "pela", "pelas", "pelo", "pelos", "pequena", "pequenas", "pequeno", "pequenos", "per", "perante", "perto", "pode", "pude", "pôde", "podem", "podendo", "poder", "poderia", "poderiam", "podia", "podiam", "põe", "põem", "pois", "ponto", "pontos", "por", "porém", "porque", "porquê", "posição", "possível", "possivelmente", "posso", "pouca", "poucas", "pouco", "poucos", "primeira", "primeiras", "primeiro", "primeiros", "própria", "próprias", "próprio", "próprios", "próxima", "próximas", "próximo", "próximos", "pude", "puderam", "quais", "quáis", "qual", "quando", "quanto", "quantos", "quarta", "quarto", "quatro", "que", "quê", "quem", "quer", "quereis", "querem", "queremas", "queres", "quero", "questão", "quinta", "quinto", "quinze", "relação", "sabe", "sabem", "são", "se", "segunda", "segundo", "sei", "seis", "seja", "sejam", "sejamos", "sem", "sempre", "sendo", "ser", "será", "serão", "serei", "seremos", "seria", "seriam", "seríamos", "sete", "sétima", "sétimo", "seu", "seus", "sexta", "sexto", "si", "sido", "sim", "sistema", "só", "sob", "sobre", "sois", "somos", "sou", "sua", "suas", "tal", "talvez", "também", "tampouco", "tanta", "tantas", "tanto", "tão", "tarde", "te", "tem", "tém", "têm", "temos", "tendes", "tendo", "tenha", "tenham", "tenhamos", "tenho", "tens", "ter", "terá", "terão", "terceira", "terceiro", "terei", "teremos", "teria", "teriam", "teríamos", "teu", "teus", "teve", "ti", "tido", "tinha", "tinham", "tínhamos", "tive", "tivemos", "tiver", "tivera", "tiveram", "tivéramos", "tiverem", "tivermos", "tivesse", "tivessem", "tivéssemos", "tiveste", "tivestes", "toda", "todas", "todavia", "todo", "todos", "trabalho", "três", "treze", "tu", "tua", "tuas", "tudo", "última", "últimas", "último", "últimos", "um", "uma", "umas", "uns", "vai", "vais", "vão", "vários", "vem", "vêm", "vendo", "vens", "ver", "vez", "vezes", "viagem", "vindo", "vinte", "vir", "você", "vocês", "vos", "vós", "vossa", "vossas", "vosso", "vossos", "zero", "1", "2", "3", "4", "5", "6", "7", "8", "9", "0", "_"
    };
    private static Set<String> STOP_WORDS_SET = null;

    private File arquivoSelecionado;

    /* Usado no painel que desenha as arvores */
    private double nivelDeZoom = 1.0;
    private DesenharArvore desenhoAVL;
    private DesenharArvore desenhoBinariaDeBusca;

    @FXML
    public void initialize() {
        if (STOP_WORDS_SET == null) {
            STOP_WORDS_SET = new HashSet<>(Arrays.asList(STOP_WORDS));
        }

        painelArvore.addEventFilter(ScrollEvent.SCROLL, event -> {
            if (event.isControlDown()) {
                if (event.getDeltaY() > 0) {
                    nivelDeZoom *= 1.1;
                } else {
                    nivelDeZoom /= 1.1;
                }
                if (painelArvore.getContent() != null) {
                    painelArvore.getContent().setScaleX(nivelDeZoom);
                    painelArvore.getContent().setScaleY(nivelDeZoom);
                }
                event.consume();
            }
        });

        choiceBoxArvore.getItems().addAll("Árvore AVL", "Busca Binária (Array Dinâmica)");
        choiceBoxArvore.setOnAction(event -> {
            String opc = choiceBoxArvore.getValue();
            if (opc.equals("Árvore AVL")) {
                painelArvore.setContent(desenhoAVL);
            } else {
                painelArvore.setContent(desenhoBinariaDeBusca);
            }
        });
    }

    public void processarArquivo(File file) {
        if (file == null || areaResultado == null) {
            System.err.println("Arquivo ou área de resultado inválidos.");
            return;
        }

        try {
            List<String> palavras = lerEProcessarArquivo(file);

            ArrayDinamica arrayDinamica = new ArrayDinamica();
            ArvoreBinariaDeBusca arvoreBinariaDeBusca = new ArvoreBinariaDeBusca();
            ArvoreAVL arvoreAVL = new ArvoreAVL();

            /* Inserir em cada estrutura e medir tempos */
            long startTimeArray = System.nanoTime();
            for (String palavra : palavras) arrayDinamica.inserir(palavra);
            long endTimeArray = System.nanoTime();
            double timeArray = (endTimeArray - startTimeArray) / 1_000_000_000.0;

            long startTimeBST = System.nanoTime();
            for (String palavra : palavras) arvoreBinariaDeBusca.inserir(palavra);
            long endTimeBST = System.nanoTime();
            double timeBST = (endTimeBST - startTimeBST) / 1_000_000_000.0;

            long startTimeAVL = System.nanoTime();
            for (String palavra : palavras) arvoreAVL.inserir(palavra);
            long endTimeAVL = System.nanoTime();
            double timeAVL = (endTimeAVL - startTimeAVL) / 1_000_000_000.0;

            List<ResultadoDePerformance> resultados = new ArrayList<>();
            resultados.add(new ResultadoDePerformance("Árvore AVL", arvoreAVL.getcomparacoes(), timeAVL));
            resultados.add(new ResultadoDePerformance("Árvore Binária de Busca", arvoreBinariaDeBusca.getComparacoes(), timeBST));
            resultados.add(new ResultadoDePerformance("Busca Binária (Array Dinâmica)", arrayDinamica.getComparacoes(), timeArray));

            resultados.sort(Comparator.comparingDouble(ResultadoDePerformance::getTempo));

            /* Criação do texto com resultados */
            StringBuilder sb = new StringBuilder();
            for (ResultadoDePerformance resultado : resultados) {
                sb.append(resultado.getNome()).append(":\n");
                sb.append("    ").append(resultado.getComparacoes()).append(" Comparações\n");
                sb.append("    ").append(String.format("%.2f", resultado.getTempo())).append(" Segundos\n");
            }
            sb.append("\nFrequência das palavras (em ordem alfabetica):\n");

            Map<String, Integer> avlFrequencias = arvoreAVL.getFrequencias();
            for (Map.Entry<String, Integer> entry : avlFrequencias.entrySet()) {
                sb.append(entry.getKey()).append(": ").append(entry.getValue()).append("\n");
            }

            areaResultado.setText(sb.toString());

            desenhoBinariaDeBusca = new DesenharArvore(arvoreBinariaDeBusca.getRaiz(), Color.LIGHTGREEN);
            desenhoAVL = new DesenharArvore(arvoreAVL.getRaiz(), Color.LIGHTBLUE);

            choiceBoxArvore.getSelectionModel().select(0);
        } catch (Exception e) {
            e.printStackTrace();
            areaResultado.setText("Erro ao processar arquivo: " + e.getMessage());
        }
    }

    private static List<String> lerEProcessarArquivo(File file) throws IOException {
        List<String> palavras = new ArrayList<>();

        try (BufferedReader leitor = new BufferedReader(new FileReader(file))) {
            String linha;
            while ((linha = leitor.readLine()) != null) {
                linha = linha.toLowerCase();
                String[] palavrasNaLinha = linha.split("\\s+");
                for (String palavra : palavrasNaLinha) {
                    palavra = palavra.trim();
                    palavra = palavra.replaceAll("^[\\p{Punct}]+|[\\p{Punct}]+$", "");

                    if (!palavra.isEmpty() && !STOP_WORDS_SET.contains(palavra)) {
                        palavra = removerAcentos(palavra);
                        palavras.add(palavra);
                    }
                }
            }
        }
        return palavras;
    }

    public static String removerAcentos(String texto) {
        return Normalizer.normalize(texto, Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "");
    }

    public void setArquivoParaProcessar(File arquivo) {
        if (arquivo != null) {
            processarArquivo(arquivo);
        }
    }

    static class ResultadoDePerformance {
        private final String nome;
        private final int comparacoes;
        private final double tempo;

        public ResultadoDePerformance(String nome, int comparacoes, double tempo) {
            this.nome = nome;
            this.comparacoes = comparacoes;
            this.tempo = tempo;
        }

        public String getNome() { return nome; }
        public int getComparacoes() { return comparacoes; }
        public double getTempo() { return tempo; }
    }

    @FXML
    protected void onVoltarButton(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MenuInicial.class.getResource("MenuInicial.fxml"));
        Scene scene = new Scene(fxmlLoader.load());

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle("Visualizador De Árvores");
        stage.setScene(scene);
        stage.show();
    }
}
