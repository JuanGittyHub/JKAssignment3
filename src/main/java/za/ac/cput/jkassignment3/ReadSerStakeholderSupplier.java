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
import java.util.ArrayList;

/**
 *
 * @author Juan-Lee Klink(218236883)
 */
public class ReadSerStakeholderSupplier {
   private ObjectInputStream input;
   private FileWriter fw;
   private BufferedWriter bw;
   private ArrayList<Stakeholder> sList = new ArrayList<>();
   private ArrayList<Supplier> supplierList = new ArrayList<>();
   
   public void openFile(){
       try{
           input = new ObjectInputStream( new FileInputStream("stakeholder.ser"));
           System.out.println("Stakeholers ser file opened for reading");
       }
       catch(IOException ioe){
           System.out.println("Error opening Stakeholders ser file" + ioe.getMessage());
           
       }
   }
   
   public void closeFile(){
       try{
           input.close();
       }
       catch(IOException ioe){
           System.out.println("Error closing Stakeholder ser file" + ioe.getMessage());
       }
   }
   
   public void readFromFile(){
       try{
           for(int i = 0; i < 11; i++){
               sList.add((Stakeholder)input.readObject());
           }
           
           for(int i = 0; i < sList.size(); i++){
               if(sList.get(i) instanceof Supplier){
                   supplierList.add((Supplier) sList.get(i));
               }
           }
       }
       catch(EOFException eofe){
           System.out.println("End of file has been reached");
       }
       catch(ClassNotFoundException ioe){
           System.out.println("Class error reading Stakeholder ser file");
       }
       catch(IOException ioe){
           System.out.println("Error reading ser file");
       }
   }
   
   public void writeToFile(){
       try{
           fw = new FileWriter("supplierOutFile.txt");
           bw = new BufferedWriter(fw);
           
           bw.write("====================== Supplier =============================\n");
           bw.write("ID      Name                    Product Type    Description\n");
           bw.write("=============================================================\n");
           
           for(int i = 0; i < supplierList.size(); i++){
               String id = supplierList.get(i).getStHolderId();
               String name = supplierList.get(i).getName();
               String pType = supplierList.get(i).getProductType();
               String pDescription = supplierList.get(i).getProductDescription();
               
               String sDetails = String.format("%-5s\t%-20s\t%-10s\t%-15s\n",id, name, pType, pDescription);
               bw.write(sDetails);
           }
           
           bw.close();
           System.out.println("Write to Supplier file was a success");
            
       }
       catch(IOException ioe){
           System.out.println("Error writing to file");
       }
       finally{
           closeFile();
           System.out.println("File has been closed");
       }
   }
   
    public static void main(String[] args) {
        ReadSerStakeholderSupplier sFile = new ReadSerStakeholderSupplier();
        sFile.openFile();
        sFile.readFromFile();
        sFile.writeToFile();
    }
}
