package com.staykov.swimmingtimeconvertiontool;

import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.util.StringConverter;

public class UIController {
    @FXML
    private ChoiceBox<PoolCourse> fromChoiceBox;
    @FXML
    private ChoiceBox<PoolCourse> toChoiceBox;
    @FXML
    private ChoiceBox<SwimStyle> strokeChoiceBox;
    @FXML
    private ChoiceBox<Distance> distanceChoiceBox;

    @FXML
    private TextField mmIn;
    @FXML
    private TextField ssIn;
    @FXML
    private TextField msIn;
    @FXML
    private TextField mmOut;
    @FXML
    private TextField ssOut;
    @FXML
    private TextField msOut;
    @FXML
    private Label msgLbl;
    @FXML
    private Button calcBtn;

    @FXML
    private Button clrBtn;


    @FXML
    private void initialize() {
        fromChoiceBox.getItems().addAll(PoolCourse.values());
        fromChoiceBox.getSelectionModel().select(1);
        fromChoiceBox.setConverter(getDisplayNameConverter());

        toChoiceBox.getItems().addAll(PoolCourse.values());
        toChoiceBox.getSelectionModel().selectFirst();
        toChoiceBox.setConverter(getDisplayNameConverter());

        strokeChoiceBox.getItems().addAll(SwimStyle.values());
        strokeChoiceBox.getSelectionModel().selectFirst();
        strokeChoiceBox.setConverter(getDisplayNameConverter());

        distanceChoiceBox.getItems().addAll(Distance.values());
        distanceChoiceBox.getSelectionModel().selectFirst();
        distanceChoiceBox.setConverter(getDisplayNameConverter());
        setDefaults();

        calcBtn.disableProperty().bind(Bindings.isEmpty(ssIn.textProperty()));

    }

    @FXML
    private void onCalc(ActionEvent event) {
        if(mmIn.getText().isEmpty())mmIn.setText("0");
        if(msIn.getText().isEmpty())msIn.setText("0");
        if (!isValid()) return;

        SwimTime swimTime = new SwimTime(Integer.parseInt(mmIn.getText()), Integer.parseInt(ssIn.getText()), Integer.parseInt(msIn.getText()));

        EquivalentTime equivalentTime = new EquivalentTime(fromChoiceBox.getValue(), toChoiceBox.getValue(), strokeChoiceBox.getValue(), distanceChoiceBox.getValue(), swimTime);
        equivalentTime.convertTime();
        String convertedTime = equivalentTime.convTimeToString();
        String[] split = convertedTime.split(":");
        mmOut.setText(split[0]);
        ssOut.setText(split[1]);
        msOut.setText(split[2]);
        System.out.println(equivalentTime);


    }

    @FXML
    private void onClear(ActionEvent event) {
        setDefaults();
    }

    private boolean isValid() {
        msgLbl.setText("");
        try {
            if (!mmIn.getText().isEmpty()) Integer.parseInt(mmIn.getText());
            Integer.parseInt(ssIn.getText());
            if (!msIn.getText().isEmpty()) Integer.parseInt(msIn.getText());

        } catch (NumberFormatException e) {
            msgLbl.setText("Invalid input");
            return false;
        }
        return true;
    }

    private void setDefaults() {
        mmIn.setText("");
        ssIn.setText("");
        msIn.setText("");
        mmOut.setText("");
        ssOut.setText("");
        msOut.setText("");
        msgLbl.setText("");
    }

    private static <T extends HasDisplayName> StringConverter<T> getDisplayNameConverter() {
        return new StringConverter<T>() {
            @Override
            public String toString(T object) {
                return object.getDisplayName();
            }

            @Override
            public T fromString(String string) {
                return null;
            }
        };
    }

}