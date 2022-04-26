package models;

public class Ofertante {
    private Comprador comprador;
    private int oferta;

    Ofertante(Comprador comp, int montoOferta) {
        this.comprador = comp;
        this.oferta = montoOferta;
    }
    
    public void Ofertante(Comprador comp, int monto){
        this.comprador = comp;
        this.oferta = monto;
    }
    
    public Comprador getComprador (){
        return comprador;
    }
    
    public int getMonto (){
        return oferta;
    }
}
