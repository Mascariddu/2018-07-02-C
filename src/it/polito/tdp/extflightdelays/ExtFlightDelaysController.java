/**
 * Sample Skeleton for 'ExtFlightDelays.fxml' Controller Class
 */

package it.polito.tdp.extflightdelays;

import java.net.URL;
import java.util.ResourceBundle;

import it.polito.tdp.extflightdelays.model.Airport;
import it.polito.tdp.extflightdelays.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class ExtFlightDelaysController {

	private Model model;

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader

    @FXML // fx:id="compagnieMinimo"
    private TextField compagnieMinimo; // Value injected by FXMLLoader

    @FXML // fx:id="btnAnalizza"
    private Button btnAnalizza; // Value injected by FXMLLoader

    @FXML // fx:id="cmbBoxAeroportoPartenza"
    private ComboBox<Airport> cmbBoxAeroportoPartenza; // Value injected by FXMLLoader

    @FXML // fx:id="btnAeroportiConnessi"
    private Button btnAeroportiConnessi; // Value injected by FXMLLoader

    @FXML // fx:id="cmbBoxAeroportoDestinazione"
    private ComboBox<Airport> cmbBoxAeroportoDestinazione; // Value injected by FXMLLoader

    @FXML // fx:id="numeroTratteTxtInput"
    private TextField numeroTratteTxtInput; // Value injected by FXMLLoader

    @FXML // fx:id="btnCercaItinerario"
    private Button btnCercaItinerario; // Value injected by FXMLLoader

    @FXML
    void doAnalizzaAeroporti(ActionEvent event) {
    	
    	txtResult.clear();

    	try {
    		
    		int num = Integer.parseInt(this.compagnieMinimo.getText());
    		model.creaGrafo(num);
    		this.cmbBoxAeroportoPartenza.getItems().clear();
    		this.cmbBoxAeroportoPartenza.getItems().addAll(model.getVertex());
    		
    	} catch(NumberFormatException e) {
    		
    		txtResult.appendText("Inserisci un valore numerico!");
    		
    	}
    }

    @FXML
    void doCalcolaAeroportiConnessi(ActionEvent event) {

    	txtResult.clear();
    	
    	Airport airport = this.cmbBoxAeroportoPartenza.getValue();
    	
    	if(airport != null) {
    		
    		for(String string : model.getConnessi(airport))
    			txtResult.appendText(string+"\n");
    		this.cmbBoxAeroportoDestinazione.getItems().clear();
    		this.cmbBoxAeroportoDestinazione.getItems().addAll(model.getVertex());
    		
    	} else txtResult.appendText("Seleziona almeno un aeroporto!");
    }

    @FXML
    void doCercaItinerario(ActionEvent event) {
    	
    	txtResult.clear();
    	
    	Airport destinazione = this.cmbBoxAeroportoDestinazione.getValue();
    	Airport partenza = this.cmbBoxAeroportoPartenza.getValue();
    	
    	try {
		
        int tratte =  Integer.parseInt(numeroTratteTxtInput.getText());
    	if(partenza != null && destinazione != null) {
    		
    		if(!partenza.equals(destinazione)) {
    		for(Airport airport : model.getSequenza(partenza,destinazione,tratte))
    			txtResult.appendText(airport+"\n");
    		txtResult.appendText("Numero totale di voli: " +model.conta(model.getSequenza(partenza, destinazione, tratte)));
    		
    		}else txtResult.appendText("Seleziona due aeroporti differenti!");
    	} else txtResult.appendText("Seleziona un aeroporto di partenza ed uno di destinazione!");
    	}  catch (NumberFormatException e) {
			// TODO: handle exception
    		txtResult.appendText("Inserisci un valore numerico!");
		}
    	
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'ExtFlightDelays.fxml'.";
        assert compagnieMinimo != null : "fx:id=\"compagnieMinimo\" was not injected: check your FXML file 'ExtFlightDelays.fxml'.";
        assert btnAnalizza != null : "fx:id=\"btnAnalizza\" was not injected: check your FXML file 'ExtFlightDelays.fxml'.";
        assert cmbBoxAeroportoPartenza != null : "fx:id=\"cmbBoxAeroportoPartenza\" was not injected: check your FXML file 'ExtFlightDelays.fxml'.";
        assert btnAeroportiConnessi != null : "fx:id=\"btnAeroportiConnessi\" was not injected: check your FXML file 'ExtFlightDelays.fxml'.";
        assert cmbBoxAeroportoDestinazione != null : "fx:id=\"cmbBoxAeroportoDestinazione\" was not injected: check your FXML file 'ExtFlightDelays.fxml'.";
        assert numeroTratteTxtInput != null : "fx:id=\"numeroTratteTxtInput\" was not injected: check your FXML file 'ExtFlightDelays.fxml'.";
        assert btnCercaItinerario != null : "fx:id=\"btnCercaItinerario\" was not injected: check your FXML file 'ExtFlightDelays.fxml'.";

    }
    
    
    public void setModel(Model model) {
  		this.model = model;
  		
  	}
}
