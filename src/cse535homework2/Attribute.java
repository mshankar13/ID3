/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cse535homework2;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author Marlene Shankar
 */
public class Attribute {
    String name;
    ArrayList<String> values = new ArrayList<String>();
    Set<String> uniqueVal = new HashSet<String>();
    String cont = "continuous";
    boolean used = false;
    double gain;
    
    public Attribute(String name){
        this.name = name;
    }
    
    public Attribute(String name, ArrayList<String> values){
        this.name = name;
        this.values.clear();
        this.values.addAll(values);
    }
    
    public void setName(String name){
        this.name = name;
    }
    
    public void setvalues(ArrayList<String> values){
        this.values.clear();
        this.values.addAll(values);
        setuniqueVal(values);
    }
    
    public void addvalue(String value){ 
        this.values.add(value);
        setuniqueVal(values);
    }
    
    public void setuniqueVal(ArrayList<String> values){
        this.uniqueVal.addAll(values);
    }
    
    public ArrayList <String> getuniqueVal(){
        ArrayList <String> uniqueVal = new ArrayList <String> ();
        uniqueVal.addAll(uniqueVal);
        return uniqueVal;
    }
    
    public String getName(){
        return name;
    }
    
    public ArrayList<String> getvalues(){
        return values;
    }
    
    public Set<String> setuniqueVal(){
        return uniqueVal;
    }
    // marker if the attribute is continuous
    public boolean isContinuous(){
         if(this.values.get(0).equalsIgnoreCase(this.cont)){
             return true;
         }
         else{
             return false;
         }
    }
    
    public ArrayList<String> setContinuous(){
        this.values.add("continuous");
        return this.values;
    }
    
    public void setUsed(boolean used){
        this.used = used;
    }
    
    //Keeps track of if the attribute was used
    public boolean isUsed(){
        return this.used;
    }
    
    public void setGainRatio(double gain){
        this.gain = gain;
    }
    
    public double getGainRatio(){
        return this.gain;
    }
}
