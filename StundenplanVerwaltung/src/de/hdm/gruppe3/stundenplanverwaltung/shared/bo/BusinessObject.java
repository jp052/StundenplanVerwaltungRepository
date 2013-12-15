package de.hdm.gruppe3.stundenplanverwaltung.shared.bo;

import java.io.Serializable;

/**
 * @author Yasemin Karakoc, Jan Plank
 *
 */
public abstract class BusinessObject implements Serializable {
 //julia
	//jan
  private static final long serialVersionUID = 1L;

  private int id = 0;

  public int getId() {
    return this.id;
  }


  public void setId(int id) {
    this.id = id;
  }

  public String toString() {
	//Klassenname + Objekt Id
    return this.getClass().getName() + " #" + this.id;
  }


  public boolean equals(Object object) {
	/*schaut ob objek existiert und ein BusinessObject ist, wenn ja wird
	übergebenes Objekt in ein BusinessObjekt umgewandelt (cast)*/
    if (object != null && object instanceof BusinessObject) {
      BusinessObject businessObj = (BusinessObject) object;
      try {
    	//Wennn Id gleich ist, dann wird true zurückggeben.
        if (businessObj.getId() == this.id)
          return true;
      }
      catch (IllegalArgumentException e) {
    	//Bei Fehler false zurückgeben
        return false;
      }
    }
    //Ist die Id nicht gleich wird false zurückggeben
    return false;
  }
  
  public int hashCode() {
	  return this.id;
  }

}

