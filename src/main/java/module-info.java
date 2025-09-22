module com.gjls.arverebinarie {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires net.synedra.validatorfx;

    opens com.gjls.arverebinarie to javafx.fxml;
    exports com.gjls.arverebinarie;
}