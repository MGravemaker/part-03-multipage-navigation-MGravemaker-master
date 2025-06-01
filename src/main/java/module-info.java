module org.adainf.javapro1uipart03 {
    requires javafx.controls;
    requires javafx.fxml;
    requires mysql.connector.j;
    requires java.sql;

    opens org.adainf.javapro1uipart03 to javafx.fxml;
    exports org.adainf.javapro1uipart03;
}