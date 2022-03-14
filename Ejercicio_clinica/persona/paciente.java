package persona;

public class paciente extends persona {
    private int id_cliente;
    private especialista especialista;

   

    public paciente(int id_cliente, int genero){
        super(genero);
        this.id_cliente = id_cliente;
        this.especialista = null;
    }

    public boolean especialistaValido(int idEspecialista){

        return especialista==null || (especialista!= null && especialista.getId()==idEspecialista);
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
