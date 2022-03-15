package persona;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class persona {
    private String dni;
    private String nombre;
    private String apellido_1;
    private String apellido_2;
    private LocalDate fecha_nacimiento;
    private int genero;
    private String calle;
    private int telefono;

    //CONSTRUCTOR DE CLIENTE
    public persona(int genero){
        String [] nombresFemeninos = {"Lucia","Isabel","Ana","Samantha","Eowin", "Galadriel", "Arwen", "Ella", "Ellen", "Alita", "Arya", "Samsa", "Anabel", "Mabel", "Ellie", "Alloy", "Lara", "Samus", "Cortana", "Siri", "Alexa", "Paula", "Sofia", "Candela", "Gemma", "Lourdes", "Mileena", "Baraca", "Lida", "Jun", "Mondella", "Basma"};
        String [] nombresMasculinos ={"Alvaro","David","Jose","Adrian","Yoda","Frodo","Sam","Merry","Pipin","Gimli","Aragorn","Trancos","Legolas","Elendir","Pepe","Harry","Ramiro","Hank","Benjamin","Alexander","Berni","Illias","Ellias","Horus","Mortarion","Roboute","Sanguinus","Fulgrim","Rogal","Vulkan"};
        String [] apellidos = {"Lamela","Peral","Dominguez","Carrasco","Mas","Lillo","Lozano","Viyuela","Broncano","Moreno","Zabala","Espinosa","Mauri","Mariño","Mateos","Rodriguez","Perez","Blanco","De la Rosa","Cabañas","Romero","Perona","Matias","Aldana","Fernandez","Diez","Clemente","Briega","Carmona","Luque","Redondo","Diaz","Cuevas","Parejo","Soriano","Gonzalez","Zarza","Carrasco","Gil","Cabrera","Nuñez","Rojas","Valero","Santamaria","Ramos","Mata","Garcia","De la Red"};

        this.apellido_1 = stringAleatorio(apellidos);
        this.apellido_2 = stringAleatorio(apellidos);
        this.fecha_nacimiento = fecha_nacimiento();
        this.dni = CrearDNI();
        this.genero = genero;
        if(genero==0){
            this.nombre = stringAleatorio(nombresFemeninos);
        }else{
            this.nombre = stringAleatorio(nombresMasculinos);
        }
        this.calle = generadorCalle();
        this.telefono = randomizador(100000000, 999999999);
    }

    //CONSTRUCTOR DE ESPECIALISTA
    public persona(String nombre, LocalDate fecha_nacimiento, int genero){
        String [] apellidos = {"Lamela","Peral","Dominguez","Martinez","Garcia","Carrasco","Mas","Lillo","Lozano","Viyuela","Broncano"};

        this.nombre = nombre;
        this.apellido_1 = stringAleatorio(apellidos);
        this.apellido_2 = stringAleatorio(apellidos);
        this.fecha_nacimiento = fecha_nacimiento;
        this.dni = CrearDNI();
        this.genero = genero;
        this.calle = generadorCalle();
        this.telefono = randomizador(100000000, 999999999);

    }

    
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
    //GENERA LA FECHA DE NACIMIENTO
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

    public String getApellido_1(){
        return apellido_1;
    }

    public String getApellido_2(){
        return apellido_2;
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

    public void setApellido_1 (String apellido){
        this.apellido_1 = apellido;
    }

    public void setApellido_2 (String apellido){
        this.apellido_2 = apellido;
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

