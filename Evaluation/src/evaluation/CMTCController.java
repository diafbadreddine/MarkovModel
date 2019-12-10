/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package evaluation;

import static java.awt.Color.blue;
import static java.awt.Color.green;
import static java.awt.Color.red;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 *
 * @author USER
 */
public class CMTCController implements Initializable {
    
    
    @FXML
    public AnchorPane pane;
    @FXML
    Button okBtn,finBtn;
    @FXML
    public Circle circle;
    @FXML
    ComboBox ColorS;
    @FXML
    Label infoL;
    @FXML
    private TextField cmtcVal,TNoeud,TDe,TValeur,TA;
    
    double orgSceneX, orgSceneY,offsetX,offsetY,newTranslateX,newTranslateY;
    double orgTranslateX, orgTranslateY;
    int clicked = 1;int size;
    int number;
    ArrayList<Noeud> noeuds = new ArrayList<Noeud>();
    ArrayList<Arc> arcs = new ArrayList<Arc>();
    
    
    @FXML
    private void mousemoved() throws IOException{
        okBtn.setStyle("-fx-background-color: rgba(0,255,127,0.5); -fx-background-radius:100em;");
    }
    
     @FXML
    private void mouseexcited() throws IOException{
      
        okBtn.setStyle("-fx-background-color:  rgba(65,105,225,0.4); -fx-background-radius:100em;");
    }
    
    @FXML
    private void mousemoved1() throws IOException{
        finBtn.setStyle("-fx-background-color: rgba(0,255,127,0.5); -fx-background-radius:100em;");
    }
    
