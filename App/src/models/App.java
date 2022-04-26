//import java.io.*;
import static java.lang.Thread.sleep;
import java.util.*;
import java.util.Scanner;

import models.Comprador;
import models.EndPrint;
import models.Subasta;
import models.Vendedor;
import models.Listas;

public class App {

  public synchronized static void handleListV(List<Vendedor> listVendedor, Listas db) {
    for (int i = 0; i < listVendedor.size(); i++) {
      listVendedor.get(i).setListSubastas(db);
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
  
  public static boolean notEndSubastas (List<Subasta> subastas) {
    for (int i = 0; i < subastas.size(); i++) {
        //System.out.println("N" + !subastas.get(i).getCloseSubasta() + " " + subastas.get(i).getName());
        if (!subastas.get(i).getCloseSubasta()) {
          return true;
        }
    }
    return false;
  }
  
    public static int totalVentasVendedor (List<Subasta> listSubasta, Vendedor vende) {
      int cont = 0;
      for(int j = 0; j < listSubasta.size(); j++){
          if(listSubasta.get(j).getVendedor() == vende && listSubasta.get(j).getComprador() != null){
              cont+= listSubasta.get(j).getMontoActual();
          }
      }
      return cont;
    }

    public static int totalVentas (List<Subasta> listSubasta) {
       int cont = 0;
       for(int j = 0; j < listSubasta.size(); j++){
           if(listSubasta.get(j).getVendedor() != null && listSubasta.get(j).getComprador() != null){
               cont+= listSubasta.get(j).getMontoActual();
           }
       }
       return cont;
     }

    public static void main(String[] args) throws InterruptedException { 
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
      handleListV(db.listVendedor, db);
      handleListS(db.listSubasta);
      handleListC(db.listComprador, db);
      EndPrint fin = new EndPrint(in);
      fin.start();

      //Espera al fin
      
        while(notEndSubastas(db.listSubasta) && !fin.getPrintFin()){
            //Espero y analizo mientras
            //System.out.println("aqui " + !fin.getPrintFin());
            sleep(0);
        }
        
        //System.out.println("Stoooooooooooooooooooooooooooooooooooooooop" + notEndSubastas(db.listSubasta) + "/" +!fin.getPrintFin());
        
        fin.interrupt();

        for (int i = 0; i < db.getListComprador().size(); i++) {
            db.getListComprador().get(i).stopped();
        }
         for (int i = 0; i < db.getListSubasta().size(); i++) {
            db.getListSubasta().get(i).stopped();
        }
          for (int i = 0; i < db.getListVendedor().size(); i++) {
            db.getListVendedor().get(i).stopped();
        }
         
        System.out.println("Compradores: ");
        sleep(1000);
        for(int i = 0; i < db.listComprador.size(); i++){
            Comprador auxComprador = db.listComprador.get(i);
            System.out.print(auxComprador.GetName() + " monto actual: " + auxComprador.GetMonto());
            boolean flag = true;
            for(int j = 0; j < db.listSubasta.size(); j++){
                if(db.listSubasta.get(j).getComprador() == db.listComprador.get(i)){
                    if(flag){
                        System.out.println(" y adquirio: ");
                        flag=false;
                    }
                    System.out.println(db.listSubasta.get(j).GetProduct() + " por " + db.listSubasta.get(j).getMontoActual());
                }
            }
            if(flag){
                System.out.println(" no adquirio ningun producto ");
            }
        }
        
        System.out.println("Vendedores: ");
        for(int i = 0; i < db.listVendedor.size(); i++){
            Vendedor auxVendedor = db.listVendedor.get(i);
            int auxMontoV = totalVentasVendedor(db.listSubasta, auxVendedor);
            int auxGana = (int) (auxMontoV * 0.1);
            System.out.println(
              auxVendedor.getName() + " Total en ventas: " +
              auxMontoV + " / Ganancia: " + auxGana
            );
        }
        
        System.out.println("Martillero: ");
        int auxMonto = totalVentas(db.listSubasta);
        int auxGana = (int) (auxMonto * 0.15);
        System.out.println(
          "Total en ventas: " + auxMonto + " / Ganancia: " + auxGana
        );

      //System.out.println("FIN system");

      in.close();
      return;
  }
}

