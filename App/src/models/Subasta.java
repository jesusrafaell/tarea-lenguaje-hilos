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

    private Comprador ofertante = null;
    private int montoOfertante = 0;

    public Subasta(Vendedor vendedor, String product, int monto) {
        super(product);
        this.vendedor = vendedor;
        this.monto = monto;
        this.montoActual = monto;
        this.product = product;

        System.out.println("Creando hilo subasta " + '/' + vendedor.getName() + '/' + monto + '/' + product);
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
            try {
                //System.out.println("Hilo Subasta->" + getName() + this.ofertante + this.comprador );
                for (int i = 0; i < listEspera.size(); i++) {   
                    if (listEspera.get(i) != null && listEspera.get(i) != comprador) {
                        System.out.println(
                            getName() + " Monto Actual es: " + 
                            montoActual + " Comprador: " + 
                            listEspera.get(i).getName() +
                            " Monto a ofertar " + listEsperaMonto.get(i)
                         );
                        //printName();
                        if (montoActual < listEsperaMonto.get(i)) {
                            if (comprador != null) {
                                 //Devuelver el Dinero al comprador anterior
                                comprador.SetMonto(comprador.GetMonto() + montoActual);
                                System.out.println(
                                    "Comprador " + comprador.getName() + " recupero Monto: " + montoActual + " //////////// "
                                );
                            }
                            comprador = listEspera.get(i);
                            montoActual = listEsperaMonto.get(i);
                            comprador.initPuja(this);
                            //crear el hilo de esperar para comprador
                            removeFromListEspera(i);
                            removeFromListEsperaMonto(i);
                        } else {
                            listEspera.get(i).SetMonto(comprador.GetMonto() + listEsperaMonto.get(i));
                            removeFromListEspera(i);
                            removeFromListEsperaMonto(i);
                        }
                    }
                }
             
                Thread.sleep(1000);
                if (no) {
                    Thread.sleep(4000);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    // behavior or method
    /*public void printName() {
    if(comprador != null){
      System.out.println(
        "Hilo Subasta->" + product + " == " + " Comprador: " + comprador.getName() +
        " / Monto: " + montoActual + " / ofertante: " + ofertante.getName() +
        " oferta: " + montoOfertante );
      } else {
        System.out.println("Hilo Subasta->" + product + " == " +
        "/ no tiene comprador / Monto: " + monto + " / ofertante: "
        + ofertante.getName()  + " oferta: " + montoOfertante );
    }
  }*/
    public void printName2() {
        if (comprador != null && compradorActual != ofertante) {
            System.out.println(
                    "HiloX Subasta->" + product + " == " + " Comprador: " + comprador.getName()
                    + " / Monto: " + montoActual);
        } else {
            System.out.println("El comprador actual es " + compradorActual.GetName() + " con un precio de " + montoActual);
            /*System.out.println("HiloX Subasta->" + product + " == " +
        "/ no tiene comprador / Monto: " + monto);*/

        }
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

    public void setComprador(Comprador comprador) {
        this.comprador = comprador;
    }

    public String getCName() {
        return comprador.getName();
    }

    public void newOfertante(Comprador ofertante, int monto) {
        //System.out.println("Holaaa entre en el metodo newOfertante: ");
        if (montoActual < monto) {
            int exMonto = ofertante.GetMonto();
            ofertante.SetMonto(ofertante.GetMonto() - monto);
            addToListEspera(ofertante);
            addToListEsperaMonto(monto);
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
        }
    }
}
