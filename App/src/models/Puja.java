package models;

public class Puja extends Thread {
  private Comprador comprador;
  private Subasta subasta;
  private int time=0;
  private boolean on = false;

  public Puja (String name, Comprador comp, Subasta sub, int timeInit){
    super(name);
    this.comprador = comp;
    this.subasta = sub;
    this.time = timeInit;

    System.out.println("Creado la puja: " + name + " por el timepo " + timeInit);
  }
  
    public void run() {
        try {
            on = true;
            Thread.sleep(time);
            System.out.println("Hilo Puja el Comprador: " + comprador.GetName() + " puede volver a pujar en " + subasta.getName());
            on = false;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }  
    
    public Subasta getSubasta () {
        return subasta;
    }
    
    public boolean endTime () {
        return on;
    }
    
}
