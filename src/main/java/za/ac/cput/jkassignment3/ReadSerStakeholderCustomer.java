/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package za.ac.cput.jkassignment3;

import java.io.BufferedWriter;
import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


/**
 *
 * @author Juan-Lee Klink (218236883)
 */
public class ReadSerStakeholderCustomer {
    private ObjectInputStream input;
    private FileWriter fw;
    private BufferedWriter bw;
    private ArrayList<Stakeholder> sList = new ArrayList<>();
    private ArrayList<Customer> cList = new ArrayList<>();
    boolean rent;
    int totalCustomersCanRent = 0;
    int totalCustomersNotRent = 0;
    String[] newDate = new String[6];
    
    
    public void openFile(){
        try{
            input = new ObjectInputStream(new FileInputStream("stakeholder.ser"));
            System.out.println(" *Stakeholder serialized file opened for reading* ");
        }
        catch (IOException ioe){
            System.out.println("error opening Stakeholder ser file: " + ioe.getMessage());
        }
    }
    
    public void closeFile(){
        try{
            input.close();
        }
        catch (IOException ioe){
            System.out.println("There has been an error closing the Stakeholer ser file:" + ioe.getMessage());
        }
    }
    
    
    
    public void readFromFile(){
        
        try{
            for(int i = 0; i < 11; i++) {
                
                sList.add((Stakeholder)input.readObject());
            }
            
            for(int i = 0; i < sList.size(); i++){
            
            if(sList.get(i) instanceof Customer){
                cList.add((Customer) sList.get(i));
            }
            
        }
        }
        
        catch(EOFException eofe){
            System.out.println("End of File has been reached");
        }
        catch(ClassNotFoundException ioe){
            System.out.println("Class Error reading Stakeholder ser file" +ioe);
        }
        catch(IOException ioe){
            System.out.println("Error reading Stakeholder ser file" +ioe);
        }
        
        
       
    }
    
    public void writeToFile(){
        try{
            fw = new FileWriter("customerOutFile.txt");
            bw = new BufferedWriter(fw);
            
            bw.write("====================== Customer ==============================\n");
            bw.write("ID              Name            Surname         Date Of Birth\n");
            bw.write("==============================================================\n");
            
            changeDate();
            calculateRent();
            
            for( int i = 0; i < cList.size(); i++){
                String id = cList.get(i).getStHolderId();
                String name = cList.get(i).getFirstName();
                String surname = cList.get(i).getSurName();
                String fDate = newDate[i];
                
                String cDetails = String.format("%-10s\t%-10s\t%-10s\t%-15s\n",id, name, surname, fDate);
                bw.write(cDetails);
            }
            bw.write("\n");
            bw.write("Total number of customers who can rent: " + totalCustomersCanRent + "\n");
            bw.write("Total number of customers who cannot rent: " + totalCustomersNotRent);
            
            bw.close();
            System.out.println("Write to Customer file was a success");
            
            
        }
        catch(IOException ioe){
            System.out.println("error writing to file");
        }
        finally{
            closeFile();
            System.out.println("File has been closed");
        }
    }
    
    public void changeDate(){
        DateFormat outputFormat = new SimpleDateFormat("dd MMM YYY");
        DateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd");
        
        for (int i = 0; i < cList.size(); i++){
            try{
                String inputText = cList.get(i).getDateOfBirth();
                
                Date d = inputFormat.parse(inputText);
                String formattedDate = outputFormat.format(d);
                
                newDate[i] = formattedDate;
            }
            catch(ParseException e){
                System.out.println("parse exception caught" + e.getMessage());
            }
        }
    }
    
    public void calculateRent(){
        for (int i = 0; i < cList.size(); i++){
            rent = cList.get(i).getCanRent();
            
            if(rent == true){
                totalCustomersCanRent +=1;
            }
            else{
                totalCustomersNotRent +=1;
            }
        }
    }
    
    public static void main(String[] args) {
        ReadSerStakeholderCustomer cFile = new ReadSerStakeholderCustomer();
        cFile.openFile();
        cFile.readFromFile();
        cFile.writeToFile();
    }
    
    
    
   
}
