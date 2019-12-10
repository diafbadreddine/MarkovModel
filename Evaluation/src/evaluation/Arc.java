package evaluation;

import java.math.BigDecimal;


public class Arc {

    public Noeud from,to;
    public float valeur;
    public void Arc(){
        
    }   
    public boolean isOk(){
        if(valeur>1 || valeur<0)
            return false;
        else
            return true;
    }
}
