import java.util.ArrayList;
import java.util.Scanner;
import java.time.*;
import java.time.temporal.ChronoUnit;
//ESTO ES LO QUE PIDE EN LA SEGUNDA PARTE, PERO ME HE DADO CUENTA A ULTIMA HORA QUE HAY VARIAS ERRORES EN EL DISEÑO Y ESO IMPLICARÍA RESTRUCTURAR EL PROGRAMA POR COMPLETO
//ESTO ME HA OCURRIDO A LA HORA DE GENERAR LAS FICHAS DE CLIENTES
//HE INTENTADO ARREGLARLO EN UN PERIODO MUY CORTO DE TIEMPO PERO ERA DEMASIADO LIOSO Y LE VOY A ENTREGAR LA VERSION BASICA QUE NO TIENE EN CUENTA LAS FICHAS DE CLIENTES
//PERDONAME LA VIDA PORFAVOR ^^'
public class Programa_clinica {
    //MAIN
    public static void main(String[] args) {
      
        String especialista [] = {"Maria", "Juan", "Paco"} ;                //especialistas: Array de 3 strings, Maria, Juan y Paco 
        String ramas [] = {"Nutricion", "Fisioterapia"};                    //ramas: Array de 2 Strings, Nutrición y Fisioterapia                  
        int precios [] = {40, 50};                                          //precios: Array de enteros: 40 y 50 euros 
        String cobro [] = {"Efectivo", "Tarjeta", "Transferencia"};         //formas de cobro: Array de 3 Strings, Efectivo, Tarjeta y Transferencia 
        int ramasDeEspecialista [][] = {{0,1}, {0}, {1}};                   //Ramas de cada especialista: Matriz de enteros con los índices (según array de ramas) que atiende cada especialista
        ArrayList <int []> visitas = new ArrayList<>();                     //ArrayList que permita almacenar las visitas generadas//
        LocalDate fechaActual = LocalDate.of(2022, 3, 1);
        
        Scanner sc = new Scanner(System.in);
        int opcion = 0;
        
        MostrarMenu();

        do{
            try {
                opcion = sc.nextInt();
                switch(opcion){
                    case 1:
                        
                        visitas=GenerarDatosVisitas(ramasDeEspecialista);
                        System.out.println("DATOS GENERADOS!!!");
                        MostrarMenu();
                    break;
                    case 2:
                        //Mostrar datos generados;
                        System.out.print("//Mostrar datos generados!!!//");
                        mostrar_datos(visitas,especialista,ramas,precios,cobro,fechaActual);
                        MostrarMenu();
                    break;
                    case 3:
                        //Mostrar resumen
                        System.out.print("//Mostrar resumen!!!//");
                        mostrar_resumen();
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

    public static int[] GenerarCitaAleatoria(int especialista, int fecha, int [] ramas,boolean urgencia_utilizada){
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

    public static String CrearDNI() {
        //primero se genera los 8 numero, despues se para a String y se le aÃ±ade la letra final
        int numero = randomizador(100000000, 999999999);
        char [] letras = {'T','R','W','A','G','M','Y','F','P','D','X','B','N','J','Z','S','Q','V','H','L','C','K','E'};
        char letra = letras[numero%23];
        String dni = String.valueOf(numero)+letra;
        return dni;
    }

    //Metodo para saber si existe en la base de datos
    public static boolean buscarDatos(ArrayList<ArrayList<Object>> clientes, int numero_cliente, int indice){
        
        for (ArrayList<Object> arrayList : clientes) {
            
            if ((int)arrayList.get(indice) == numero_cliente) {
                return true;
            }
        }

        return false;
    }
    //Muestra los datos de todas las citas
    public static void mostrar_datos(ArrayList <int []> visitas, String especialista [],String ramas [],int precios [],String cobro [],LocalDate fechaActual) {
        for (int[] array : visitas) {
            System.out.println("El cliente " + array[3]+ " en la fecha "+ fechaActual.plusDays(array[0]) +" con el especialista " + especialista[array[1]] + " con la rama " + ramas[array[2]]);
            System.out.println("El importe es de "+ precios[array[4]]+ " y se ha pagado con "+ cobro[array[5]]);
        }
    }

    public static void mostrar_resumen() {
        //Muestra el resumen
    }

   
}