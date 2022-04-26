package models;

import java.util.ArrayList;
import java.util.List;


public class Subasta extends Thread {
    // state or field

    private Vendedor vendedor;
    private Comprador comprador = null;
    private Comprador compradorActual = null;
    private int monto = 0;
    private int montoActual;
    private String product;
    private boolean exit = false;
    private boolean closeSubasta =false;
    private boolean print =true;
    
    private TimeSubasta time;


    public Subasta(Vendedor vendedor, String product, int monto) {
        super(product);
        this.vendedor = vendedor;
        this.monto = monto;
        this.montoActual = monto;
        this.product = product;
        this.time = time = new TimeSubasta("t " + product);

        //System.out.println("Creando hilo subasta " + '/' + vendedor.getName() + '/' + monto + '/' + product);
    }

   // List<Comprador> listEspera = new ArrayList<Comprador>();
    //List<Integer> listEsperaMonto = new ArrayList<>();
    
    List<Ofertante> listEspera = new ArrayList<Ofertante>();
    
    public List<Ofertante> getListEspera () {
        return listEspera;
    }
    
    public void addToListEspera (Comprador comprador, int montoOferta) {
        listEspera.add(new Ofertante(comprador, montoOferta));
    }
    
    public void removeFromListEspera (int index){
        listEspera.remove(index);
    }
    
    
    public void run() {
        boolean no = false;
        time.start();
        while (!exit) {
            try{
                if(time.endTime() && !closeSubasta){
                    //System.out.println("Fin subasta " + getName() + " la lista de espera: " + listEspera.size());
                    closeSubasta = true;
                    exit = true;
                    break;
                }
                List<Ofertante> auxListEspera = listEspera;
                //List<Integer> auxListMonto  = listEsperaMonto;
                for (int i = 0; i < auxListEspera.size(); i++) {  
                    if (auxListEspera.get(i).getComprador() != null && auxListEspera.get(i).getComprador() != comprador) {
                       //System.out.println("bug1");
                        //System.out.println(i + " " + auxListMonto.size() + " sss " + getName());
                        if (montoActual < auxListEspera.get(i).getMonto()) {
                            //System.out.println("bug2");
                            if (comprador != null) {
                                 //Devuelver el Dinero al comprador anterior
                                //System.out.println("Comprador " + comprador.getName() + " recupero Monto: " + montoActual + " en " + getName() + " tiene " + comprador.GetMonto());
                                comprador.SetMonto(comprador.GetMonto() + montoActual);
                                //System.out.println("Comprador " + comprador.getName() + " recupero Monto: " + montoActual + " en " + getName() + " ahora tiene " + comprador.GetMonto());
                            }
                            comprador = listEspera.get(i).getComprador();
                            montoActual = listEspera.get(i).getMonto();
                            comprador.initPuja(this);
                            //time.interrupt();
                            //System.out.println("Reinicar contador de " + getName());
                            time = new TimeSubasta("t " + product);
                            time.start();
                            
                            printOferta(comprador, montoActual);
                        } else {
                            auxListEspera.get(i).getComprador().SetMonto(auxListEspera.get(i).getComprador().GetMonto() + listEspera.get(i).getMonto());
                            //System.out.println("Comprador " + auxListEspera.get(i).getComprador().getName() + " recupero de la oferta: " + listEspera.get(i).getMonto() + " en " + getName() + " tiene " + auxListEspera.get(i).getComprador().GetMonto());
                        }
                    }
                    removeFromListEspera(i);
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
    
    
  public void stopped() {
    for (int i = 0; i < listEspera.size(); i++) {  
        if (listEspera.get(i).getComprador() != null && listEspera.get(i).getComprador() != comprador) {
            /*
            System.out.println(listEspera.get(i).getComprador().getName() + " recupero " + listEspera.get(i).getMonto()
                    + " porque actual " + getName() + " vale " + montoActual);
            */
            listEspera.get(i).getComprador().SetMonto(listEspera.get(i).getComprador().GetMonto() + listEspera.get(i).getMonto());
        }
    }
    exit = true;
  }

    
    public void printOferta (Comprador comp, int oferta) {
        System.out.println(comp.GetName() + " ofreció " + oferta + " por el producto " + product);
    }

    public void printName(String nameEspera, int montoEspera) {
        /*
        System.out.println(
            "Hilo Subasta: " + getName() + " Monto Actual es: " + 
            montoActual + " / Ofertante: " + 
            nameEspera +
            " Monto a ofertar " + montoEspera
         );
        */
    }

    public int GetMonto() {
        return 0;
    }

    public String GetProduct() {
        return product;
    }

    public String GetVName() {
        return vendedor.getName();
    }

    public void SetMonto(int nmonto) {
        this.monto = nmonto;
    }

    public int getMontoActual() {
        return this.montoActual;
    }

    public void setMontoActual(int monto) {
        this.montoActual = monto;
    }

    public boolean subastar(Comprador comprador) {
        if (this.comprador != null) {
            return true;
        } else {
            return false;
        }
    }

    public Comprador getComprador() {
        return this.comprador;
    }
    
    public Vendedor getVendedor() {
        return this.vendedor;
    }

    public void setComprador(Comprador comprador) {
        this.comprador = comprador;
    }

    public String getCName() {
        return comprador.getName();
    }
    
    public boolean getCloseSubasta () {
        //System.out.println(getName() + " aun no termina " +  closeSubasta  + " exit: " + exit);
        return closeSubasta;
    }
    
    public boolean canPrintV () {
        return print;
    }
    
     public void setPint () {
        print = false;
    }
    

    public void newOfertante(Comprador ofertante, int monto) {
        if (montoActual < monto) {
            ofertante.SetMonto(ofertante.GetMonto() - monto);
            addToListEspera(ofertante, monto);
            //System.out.println("Comprador " + ofertante.getName() + " oferto: " + monto + " en " + getName() + " tiene " + ofertante.GetMonto());
        }
    }
}
