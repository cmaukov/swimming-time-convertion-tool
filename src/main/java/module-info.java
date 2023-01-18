module com.staykov.swimmingtimeconvertiontool {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.staykov.swimmingtimeconvertiontool to javafx.fxml;
    exports com.staykov.swimmingtimeconvertiontool;
}