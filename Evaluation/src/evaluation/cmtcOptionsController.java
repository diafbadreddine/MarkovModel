package evaluation;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.Vector;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import org.ejml.simple.SimpleMatrix;

public class cmtcOptionsController implements Initializable {
    
    @FXML
    TextField periodValue,TPuiss,TPi0;
    @FXML
    Button statBtn,ergoBtn,calculBtn,irrBtn,irrBtn1,matBtn;
    @FXML
    Label errL,statL;
    @FXML
    ImageView ergoImage,irrImage,irrImage1;
    public ArrayList<Noeud> noeuds;
    public ArrayList<Arc> arcs ;
    ArrayList<Arc> newarcs = new ArrayList<Arc>();
    int size;
    float[][] mat ;
    String[] pi0S;

    
    
    static int gcd(int a,int b)
    {
        if(a==0)
            return b;
        return gcd(b%a,a);
    }
    
    static int findGCD(ArrayList<Integer> arr)
    {
        int result = arr.get(0);
        for(int i=1;i<arr.size();i++)
            result = gcd(arr.get(i),result);
        return result;
    }
    
    public static void gaussian(float a[][], int index[]) 
    {
        int n = index.length;
        float c[] = new float[n];
 
 // Initialize the index
        for (int i=0; i<n; ++i) 
            index[i] = i;
 
 // Find the rescaling factors, one from each row
        for (int i=0; i<n; ++i) 
        {
            float c1 = 0;
            for (int j=0; j<n; ++j) 
            {
                float c0 = Math.abs(a[i][j]);
                if (c0 > c1) c1 = c0;
            }
            c[i] = c1;
        }
 
 // Search the pivoting element from each column
        int k = 0;
        for (int j=0; j<n-1; ++j) 
        {
            float pi1 = 0;
            for (int i=j; i<n; ++i) 
            {
                float pi0 = Math.abs(a[index[i]][j]);
                pi0 /= c[index[i]];
                if (pi0 > pi1) 
                {
                    pi1 = pi0;
                    k = i;
                }
            }
 
   // Interchange rows according to the pivoting order
            int itmp = index[j];
            index[j] = index[k];
            index[k] = itmp;
            for (int i=j+1; i<n; ++i) 	
            {
                float pj = a[index[i]][j]/a[index[j]][j];
 
 // Record pivoting ratios below the diagonal
                a[index[i]][j] = pj;
 
 // Modify other elements accordingly
                for (int l=j+1; l<n; ++l)
                    a[index[i]][l] -= pj*a[index[j]][l];
            }
        }
    }
    public static float[][] invert(float a[][]) 
    {
        int n = a.length;
        float x[][] = new float[n][n];
        float b[][] = new float[n][n];
        int index[] = new int[n];
        for (int i=0; i<n; ++i) 
            b[i][i] = 1;
 
 // Transform the matrix into an upper triangle
        gaussian(a, index);
 
 // Update the matrix b[i][j] with the ratios stored
        for (int i=0; i<n-1; ++i)
            for (int j=i+1; j<n; ++j)
                for (int k=0; k<n; ++k)
                    b[index[j]][k]
                    	    -= a[index[j]][i]*b[index[i]][k];
 
 // Perform backward substitutions
        for (int i=0; i<n; ++i) 
        {
            x[n-1][i] = b[index[n-1]][i]/a[index[n-1]][n-1];
            for (int j=n-2; j>=0; --j) 
            {
                x[j][i] = b[index[j]][i];
                for (int k=j+1; k<n; ++k) 
                {
                    x[j][i] -= a[index[j]][k]*x[k][i];
                }
                x[j][i] /= a[index[j]][j];
            }
        }
        return x;
    }
    public static boolean f(Noeud A, int x)
    {
        ArrayList<ArrayList<Noeud>> start = new ArrayList<ArrayList<Noeud>>();
        ArrayList<Noeud> chaine = new ArrayList<>();
        
        
                 if(x!=1)
                    {   
                    for(int l=0;l<A.To.size();l++)
                        if(A.To.get(l)!=A )
                        {
                            chaine.add(A.To.get(l));
                        }

                    }
                    else
                    {
                        for(int l=0;l<A.To.size();l++)
                        {
                         
                               chaine.add(A.To.get(l));
                        }
                    }
        
        
        start.add(chaine);
        boolean res = false;
        Noeud unknown = new Noeud();
        int j=0;
        while(x!=0)
        {
            x--;
            for( int i=0;i<start.size();i++)
            {
                
                for(int k=0;k<start.get(i).size();k++)
                {
                    if(x==0 && start.get(i).get(k) == A)
                        res = true;
                }
            }
            int size = start.size();
            ArrayList<ArrayList<Noeud>> newStart = new ArrayList<ArrayList<Noeud>>();
            for( int i=0;i<start.size();i++)
            {
                ArrayList<Noeud> toAdd = new ArrayList<>();
                for(int k=0;k<start.get(i).size();k++)
                {
                    toAdd.add(start.get(i).get(k));
                }
                newStart.add(toAdd);
            }
            
            start.clear();
            
            for( int i=0;i<size;i++)
            {
                for(int k=0;k<newStart.get(i).size();k++)
                {
                    ArrayList<Noeud> toAdd = new ArrayList<>();
                    if(x!=1)
                    {   
                    for(int l=0;l<newStart.get(i).get(k).To.size();l++)
                        if(newStart.get(i).get(k).To.get(l)!=A )
                        {
                            toAdd.add(newStart.get(i).get(k).To.get(l));
                        }

                    }
                    else
                    {
                        for(int l=0;l<newStart.get(i).get(k).To.size();l++)
                        {
                            if(newStart.get(i).get(k)!=A)
                               toAdd.add(newStart.get(i).get(k).To.get(l));
                        }
                    }
                    start.add(toAdd);
                }
            }
            
            
            j= size;
           
        }
        return res;
    }
    
