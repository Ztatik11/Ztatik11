package persona;

public class rama {
    int id;
    String nombreRama;
    int precioBase;

    public rama (int id, String nombreRama, int precioBase){
        this.id = id;
        this.nombreRama = nombreRama;
        this.precioBase = precioBase;
    }

    public double precioUrgencia(int porcentaje){
        return (double) precioBase + precioBase*porcentaje/100.0;
    }

    public double descuento( int porcentaje){
        return (double) precioBase - precioBase*porcentaje/100.0;
    }

    //-------------------setters-------------------
    public void setNombreRama ( String nombreRama){
        this.nombreRama = nombreRama;
    }

    public void setPrecioBase(int precioBase){
        this.precioBase =precioBase;
    }

    public void setId(int id){
        this.id= id;
    }

    //-------------------getters-------------------
    public int getID(){
        return id;
    }

    public String getNombreRama(){
        return nombreRama;
    }

    public int getPrecioBase(){
        return precioBase;
    }
}
