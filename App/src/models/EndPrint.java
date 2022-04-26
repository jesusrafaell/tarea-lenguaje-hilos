package models;

import java.util.Scanner;

public class EndPrint extends Thread {
  private boolean printFin = false;


    Scanner in = new Scanner(System.in);
    
    public void run() {
        String line = in.nextLine();
        while(!line.equals("Fin") && !line.equals("FIN")){
            line = in.nextLine();
        }
        printFin = true;
        //System.out.println("Fiiiiiin ==========================================================================");
    }
    
    public void stopped () {
        in.close();
    }
    
    public boolean getPrintFin () {
        return printFin;
    }
    
}
