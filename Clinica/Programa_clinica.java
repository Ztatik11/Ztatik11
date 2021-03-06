import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Collections;
import java.util.Scanner;
import java.time.*;
import java.time.temporal.ChronoUnit;
import java.lang.reflect.Array;
import java.text.Collator;
import java.text.NumberFormat;
import java.util.Locale;
import persona.*;

public class Programa_clinica {
    //MAIN
    public static void main(String[] args) {
      
                                                                                                    
        especialista [] especialitas;                                                      
        ArrayList <citas> visitas = new ArrayList<>();                                             
        LocalDate fechaActual = LocalDate.of(2022, 1, 1);
        ArrayList<paciente> clientes = new ArrayList<>();
        ArrayList<paciente> gente_nacida_febrero = new ArrayList<>();
        ArrayList<Object> conteo_fechas = new ArrayList<>();
        ArrayList<paciente> mujeres = new ArrayList<>();
        

        Scanner sc = new Scanner(System.in);
        int opcion = 0;
        //CREACION DE ESPECIALISTAS
        especialitas = crear_especialista();
        //CREACION DE CLIENTES//He pensado que al arrancar el programa se crean 3000 clientes iniciales y todos eston clientes no tienen asignado ningun especialista
        //se asiganaran cuando tengan su primera cita
        for (int i = 0; i < 3000; i++) {
            int genero = randomizador(1, 10) < 5 ? 0 : 1;
            paciente paciente_actual = crear_ficha(clientes, (i+1),genero,gente_nacida_febrero,mujeres);
            clientes.add(paciente_actual);
        }
        
        MostrarMenu();

        do{
            try {
                opcion = sc.nextInt();
                switch(opcion){
                    case 1:
                        visitas.clear();
                        gente_nacida_febrero.clear();
                        conteo_fechas.clear();
                        GenerarDatosVisitas(especialitas,clientes,visitas,gente_nacida_febrero,mujeres);
                        System.out.println("DATOS GENERADOS!!!");
                        ContadorFechas(conteo_fechas, clientes);
                        MostrarMenu();
                    break;
                    case 2:
                        //Mostrar datos generados;
                        if (visitas.size()>0) {
                            System.out.println("//Mostrar datos generados!!!//");
                            mostrar_datos(visitas,fechaActual);
                            MostrarMenu();
                        } else {
                            System.out.println("NO HAY DATOS CREADOS");
                            MostrarMenu();
                        }
                        
                    break;
                    case 3:
                        //Mostrar resumen
                        if (visitas.size()>0) {
                            System.out.println("//Mostrar resumen!!!//");
                            calcular_resumen(visitas);
                            calcular_mensualidades(especialitas);
                            MostrarMenu();
                        } else {
                            System.out.println("NO HAY DATOS CREADOS");
                            MostrarMenu();
                        }
                        
                    break;

                    case 4:
                        //Mostrar gente nacida en febrero
                        if (gente_nacida_febrero.size()>0) {
                            System.out.println("//Mostrar gente nacida en febrero!!!//");
                            System.out.println("Tenemos "+gente_nacida_febrero.size()+" nacida el 29 febrero");
                            for (paciente paciente : gente_nacida_febrero) {
                                System.out.println("El paciente "+paciente.getNombre()+" "+paciente.getApellido_1()+" "+paciente.getApellido_2()+" ha nacido un 29 de febrero");
                            }
                            MostrarMenu();
                        } else {
                            System.out.println("NO HA NACIDO GENTE EL 29 DE FEBRERO");
                            MostrarMenu();
                        }
                        
                    break;

                    case 5:
                        //IMPRIMIR CONTEO DE NACIMIENTOS!!!
                        if (conteo_fechas.size()>0) {
                            System.out.println("//IMPRIMIR CONTEO DE NACIMIENTOS!!!//");
                            opcion_mas_menos_fechas(conteo_fechas);

                            MostrarMenu();
                        } else {
                            System.out.println("NO HA NACIDO GENTE EL 29 DE FEBRERO");
                            MostrarMenu();
                        }
                        
                    break;
                        case 6:
                        //IMPRIMIR CONTEO DE NACIMIENTOS!!!
                        if (mujeres.size()>0) {
                            System.out.println("//IMPRIMIR MUJERES!!!//");

                            //Ordena las mujeres por edad/apellidos/nombre
                            Collections.sort(mujeres,new ordenar_mujeres());
                            //Borra todas las mujeres hasta que queden las 10 mujeres mas jovenes
                            while (mujeres.size()>10) {
                                mujeres.remove(10);
                            }
                            //Muestra las mujeres
                            for (paciente paciente : mujeres) {
                                System.out.println("//Nombre: "+paciente.getNombre()+"//Apellidos: "+paciente.getApellido_1()+" "+paciente.getApellido_2()+"//Fecha Nacimiento: "+paciente.getFechaNacimiento()+"//Edad: "+ ChronoUnit.YEARS.between(paciente.getFechaNacimiento(),LocalDate.now()));
                            }
                            

                            MostrarMenu();
                        } else {
                            System.out.println("NO HAY MUJERES EN LA CLINICA");
                            MostrarMenu();
                        }
                        break;

                    case 7:
                        //Salir
                        System.out.println("//Nos vemos!!!//");
                    break;
                    default:
                        System.out.println("//Introduce un numero valido//");
                        MostrarMenu();
                    break;
                }
            } catch (Exception e) {
                System.out.println("//Debes insertar un numero//");
                e.printStackTrace();
            }
        }while(opcion!=7);
    }

