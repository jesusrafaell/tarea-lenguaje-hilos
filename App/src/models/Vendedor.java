package models;

public class Vendedor extends Thread {
 // state or field
  private String name;
  private boolean exit = false;

  public Vendedor (String name){
    super(name);
    this.name = name;

    System.out.println("Creado el Vendedor: " + name);
  }

  public void stopped() {
    exit = true;
  }

  public void run() {
    while(!exit){
      try {
        //printName();
        Thread.sleep(1000);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }   
    }
  }

  // behavior or method
  public void printName() {
    System.out.println("Hilo Vendedor-> " + name);
  }

}
