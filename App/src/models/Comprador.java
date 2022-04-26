package models;

import utilis.Functions;

import java.util.*;
import static utilis.Functions.rand;

public class Comprador extends Thread {
 // state or field
  private String name;
  private int monto; 
  private int percentMax;
  private boolean exit = false;

  public Comprador (String name, int monto, int percent){
    super(name);
    this.name = name;
    this.monto = monto;
    this.percentMax = percent;

    //System.out.println("Creado el Comprador: " + name + " " +  monto  +" " + percent);
  }

  public void stopped() {
    exit = true;
  }

  //private Listas db = new Listas();
  Listas db;

  public void run() {
    List<Subasta> subastas = db.getListSubasta();
    try{
      int time = Functions.rand(1, 4) * 1000;
      //System.out.println("Hilo Comprador: " + name + " esperara " + time);
      Thread.sleep(time);
      //System.out.println("Inicio Hilo Comprador: " + name);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }   

    boolean no = true;
    while(!exit){
      try {
        //printName();
        for (int i = 0; i < subastas.size(); i++) {
          int montoSubastar = montoOfert(subastas.get(i).getMontoActual());
           //System.out.println("Hilox Comprador: " + name + " tiene " + monto);
          if (
            montoSubastar <= this.monto &&              //Tiene el monto para subastar
            this != subastas.get(i).getComprador() &&   //No es el actual comprador de la subasta
            checkPuja(subastas.get(i))                  //Puede pujar ya que paso el tiempo de espera
          ) {
             //Crear una oferta
            subastas.get(i).newOfertante(this, montoSubastar);
          }
        }
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
    //System.out.println("Hilo Comprador-> " + name + '/' + monto + '/' + percentMax);
  }

  public void setListSubastas(Listas newDB) {
    this.db = newDB;
  }

  public int porcentaje(int precioA, int maxPorcent) {
    return (int) ((precioA * (Functions.rand(1, maxPorcent))/100 + 1));
  }

  public int montoOfert(int precio) {
    int puja = porcentaje(precio, this.percentMax);
    //System.out.println(precio + " --- " + puja + " Comprador: " + name);
    int montoOferta = precio + puja;
    return montoOferta;
  }
  
  public void SetMonto(int monto){
    this.monto = monto;
  }
  
  public int GetMonto(){
    return monto;
  }
        
  public String GetName(){
    return name;
  }

  public int GetPercent(){
    return percentMax;
  }
  
  List<Puja> listPuja = new ArrayList<Puja>();
  
  public void initPuja (Subasta subasta) {
      int ran = rand(2,8)*1000;
      String namePuja = subasta.getName() + " " + name;
      Puja newPuja = new Puja(namePuja, this, subasta, ran);
      listPuja.add(newPuja);
      newPuja.start();
  }
  
  public boolean checkPuja (Subasta sub) {
    for (int i = 0; i < listPuja.size(); i++) {
        //System.out.println("Puja Comprador: " + name + " " + i + " / " + listPuja.size());
        if(listPuja.get(i).getSubasta() == sub){
            if(listPuja.get(i).endTime()){
                //System.out.println("Puja Comprador: " + name + " no pude pujar en " + sub.getName() + " aun no es tiempo ");
                return false;
            }else{
                listPuja.remove(i);
                //System.out.println("Puja Comprador: " + name + " ya puede pujar en " + sub.getName());
                return true;
            }
        }
    } 
    //System.out.println("Puja Comprador: " + name + " puede pujar en " + sub.getName());
    return true;
  }
}