    public static int nodePeriod(Noeud x)
    {
        ArrayList<Integer> y = new ArrayList<Integer>();
        for(int i=1;i<15;i++)
        {
            if(f(x,i))
                y.add(i);
        }
        
        return findGCD(y);
        
    }
    
    public static int graphPeriod(ArrayList<Noeud> x)
    {
        ArrayList<Integer> y = new ArrayList<Integer>();
        for(Noeud w:x)
        {
            y.add(nodePeriod(w));
        }

        return findGCD(y);
    }
    
    public static ArrayList<Integer> graphPeriods(ArrayList<Noeud> x)
    {
        ArrayList<Integer> y = new ArrayList<Integer>();
        for(Noeud w:x)
        {
            y.add(nodePeriod(w));
        }

        return y;
    }
    
    private boolean irr(int src){

        int V = noeuds.size(), E = arcs.size(); 
        float dist[] = new float[V]; 
  
        // Step 1: Initialize distances from src to all other 
        // vertices as INFINITE 
        for (int i = 0; i < V; ++i) 
            dist[i] = Integer.MAX_VALUE; 
        dist[src] = 0; 
  
        // Step 2: Relax all edges |V| - 1 times. A simple 
        // shortest path from src to any other vertex can 
        // have at-most |V| - 1 edges 
        for (int i = 1; i < V; ++i) { 
            for (int j = 0; j < E; ++j) { 
                int u = arcs.get(j).from.num; 
                int v = arcs.get(j).to.num; 
                float weight = arcs.get(j).valeur; 
                if (dist[u] != Integer.MAX_VALUE && dist[u] + weight < dist[v]) 
                    dist[v] = dist[u] + weight; 
            } 
        } 
  
        for (int i = 0; i < V; ++i) 
        {
           if(dist[i] == Integer.MAX_VALUE)
               return false;
        }
        return true;

    }
    @FXML
    private void irrClicked(){
        errL.setText("");
        boolean val = true;
        for(int i=0;i<noeuds.size();i++)
            if(!irr(i))
                val = false;
        if(val)
        {
            Image img = new Image(getClass().getResource("true.png").toExternalForm());
            irrImage.setImage(img);
        }
        else
        {
            Image img = new Image(getClass().getResource("false.png").toExternalForm());
            irrImage.setImage(img);
        }
    }
    @FXML
    private void mousemoved() throws IOException{
        matBtn.setStyle("-fx-background-color: rgba(0,255,127,0.5); -fx-background-radius:100em;");
    }
    
