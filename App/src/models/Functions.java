package utilis;
//import models.Comprador;
//import models.Subasta;

import java.util.Random;

public class Functions {

  public static int rand(int min, int max) {
      return new Random().nextInt(max - min + 1) + min;
  }

  //Subasta subasta;
  //Comprador comprador;

  /*
  public Boolean Comp2(Comprador comprador, Subasta subasta) {
      this.subasta = subasta;
      this.comprador = comprador;
      int percent = 1;
      percent += rand(1, comprador.GetPercent()) / 100;
      return (comprador.GetMonto() <= subasta.GetMonto() * percent);
  }
  */

  /*
  public Boolean Comprobar(Subasta subasta, Comprador comprador) {
      this.subasta = subasta;
      this.comprador = comprador;
      boolean flag = false;
      if ((comprador.GetMonto() > subasta.GetMonto()) && Comp2(comprador, subasta)) {
          flag = true;
      }else flag = false;
      
      return flag;
  }
  
  public void Puja(Subasta subasta, Comprador comprador){
      this.subasta = subasta;
      this.comprador = comprador;
      int percent = 1;
      percent += rand(1, comprador.GetPercent()) / 100;
      if(Comprobar(subasta,comprador)){
          System.out.println("[" + subasta.GetVName()+ "] " + comprador.GetName() + " ofrecio " + subasta.GetMonto() * percent + " por el producto " + subasta.GetProduct());
          subasta.SetMonto(subasta.GetMonto() * percent);
      }
  }
  */
}
