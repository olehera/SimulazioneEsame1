/**
 * Sample Skeleton for 'Crimes.fxml' Controller Class
 */

package it.polito.tdp.crimes;

import java.net.URL;
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
	
	public void setModel(Model model) {
    	this.model = model;
    	boxAnno.getItems().addAll(model.getAnni());
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

    
    @SuppressWarnings("unused")
	@FXML
    void doCreaReteCittadina(ActionEvent event) {
    	Integer anno = (int) boxAnno.getValue();
    	
    	if (anno==null) {
    		txtResult.setText("Devi selezionare un anno!");
    		return ;
    	}
    	
    	model.creaGrafo(anno);
    	
    	for (int d: model.getDistretti()) {
    		txtResult.appendText(d+" -> ");
    		for (int distr: model.trovaAdiacenti(d)) {
    			txtResult.appendText(distr+" - ");
    		}
    		txtResult.appendText("\n\n");
    	}
    	
    	boxMese.getItems().addAll(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12);
    	boxGiorno.getItems().addAll(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31);
    	boxAnno.setEditable(false);
    }

    @FXML
    void doSimula(ActionEvent event) {
    	int n = 0;
    	int mese = 0;
    	int giorno = 0;

    	try {
    		n = Integer.parseInt(txtN.getText().trim());
    	} catch(NullPointerException npe) {
    		npe.printStackTrace();
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
