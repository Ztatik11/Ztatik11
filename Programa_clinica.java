import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

import javax.sound.midi.Sequencer.SyncMode;

import java.time.*;
import java.time.temporal.ChronoUnit;
//EL TRABAJO LO VOY A HACER SOLO
public class Programa_clinica {
    //MAIN
    public static void main(String[] args) {
       /*2022.02.26 comentar declaraciones anteriores
        ArrayList <ArrayList> citas = new ArrayList <ArrayList> ();
        ArrayList <ArrayList> fichaClientes = new ArrayList <ArrayList> ();
        /Aqui estaran los datos de cada especialista(no esta terminado)
        Oject especialistas[] = new Object [3];
        //Aqui se pondran los nombres/appelidos aleatorios
        String nombresAleatorios[][] = new String [2][20];
        int id_cliente = 0;
        
        boolean urgencias = false;
        //He supuesto que la base de datos se rellena al empezar el programa y el menu es para consultarla*/

        /*26.02.2022 La declaración de las variables, array y arralist 
        necesarios para atender los requisitos del problema anterior.*/
        String especialista [] = {"Maria", "Juan", "Paco"} ;                //especialistas: Array de 3 strings, Maria, Juan y Paco 
        String ramas [] = {"Nutricion", "Fisioterapia"};                    //ramas: Array de 2 Strings, Nutrición y Fisioterapia                  
        int precios [] = {40, 50};                                          //precios: Array de enteros: 40 y 50 euros 
        String cobro [] = {"Efectivo", "Tarjeta", "Transferencia"};         //formas de cobro: Array de 3 Strings, Efectivo, Tarjeta y Transferencia 
        int ramasDeEspecialista [][] = {{0,1}, {0}, {1}};                   //Ramas de cada especialista: Matriz de enteros con los índices (según array de ramas) que atiende cada especialista
        ArrayList <int []> visitas = new ArrayList<>();                     //ArrayList que permita almacenar las visitas generadas//
        
        
        Scanner sc = new Scanner(System.in);
        int opcion = 0;
        
        MostrarMenu();

        do{
            try {
                opcion = sc.nextInt();
                switch(opcion){
                    case 1:
                        
                        //visitas=GenerarDatosVisitas(ramasDeEspecialista);
                        System.out.println("DATOS GENERADOS!!!");
                        //int[] GenerarCitaAleatoria( int especialista, int fecha, int [] ramas)
                        /*int citaAleatoria[] = GenerarCitaAleatoria(2, 2, ramasDeEspecialista[2]);
                        for (int i = 0; i < citaAleatoria.length; i++) {
                            System.out.println(citaAleatoria[i]);
                        }*/
                        
                        System.out.println("DATOS GENERADOS!!!");
                        MostrarMenu();
                    break;
                    case 2:
                        //Mostrar datos generados;
                        System.out.print("//Mostrar datos generados!!!//");
                        MostrarMenu();
                    break;
                    case 3:
                        //Mostrar resumen
                        System.out.print("//Mostrar resumen!!!//");
                        MostrarMenu();
                    break;
                    case 4:
                        //Salir
                        System.out.print("//Nos vemos!!!//");
                        MostrarMenu();
                    break;
                    default:
                        System.out.println("//Introduce un numero valido//");
                        MostrarMenu();
                    break;
                }
            } catch (Exception e) {
                System.out.println("//Debes insertar un numero//");
                sc.next();
            }
        }while(opcion!=4);
        
        
        
        
    }
     //Opcion 1 del menu

        

    //Genera los datos de todos los especialistas en un dia
     public static ArrayList <int []> GenerarDatosVisitas(int [][] ramasDeEspecialista ){
        ArrayList <int []> datosVisitas = new ArrayList<>();
      

        for (int especialista = 0; especialista< 3; especialista ++){
             //Dias
             datosVisitas.addAll( GenerarCitasXEspecialista(especialista, ramasDeEspecialista[especialista]));
        }
        return datosVisitas;
    }