    public static especialista []  crear_especialista() {
        //ID DE RAMA/ NOMBRE DE RAMA / PRECIO DE RAMA
        rama r0 = new rama(0, "Nutricion", 40);
        rama r1 = new rama(1, "Fisioterapia", 50);
        //NOMBRE DE LOS ESPECIALISTAS
        String nombres_especialistas [] = {"Maria", "Juan", "Paco","Elena","Pepe","David"} ;  
        especialista[] especialistas = new especialista [nombres_especialistas.length];
        //RAMAS DE CADA ESPECIALISTA
        rama ramasDeEspecialista [][] = {{r0,r1}, {r0}, {r1},{r0,r1},{r0,r1},{r1}};
        //DIAS LIBRES DE CADA ESPECIALISTA
        DayOfWeek [] dias_libres = {DayOfWeek.FRIDAY,DayOfWeek.MONDAY,null,DayOfWeek.FRIDAY,DayOfWeek.FRIDAY,DayOfWeek.FRIDAY};
        //FECHAS DE NACIMIENTO DE CADA ESPECIALISTA
        LocalDate [] fechas_nacimiento = {LocalDate.of(1994, 2 , 9),LocalDate.of(1980, 10 , 13),LocalDate.of(1990, 7 , 21),LocalDate.of(2000, 10 , 10),LocalDate.of(1999, 1 , 27),LocalDate.of(1970, 4 , 9)};
        //0=MUJER 1=HOMBRE
        int [] genero={0,1,1,0,1,1};
        //CREACION DE ESPECIALISTAS
        for (int i = 0; i < nombres_especialistas.length; i++) {
            especialistas[i]= new especialista(i, ramasDeEspecialista[i], dias_libres[i], nombres_especialistas[i], fechas_nacimiento[i], genero[i]);
        }   
        return especialistas;
    }
     //Opcion 1 del menu
    //Metodo que enumera las fechas y su cantidad de repeticiones
    public static void ContadorFechas(ArrayList <Object> fechasContadas, ArrayList<paciente> pacientes){
        for (paciente paciente : pacientes) {
            if(fechasContadas.contains(paciente.getCumpleanyos())){
            	// Obtiene el indice del objeto que contiene la fecha
               int posicion = fechasContadas.indexOf(paciente.getCumpleanyos())+1;
               	//Enumera la cantidad de veces que aparece la fecha
               int repeticion = (int) fechasContadas.get(posicion);

               fechasContadas.set(posicion, repeticion + 1);
            }else{
                fechasContadas.add(paciente.getCumpleanyos());
                fechasContadas.add(1);
            }
        }
    }

    //GENERA LAS CITAS DE UN ESPECIALISTA DURANTE TODO EL ANYO
     public static void GenerarDatosVisitas(especialista[] especialistas, ArrayList<paciente> clientes, ArrayList <citas> visitas, ArrayList<paciente> gente_nacida_en_febrero, ArrayList<paciente> mujeres ){

        for (int especialista = 0; especialista< especialistas.length; especialista ++){
             //Dias
            GenerarCitasXEspecialista(especialistas[especialista],clientes,visitas,gente_nacida_en_febrero,mujeres);
        }

    }