     @FXML
    private void mouseexcited() throws IOException{
      
        matBtn.setStyle("-fx-background-color:  rgba(65,105,225,0.4); -fx-background-radius:100em;");
    }
    
    @FXML
    private void mousemoved1() throws IOException{
        calculBtn.setStyle("-fx-background-color: rgba(0,255,127,0.5); -fx-background-radius:100em;");
    }
    
     @FXML
    private void mouseexcited1() throws IOException{
      
        calculBtn.setStyle("-fx-background-color:  rgba(65,105,225,0.4); -fx-background-radius:100em;");
    }
    
    @FXML
    private void mousemoved2() throws IOException{
        irrBtn.setStyle("-fx-background-color: rgba(0,255,127,0.5); -fx-background-radius:100em;");
    }
    
     @FXML
    private void mouseexcited2() throws IOException{
      
        irrBtn.setStyle("-fx-background-color:  rgba(65,105,225,0.4); -fx-background-radius:100em;");
    }
    
    @FXML
    private void mousemoved3() throws IOException{
        irrBtn1.setStyle("-fx-background-color: rgba(0,255,127,0.5); -fx-background-radius:100em;");
    }
    
     @FXML
    private void mouseexcited3() throws IOException{
      
        irrBtn1.setStyle("-fx-background-color:  rgba(65,105,225,0.4); -fx-background-radius:100em;");
    }
    
    @FXML
    private void mousemoved4() throws IOException{
        ergoBtn.setStyle("-fx-background-color: rgba(0,255,127,0.5); -fx-background-radius:100em;");
    }
    
     @FXML
    private void mouseexcited4() throws IOException{
      
        ergoBtn.setStyle("-fx-background-color:  rgba(65,105,225,0.4); -fx-background-radius:100em;");
    }
        
    
    @FXML
    private void mousemoved5() throws IOException{
        statBtn.setStyle("-fx-background-color: rgba(0,255,127,0.5); -fx-background-radius:100em;");
    }
    
     @FXML
    private void mouseexcited5() throws IOException{
      
        statBtn.setStyle("-fx-background-color:  rgba(65,105,225,0.4); -fx-background-radius:100em;");
    }
    
    @FXML
    private void periodiqueClicked() throws IOException{
        
        if(graphPeriod(noeuds)==1)
        {
            Image img = new Image(getClass().getResource("false.png").toExternalForm());
            irrImage1.setImage(img);
            periodValue.setText("1");
        }
        else
        {
            Image img = new Image(getClass().getResource("true.png").toExternalForm());
            irrImage1.setImage(img);
            periodValue.setText(String.valueOf(graphPeriod(noeuds)));
            String txt ="           Les périodes de chaque état         \n";
            ArrayList<Integer> y = new ArrayList<Integer>();
            y=graphPeriods(noeuds);
            int i=0;
            for(Noeud w:noeuds)
            {
                txt = txt+w.getName()+"     "+String.valueOf(y.get(i))+"\n";
                i++;
            }
            FXMLLoader loader = new FXMLLoader();
               loader.setLocation(getClass().getResource("Screen.fxml"));
        
               loader.load();
        
               ScreenController display = loader.getController();
               display.infoL.setText(txt);
               
               Parent s = loader.getRoot();
        
               Stage stage = new Stage ();
        
               stage.setScene(new Scene(s));
        
               stage.showAndWait();
        }
    }
    
