package evaluation;

import java.util.ArrayList;

 
public class Noeud {
    
   private double x,y;
    private String name ;
    public ArrayList<Noeud> From = new ArrayList<Noeud>();
    public ArrayList<Noeud> To = new ArrayList<Noeud>();
    public int num;
    public float cmtcValue;
    public void Noeud(){
        
    }
    public double getX(){
        return x;
    }
    
    public double getY(){
        return y;
    }
    
    public String getName(){
        return name;
    }
    
    public void setX(double a){
        x=a;
    }
    
    public void setY(double b){
        y=b;
    }
    
    public void setName(String c){
        name = c;
    }
}
