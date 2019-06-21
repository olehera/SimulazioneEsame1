package it.polito.tdp.crimes;

import java.net.URL;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.ResourceBundle;

import it.polito.tdp.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class CrimesController {

	private Model model;
	private int anno;
	
	public void setModel(Model model) {
    	this.model = model;
    	boxAnno.getItems().addAll(model.getAnni());
    	anno = 0;
    }
	
    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="boxAnno"
    private ComboBox<Integer> boxAnno; // Value injected by FXMLLoader

    @FXML // fx:id="boxMese"
    private ComboBox<Integer> boxMese; // Value injected by FXMLLoader

    @FXML // fx:id="boxGiorno"
    private ComboBox<Integer> boxGiorno; // Value injected by FXMLLoader

    @FXML // fx:id="btnCreaReteCittadina"
    private Button btnCreaReteCittadina; // Value injected by FXMLLoader

    @FXML // fx:id="btnSimula"
    private Button btnSimula; // Value injected by FXMLLoader

    @FXML // fx:id="txtN"
    private TextField txtN; // Value injected by FXMLLoader

    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader

    @FXML
    void doCreaReteCittadina(ActionEvent event) {
    	
    	if (boxAnno.getValue()==null) {
    		txtResult.setText("Devi selezionare un anno!");
    		return ;
    	} else 
    		anno = boxAnno.getValue();
    	
    	model.creaGrafo(anno);
    	
    	for (int d: model.getDistretti()) {
    		txtResult.appendText(d+" -> ");
    		for (int distr: model.trovaAdiacenti(d)) {
    			txtResult.appendText(distr+" - ");
    		}
    		txtResult.appendText("\n\n");
    	}
    	
    	boxMese.getItems().addAll(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12);
    }
    
    @FXML
    void doCaricaGiorni(ActionEvent event) {
    	Integer mese = boxMese.getValue();
    	boxGiorno.getItems().clear();
    	boxGiorno.getItems().addAll(model.getGiorni(mese, anno));
    }

    @FXML
    void doSimula(ActionEvent event) {
    	int n = 0;
    	int mese = 0;
    	int giorno = 0;
    	LocalDate data;

    	try {
    		n = Integer.parseInt(txtN.getText().trim());
    	} catch(NullPointerException npe) {
    		txtResult.setText("Inserisci un numero intero tra 1 e 10");
    		txtN.clear();
    		return ;
    	}
    	
    	if ( n < 1 || n > 10 ) {
    		txtResult.setText("Inserisci numero tra 1 e 10");
    		return ;
    	}
    	
    	if (boxMese.getValue()==null) {
    		txtResult.setText("Devi selezionare un mese!");
    		return ;
    	} else 
    		mese = (int) boxMese.getValue();
    	
    	if (boxGiorno.getValue()==null) {
    		txtResult.setText("Devi selezionare un giorno!");
    		return ;
    	} else
    		giorno = (int) boxGiorno.getValue();
    	
    	try {
    		data = LocalDate.of(anno, mese, giorno);
    	} catch (DateTimeException e) {
    		txtResult.setText("Errore nella Data selezionata!");
    		return ;
		}
    	
    	txtResult.setText("Numero di eventi mal gestiti: "+model.simula(n, anno, data));
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert boxAnno != null : "fx:id=\"boxAnno\" was not injected: check your FXML file 'Crimes.fxml'.";
        assert boxMese != null : "fx:id=\"boxMese\" was not injected: check your FXML file 'Crimes.fxml'.";
        assert boxGiorno != null : "fx:id=\"boxGiorno\" was not injected: check your FXML file 'Crimes.fxml'.";
        assert btnCreaReteCittadina != null : "fx:id=\"btnCreaReteCittadina\" was not injected: check your FXML file 'Crimes.fxml'.";
        assert btnSimula != null : "fx:id=\"btnSimula\" was not injected: check your FXML file 'Crimes.fxml'.";
        assert txtN != null : "fx:id=\"txtN\" was not injected: check your FXML file 'Crimes.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Crimes.fxml'.";
    }
    
}