    @FXML
    public void clicked() throws IOException{
        errL.setText("");
        ArrayList<String> y = new ArrayList<String>();
        for(Noeud x : noeuds)
        {
            boolean found = false;
            for(String z:y)
            {
                if(x.getName().equals(z))
                    found = true;
            }
            if (!found)
                y.add(x.getName());
        }
        
        int nombre = y.size();
        for(String z:y)
        {
            int i=0;
            while(i<nombre)
            {
                boolean exist = false;
                for(Arc x:arcs)
                {
                    if(x.from.getName().equals(z) && x.to.getName().equals(y.get(i)))
                    {
                        exist = true;
                        newarcs.add(x);
                    }
                }
                if(!exist)
                {
                    Arc w = new Arc();
                    Noeud f= new Noeud ();
                    Noeud d = new Noeud();
                    f.setName(z);
                    w.from=f;
                    d.setName(y.get(i));
                    w.to=d;
                    w.valeur=0;
                    newarcs.add(w);
                }
                i++;
            }
        }
        mat = new float[y.size()][y.size()];
        
        int i=0,j=0;
        for(String z:y)
        {     
            j=0;
            for(Arc x:newarcs)
            {
                if(x.from.getName().equals(z))
                {
                       mat[i][j]=x.valeur;
                       j++;
                }
            }
            i++;
        }
        String text ="La matrice de transition \n\n\n";
        
        for(int k=0;k<nombre;k++)
        {
            text = text + y.get(k)+"      ";
            for(int l=0;l<nombre;l++)
            {
                text=text+String.valueOf(mat[k][l])+"   ";
            }
            text+=" \n\n";
        }
        FXMLLoader loader = new FXMLLoader();
               loader.setLocation(getClass().getResource("Screen.fxml"));
        
               loader.load();
        
               ScreenController display = loader.getController();
               display.infoL.setText(text);
               
               Parent s = loader.getRoot();
        
               Stage stage = new Stage ();
        
               stage.setScene(new Scene(s));
        
               stage.showAndWait();
               


    }
    
    @FXML
    public void ergoClicked(){
        boolean val = true;
        for(int i=0;i<noeuds.size();i++)
            if(!irr(i))
                val = false;
        if(val)
        {
            Image img = new Image(getClass().getResource("true.png").toExternalForm());
            ergoImage.setImage(img);
        }
        else
        {
            Image img = new Image(getClass().getResource("false.png").toExternalForm());
            ergoImage.setImage(img);
        }
    }
    
    @FXML
    public void calculPi() throws IOException{
        errL.setText("");
        boolean tpi0 = false,tpuiss=false;
        if(TPi0.getText().isEmpty() && TPuiss.getText().isEmpty())
        {
            TPi0.setStyle("-fx-background-color:rgba(255,160,122,0.5);");
            TPuiss.setStyle("-fx-background-color:rgba(255,160,122,0.5);");
        }
        else
        {
            if(TPi0.getText().isEmpty() )
            {
                TPi0.setStyle("-fx-background-color:rgba(255,160,122,0.5);");
                TPuiss.setStyle("-fx-background-color:rgba(255,255,255,1);");
            }
            else
            {
                if(TPuiss.getText().isEmpty() )
                {
                TPuiss.setStyle("-fx-background-color:rgba(255,160,122,0.5);");
                TPi0.setStyle("-fx-background-color:rgba(255,255,255,1);");
                }
                else
                {
                    TPuiss.setStyle("-fx-background-color:rgba(255,255,255,1);");
                    TPi0.setStyle("-fx-background-color:rgba(255,255,255,1);");
                    
                    pi0S = TPi0.getText().split(" ");
                    if(pi0S.length!=size)
                    {
                        errL.setText("La taille de Pi0 n'est pas convenable ");
                    }
                    else
                    {
                        float[] pi0 = new float[size];
                        for(int i=0;i<size;i++)
                        {
                            pi0[i] = Float.parseFloat(pi0S[i]);
                        }
                        if(!verifyPi(pi0))
                            errL.setText("Pi0 n'est pas correcte !!");
                        else
                        {
                            int puissance = Integer.parseInt(TPuiss.getText());
                            double[][]result = new double[size][size];
                            for(int k=0;k<size;k++)
                                for(int l=0;l<size;l++)
                                    result[k][l]=mat[k][l];
                            int i=0;
                            
                            SimpleMatrix S = new SimpleMatrix(result);
                            SimpleMatrix res = new SimpleMatrix(result);
                            while(i<puissance-1)
                            {
                                res = S.mult(res);
                                i++;
                            }
                          
                            double [] fin = new double[size];
                            for(int k=0;k<size;k++)
                            {
                                fin[k]=pi0[k];
                            }
                            if(puissance>0)
                            {
                            for(int k=0;k<size;k++)
                            {
                                double sum = 0 ;
                                for(int l=0;l<size;l++)
                                {
                                    sum = sum + pi0[l]*res.get(l,k);
                                }
                                fin[k] = sum;
                            }
                            }
                            String text ="Pi ^ "+String.valueOf(puissance)+" = "+"[   ";
                            for(int j=0;j<result.length;j++)
                            {
                                text=text+String.valueOf(fin[j])+"  ";
                            }
                            text=text+" ]";
                            FXMLLoader loader = new FXMLLoader();
               loader.setLocation(getClass().getResource("Screen.fxml"));
        
               loader.load();
        
               ScreenController display = loader.getController();
               display.infoL.setText(text);
               
               Parent s = loader.getRoot();
        
               Stage stage = new Stage ();
        
               stage.setScene(new Scene(s));
        
               stage.showAndWait();
                        }
                                
                    }
                }
            }
        }
    }
    
