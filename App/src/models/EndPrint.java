package models;

import java.io.*;
import java.util.concurrent.*;
import java.util.Scanner;

public class EndPrint extends Thread {
  private boolean printFin = false;
  private boolean exit = false;

    Scanner in;
    
    public EndPrint (Scanner inMain){
        this.in = inMain;
    }
    
 
    
    public void run() {
        String line = " ";
        try{
            do {
                while (!in.hasNextLine()){
                    Thread.sleep(0);
                    if(exit){
                     return;
                    }
                }
                line = in.nextLine();
            }while(!line.equals("FIN") && !line.equals("Fin"));
         } catch (InterruptedException e) {
            e.printStackTrace();
        }   
      
        printFin = true;
        in.close();
        //System.out.println("Fiiiiiin ==========================================================================");
    }
    
    public void stopped () {
        exit=true;
        //in.close();
    }
    
    public boolean getPrintFin () {
        return printFin;
    }
    
}
