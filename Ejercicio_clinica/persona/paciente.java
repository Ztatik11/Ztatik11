package persona;

public class paciente extends persona {
    private int id_cliente;
    private especialista especialista;

   

    public paciente(int id_cliente, int genero){
        super(genero);
        this.id_cliente = randomizador(0, 3000);
        this.especialista = null;
    }

    public void setEspecialista(especialista especialista) {
        this.especialista = especialista;
    }

    

    public int getId_cliente() {
        return id_cliente;
    }
}
