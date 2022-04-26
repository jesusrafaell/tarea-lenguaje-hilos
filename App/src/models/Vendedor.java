package models;

import java.util.*;

public class Vendedor extends Thread {
 // state or field
  private String name;
  private int monto=0;
  private boolean exit = false;

  public Vendedor (String name){
    super(name);
    this.name = name;

    //System.out.println("Creado el Vendedor: " + name);
  }

  public void stopped() {
    exit = true;
  }
  
   Listas db;

  public void run() {   
    List<Subasta> subastas= db.getListSubasta();
    boolean no = true;
    while(!exit){
        try {
            //printName();
            for (int i = 0; i < subastas.size(); i++) {
              if(subastas.get(i).getCloseSubasta() && 
                subastas.get(i).getComprador() != null &&
                subastas.get(i).getVendedor() == this &&
                subastas.get(i).canPrintV()
            ){
                  subastas.get(i).setPint();
                  System.out.println(
                    "El producto "+ subastas.get(i).GetProduct() +
                    " fue vendido a " + subastas.get(i).getCName() +
                    " por " + subastas.get(i).getMontoActual()
                  );
                  //subastas.remove(i);
                  monto += (subastas.get(i).getMontoActual() / 10);
                  break;
              }
            }
            Thread.sleep(0);
            if (no) {
              Thread.sleep(1000);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }   
    }
  }

  // behavior or method
  public void printName() {
    System.out.println("Hilo Vendedor-> " + name);
  }
  
  public void setListSubastas(Listas newDB) {
    this.db = newDB;
  }

   public int getMonto (){
       return monto;
   }

}
