package persona;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class persona {
    private String dni;
    private String nombre;
    private String apellido;
    private LocalDate fecha_nacimiento;
    private int genero;
    private String calle;
    private int telefono;

    
    public persona(int genero){
        String [] nombres = {"Alvaro","Lucia","Isabel","David","Jose","Adrian","Ana","Yoda","Frodo","Sam","Patata"};
        String [] apellidos = {"Lamela","Peral","Dominguez","Martinez","Garcia","Carrasco","Mas","Lillo","Lozano","Viyuela","Broncano"};

        this.nombre = stringAleatorio(nombres);
        this.apellido = stringAleatorio(apellidos);
        this.fecha_nacimiento = fecha_nacimiento();
        this.dni = CrearDNI();
        this.genero = genero;
        this.calle = generadorCalle();
        this.telefono = randomizador(100000000, 999999999);
    }

    //El otro constructor que vamos a llamar desde especialista, para controlar los datos principales
    public persona(String nombre, LocalDate fecha_nacimiento, int genero){
        String [] apellidos = {"Lamela","Peral","Dominguez","Martinez","Garcia","Carrasco","Mas","Lillo","Lozano","Viyuela","Broncano"};

        this.nombre = nombre;
        this.apellido = stringAleatorio(apellidos);
        this.fecha_nacimiento = fecha_nacimiento;
        this.dni = CrearDNI();
        this.genero = genero;
        this.calle = generadorCalle();
        this.telefono = randomizador(100000000, 999999999);

    }

    //Idea feliz

    public static String stringAleatorio(String [] arrayStrings){
        return arrayStrings.length > 0 ? arrayStrings[randomizador(0, arrayStrings.length-1)] : "";
    }

    public static String generadorCalle(){
        String [] categoria = {"Calle","Avd"};
        String [] nombre1 = {"Virgen","Imaginero","Luis","Monovar","Jose","Luillo","Salle","Ancha","Mayor","Menor","Ilarios"};
        String [] nombre2 = {"Rocio","Fernandez","Montoto","Laguillo","Sastre","Di-Castellar","Pepe","Salamandre","Silvestre","Golondrina","Generalisimo",};
        String [] piso = {"A","B","C","D"};
        String calle = stringAleatorio(categoria) + " " + stringAleatorio(nombre1) + " " + stringAleatorio(nombre2) +" "+randomizador(1, 10) +", "+randomizador(1, 12) + " " +stringAleatorio(piso);
        return calle;
    }

    public static String CrearDNI() {
        //primero se genera los 8 numero, despues se para a String y se le aniade la letra final
        int numero = randomizador(100000000, 999999999);
        char [] letras = {'T','R','W','A','G','M','Y','F','P','D','X','B','N','J','Z','S','Q','V','H','L','C','K','E'};
        char letra = letras[numero%23];
        String dni = String.valueOf(numero)+letra;
        return dni;
    }

    public static LocalDate fecha_nacimiento(){
        LocalDate fecha = LocalDate.of(1917, 1 , 1);
        fecha.plusDays(randomizador(1, (int)ChronoUnit.DAYS.between(LocalDate.of(1917, 1 , 1), LocalDate.of(2014, 12, 31))));
        return fecha;
    }


    public static int randomizador(int desde, int hasta) {
        int numero;
        numero = (int) (Math.floor((hasta - desde + 1) * Math.random()));
        return numero;
    }

    // ---------------------Getters---------------------
    public String getDni(){
        return dni;
    }

    public String getNombre(){
        return nombre;
    }

    public String getApellido(){
        return apellido;
    }

    public LocalDate getFechaNacimiento(){
        return fecha_nacimiento;
    }

    public int getGenero(){
        return genero;
    }

    public String getCalle(){
        return calle;
    }

    public int getTelefono(){
        return telefono;
    }

    //---------------------Setters---------------------

    public void setDni(String dni){
        this.dni = dni;
    }

    public void setNombre(String nombre){
        this.nombre = nombre;
    }

    public void setApellido (String apellido){
        this.apellido = apellido;
    }

    public void setFechaNacimiento(LocalDate fecha_nacimiento){
        this.fecha_nacimiento = fecha_nacimiento;
    }

    public void setGenero(int genero){
        this.genero = genero;
    }

    public void setCalle(String calle){
        this.calle = calle;
    }

    public void setTelefono(int telefono){
        this.telefono = telefono;
    }
    
}