     @FXML
    private void mouseexcited1() throws IOException{
      
        finBtn.setStyle("-fx-background-color:  rgba(65,105,225,0.4); -fx-background-radius:100em;");
    }
    @FXML
    private void heallo() {
        
        Text txt = new Text("A");
        txt.setX(93);
        txt.setY(102);
        txt.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 20)); 
        //Setting the color 
      txt.setFill(Color.BROWN); 
       
      //Setting the Stroke  
      txt.setStrokeWidth(2); 
      
      // Setting the stroke color
      txt.setStroke(Color.BLUE); 
        Circle y = new Circle(100,100,30);
        pane.getChildren().add(y);
        pane.getChildren().add(txt);
        
    }
    
    @FXML
        public void pressed(MouseEvent t) {
            orgSceneX = t.getSceneX();
            orgSceneY = t.getSceneY();
            orgTranslateX = ((Circle)(t.getSource())).getTranslateX();
            orgTranslateY = ((Circle)(t.getSource())).getTranslateY();
        }
    @FXML
        public void Dragged(MouseEvent t) {
            offsetX = t.getSceneX() - orgSceneX;
            offsetY = t.getSceneY() - orgSceneY;
            newTranslateX = orgTranslateX + offsetX;
            newTranslateY = orgTranslateY + offsetY;
             
//            ((Circle)(t.getSource())).setTranslateX(newTranslateX);
//            ((Circle)(t.getSource())).setTranslateY(newTranslateY);
        }
    @FXML
        public void Released(MouseEvent t)     {
            infoL.setText("");
            if(TNoeud.getText().isEmpty())
             {
                TNoeud.setStyle("-fx-background-color:rgba(255,160,122,0.5);");
             }
            else
            {
                if(cmtcVal.getText().isEmpty())
                {
                    cmtcVal.setStyle("-fx-background-color:rgba(255,160,122,0.5);");
                }
                else
                {
                boolean found = false;
             for(Noeud x:noeuds)
             {
                 if(x.getName().equals(TNoeud.getText()))
                 {
                     found = true;
                 }
             }
             if(found)
             {
                 infoL.setStyle("-fx-text-fill : #FFA07A");
                   infoL.setText("This node already exist ");
             }
             else
             {
             TNoeud.setStyle("-fx-background-color:rgba(255,255,255,1);");
             float val = Float.valueOf(cmtcVal.getText());
             Text txt = new Text(TNoeud.getText());
             txt.setTranslateX(newTranslateX-7-200);
             txt.setTranslateY(newTranslateY+2);
             Noeud x = new Noeud();
//             txt.setX(93);
//             txt.setY(102);
             txt.setFont(Font.font("verdana", FontWeight.NORMAL, FontPosture.REGULAR, 20)); 
             //Setting the color 
                txt.setFill(Color.BLACK); 

                //Setting the Stroke  
                txt.setStrokeWidth(2); 

                // Setting the stroke color
                txt.setStroke(Color.BLACK);
      
             Circle z = paintCircle();
             z.setTranslateX(newTranslateX-200);
             z.setTranslateY(newTranslateY);
             x.cmtcValue=val;
             x.setX(newTranslateX-200);
             x.setY(newTranslateY);
             x.setName(TNoeud.getText());
             x.num = number;
             number++;
             noeuds.add(x);
             
             pane.getChildren().add(z);
             pane.getChildren().add(txt);
             offsetX=0;offsetY=0;newTranslateX=0;newTranslateY=0;
             }
            }
        }
//            ((Circle)(t.getSource())).setTranslateX(newTranslateX);
//            ((Circle)(t.getSource())).setTranslateY(newTranslateY);
        }
        
    @FXML
       public void okClicked(){   
           infoL.setText("");
           String from = TDe.getText();
           String to = TA.getText();
           Noeud start = new Noeud();
           Noeud stop = new Noeud();
           boolean deja = false,dejaFrom = false;
           for(Noeud x:noeuds)
           {
               if(x.getName().equals(from))
               {
                   start = x;
               }
               if(x.getName().equals(to))
               {
                   stop = x;
               }
           }
           
           if(!nodeExist(from))
           {
               infoL.setStyle("-fx-text-fill : #FFA07A");
                   infoL.setText("Node "+from+" does not exist ");
           }
            else
           {
               if(!nodeExist(to))
               {
                   infoL.setStyle("-fx-text-fill : #FFA07A");
                   infoL.setText("Node "+to+" does not exist ");
                   
               }
               else
               {
                   
                if(from.equals(to))
                {
                    infoL.setText("C'est une CMTC ");
                }
                else
                {
           for(Noeud x:start.To)
           {
               if(x.getName().equals(stop.getName()))
               {
                   deja = true;
               }
           }
           if(deja)
           {
               infoL.setStyle("-fx-text-fill : #FFA07A");
                   infoL.setText("This arc already exist ");
           }
           else
           {
               
               for(Noeud x:start.From)
           {
               if(x.getName().equals(stop.getName()))
               {
                   dejaFrom = true;
               }
           }
           Arrow a = new Arrow();
           Arc y = new Arc();
           y.from=start;
           y.to=stop;
         //  y.valeur=Double.parseDouble(TValeur.getText());
           
           if(TValeur.getText().isEmpty())
           {
               TValeur.setStyle("-fx-background-color:rgba(255,160,122,0.5);");
           }
           else
           {
           TValeur.setStyle("-fx-background-color:rgba(255,255,255,1);");
           
           y.valeur=Float.parseFloat(TValeur.getText());
           if(y.valeur<0 || y.valeur > y.from.cmtcValue)
           {
               TValeur.setStyle("-fx-background-color:rgba(255,160,122,0.5);");
           }
           else
           {
               
               float check=0;
               for(Arc x:arcs)
               {
                   if(x.from.getName().equals(start.getName()))
                   {
                       check=check+ x.valeur;
                   }
               }
           
            if(check>start.cmtcValue)
            {
                infoL.setStyle("-fx-text-fill : #FFA07A");
                   infoL.setText("Value > "+String.valueOf(start.cmtcValue));
                
            }
            else
            {
                
            if(check+y.valeur>start.cmtcValue)
            {
                infoL.setStyle("-fx-text-fill : #FFA07A");
                   infoL.setText("Choose another value ");
                
            }
            else
            {
           arcs.add(y);
           for(Noeud x:noeuds)
           {
               if(x.getName().equals(from))
               {
                   x.To.add(stop);
               }
               if(x.getName().equals(to))
               {
                   x.From.add(start);
               }
               
           }
           TValeur.setStyle("-fx-background-color:rgba(255,255,255,1);");
           if(!dejaFrom)
           {
           if(start.getName().equals(stop.getName()))
           {
           Text txt = new Text(TValeur.getText());
           txt.setX(start.getX());
           txt.setY(start.getY()-60.0f);
        
           txt.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 20)); 
           //Setting the color 
           txt.setFill(Color.WHITE); 
       
           //Setting the Stroke  
           txt.setStrokeWidth(2); 
      
           // Setting the stroke color
           txt.setStroke(Color.WHITE); 
           pane.getChildren().add(txt);
           }
           else
           {
           a.setStartX(start.getX()+20 );
           a.setStartY(start.getY()+20);
           a.setEndX(stop.getX()+20);
           a.setEndY(stop.getY()+20);
           Text txt = new Text(TValeur.getText());
           txt.setX(start.getX()+20+(stop.getX()-start.getX())/2);
           txt.setY(start.getY()+20+(stop.getY()-start.getY())/2+Math.random()%10);
        
           txt.setFont(Font.font("verdana", FontWeight.NORMAL, FontPosture.REGULAR, 20)); 
           //Setting the color 
           txt.setFill(Color.WHITE); 
       
           //Setting the Stroke  
           txt.setStrokeWidth(2); 
      
           // Setting the stroke color
           txt.setStroke(Color.WHITE); 
        
        
           pane.getChildren().add(txt);
           }
           }
           else
           {
           a.setStartX(start.getX()-20);
           a.setStartY(start.getY()-20);
           a.setEndX(stop.getX()-20);
           a.setEndY(stop.getY()-20); 
           Text txt = new Text(TValeur.getText());
           txt.setX(start.getX()-20+(stop.getX()-start.getX())/2);
           txt.setY(start.getY()-20+(stop.getY()-start.getY())/2+Math.random()%10);
        
           txt.setFont(Font.font("verdana", FontWeight.NORMAL, FontPosture.REGULAR, 20)); 
           //Setting the color 
           txt.setFill(Color.WHITE); 
       
           //Setting the Stroke  
           txt.setStrokeWidth(2); 

           // Setting the stroke color
           txt.setStroke(Color.WHITE); 
        
           
           
           pane.getChildren().add(txt);
           }

           pane.getChildren().add(a);
            }
            }
           }
           
           }
           }
               }
               }
       }
       }
       
    @FXML 
       public void arrowDraw(MouseEvent t)
       {
           Arrow arrow = new Arrow();
           pane.getChildren().add(arrow);
           
           switch(t.getButton()){
            case SECONDARY:
                // set pos of end with arrow head
                arrow.setEndX(t.getX());
                arrow.setEndY(t.getY());
                break;
            case PRIMARY:
                // set pos of end without arrow head
                arrow.setStartX(t.getX());
                arrow.setStartY(t.getY());
                break;
           }
           
       }
    
    @FXML
       public void finichClicked() throws IOException{
           if(checkGraph())
           {
               FXMLLoader loader = new FXMLLoader();
               loader.setLocation(getClass().getResource("cmtcOptions.fxml"));
        
               loader.load();
        
               cmtcOptionsController display = loader.getController();
        
               display.arcs=arcs;
               display.noeuds=noeuds;
               display.size=size;
               Parent k = loader.getRoot();
        
               Stage stage = new Stage ();
        
               stage.setScene(new Scene(k));
        
               stage.showAndWait();
           }
           else
           {
               infoL.setStyle("-fx-text-fill : #FFA07A");
                   infoL.setText("Your MM is not yet completed ");
           }
       }
    @FXML
      public void comboSelect()
      {
          if(ColorS.getValue().toString().equals("Blanc"))
          {
              circle.setFill(Color.WHITE);
          }
          if(ColorS.getValue().toString().equals("Bleu"))
          {
              circle.setFill(Color.BLUE);
          }
          if(ColorS.getValue().toString().equals("Vert"))
          {
              circle.setFill(Color.GREEN);
          }
          if(ColorS.getValue().toString().equals("Rouge"))
          {
              circle.setFill(Color.RED);
          }
      }
      
    @FXML
    public void cmtdIncClicked() throws IOException{
        ArrayList<Noeud> newNoeuds = new ArrayList<Noeud>();
        ArrayList<Arc> newArcs = new ArrayList<Arc>();
        Arc x = new Arc();
        for(int i=0;i<noeuds.size();i++)
        {
           newNoeuds.add(noeuds.get(i));
        }
        for(int i=0;i<arcs.size();i++)
        {
           x = new Arc();
           x.from=arcs.get(i).from;
           x.to=arcs.get(i).to;
           x.valeur=arcs.get(i).valeur/arcs.get(i).from.cmtcValue;
           newArcs.add(x);
        }
        FXMLLoader loader = new FXMLLoader();
               loader.setLocation(getClass().getResource("FXMLDocument.fxml"));
        
               loader.load();
        
               FXMLDocumentController display = loader.getController();
        
               display.arcs=newArcs;
               display.noeuds=newNoeuds;
               display.size=size;
               for(int i=0;i<newNoeuds.size();i++)
               {
                   Text txt = new Text(newNoeuds.get(i).getName());
                  txt.setTranslateX(newNoeuds.get(i).getX());
                 txt.setTranslateY(newNoeuds.get(i).getY());
                 
                  txt.setFont(Font.font("verdana", FontWeight.NORMAL, FontPosture.REGULAR, 20)); 
             //Setting the color 
                txt.setFill(Color.BLACK); 

                //Setting the Stroke  
                txt.setStrokeWidth(2); 

                // Setting the stroke color
                txt.setStroke(Color.BLACK);
                Circle z = paintCircle();
             z.setTranslateX(newNoeuds.get(i).getX());
             z.setTranslateY(newNoeuds.get(i).getY());
                display.pane.getChildren().add(z);
             display.pane.getChildren().add(txt);
               }
               
               for(int i=0;i<newArcs.size();i++)
               {
                   Arrow a =new Arrow();
                   a.setStartX(newArcs.get(i).from.getX()+20 );
           a.setStartY(newArcs.get(i).from.getY()+20);
           a.setEndX(newArcs.get(i).to.getX()+20);
           a.setEndY(newArcs.get(i).to.getY()+20);
           Text txt = new Text(String.valueOf(newArcs.get(i).valeur));
           txt.setX(newArcs.get(i).from.getX()+20+(newArcs.get(i).to.getX()-newArcs.get(i).from.getX())/2);
           txt.setY(newArcs.get(i).from.getY()+20+(newArcs.get(i).to.getY()-newArcs.get(i).from.getY())/2+Math.random()%10);
        
           txt.setFont(Font.font("verdana", FontWeight.NORMAL, FontPosture.REGULAR, 20)); 
           //Setting the color 
           txt.setFill(Color.WHITE); 
       
           //Setting the Stroke  
           txt.setStrokeWidth(2); 
      
           // Setting the stroke color
           txt.setStroke(Color.WHITE); 
        
        
           display.pane.getChildren().add(txt);
           display.pane.getChildren().add(a);
               }
               Parent k = loader.getRoot();
        
               Stage stage = new Stage ();
        
               stage.setScene(new Scene(k));
        
               stage.showAndWait();
    }
    
    public Circle paintCircle(){
        Circle x =new Circle(50.0f,Color.WHITE);
        
          if(ColorS.getValue().toString().equals("Bleu"))
          {
              x.setFill(Color.BLUE);
          }
          if(ColorS.getValue().toString().equals("Vert"))
          {
              x.setFill(Color.GREEN);
          }
          if(ColorS.getValue().toString().equals("Rouge"))
          {
              x.setFill(Color.RED);
          }
          return x;
    }
    
    public boolean checkGraph(){
        ArrayList<String> y = new ArrayList<String>();
        for(Noeud x:noeuds)
        {
            boolean found = false;
            for(String z:y)
            {
                if(x.getName().equals(z))
                    found = true;
            }
            if(!found)
               y.add(x.getName());
        }
        size = y.size();
        int i =0;
        for(String x:y)
        {
            Noeud  w = new Noeud();
            boolean entered =false;
            float sum=0;
            for(Arc z:arcs)
            {
                if(z.from.getName().equals(x))
                {
                    w=z.from;
                    entered = true;                 
                    sum=sum+z.valeur;
                }
            }
            if(entered)
                i++;
            if (sum<w.cmtcValue)
                return false;   
        }
        if(i != y.size())
            return false;
        return true;
    }
    public boolean nodeExist(String x)
    {
        for(Noeud y:noeuds)
            if(y.getName().equals(x))
                return true;
        return false;
    }
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
      ColorS.getItems().addAll("Blanc","Bleu","Vert","Rouge");
      ColorS.getSelectionModel().select("Blanc");
      circle.setFill(Color.WHITE);
              }    
    
}
