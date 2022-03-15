package persona;

//Citas: fecha, especialista, ramas[rama], numeroCliente, precio, cobro, urgencia 
public class citas {
    private int fecha;
    private especialista especialista;
    private rama rama;
    private paciente paciente;
    private int cobro;
    private int urgencia;


       
    public citas(int fecha, especialista especialista,   paciente paciente){
       
        this.fecha = fecha;
        
        
        this.especialista = especialista;
        this.rama = especialista.getRamas()[randomizador(0, especialista.getRamas().length-1)];
        
        this.paciente = paciente;
        this.cobro = randomizador(0, 2);
        this.urgencia = 0;

    }

    //ESTOS METODOS FORMATEAN LOS DATOS A LA HORA DE MOSTRARLOS
    public String nombrePaciente(){
        return paciente.getNombre() + " " + paciente.getApellido_1() + " " + paciente.getApellido_2();
    }

    public String dniPaciente(){
        return paciente.getDni();
    }

    public String generoPaciente(){
        return paciente.getGenero() == 0 ? "Mujer" : "Hombre";
    }

    public String nombreEspecialista(){
        return especialista.getNombre() + " " +especialista.getApellido_1() + " " + especialista.getApellido_2();
    }

    public String nombreRama(){
        return rama.getNombreRama();
    }

    public String esUrgencia(){
        return urgencia == 1 ? "Es urgencia" : "No es urgencia";
    }

    public String nombreCobro(){

        switch (this.cobro) {
            case 0:
                
                return "Tarjeta";
            case 1:
                
                return "Metalico";
            case 2:
                
                return "Transferencia";
        }

        return "error";

    }
    //
    public double precioCita(){
        return this.urgencia==1 ? rama.precioUrgencia(20) : rama.getPrecioBase();
    }
    //GETTERS
    public int getFecha() {
        return fecha;
    }

    public especialista getEspecialista() {
        return especialista;
    }

    public rama getRama() {
        return rama;
    }

    public paciente getPaciente() {
        return paciente;
    }

    public int getCobro() {
        return cobro;
    }

    public int getUrgencia() {
        return urgencia;
    }
    //SETTERS
    public void setFecha(int fecha) {
        this.fecha = fecha;
    }

    public void setEspecialista(especialista especialista) {
        this.especialista = especialista;
    }

    public void setRama(rama rama) {
        this.rama = rama;
    }

    public void setPaciente(paciente paciente) {
        this.paciente = paciente;
    }

    public void setCobro(int cobro) {
        this.cobro = cobro;
    }

    public void setUrgencia(int urgencia){
        this.urgencia = urgencia;
    }

    




 

    public static int randomizador(int desde, int hasta) {
        int numero;
        numero = (int) (Math.floor((hasta - desde + 1) * Math.random()));
        return numero;
    }

    


}
