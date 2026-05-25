package co.edu.uco.ucoparking.transversal.utilitario;


import java.util.UUID;

public final class UtilUUID {


    public static final UUID UUID_DEFECTO = UUID.fromString("00000000-0000-0000-0000-000000000000");

  
    // Constructor privado → no se puede instanciar, es una clase de utilidades puras
    private UtilUUID() {
        super();
    }

}
