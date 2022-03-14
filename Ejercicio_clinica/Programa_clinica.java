import java.util.ArrayList;
import java.util.Scanner;
import java.time.*;
import java.time.temporal.ChronoUnit;
import java.text.NumberFormat;
import java.util.Locale;
import persona.*;

public class Programa_clinica {
    //MAIN
    public static void main(String[] args) {
      
        String especialista [] = {"Maria", "Juan", "Paco","Elena","Pepe","David"} ;                //especialistas: Array de 3 strings, Maria, Juan y Paco 
        String ramas [] = {"Nutricion", "Fisioterapia"};                                            //ramas: Array de 2 Strings, NutriciÃ³n y Fisioterapia                  
        int precios [] = {40, 50, 48, 60};                                                          //precios: Array de enteros: 40 y 50 euros 
        String cobro [] = {"Efectivo", "Tarjeta", "Transferencia"};                                 //formas de cobro: Array de 3 Strings, Efectivo, Tarjeta y Transferencia 
        int ramasDeEspecialista [][] = {{0,1}, {0}, {1},{0,1},{0,1},{1}};                           //Ramas de cada especialista: Matriz de enteros con los Ã­ndices (segÃºn array de ramas) que atiende cada especialista
        ArrayList <int []> visitas = new ArrayList<>();                                             //ArrayList que permita almacenar las visitas generadas//
        LocalDate fechaActual = LocalDate.of(2022, 3, 1);
        ArrayList<paciente> clientes = new ArrayList<>();

        Scanner sc = new Scanner(System.in);
        int opcion = 0;
        
        MostrarMenu();

        do{
            try {
                opcion = sc.nextInt();
                switch(opcion){
                    case 1:
                        
                        GenerarDatosVisitas(ramasDeEspecialista,clientes,visitas);
                        System.out.println("DATOS GENERADOS!!!");
                        MostrarMenu();
                    break;
                    case 2:
                        //Mostrar datos generados;
                        System.out.println("//Mostrar datos generados!!!//");
                        mostrar_datos(visitas,especialista,ramas,precios,cobro,fechaActual);
                        MostrarMenu();
                    break;
                    case 3:
                        //Mostrar resumen
                        System.out.println("//Mostrar resumen!!!//");
                        calcular_resumen(visitas, precios);
                        calcular_mensualidades(visitas, precios, especialista);
                        
                        MostrarMenu();
                    break;
                    case 4:
                        //Salir
                        System.out.println("//Nos vemos!!!//");
                        MostrarMenu();
                    break;
                    default:
                        System.out.println("//Introduce un numero valido//");
                        MostrarMenu();
                    break;
                }
            } catch (Exception e) {
                System.out.println("//Debes insertar un numero//");
                System.err.println(e);
                sc.next();
            }
        }while(opcion!=4);
         
    }
     //Opcion 1 del menu

    //Genera los datos de todos los especialistas en un dia
     public static void GenerarDatosVisitas(int [][] ramasDeEspecialista,ArrayList<ArrayList<Object>> clientes,ArrayList <int []> visitas){

        for (int especialista = 0; especialista< ramasDeEspecialista.length; especialista ++){
             //Dias
            GenerarCitasXEspecialista(especialista, ramasDeEspecialista[especialista],clientes,visitas);
        }

    }

    //En este metodo se generan las citas por especialista en dos meses
    public static void GenerarCitasXEspecialista(int especialista, int [] ramas, ArrayList<ArrayList<Object>> clientes,ArrayList <int []> visitas){
        boolean urgencia_utilizada = false;
        int numero_citas_semanales=0;
        LocalDate fechaActual = LocalDate.of(2022, 1, 1);
        ArrayList <LocalDate> dias_festivos = calcular_dias_festivos();

        for (int i = 0; i < (ChronoUnit.DAYS.between(fechaActual, LocalDate.of(2022, 12, 31))); i++){
            //Este if comprueba los festivos generales y de cada especialista
        	//En este if estan todos los dias de descanso y fiesta
            if ((especialista==1 && fechaActual.plusDays(i).getDayOfWeek()==DayOfWeek.MONDAY)||((especialista==2 || especialista==3 || especialista==4 || especialista==5) && fechaActual.plusDays(i).getDayOfWeek()==DayOfWeek.FRIDAY) ||fechaActual.plusDays(i).getDayOfWeek()==DayOfWeek.SATURDAY ||fechaActual.plusDays(i).getDayOfWeek()==DayOfWeek.SUNDAY|| dias_festivos.contains(fechaActual.plusDays(i))) {
                System.out.println("El dia "+fechaActual.plusDays(i)+" no se trabaja");
                
            }else{
                    //Hace que siempre haya una urgencia durante la semana, se activa cuando la suma de dias es multiplo de 7
                if (fechaActual.plusDays(i).getDayOfWeek()==DayOfWeek.MONDAY && (i%7)==0 && fechaActual.plusDays(i)!=fechaActual) {
                	//Elige una sola cita de todas las citas que hay para signarla como urgente
                	int dia_urgencia = randomizador(1,numero_citas_semanales);
                    visitas.get(visitas.size()-dia_urgencia)[6]=1;
                    //Cambia el precio dependiendo de la especialidad y si es urgencia
                    if (visitas.get(visitas.size()-dia_urgencia)[2]==0) {
                        visitas.get(visitas.size()-dia_urgencia)[4]=2;
                    } else {
                        visitas.get(visitas.size()-dia_urgencia)[4]=3;
                    }
                }
                int numero_citas_diarias =randomizador(10,15);
                //Acumula el numero de citas de cada semana, para luego tenerlas en cuenta a la hora de asignar la urgencia
                numero_citas_semanales = numero_citas_diarias;
                //Genera las citas de cada dia por especialista
                for (int j = 0; j < numero_citas_diarias; j++) {
                	GenerarCitaAleatoria(especialista, i, ramas,urgencia_utilizada,clientes,visitas);
				}
            }
            
        }

    }

