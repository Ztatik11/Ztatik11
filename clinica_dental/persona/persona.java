package persona;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

public class persona {
    private String nombre;
    private String apellidos;
    private String fecha_nacimiento;
    private String genero;
    private String telefono;
    //String nombre,String apellidos,LocalDate fecha_nacimiento,int telefono,String genero
    //CONSTRUCTOR DE CLIENTE
    public persona(String [] datos_paciente){
    	this.nombre = datos_paciente[0];
        this.apellidos = datos_paciente[1];
        this.telefono = datos_paciente[2];
        this.genero = datos_paciente[3];

    }

    // ---------------------Getters---------------------


    public String getNombre(){
        return nombre;
    }

    public String getApellido(){
        return apellidos;
    }

    public String getGenero(){
        return genero;
    }

    public String getTelefono(){
        return telefono;
    }

    //---------------------Setters---------------------



    public void setNombre(String nombre){
        this.nombre = nombre;
    }

    public void setApellido (String apellido){
        this.apellidos = apellido;
    }


    public void setGenero(String genero){
        this.genero = genero;
    }

    public void setTelefono(String telefono){
        this.telefono = telefono;
    }
    
}


