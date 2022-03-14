package persona;

import java.security.Timestamp;
import java.util.ArrayList;

//Citas: fecha, especialista, ramas[rama], numeroCliente, precio, cobro, urgencia 
public class citas {
    private int fecha;
    private especialista especialista;
    private rama rama;
    private paciente paciente;
    private int cobro;
    private int urgencia;
    
    public citas(int fecha, especialista especialista,  ArrayList<paciente> cliente, paciente paciente){
        paciente objeto = cliente.get(randomizador(0, cliente.size()-1));
        this.fecha = fecha;
        
        
        this.especialista = especialista;
        this.rama = especialista.getRamas()[randomizador(0, especialista.getRamas().length-1)];
        
        this.paciente = paciente;
        this.cobro = randomizador(0, 2);
        this.urgencia = 0;

    }

    public double precioCita(){
        return urgencia==1 ? rama.precioUrgencia(20) : rama.getPrecioBase();
    }

    public static int randomizador(int desde, int hasta) {
        int numero;
        numero = (int) (Math.floor((hasta - desde + 1) * Math.random()));
        return numero;
    }
}
