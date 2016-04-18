/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cse535homework2;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Arrays;

/**
 *
 * @author Marlene Shankar
 */
public class Cse535Homework2{

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // Open file
        ArrayList <String> dataSet = new ArrayList <String> ();
        ArrayList <Attribute> attr = new ArrayList<Attribute> ();
        // User must create new Attribute Objects and add list of values
        // If an Attribute is continuous then call setContinuous
        
       try{
           BufferedReader reader = new BufferedReader(new FileReader("./data/cse353-hw2-data.tsv"));
           String row = reader.readLine();
           while (row != null){
               dataSet.add(row);
               //String [] data = row.split("\t");
               //read the next line
               row = reader.readLine();             
           }
           reader.close();
       } catch (Exception ex) {
           ex.printStackTrace();
       }
       // dataSet Contains all the data as Strings
       // indicies where you split the Strings was indicate attribute
       //Call ID3 and apply it
       ID3 (dataSet, attr);
    }  
    
        /**
         * Entropy with respect to the last column
         * @param dataSet
         * @return 
         */
        public static double entropy(ArrayList<String> dataSet){
        // dataset is a column of data based on target index
        // isolates the last column target into subsets
        double result = 0;
        if(dataSet.isEmpty()) return 0;
        
        ArrayList <String> lastColumn = new ArrayList <String> ();
        for(String g : dataSet){
            String [] splitG = g.split("\t");
            String last = splitG[splitG.length-1];
            lastColumn.add(last);
        }
        Set<String> lastvalues = new HashSet<String> (lastColumn);
        // do calculations with respect to last column
            // remove duplicates from the last column
        
        //go through each value and calculate entropy
        for(String v: lastvalues){
            // get the count for the total number of a value
            int valueTotal = count(v, lastColumn);
            // save the indicies of same values
            //ArrayList<Integer> indicies = subset(v, dataSet);
            double p = (double)valueTotal/dataSet.size();

            result -= p * Math.log(p)/Math.log(2);
            
        
        
    }
        //System.out.println(result);
        return result;
        }
    
    /**
     * Counts the number of a value in a column attribute
     * @param v is the value
     * @param column is the attribute column
     * @return count the number of values in the column
     */
    public static int count(String v, ArrayList<String> column){
        int count = 0; 
        //ArrayList <String> attrColumn = new ArrayList <String> ();
        for(String x: column){
            if(x.equals(v)){
                count++;
            } 
        }
        //System.out.println(count);
        return count;
    }
    
    /**
     * Constructs a subset for an attribute column given the specified attribute and value
     * @param dataSet set of all the data
     * @param attr list of attributes
     * @param a designated attribute
     * @param v designated value to sort by
     * @return the subset as an ArrayList
     */
    public static ArrayList<String> subset(ArrayList<String> dataSet, ArrayList <Attribute> attr, Attribute a, String v){
        // Subset is based on the subset of the dataSet of a designated attribute
        // the subset is broken down based on common values
        ArrayList <String> subset = new ArrayList <String>();
        
        for(String i : dataSet){
            String [] splitI = i.split("\t");
            if(splitI[attr.indexOf(a)].equals(v)){
                // figure out a way to split the 
                // add the row of data
                subset.add(i);
            }
        }
        
        return subset;
    }
    
    /**
     * Calculates the information gain for a specified attribute
     * @param dataSet set of all the data
     * @param attr list of attributes
     * @param a attribute designated
     * @param baseEntropy initial entropy of the last column
     * @return gain the information gain
     */
    public static double InformationGain(ArrayList<String> dataSet, ArrayList <Attribute> attr, Attribute a, double baseEntropy){
        double gain = baseEntropy;
        // isolate attibutes into an arraylist
        ArrayList <String> attrcolumn = new ArrayList <String> ();
        for(String x: dataSet){
            // split the string and get the index for the attribute value
            String [] splitX = x.split("\t");
            attrcolumn.add(splitX[attr.indexOf(a)]);
        }
        // dump values into set to remove repeats
        Set <String> values = new HashSet<String> (attrcolumn);
        for(String v: values){
            // save the indicies of same values
            // create a subset for the value given
            ArrayList<String> sub = subset(dataSet, attr, a, v);
            gain -= Math.abs(sub.size())/Math.abs(dataSet.size()) * entropy(sub);
            // needs to handle attributes with continuous values or difficult values
        }
        //System.out.println(gain);
        return gain;
    }
    
    /**
     * Calculates the split gain given an attribute a
     * @param dataSet the overall dataset
     * @param attr the list of attributes
     * @param a given attribute
     * @return 
     */
    public static double SplitGain(ArrayList <String> dataSet, ArrayList <Attribute> attr, Attribute a){
        double split = 0;
        
        ArrayList <String> attrcolumn = new ArrayList <String> ();
        for(String x: dataSet){
            // split the string and get the index for the attribute value
            String [] splitX = x.split("\t");
            attrcolumn.add(splitX[attr.indexOf(a)]);
        }
        // dump values into set to remove repeats
        Set <String> values = new HashSet<String> (attrcolumn);
        
        for(String v: values){
            int valueTotal = count(v, attrcolumn);
            double p = (double)Math.abs(valueTotal)/Math.abs(dataSet.size());
            split -=  p * Math.log(p)/Math.log(2);
        }
        //System.out.println(split);
        return split;
    }
    
    /**
     * Calculates the gain ratio given an attribute a
     * @param dataSet the overall data set
     * @param attr list of attributes
     * @param a attribute
     * @param baseEntropy initial entropy of last column 
     * @return 
     */
    public static double GainRatio(ArrayList <String> dataSet, ArrayList <Attribute> attr, Attribute a, double baseEntropy){
        return InformationGain(dataSet, attr, a, baseEntropy)/SplitGain(dataSet, attr, a);
    }
    
    public static void ID3(ArrayList <String> dataSet, ArrayList <Attribute> attr){
        // dataSet = training data
        // attributes = list of attributes
        if(attr.size() == 0){
            // return nothing
            return;
        }
        if(attr.size() == 1){
            // label node with the most common value in the target attribute
            // determine what is the most common value in the target attribute
            // return node
            return;
        }
        // at this point there are more than one attributes to work off of
        
        double baseEntropy = entropy(dataSet);
        ArrayList <Double> ratios = new ArrayList <Double> ();
        for(Attribute a : attr){
            if(a.isContinuous()){
                handleContinuous(dataSet, attr.indexOf(a));
            }
            else{
                if(!a.isUsed()){
                    double gain = GainRatio(dataSet, attr, a, baseEntropy);
                    System.out.println(gain);
                    a.setGainRatio(gain);
                    ratios.add(gain);
                }
                else{
                    // do nothing and reloop to try the next attribute
                }
            }   
        }
        // calculate max gain ratio
        double x = ratios.get(0);
        for(int i = 0; i < ratios.size(); i ++){
            if(x >= ratios.get(i)){
              
            }
            else{
                // x > valueList.get(i)
                x = ratios.get(i);
            }
        }
        // get the index of the best attribute
        int attributeIndex = ratios.indexOf(x);
        // make the attribute as used
        attr.get(attributeIndex).setUsed(true);
        
        // create a subset for each value
        // return the node
        
    }
    
    /**
     * Handles attributes with continuous values
     * @param dataSet
     * @param attr 
     */
    public static void handleContinuous(ArrayList <String> dataSet, int index){ 
        ArrayList <Double> valueList = new ArrayList <Double> ();
        double avg = 0;
        for(String s: dataSet){
            // separate data columns by tab
            String [] splitString = s.split("\t");
            // pass the value to double for calculations
            double value = Double.parseDouble(splitString[index]);
            valueList.add(value);
            avg += value;
        }
        
        avg = avg / valueList.size();
        // get the minimum and maximum values
        // get the range of values
        
        // find mix & max
        double x = valueList.get(0);
        double y = valueList.get(0);
        for(int i = 0; i < valueList.size(); i ++){
            if(x <= valueList.get(i)){
                y = valueList.get(i);
            }
            else{
                // x > valueList.get(i)
                x = valueList.get(i);
            }
        }
        double range = y - x;
        double interval = range / (avg - 1);
        // this will serve as the inverval for creating subsets of the attribute
    }
}