    @FXML
    public void statClicked() throws IOException{
        
        
        statL.setText("");
        
        boolean val = true;
        for(int i=0;i<noeuds.size();i++)
            if(!irr(i))
                val = false;
        if(graphPeriod(noeuds)==1 && val)
        {
        float[][] newMat = mat;
        float transpose[][]=mat;
        float [] constants = new float[noeuds.size()];
        for(int i=0;i<noeuds.size()-1;i++)
        {
            constants[i]=0;
        }
        constants[noeuds.size()-1]=1;
        
        
        ///////////////// transpose /////////////////// 
        
        for(int i=0;i<noeuds.size();i++){    
            for(int j=0;j<noeuds.size();j++){    
                transpose[i][j]=mat[j][i];  
                }    
                } 
        
        for(int i=0;i<noeuds.size();i++){    
            for(int j=0;j<noeuds.size();j++){    
                newMat[i][j]=transpose[i][j];  
                }    
                } 
        
        //////////////////////////////////////////////
        
        for(int i=0;i<noeuds.size();i++)
        {
            for(int j=0;j<noeuds.size();j++)
            {
                if(i==j)
                    newMat[i][j]=newMat[i][j]-1;
            }
        }
        
        for(int i=noeuds.size()-1;i<noeuds.size();i++)
        {
            for(int j=0;j<noeuds.size();j++)
            {
                newMat[i][j]=1;
            }
        }
        
        ///////////////////////////////////////
        
        float inverted_mat[][] = invert(mat);
        
        //Multiplication of mat inverse and constants
        float result[][] = new float[noeuds.size()][1];
        for (int i = 0; i < noeuds.size(); i++) 
        {
            for (int j = 0; j < 1; j++) 
            {
                for (int k = 0; k < noeuds.size(); k++)
                {	 
                    result[i][j] = result[i][j] + inverted_mat[i][k] * constants[k];
                }
            }
        }
        
        String txt = "Pi = [    ";
        for(int i=0; i<noeuds.size(); i++)
        {
            txt=txt+String.valueOf(result[i][0]) + "    ";
        }
        txt=txt+"]";
        FXMLLoader loader = new FXMLLoader();
               loader.setLocation(getClass().getResource("Screen.fxml"));
        
               loader.load();
        
               ScreenController display = loader.getController();
               display.infoL.setText(txt);
               
               Parent s = loader.getRoot();
        
               Stage stage = new Stage ();
        
               stage.setScene(new Scene(s));
        
               stage.showAndWait();
        }
        else
        {
            statL.setText("Pas de régime stationnaire");
        }
    }
    public boolean verifyPi(float[] x){
        int nbre0=0,nbre1=0;
        for(int i=0;i<x.length;i++)
        {
            if(x[i]==1)
                nbre1++;
            if(x[i]==0)
                nbre0++;
        }
        if(nbre1 == 1 && nbre0==x.length-1)
            return true;
        else
            return false;
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {


    }    
    
}