    public static ArrayList <LocalDate> calcular_dias_festivos() {
        ArrayList <LocalDate> dias_festivos= new ArrayList<>();
        dias_festivos.add(LocalDate.of(2022, 1, 1));
        dias_festivos.add(LocalDate.of(2022, 2, 28));
        dias_festivos.add(LocalDate.of(2022, 3, 1));
        dias_festivos.add(LocalDate.of(2022, 4, 1));
        dias_festivos.add(LocalDate.of(2022, 4, 2));
        dias_festivos.add(LocalDate.of(2022, 5, 1));
        dias_festivos.add(LocalDate.of(2022, 8, 15));
        dias_festivos.add(LocalDate.of(2022, 10, 12));
        dias_festivos.add(LocalDate.of(2022, 11, 1));
        dias_festivos.add(LocalDate.of(2022, 12, 6));
        dias_festivos.add(LocalDate.of(2022, 12, 8));
        dias_festivos.add(LocalDate.of(2022, 12, 25));
        
        return dias_festivos;
    }
    //Citas: fecha, especialista, ramas[rama], numeroCliente, precio, cobro, urgencia 
    //Fichas: ID_Cliente - Nombre - Apellido - DNI - Especialista
    public static void GenerarCitaAleatoria(int especialista, int fecha, int [] ramas,boolean urgencia_utilizada,ArrayList<ArrayList<Object>> clientes,ArrayList <int []> visitas){
        int numeroCliente;
        do {
            numeroCliente = randomizador(1, 3000);
        } while (!validarCliente(clientes, numeroCliente, 0, especialista, 4));
        int urgencia=0;
        int cobro = randomizador(0, 2);
        int rama = randomizador(0, ramas.length-1 );
        int precio= ramas[rama];

        int [] cita =  {fecha, especialista, ramas[rama], numeroCliente,  precio, cobro, urgencia };
        visitas.add(cita);
        
    }

    
    //METODOS
    public static void MostrarMenu() {
     
        System.out.println("//MENU//");
        System.out.println("1. Generar los datos de visitas. ");
        System.out.println("2. Mostrar los datos generados. ");
        System.out.println("3. Mostrar resumen. ");
        System.out.println("4. Salir. ");
           
    }   

    public static int randomizador(int desde, int hasta) {
        int numero;
        numero = (int) (Math.floor((hasta - desde + 1) * Math.random()));
        return numero;
    }

    public static String CrearDNI() {
        //primero se genera los 8 numero, despues se para a String y se le aniade la letra final
        int numero = randomizador(100000000, 999999999);
        char [] letras = {'T','R','W','A','G','M','Y','F','P','D','X','B','N','J','Z','S','Q','V','H','L','C','K','E'};
        char letra = letras[numero%23];
        String dni = String.valueOf(numero)+letra;
        return dni;
    }

    //Metodo para comprobar si un ciente es valido
    public static boolean validarCliente(ArrayList<ArrayList<Object>> clientes, int numero_cliente, int posicion_id_cliente, int numeroEspecialista, int posicion_Especialista){
        ArrayList<Object> datoBuscado = buscarDatos(clientes, numero_cliente, posicion_id_cliente);

        if(datoBuscado!=null){
            if(clienteEsValido(datoBuscado, numeroEspecialista, posicion_Especialista)){                
                return true;
            }
        }
        else{
            //crear cliente en BBDD
            crear_fichas(clientes, numeroEspecialista, numero_cliente);            
            return true;
        }
        return false;

    }

    //Metodo para saber si existe en la base de datos
    public static ArrayList<Object> buscarDatos(ArrayList<ArrayList<Object>> clientes, int numero_cliente, int indice){
        
        if (clientes.size()>0){
            for (ArrayList<Object> arrayList : clientes) {           
                if ((int)arrayList.get(indice) == numero_cliente) {
                    return arrayList;
                }
            }
        }
        return null;
    }

