package models;

public class TimeSubasta extends Thread {
  private boolean on = false;

  public TimeSubasta (String name){
    super(name);

    //System.out.println("Creado la puja: " + name + " por el tiempo " + timeInit/1000 + " seg");
  }
  
    public void run() {
        //System.out.println("Crear 4s for " + getName());
        try {
            on = false;
            Thread.sleep(4000); //4s
            on = true;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    } 
    
    public boolean endTime () {
        return on;
    }
    
    
}
