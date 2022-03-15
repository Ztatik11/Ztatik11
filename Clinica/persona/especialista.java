package persona;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;

public class especialista extends persona {
    private int id;
    private rama [] ramas;
    
    private DayOfWeek diaLibre;
    private ArrayList <Double> mensualidad;
    private ArrayList <Month> mesesMensualidad;

    //CONSTRUCTO DE ESPECIALISTA QUE HEREDA DE PERSONA
    public especialista(int id, rama [] rama,  DayOfWeek diaLibre,  String nombre, LocalDate fecha_nacimiento, int genero){
        //llamada a superclase
        super( nombre,  fecha_nacimiento, genero);

        //Parametros propios
        this.id = id;
        this.ramas=rama;
       
        this.diaLibre = diaLibre;
        this.mensualidad = new ArrayList<Double>();
        this.mesesMensualidad = new ArrayList<Month>();


    }
    //CAMBIA DE MES
    public void nuevoMes(Month mes){
        this.mensualidad.add(0.0);
        this.mesesMensualidad.add(mes);
    }
    //SUMA LOS BENEFICIOS AL MES
    public void sumarBeneficio(double cobro){
        if(mensualidad.size()>1){
        double valorActual = mensualidad.get(mensualidad.size()-1);
        mensualidad.set(mensualidad.size()-1, valorActual + (cobro*0.15));
        }
    }
    //COMPRUEBA SI EL DIA ACTUAL ES LABORABLE O NO
    public boolean diaNoLaborable( ArrayList <LocalDate> dias_festivos, LocalDate dia){
        return (dias_festivos.contains(dia) || (this.diaLibre!=null && dia.getDayOfWeek() == this.diaLibre ) ||dia.getDayOfWeek()==DayOfWeek.SATURDAY||dia.getDayOfWeek()==DayOfWeek.SUNDAY);
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

    public ArrayList<Double> getMensualidad() {
        return mensualidad;
    }
    public ArrayList<Month> getMesesMensualidad() {
        return mesesMensualidad;
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

    public void setMensualidad(ArrayList<Double> mensualidad) {
        this.mensualidad = mensualidad;
    }
}