    //Metodo para comprobar es valido con determinado especialista
    public static boolean clienteEsValido(ArrayList<Object> cliente, int numeroEspecialista, int indiceEspecialista) {
        if ((int)cliente.get(indiceEspecialista)==numeroEspecialista)
            return true;

        return false;
    }
    //ID_Cliente - Nombre - Apellido - DNI - Especialista
    public static void crear_fichas(ArrayList<Object> clientes, int especialista,int id_cliente) {
        
        //clientes.add;
        
    }

    //Muestra los datos de todas las citas
    public static void mostrar_datos(ArrayList <int []> visitas, String especialista [],String ramas [],int precios [],String cobro [],LocalDate fechaActual) {
        NumberFormat nf = NumberFormat.getCurrencyInstance(Locale.US);
        String [] urgencia={"No es una urgencia","Es una urgencia"};
        for (int[] array : visitas) {
            System.out.println("--------------------------------------------------------------------------------------");
            System.out.println("El cliente " + array[3]+ " en la fecha "+ fechaActual.plusDays(array[0]) +" con el especialista " + especialista[array[1]] + " con la rama " + ramas[array[2]]);
            System.out.println("El importe es de "+ nf.format(precios[array[4]]) + " y se ha pagado con "+ cobro[array[5]]);
            System.out.println(urgencia[array[6]]);
            System.out.println("--------------------------------------------------------------------------------------");
        }
    }
    //Metodo para calcular el resumen de pagos
    public static void calcular_resumen(ArrayList <int []> visitas,int precios []) {
        int [] suma_ganancias=new int [3];
        NumberFormat nf = NumberFormat.getCurrencyInstance(Locale.US);
        for (int[] array : visitas) {
            switch (array[5]) {
                //EFECTIVO
                case 0:
                    suma_ganancias[0]+= precios[array[4]];
                    break;
                //TARJETA
                case 1:
                    suma_ganancias[1]+= precios[array[4]];
                    break;
                //TRANSFERENCIA
                case 2:
                    suma_ganancias[2]+= precios[array[4]];
                    break;
            }
        }
        imprimir_resumen(suma_ganancias, 0, " en efectivo");
        imprimir_resumen(suma_ganancias, 1, " con tarjeta");
        imprimir_resumen(suma_ganancias, 2, " con transferencia");
        System.out.println("--------------------------------------------------------------------------------------");
        System.out.println("En total se ha pagado "+nf.format((suma_ganancias[0]+suma_ganancias[1]+suma_ganancias[2])));

    }

    public static void imprimir_resumen(int [] suma_ganancias,int tipo_de_pago, String texto) {
        NumberFormat nf = NumberFormat.getCurrencyInstance(Locale.US);
        System.out.println("Se han cobrado "+nf.format(suma_ganancias[tipo_de_pago])+texto);
    }

    //Metodo para calcular la mensualidad de cada especialista
    //fecha, especialista, ramas[rama], numeroCliente, precio, cobro, urgencia 
    public static void calcular_mensualidades(ArrayList <int []> visitas,int precios [], String especialista []) {
        String [] meses = {"marzo","abril"};
        double[][]mensualidades= new double [2][3];
        LocalDate fechaActual = LocalDate.of(2022, 1, 1);
        NumberFormat nf = NumberFormat.getCurrencyInstance(Locale.US);
        for (int[] array : visitas) {
            switch (array[1]) {
                //Maria
                case 0:
                    if(fechaActual.plusDays(array[0]).getMonth().equals(Month.MARCH)){
                        mensualidades[0][array[1]]+= precios[array[4]];
                    }else{
                        mensualidades[1][array[1]]+= precios[array[4]];
                    }
                    break;
                //JUAN
                case 1:
                
                    if(fechaActual.plusDays(array[0]).getMonth().equals(Month.MARCH)){
                        mensualidades[0][array[1]]+= precios[array[4]];
                    }else{
                        mensualidades[1][array[1]]+= precios[array[4]];
                    }
                    break;
                //PACO
                case 2:
                   
                    if(fechaActual.plusDays(array[0]).getMonth().equals(Month.MARCH)){
                        mensualidades[0][array[1]]+= precios[array[4]];
                    }else{
                        mensualidades[1][array[1]]+= precios[array[4]];
                    }
                    break;
            }
        }
        //Imprime el salario de cada mes
        for (int i = 0; i < mensualidades.length; i++) {
            for (int n = 0; n < mensualidades[0].length; n++) {
                mensualidades[i][n]=(mensualidades[i][n]*0.15);
                System.out.println("El mes de "+meses[i]+", el especialista "+especialista[n]+" ha cobrado "+nf.format(mensualidades[i][n]));
            }
        } 
    }
}