    //En este metodo se generan las citas por especialista en dos meses
    public static void GenerarCitasXEspecialista(especialista especialista, ArrayList<paciente> clientes,ArrayList <citas> visitas,ArrayList<paciente> gente_nacida_en_febrero, ArrayList<paciente> mujeres){
        //NECESARIO PARA CALCULAR LAS URGENCIAS DE CADA SEMANA
        int numero_citas_semanales=0;
        LocalDate fechaActual = LocalDate.of(2022, 1, 1);
        //LLEVA LA CUENTA NECESARIA PARA CREAR UN CLIENTE NUEVO
        Month month_old = null;

        //CONTADOR DE DIAS
        for (int i = 0; i < (ChronoUnit.DAYS.between(fechaActual, LocalDate.of(2022, 12, 31))); i++){
            //DIAS LIBRES
            if (especialista.diaNoLaborable(calcular_dias_festivos(), fechaActual.plusDays(i))) {
                System.out.println("El dia "+fechaActual.plusDays(i)+" no se trabaja");
            }else{
                //SET URGENCIA
                if (fechaActual.plusDays(i).getDayOfWeek()==DayOfWeek.MONDAY && fechaActual.plusDays(i)!=fechaActual && visitas.size()>0) {
                	citas urgencia = visitas.get(randomizador((visitas.size()-numero_citas_semanales), (visitas.size()-1)));
                    urgencia.setUrgencia(1);
                    numero_citas_semanales=0;
                    especialista.sumarBeneficio(urgencia.getRama().getPrecioBase()*0.2);

                }
                int numero_citas_diarias =randomizador(10,15);
                //Acumula el numero de citas de cada semana, para luego tenerlas en cuenta a la hora de asignar la urgencia
                numero_citas_semanales = numero_citas_diarias;
                //Genera las citas de cada dia por especialista
                for (int j = 0; j < numero_citas_diarias; j++) {
                    paciente paciente = null;
                    boolean pacienteOK = false;
                    //SE SELECCIONA UN CLIENTE DE LOS YA CREADOS Y SE COMPRUEBA SI NO TIENE UN ESPECIALISTA ASIGNADO O SI ES EL ESPECIALISTA CORRECTO
                    while(!pacienteOK){
                        paciente = clientes.get(randomizador(0, clientes.size()-1));
                        if (paciente.especialistaValido(i)){
                            pacienteOK = true;
                        }
                    }
                	visitas.add(new citas(i, especialista, paciente));
                    //A LA HORA DE CALCULAR LOS INGRESOS MENSUALES DE CADA ESPCIALISTA, ESTE IF SE ENCARGA DE CAMBIAR DE MES EN UN ARRAY QUE TENEMOS DENTRO DEL OBJETO DE ESPECISLITA 
                    if( month_old == null || month_old!=fechaActual.plusDays(i).getMonth()){
                        month_old = fechaActual.plusDays(i).getMonth();
                        especialista.nuevoMes(fechaActual.plusDays(i).getMonth());
                    }
                    //SUMA LOS BENEFICIOS DEL MES
                    especialista.sumarBeneficio(visitas.get(visitas.size()-1).precioCita());

                    if (randomizador(1, 200)==200) {
                        crear_ficha(clientes, (clientes.size()+1), randomizador(1, 10) < 5 ? 0 : 1,gente_nacida_en_febrero,mujeres);
                    }
				}
            }
        }

    }
    //CREA EL ARRAYLIST CON LOS DIAS FESTIVOS 
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

    //MOSTRAR MENU
    public static void MostrarMenu() {
     
        System.out.println("//MENU//");
        System.out.println("1. Generar los datos de visitas. ");
        System.out.println("2. Mostrar los datos generados. ");
        System.out.println("3. Mostrar resumen. ");
        System.out.println("4. Clientes nacidos en febrero. ");
        System.out.println("5. Mostrar resumen. ");
        System.out.println("6. Ordenar mujeres mas jovenes");
        System.out.println("7. Salir. ");
           
    }   
    //METODO PARA OBTENER UN NUMERO ALEATORIO
    public static int randomizador(int desde, int hasta) {
        int numero;
        numero = (int) (Math.floor((hasta - desde + 1) * Math.random()));
        return numero;
    }


    //CREA LA FICHA DE LOAS PACIENTES
    public static paciente crear_ficha(ArrayList<paciente> clientes,int id_cliente, int genero,ArrayList<paciente> gente_nacida_en_febrero, ArrayList<paciente> mujeres) {
        paciente nuevoPaciente  = null;
        boolean clienteValido = false;
        

        while(!clienteValido){
            nuevoPaciente  = new paciente(id_cliente, genero);
            clienteValido = clienteValido(nuevoPaciente, clientes);
        }

        if (nuevoPaciente.getFechaNacimiento().getMonthValue()==2 && nuevoPaciente.getFechaNacimiento().getDayOfMonth()==29) {
            gente_nacida_en_febrero.add(nuevoPaciente);
        }
        if (nuevoPaciente.getGenero()==0) {
            mujeres.add(nuevoPaciente);
        }

        return nuevoPaciente;
    }

    //VALIDA EL CLIENTE
    public static boolean clienteValido(paciente nuevoPaciente, ArrayList<paciente> clientes ){
        for (paciente paciente : clientes) {
            if(mismoCliente(paciente, nuevoPaciente)){
                return false;                
            }
        }          
        return true;
    }

