package persona;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;

public class especialista extends persona {
    private int id;
    private rama [] ramas;
    
    private DayOfWeek diaLibre;

    public especialista(int id, rama [] rama,  DayOfWeek diaLibre,  String nombre, LocalDate fecha_nacimiento, int genero){
        //llamada a superclase
        super( nombre,  fecha_nacimiento, genero);

        //Parametros propios
        this.id = id;
        this.ramas=rama;
       
        this.diaLibre = diaLibre;


    }

    public void nuevoMes(){
        this.mensualidad.add(0.0);
    }

    public void sumarBeneficio(double cobro){
        
    }

    public boolean diaNoLaborable( ArrayList <LocalDate> dias_festivos, LocalDate dia){
        return (dias_festivos.contains(dia) || (this.diaLibre!=null && dia.getDayOfWeek() == this.diaLibre ));
    }
    //Getters-------------------------------------------------------
    public int getId() {
        return id;
    }

    public rama[] getRamas() {
        return ramas;
    }

    public DayOfWeek getDiaLibre() {
        return diaLibre;
    }
    //Setters-------------------------------------------------------
    public void setId(int id) {
        this.id = id;
    }

    public void setRamas(rama[] ramas) {
        this.ramas = ramas;
    }

    public void setDiaLibre(DayOfWeek diaLibre) {
        this.diaLibre = diaLibre;
    }
}
