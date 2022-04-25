package models;

import java.util.*;

public class Listas {
  public List<Vendedor> listVendedor = new ArrayList<Vendedor>();
  public List<Comprador> listComprador = new ArrayList<Comprador>();
  public List<Subasta> listSubasta = new ArrayList<Subasta>();

  public List<Vendedor> getListVendedor() {
    return listVendedor;
  }

  public List<Comprador> getListComprador() {
    return listComprador;
  }

  public List<Subasta> getListSubasta() {
    return listSubasta;
  }

  public void setListVendedor(Vendedor newVendedor) {
    listVendedor.add(newVendedor);
  }

  public void setListComprador(Comprador newComprador) {
    listComprador.add(newComprador);
  }

  public void setListSubasta(Subasta newSubasta) {
    listSubasta.add(newSubasta);
  }
}
