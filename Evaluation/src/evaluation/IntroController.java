/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package evaluation;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author USER
 */
public class IntroController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    Button cmtdBtn,cmtcBtn;
    
    @FXML
    private void mousemoved() throws IOException{
        cmtdBtn.setStyle("-fx-background-color: rgba(0,255,127,0.5); -fx-background-radius:100em;");
    }
    
     @FXML
    private void mouseexcited() throws IOException{
      
        cmtdBtn.setStyle("-fx-background-color:  rgba(65,105,225,0.4); -fx-background-radius:100em;");
    }
    
    @FXML
    private void mousemoved1() throws IOException{
        cmtcBtn.setStyle("-fx-background-color: rgba(0,255,127,0.5); -fx-background-radius:100em;");
    }
    
     @FXML
    private void mouseexcited1() throws IOException{
      
        cmtcBtn.setStyle("-fx-background-color:  rgba(65,105,225,0.4); -fx-background-radius:100em;");
    }
    
    @FXML
    private void cmtdClicked() throws IOException{
        FXMLLoader loader = new FXMLLoader();
               loader.setLocation(getClass().getResource("FXMLDocument.fxml"));
        
               loader.load();
        
               FXMLDocumentController display = loader.getController();
        
               Parent k = loader.getRoot();
        
               Stage stage = new Stage ();
        
               stage.setScene(new Scene(k));
               
               stage.show();
    }
    
    @FXML
    private void cmtcClicked() throws IOException{
        FXMLLoader loader = new FXMLLoader();
               loader.setLocation(getClass().getResource("CMTC.fxml"));
        
               loader.load();
        
               CMTCController display = loader.getController();
        
               Parent k = loader.getRoot();
        
               Stage stage = new Stage ();
        
               stage.setScene(new Scene(k));
               
               stage.show();
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
