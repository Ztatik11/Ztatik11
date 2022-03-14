package persona;

public class paciente extends persona {
    private int id_cliente;
    private int especialista;
    public paciente(int id_cliente,int[][] ramas, int genero){
        super(genero);
        this.id_cliente = randomizador(0, 3000);
        this.especialista = randomizador(0, ramas.length-1);
    }

    public void setEspecialista(int especialista) {
        this.especialista = especialista;
    }

    public int getId_cliente() {
        return id_cliente;
    }
}
