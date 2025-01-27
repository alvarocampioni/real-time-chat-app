module com.realtimechat {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;
    requires java.desktop;
    requires spring.context;
    requires spring.security.config;
    requires spring.jcl;
    requires java.sql;
    requires spring.security.crypto;

    opens Server to javafx.fxml;
    exports Server to javafx.fxml;
    opens Repository to javafx.fxml;
    exports Repository to javafx.fxml;
    opens Client to javafx.fxml;
    exports Client;
    opens Controller to javafx.fxml;
    exports Controller;
}