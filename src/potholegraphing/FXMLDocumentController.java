/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package potholegraphing;

import com.google.gson.Gson;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Scanner;
import java.util.TreeMap;
import javafx.application.Platform;
import javafx.beans.InvalidationListener;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;

/**
 *
 * @author csstudent
 */
public class FXMLDocumentController implements Initializable, ChangeListener<String> {
    
    @FXML
    private MenuBar topMenu;
    
    @FXML
    private void handleCloseButtonAction(ActionEvent event) {
        Platform.exit();
    }
    
    @FXML
    
    private void handleAboutButtonAction(ActionEvent event){
        Alert aboutAlert = new Alert(Alert.AlertType.INFORMATION);
        aboutAlert.setTitle("About this Program");
        aboutAlert.setHeaderText(null);
        aboutAlert.setContentText("I made this program for an AP Computer Science class. It is meant to show polio vaccination percentages from 1980. If you've come across this on GitHub and you're not Mr. Wheadon, I'm not really sure why you're looking at this. Enjoy!");
        aboutAlert.showAndWait();
    }
    
    @FXML
    private MenuItem closeButton, aboutButton;
    
    @FXML
    private ChoiceBox filterChoice;
    
    public void changed(ObservableValue ov, String value, String newValue){
        Settings.setFilterValue(newValue);
        updateGraph(newValue);
    }
    
    @FXML
    private BarChart chart;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        filterChoice.setItems(FXCollections.observableArrayList("All", "2011", "2012", "2013"));
        filterChoice.setValue("All");
        filterChoice.getSelectionModel().selectedItemProperty().addListener(this);
        if(Settings.getPotholes() == null){
            refreshData();
        } else {
            updateGraph(filterChoice.getValue().toString());
        }
        
    }    
    
    public void refreshData(){
        String s = "https://data.cityofchicago.org/resource/7as2-ds3y.json?$select=zip,completion_date,status";
        URL myUrl = null;
        try{
            myUrl= new URL(s);
        } catch (Exception e){
            System.out.println("Something's wrong with your URL - " + s);
            System.exit(-1);
        }
        
        Scanner scan = null;
        try{
            scan = new Scanner(myUrl.openStream());
        }
        catch (Exception e){
            System.out.println("Couldn't connect to - " + s);
            System.exit(-1);
        }
        String str = new String();
        while(scan.hasNext()){
            str += scan.nextLine() + "\n";
        }
        scan.close();

        Gson gson = new Gson();
        Pothole[] potholes = gson.fromJson(str, Pothole[].class);
        Settings.setPotholes(potholes);
        updateGraph(filterChoice.getValue().toString());
    }
    
    
    public void updateGraph(String newFilter){
        chart.getData().clear();
        Map<Integer, Integer> holes = new TreeMap<Integer, Integer>();
        for(Pothole p : Settings.getPotholes()){
            boolean valid = false;
            if(newFilter.equals("All")){
                valid = true;
            } else if(newFilter.equals("2011") && p.getDate() == 2011){
                valid = true;
            } else if(newFilter.equals("2012") && p.getDate() == 2012){
                valid = true;
            } else if(newFilter.equals("2013") && p.getDate() == 2013){
                valid = true;
            }
            if(valid == true){
                Integer zip = p.getZip();
                boolean complete = p.completed();
                if(zip > 60000 && complete){
                    if(! holes.containsKey(zip)) {
                        holes.put(zip, 1);
                    }
                    Integer zipCount = holes.get(zip);
                    holes.put(zip, zipCount + 1);
                }
            }
        }

        XYChart.Series<String, Integer> filledHoles = new XYChart.Series();
        filledHoles.setName("# Filled Potholes");
        Object[] keys = holes.keySet().toArray();
        Arrays.sort(keys);
        for(Object zip : keys){
            filledHoles.getData().add(new XYChart.Data(zip.toString(), holes.get(zip)));
        }
        
        chart.getData().add(filledHoles);
    }
    
}