    //COMPRUEBA SI EL CLIENTE YA EXISTE
    public static boolean mismoCliente(paciente paciente1, paciente paciente2){
    	
    	if(ChronoUnit.YEARS.between(paciente1.getFechaNacimiento(), paciente2.getFechaNacimiento())>=10)
            return false;

        if(paciente1.getApellido_1()!=paciente2.getApellido_1())
            return false;
        
        if(paciente1.getApellido_2()!=paciente2.getApellido_2())
            return false;

        if(paciente1.getNombre()!=paciente2.getNombre())
            return false;

        return true;   
    }

    //MUESTRA LOS DATOS DE LAS CITAS
    public static void mostrar_datos(ArrayList <citas> visitas,LocalDate fechaActual) {
        NumberFormat nf = NumberFormat.getCurrencyInstance(Locale.US);
        for (citas visita : visitas) {
            System.out.println("--------------------------------------------------------------------------------------");
            System.out.println("El cliente " + visita.nombrePaciente() + " en la fecha "+ fechaActual.plusDays(visita.getFecha()) +" con el especialista " + visita.nombreEspecialista() + " con la rama " + visita.nombreRama());
            System.out.println("El importe es de "+ nf.format(visita.precioCita()) + " y se ha pagado con "+ visita.nombreCobro());
            System.out.println(visita.esUrgencia());
            System.out.println("--------------------------------------------------------------------------------------");
        }
    }

    //METODO PARA CALCULAR EN EL RESUMEN
    public static void calcular_resumen(ArrayList <citas> visitas) {
        double [] suma_ganancias=new double [3];
        NumberFormat nf = NumberFormat.getCurrencyInstance(Locale.US);
        for (citas visita : visitas) {
            suma_ganancias[visita.getCobro()] += visita.precioCita();
        }
        imprimir_resumen(suma_ganancias[0], " en efectivo");
        imprimir_resumen(suma_ganancias[1], " con tarjeta");
        imprimir_resumen(suma_ganancias[2], " con transferencia");
        System.out.println("--------------------------------------------------------------------------------------");
        System.out.println("En total se ha pagado "+nf.format((suma_ganancias[0]+suma_ganancias[1]+suma_ganancias[2])));

    }


    public static void imprimir_resumen(double suma_ganancias, String texto) {
        NumberFormat nf = NumberFormat.getCurrencyInstance(Locale.US);
        System.out.println("Se han cobrado "+nf.format(suma_ganancias)+texto);
    }

    //METODO PARA CALCULAR LAS MENSUALIDADES DE DE CADA MES POR ESPECIALISTA
    public static void calcular_mensualidades(especialista [] especialista) {
        NumberFormat nf = NumberFormat.getCurrencyInstance(Locale.US);
       for (especialista profesional : especialista) {
           for (int i = 0; i < profesional.getMensualidad().size(); i++) {
               System.out.println("El profesional "+profesional.getNombre()+" "+profesional.getApellido_1()+" ha ganado "+ nf.format(profesional.getMensualidad().get(i)) + " en el mes de " + profesional.getMesesMensualidad().get(i));
           }
       }
    }

    public static void opcion_mas_menos_fechas(ArrayList<Object> conteo_fechas) {
        int fecha_mayor=0;
        int fecha_menor=9999;
        ArrayList<String> listaFechasMayores = new ArrayList<>();
        ArrayList<String> listaFechasMenores = new ArrayList<>();
        for (int i = 1; i < conteo_fechas.size(); i+=2) {
            if (fecha_mayor < (int)conteo_fechas.get(i)) {
                fecha_mayor = (int)conteo_fechas.get(i);
                listaFechasMayores.clear();
                listaFechasMayores.add((String)conteo_fechas.get(i-1));
            }
            else if (fecha_mayor == (int)conteo_fechas.get(i)){
                listaFechasMayores.add((String)conteo_fechas.get(i-1));
            }

            if (fecha_menor > (int)conteo_fechas.get(i)) {
                fecha_menor = (int)conteo_fechas.get(i);
                listaFechasMenores.clear();
                listaFechasMenores.add((String)conteo_fechas.get(i-1));
            }
            else if(fecha_menor == (int)conteo_fechas.get(i)){
                listaFechasMenores.add((String)conteo_fechas.get(i-1));
            }
        }
        System.out.println("Los dias en los que han nacido mas personas son en los dias:");
        for (int i = 0; i < listaFechasMayores.size(); i++) {
            System.out.print(", "+listaFechasMayores.get(i));
        }
        System.out.println(" con "+fecha_mayor+" nacimientos.");

        System.out.print("Los dias en el que han nacido menos personas son en los dias:");
        for (int i = 0; i < listaFechasMenores.size(); i++) {
            System.out.print(", "+listaFechasMenores.get(i));
        }
        System.out.println(" con "+fecha_menor+" nacimientos.");
    }
}