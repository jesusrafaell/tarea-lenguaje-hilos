//import java.io.*;
import java.util.*;
import java.util.Scanner;

import models.Comprador;
import models.Subasta;
import models.Vendedor;
import models.Listas;

public class App {

  public synchronized static void handleListV(List<Vendedor> listVendedor) {
    for (int i = 0; i < listVendedor.size(); i++) {
      listVendedor.get(i).start();
    }
  }

  public synchronized static void handleListC(List<Comprador> listComprador, Listas db) {
    for (int i = 0; i < listComprador.size(); i++) {
      listComprador.get(i).setListSubastas(db);
      listComprador.get(i).start();
    }
  }

  public synchronized static void handleListS(List<Subasta> listSubasta) {
    for (int i = 0; i < listSubasta.size(); i++) {
      listSubasta.get(i).start();
    }
  }

  public static Vendedor getVendedor(List<Vendedor> data, String name) {
    for (int i = 0; i < data.size(); i++) {
      if (data.get(i).getName().equals(name)) {
        return data.get(i);
      }
    }
    return null;
  }

	public static void main(String[] args) { 
    // Using Scanner for Getting Input from User
      Scanner in = new Scanner(System.in);

      String line = in.nextLine();

      Listas db = new Listas();

      while(!line.equals("Inicio") && !line.equals("Fin")){
        if (line.charAt(0) == 'V' && line.charAt(1) == ' ') {
          String[] vendedor = line.split(" ", 2);
          db.setListVendedor(new Vendedor(vendedor[1]));
        }else if(line.charAt(0) == 'C' && line.charAt(1) == ' '){
          String[] comprador = line.split(" ", 4);
          db.setListComprador(new Comprador(comprador[1], Integer.parseInt(comprador[2]),  Integer.parseInt(comprador[3])));
        }else if(line.charAt(0) == 'S' && line.charAt(1) == ' '){
          String[] subasta= line.split(" ", 4);
          for (int j = 0; j < db.listVendedor.size(); j++) {
            if (subasta[1].equals(db.listVendedor.get(j).getName())) {
              db.setListSubasta(new Subasta(db.listVendedor.get(j), (subasta[2]), Integer.parseInt(subasta[3])));
              break;
            }
          }
        }
        line = in.nextLine();
      }
      
      if(line.equals("Fin")){
          System.out.println("Ningun vendedor vendio nada y la comision del martillero es cero");
          return;
      }

      System.out.println("running");
      handleListV(db.listVendedor);
      handleListS(db.listSubasta);
      handleListC(db.listComprador, db);
      /*
      handleListV();
      handleListC();
      handleListS();
      */
      //Espera al fin
      while(!line.equals("Fin")){
        line = in.nextLine();
      }

      /*
      for (int i = 0; i < listVendedor.size(); i++) {
          listVendedor.get(i).stopped();
          System.out.println(listVendedor.get(i) + " stop");
      }
      */

      System.out.println("FIN system");

      in.close();
      return;
  }
}