    //En este metodo se generan las citas por especialista en dos meses
    public static ArrayList<int[]> GenerarCitasXEspecialista(int especialista, int [] ramas){
        ArrayList <int []> datosVisitas = new ArrayList<>();
        boolean urgencia_utilizada = false;

        LocalDate fechaActual = LocalDate.of(2022, 3, 1);
        for (int i = 0; i < (ChronoUnit.DAYS.between(fechaActual, LocalDate.of(2022, 4, 30))); i++){
            //Este if comprueba los festivos generales y de cada especialista
            if ((especialista==1 && fechaActual.plusDays(i).getDayOfWeek()==DayOfWeek.MONDAY)||(especialista==2 && fechaActual.plusDays(i).getDayOfWeek()==DayOfWeek.FRIDAY) || fechaActual.plusDays(i)==LocalDate.of(2022, 3, 1)||fechaActual.plusDays(i)==LocalDate.of(2022, 4, 1)||fechaActual.plusDays(i)==LocalDate.of(2022, 4, 2)) {
                System.out.println("El dia "+fechaActual.plusDays(i)+" no se trabaja");
            }else{
                    //Hace que siempre haya una urgencia durante la semana 
                if (urgencia_utilizada == false && fechaActual.plusDays(i).getDayOfWeek()==DayOfWeek.MONDAY && fechaActual.plusDays(i)!=fechaActual) {
                    datosVisitas.get(datosVisitas.size()-1)[6]=1;
                    datosVisitas.get(datosVisitas.size()-1)[4]=1;
                }
                //Comprueba que dia es hoy para comprobar que la urgencia se puede volver a utilizar
                if (fechaActual.plusDays(i).getDayOfWeek()==DayOfWeek.MONDAY) {
                    urgencia_utilizada=false;
                }

                for (int j = 0; j < randomizador(10, 15); j++){
                    
                    datosVisitas.addAll( GenerarCitasXEspecialistaXDia(especialista, i, ramas,urgencia_utilizada));
                    //Comprueba si en la ultima cita ha habido nuna urgencia
                    if (datosVisitas.get(datosVisitas.size()-1)[6]==1) {
                        urgencia_utilizada=true;
                    }     
                }
            }
            
        }
        return datosVisitas;

    }

    //En este netidi se generan las citas por especialista en un unico dia
    public static ArrayList<int[]> GenerarCitasXEspecialistaXDia(int especialista, int dia, int [] ramas,boolean urgencia_utilizada){
        ArrayList <int []> datosVisitas = new ArrayList<>();

        datosVisitas.add(GenerarCitaAleatoria(especialista, dia, ramas,urgencia_utilizada));

        return datosVisitas;
    }

    public static int[] GenerarCitaAleatoria( int especialista, int fecha, int [] ramas,boolean urgencia_utilizada){
        int urgencia;
        int numeroCliente = randomizador(1, 3000);
        if (urgencia_utilizada = false) {
            urgencia = randomizador(0, 1);
        }else{
            urgencia=0;
        }
        int cobro = randomizador(0, 2);
        int precio= urgencia;
        int rama = randomizador(0, ramas.length-1 );

        int [] cita =  {fecha, especialista, ramas[rama], numeroCliente,  precio, cobro, urgencia };
        
        return cita ;

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

    public static ArrayList CreacionFichaClientes(ArrayList ficha_Clientes) {
        //Rellena la ficha de los pacientes
        ArrayList ficha = new ArrayList <>();
        return ficha_Clientes;
    }

    public static ArrayList CreacionDeCitas(ArrayList ficha_Clientes) {
        ArrayList cita = new ArrayList <>();
        //Genera citas aleatoriamente
        //Llamar al metodo random para generar el numero de citas
        //El resultado del numero de citas es el indice de un for
        //Dentro del for llamamos al metodo para crear un DNI
        //Se comprueba si el DNI esta dentro de la base de datos de los clientes 
        return ficha_Clientes;
    }

    public static String CrearDNI() {
        //primero se genera los 8 numero, despues se para a String y se le aÃ±ade la letra final
        int numero = randomizador(100000000, 999999999);
        char [] letras = {'T','R','W','A','G','M','Y','F','P','D','X','B','N','J','Z','S','Q','V','H','L','C','K','E'};
        char letra = letras[numero%23];
        String dni = String.valueOf(numero)+letra;
        return dni;
    }



   
}