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

    List<Comprador> listEspera = new ArrayList<Comprador>();
    
    List<Integer> listEsperaMonto = new ArrayList<>();
    
    public List<Comprador> getListEspera () {
        return listEspera;
    }
    
    public void addToListEspera (Comprador comprador) {
        listEspera.add(comprador);
    }
     public void addToListEsperaMonto (int monto) {
        listEsperaMonto.add(monto);
    }
    
    public void removeFromListEspera (int index){
        listEspera.remove(index);
    }
    
     public void removeFromListEsperaMonto (int index){
        listEsperaMonto.remove(index);
    }
    
    public void run() {
        boolean no = false;
        while (!exit) {
            try{
                if(time.endTime() && !closeSubasta){
                    //System.out.println("Fin subasta " + getName() + " la lista de espera: " + listEspera.size());
                    closeSubasta = true;
                    exit = true;
                    break;
                }
                List<Comprador> auxListEspera = listEspera;
                List<Integer> auxListMonto  = listEsperaMonto;
                for (int i = 0; i < auxListEspera.size() && i < auxListMonto.size(); i++) {   
                    if (auxListEspera.get(i) != null && auxListEspera.get(i) != comprador) {
                       //System.out.println("bug1");
                        //System.out.println(i + " " + auxListMonto.size() + " sss " + getName());
                        if (montoActual < auxListMonto.get(i)) {
                            //System.out.println("bug2");
                            if (comprador != null) {
                                 //Devuelver el Dinero al comprador anterior
                                comprador.SetMonto(comprador.GetMonto() + montoActual);
                                //System.out.println("Comprador " + comprador.getName() + " recupero Monto: " + montoActual);
                            }
                            comprador = listEspera.get(i);
                            montoActual = auxListMonto.get(i);
                            comprador.initPuja(this);
                            //time.interrupt();
                            //System.out.println("Reinicar contador de " + getName());
                            time = new TimeSubasta("t " + product);
                            time.start();
                            
                            printOferta(auxListEspera.get(i), auxListMonto.get(i));
                        } else {
                            auxListEspera.get(i).SetMonto(comprador.GetMonto() + auxListMonto.get(i));
                        }
                        removeFromListEsperaMonto(i);
                        removeFromListEspera(i);
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

    public void newOfertante(Comprador ofertante, int monto) {
        if (montoActual < monto) {
            //int exMonto = ofertante.GetMonto();
            /*
              System.out.println(
                "Comprador: " + ofertante.getName() + " tiene " + exMonto + " y pujo "
                + monto + " por " + this.getName()
                + " cuesta " + montoActual            
              );
            */
            ofertante.SetMonto(ofertante.GetMonto() - monto);
            addToListEspera(ofertante);
            addToListEsperaMonto(monto);
            /*
            if (this.comprador != null && this.comprador!=ofertante) {
                System.out.println(
                    "Comprador: " + ofertante.getName() + " tiene " + exMonto + " y pujo "
                    + monto + " por " + this.getName()
                    + " cuesta " + montoActual
                    + " comprador actual " + this.comprador.getName()
                        
                );
            } else if(this.comprador==null){
                System.out.println(
                    "Comprador: " + ofertante.getName() + " tiene " + exMonto + " y oferto "
                    + monto + " por " + this.getName()
                    + " cuesta " + montoActual
                    + " no tiene comprador anterior"
                ); 
            }
            */
        }
    }
}
