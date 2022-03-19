package persona;


public class paciente extends persona {
    private int id_cliente;
    private especialista especialista;
  
   
    //CONSTRUCTOR PACIENTE QUE HEREDA DE PERSONA
    public paciente(int id_cliente, int genero){
        super(genero);
        this.id_cliente = id_cliente;
        this.especialista = null;
        
    }
    //cOMPRUEBA QUE EL PACIENTE ES VALIDO PARA LA ASIGNACION DE LA CITA CON UN UN DETERMINADO PACIENTE
    public boolean especialistaValido(int idEspecialista){

        return especialista==null || (especialista!= null && especialista.getId()==idEspecialista);
    }

    public String getCumpleanyos(){
        return getFechaNacimiento().getDayOfMonth() + " " + getFechaNacimiento().getMonth();
    }

    public int getId_cliente() {
        return id_cliente;
    }

    public especialista getEspecialista() {
        return especialista;
    }


    public void setId_cliente(int id_cliente) {
        this.id_cliente = id_cliente;
    }

    public void setEspecialista(especialista especialista) {
        this.especialista = especialista;
    }
}
