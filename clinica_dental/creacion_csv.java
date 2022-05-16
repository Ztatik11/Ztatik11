import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Statement;
import java.util.Scanner;
import java.time.format.DateTimeFormatter;
import java.nio.charset.StandardCharsets;
import java.io.FileInputStream;
import persona.*;
import servicios.*;


public class creacion_csv {
    public static void main(String[] args) {
        // Conexion
        String db_ = "dentista";
        String login_ = "root";
        String password_ = "";
        String url_ = "jdbc:mysql://127.0.0.1/" + db_;
        Connection connection_;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection_ = DriverManager.getConnection(url_, login_, password_);

            System.out.println("Conexion a base de datos " + db_ + " correcta.");
            //crea las tablas
                        
            creacion_tabla(connection_,	"pacientes","ID int NOT NULL AUTO_INCREMENT,Nombre char(100),Apellidos char(200),Fecha_nacimiento date,Telefono int,Genero char(10),DNI char(9),primary key(ID)");
            creacion_tabla(connection_, "profesionales", "ID int NOT NULL AUTO_INCREMENT,Nombre char(100),Apellidos char(200),Telefono int,Genero char(10),NIF char(9),Comision int,primary key(ID)");
            creacion_tabla(connection_, "familias", "cod_familia char(100),nombre_familia char(100),primary key(cod_familia)");
            creacion_tabla(connection_, "tratamientos", "cod_familia char(100),codigo_tratamiento char(100),nombre_tratamiento char(100),precio int,primary key(codigo_tratamiento),Constraint fk_tratamiento_familia foreign key (cod_familia) references familias(cod_familia)");
            creacion_tabla(connection_, "liquidacion", "ID int auto_increment,fecha_liquidacion date,id_profesional int,comision int,primary key(id),Constraint fkprofesional foreign key (id_profesional) references Profesionales(ID)");
            creacion_tabla(connection_, "tratamientos_realizados", "ID_servicio int auto_increment,Fecha date,IDpaciente int,IDprofesional int,IDtratamiento char(10),IDliquidacion int,Precio decimal(18,2),Cobrado decimal(18,2),primary key(ID_servicio),Constraint fkpaciente foreign key (IDpaciente) references pacientes(ID),Constraint fkespecialista foreign key (IDprofesional) references profesionales(ID),Constraint fktratamiento foreign key (IDtratamiento) references tratamientos (codigo_tratamiento),Constraint fkliquidacion foreign key (IDliquidacion) references liquidacion(ID)");
            //creacion_tabla(connection_, "grupo_caja", "ID int auto_increment,nombre char(100),primary key(ID)");
            creacion_tabla(connection_, "forma_cobro", "ID int auto_increment,nombre char(50),primary key(ID)");
            creacion_tabla(connection_, "cobros", "ID int auto_increment,Fecha date,IDpaciente int,IDformacobro int,Cobrado decimal(18,2),primary key(ID),Constraint fkpa foreign key (IDpaciente) references pacientes(ID),Constraint fkcob foreign key (IDformacobro) references forma_cobro(ID)");
            creacion_tabla(connection_, "servicios_cobros", "IDcobro int,IDservicio int,primary key(IDcobro,IDservicio),Constraint fkIDcobro foreign key (IDcobro) references cobros(ID),Constraint fkIDservicio foreign key (IDservicio) references tratamientos_realizados(ID_servicio)");
            imprimir_menu(connection_);
            connection_.close();

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static void menu(Connection connection_) {
    	Scanner sc = new Scanner(System.in);
        int opcion = 0;
    	do{
            try {
                opcion = sc.nextInt();
                switch(opcion){
                    case 1:

                    	if(leer_resultset_int((realizar_consulta("Select max(ID) as ID from pacientes",connection_)),"ID") == 0) {
                    		//Crea los csv
                        	escribir_csv(asignar_arrays("hombres.txt"), asignar_arrays("mujeres.txt"), asignar_arrays("apellidos.txt"),"profesionales.csv",5,false);
                            escribir_csv(asignar_arrays("hombres.txt"), asignar_arrays("mujeres.txt"), asignar_arrays("apellidos.txt"),"pacientes.csv",3000,true);
                            insertar_fichero_csv(leer_csv("profesionales.csv"), "profesionales","Nombre,Apellidos,Telefono,Genero,NIF,Comision", connection_, false);
                            insertar_fichero_csv(leer_csv("formas_cobro.csv"), "forma_cobro","nombre", connection_, false);
                            insertar_fichero_csv(leer_csv("pacientes.csv"), "pacientes","Nombre ,Apellidos ,Fecha_nacimiento ,Telefono ,Genero,DNI", connection_, false);
                            insertar_fichero_csv(leer_csv("familias.csv"), "familias", "cod_familia,nombre_familia", connection_, true);
                            insertar_fichero_csv(leer_csv("Tratamientos.csv"), "tratamientos", "cod_familia,codigo_tratamiento,nombre_tratamiento,precio", connection_, true);
                            System.out.println("Base de datos rellena");
                    	} else {
                    		System.out.println("Ya se habia rellenado la base de datos con anterioridad");
                    	}
                        
                    break;
                    case 2:
                    	
                    	menu_insertar_servicio(connection_);

                    break;
                    case 3:
                    	String [] atributos= {"ID_servicio","Fecha","IDpaciente","IDprofesional","IDtratamiento","IDliquidacion","Precio","Cobrado"};
                    	String [] enunciados= {"Introduzca el ID de la consulta","Desea cambiar la fecha?","Desea cambiar el ID del paciente?","Desea cambiar el ID del profesional?","Desea cambiar el ID del tratamiento?","Desea cambiar el ID de la liquidacion?","Quiere cambiar el precio?","Quiere cambiar lo cobrado?"};
                    	
                    	//menu_modificar("tratamientos_realizados",atributos,enunciados,connection_);
                    break;

                    case 4:
                    	System.out.println("COMING SOON...");

                    break;
                    
                    case 5:
                    	//menu_modificar_cobro(connection_);

                    break;
                      
                    case 6:
                        //Salir
                        System.out.println("//Nos vemos!!!//");
                        System.exit(0);
                    break;
                    default:
                        System.out.println("//Introduce un numero valido//");
                        
                    break;
                }
            } catch (Exception e) {
                System.out.println("//Debes insertar un numero//");
                e.printStackTrace();
            }
            imprimir_menu(connection_);
        }while(opcion!=5);
    }
    //Imprimir menu
    public static void imprimir_menu(Connection connection_) {
        System.out.println("1. Añadir clientes");
        System.out.println("2. Insertar un servicio");
        System.out.println("3. Modificar servicio");
        System.out.println("4. Insertar cobro");
        System.out.println("5. Modificar cobro");
        System.out.println("6. salir");
        menu(connection_);
    }
    
    @SuppressWarnings("unchecked")
    //Creacion de cliente nuevo de manera manual
	public static void cliente_nuevo(String dni,Connection connection_) {
    	System.out.println("CREACION DE NUEVO USUARIOS");
    	Scanner sc = new Scanner(System.in);
    	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
    	String [] datos_cliente = new String [6];
    	String [] enunciados = {"Introduce el nombre del nuevo cliente:","Introduce los apellidos del nuevo cliente:","Introduce la fecha de nacimiento del nuevo cliente: \n La fecha debe de estar en formato YYYYMMDD","Introduce el numero de telefono del nuevo cliente:","Introduce el genero del nuevo paciente(H/M)"};
    	
    	
    	for(int j = 0; j < enunciados.length; j++) {
    		
    		datos_cliente[j] = pedirCadena(enunciados[j]);
        	
    	}

    	datos_cliente[5] = dni;
    	
    	insertar_una_nueva_fila_en_una_tabla("pacientes","Nombre,Apellidos,Fecha_nacimiento,Telefono,Genero,DNI",parsear_atributos(datos_cliente),connection_);
    	
    }
    
    
    
    //Conjunto de inserts para introducir un servicio
    public static void transaccion_insertar_servicio(servicio servicio,Connection connection_) throws SQLException {
    	//Introducir liquidacion
    	//Suma 1 mes a la fecha actual para la fecha final de liquidacion
    			String comision = leer_resultset_string(realizar_consulta(("select * from profesionales where ID =" + servicio.getIDespecialista()),connection_),"comision");
    			
    			String [] sentencia_liquidacion = {formatear_fecha(LocalDate.now().plusMonths(1)),servicio.getIDespecialista(),comision};
    			insertar_una_nueva_fila_en_una_tabla("liquidacion","fecha_liquidacion,id_profesional,comision",parsear_atributos(sentencia_liquidacion),connection_);
    			
    	//Introducir cobro
    			
    			String [] sentencia_cobro = {servicio.getFecha(),servicio.getIDpaciente(),String.valueOf(randomizador(1,3)),servicio.getCobrado()};
    			insertar_una_nueva_fila_en_una_tabla("cobros","fecha,IDpaciente,IDformacobro,Cobrado",parsear_atributos(sentencia_cobro),connection_);
    			
    	//Introducir tratamiento
    			insertar_una_nueva_fila_en_una_tabla("tratamientos_realizados","Fecha,IDpaciente,IDprofesional,IDtratamiento,IDliquidacion,Precio,Cobrado",parsear_atributos(servicio.sentencia_sql()),connection_);
    	
    	//Introducir cobros_servicios
    	/ALERTAAA
		//String [] datos_cobros_servicios = {};
		
		
		insertar_una_nueva_fila_en_una_tabla("servicios_cobros","IDcobro,IDservicio",parsear_atributos(datos_cobros_servicios),connection_);
		
    }
    
    //Interfaz para meter un servicio
    //ARREGLAR FALTA DE DATOS
    public static void menu_insertar_servicio(Connection connection_) throws SQLException {
    	Scanner sc = new Scanner(System.in);
    	
    	String [] atributos_sql = new String [7];
		String variable_aux;
		System.out.println("INTRODUCIR UN SERVICIO");
		
		//fecha
		atributos_sql[0] = formatear_fecha(LocalDate.now()) ;
		//DNI
		
		variable_aux= parsear_cadena(pedirCadena("Introduce el dni del cliente"));
		
		
		ResultSet pacientes = realizar_consulta(("select * from pacientes where dni ="+variable_aux),connection_);
		
		System.out.println(leer_resultset_string(pacientes,"ID"));
		//Crea un cliente nuevo si no existe
		if(leer_resultset_string(pacientes,"ID")==null) {
			System.out.println("Este DNI no esxiste en nuestra base de datos");
			cliente_nuevo(variable_aux,connection_);
			System.out.println("Sigamos con la creacion de la cita");
		}
		System.out.println(leer_resultset_string(pacientes,"ID"));
		
		atributos_sql[1] = leer_resultset_string(pacientes,"ID");;
		
		
		//Especialista
		
		
		
		
		variable_aux = parsear_cadena(pedirCadena("Introduce el nombre del especialista"));
		
		ResultSet especialistas = realizar_consulta(("select * from profesionales where nombre =" + variable_aux),connection_);
		
		atributos_sql[2] = leer_resultset_string(especialistas,"ID");
		
		//Tratamiento
		
		variable_aux = parsear_cadena(pedirCadena("Introduce el nombre del tratamiento"));
		
		ResultSet tratamientos = realizar_consulta(("select * from tratamientos where nombre_tratamiento ="+variable_aux),connection_);
		
		atributos_sql[3] = leer_resultset_string(tratamientos,"codigo_tratamiento");
		
		//liquidacion
		
		ResultSet liquidaciones = realizar_consulta(("select * from liquidacion"),connection_);
		System.out.println(calcular_numero_entradas(liquidaciones)+1);
		atributos_sql[4] = String.valueOf(calcular_numero_entradas(liquidaciones)+2);
		
		//precio
		atributos_sql[5] = leer_resultset_string(tratamientos,"precio");
		
		atributos_sql[6] = atributos_sql[5];
		servicio servicio_actual = new servicio(atributos_sql);
		transaccion_insertar_servicio(servicio_actual,connection_);
    }

    
/*    public static void menu_modificar(String tabla,String[]atributos_tabla,String[] enunciados,Connection connection_) throws SQLException {
    	Scanner sc = new Scanner(System.in);
    	//Incluir en el enunciado el id
    	String[] respuestas = new String [enunciados.length];
    	//Los campos que no se quieran modificar es 0
    	//EN LA PRIMERA CELDA DE RESPUESTAS ESTA EL VALOR DEL FILTRO
    	for(int j = 0; j < enunciados.length; j++) {
    		System.out.println(enunciados[j]);
    		respuestas[j] = sc.next();
    	}
    	
		
		modificar_campo_tabla(tabla,atributos_tabla,"ID",respuestas,connection_);

		
    }
*/
    
/*    
    public static void modificar_campo_tabla(String tabla,String [] atributos,String filtro,String [] respuestas,Connection connection_) {
    	try {
    		String sentencia_atributos = "";
    		Statement st_=connection_.createStatement();
    		for(int j = 1; j < respuestas.length; j++) {
    			respuestas[j]=(isParsable(respuestas[j]))?respuestas[j]:"'"+respuestas[j]+"'";
        		sentencia_atributos = atributos[j]+" = "+respuestas[j]+",";
        		if(respuestas[j]!="0") {
		    		if(j!=respuestas.length) {
		    			sentencia_atributos += atributos[j]+" = "+respuestas[j]+",";
		    		}else {
		    			sentencia_atributos += atributos[j]+" = "+respuestas[j];
		    		}
        		}
        	}
			st_.executeUpdate("update "+tabla+" set "+sentencia_atributos+" where "+filtro+" = "+respuestas[0]);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
*/
    //Realiza la consulta de un atributo pasandole el nombre de la tabla, el atributo que buscas, el filtro por el que quieres buscar y el valor de ese filtro
    public static ResultSet realizar_consulta(String query,Connection connection_) throws SQLException {
    	
			Statement _st = connection_.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			
			System.out.println(query);
			ResultSet _rs = _st.executeQuery(query);
			

			return _rs;

    }
    
    public static String leer_resultset_string(ResultSet _rs,String atributo) throws SQLException {
    	_rs.beforeFirst();
    	String valorpedido = null;
    	while(_rs.next()) {
    		
			valorpedido = _rs.getString(atributo);
			
		}
    	return valorpedido;
    }
    
    public static int leer_resultset_int(ResultSet _rs,String atributo) throws SQLException {
    	_rs.beforeFirst();
    	int valorpedido = 0;
    	while(_rs.next()) {
    		
			valorpedido = _rs.getInt(atributo);
			
		}
    	return valorpedido;
    }
    
    public static int calcular_numero_entradas(ResultSet _rs) throws SQLException {
    	_rs.beforeFirst();
    	ResultSetMetaData rsmd = _rs.getMetaData();
    	_rs.last();
    	return _rs.getRow();
    }
    
    public static String[] array_registros(ResultSet _rs,String atributo,int tamanio_array) throws SQLException {
    	String [] valores = new String [tamanio_array];
    	
    	for(int i=0;i<tamanio_array;i++) {
    		valores[i] = _rs.getString(atributo);
    	}

    	return valores;
    }
    
    
    //Creacion de tablas al que se le pasan el nombre de la tabla y sus atributos por parametro
    public static void creacion_tabla(Connection connection_, String nombre_tabla, String atributos) {
       Statement st_;
    try {
		st_=connection_.createStatement();
		st_.executeUpdate(
				"create table if not exists "+nombre_tabla+"("+atributos+")"
				);
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
       
    }
    //Lee los ficheros de nombres y apellidos y los introduce en arrays
    public static ArrayList<String> asignar_arrays(String nombre_fichero) {
        ArrayList<String> lista = new ArrayList<String>();
        lista = leer_ficheros(nombre_fichero);
        return lista; 
    }

    // Leer ficheros
    public static ArrayList<String> leer_ficheros(String fichero_leer) {
        ArrayList<String> fichero_actual = new ArrayList<String>();
        String linea;
        try {
            File texto = new File(fichero_leer);
            //Lo he modificado a utf8 para que pueda leer los caracteres espaï¿½oles
            BufferedReader lectura_fichero = new BufferedReader(new InputStreamReader(new FileInputStream(texto), "utf-8"));
            while ((linea = lectura_fichero.readLine()) != null) {
                fichero_actual.add(lectura_fichero.readLine());
            }

        } catch (FileNotFoundException fn) {

            System.out.println("No se encuentra el archivo requerido");

        } catch (IOException e) {

            System.out.println("Error de lectura_escritura");
        }

        return fichero_actual;

    }
    //Mete los datos de los csv en un arraylist de arrays
    public static ArrayList<String []> leer_csv(String fichero_leer) throws IOException {
        String linea;
    	String [] datos; 
        ArrayList<String []> lista = new ArrayList<String[]>();
        try {
            File texto = new File(fichero_leer);
            BufferedReader lectura_fichero = new BufferedReader(new InputStreamReader(new FileInputStream(texto), "utf-8"));
            while ((linea = lectura_fichero.readLine()) != null) {
            	//Dividir la linea leida por el caractaer ";" 
                datos= linea.split(";");
                lista.add(datos);
                
            }
            return lista;
            
        } catch (FileNotFoundException fn) {
            ArrayList <String[]> vacio = new ArrayList<>();
            System.out.println("No se encuentra el archivo requerido_tratamientos");
            return vacio;

        } catch (IOException e) {
            ArrayList <String[]> vacio = new ArrayList<>();
            System.out.println("Error de lectura_escritura");
            return vacio;
        }
        
    }
    
    
    //Insertar los datos creados en el metodo "leer csv"
    public static void insertar_fichero_csv(ArrayList<String[]> csv,String tabla,String atributos,Connection connection_,boolean tiene_cabacera) {    	
        if (tiene_cabacera == true) {
		    csv.remove(0);
		}
		
		for (String [] tratamiento:csv) {
			insertar_una_nueva_fila_en_una_tabla(tabla,atributos,parsear_atributos(tratamiento),connection_);
		}
		System.out.println("DATOS DE "+tabla+" INSERTADOS");
    	
    }

        public static String parsear_atributos(String[] sentencia_csv){

        	String sentencia_atributos="";

            //Crea una sentencia compatible con MySQL
            for (int j = 0; j < sentencia_csv.length; j++) {
                
            if(!isParsable(sentencia_csv[j])) {
            	sentencia_csv[j]="'"+sentencia_csv[j]+"'";
            }
                
                sentencia_atributos+=sentencia_csv[j];
                if (j != sentencia_csv.length-1) {
                    sentencia_atributos+=",";
                }

            }
            return sentencia_atributos;
            
            

    }
    //Este metodo se utiliza para saber si es un entero o no
    public static boolean isParsable(String input) {
        try {
            Integer.parseInt(input);
            return true;
        } catch (final NumberFormatException e) {
            return false;
        }
    }
 
    // Crea csv
    public static void escribir_csv(ArrayList<String> nombres_hombres, ArrayList<String> nombres_mujeres,
            ArrayList<String> apellidos,String nombre_fichero,int numero_creaciones,boolean es_paciente) {
        try {

            ArrayList<String> dni_existentes = new ArrayList<String>();
            paciente cliente;
            profesional profesional;
            FileWriter fw = new FileWriter(nombre_fichero);

            for (int i = 0; i < numero_creaciones; i++) {
                System.out.println(i);
                if(es_paciente) {
                	cliente = new paciente(creacion_persona(nombres_hombres, nombres_mujeres, apellidos, dni_existentes),(formatear_fecha(fecha_nacimiento())));
                    
                    fw.write(cliente.sentencia_csv());
                    fw.write("\n");
                }else {
                	profesional = new profesional(creacion_persona(nombres_hombres, nombres_mujeres, apellidos, dni_existentes),String.valueOf(randomizador(20,90)));
                    
                    fw.write(profesional.sentencia_csv());
                    fw.write("\n");
                }
                
            }

            System.out.println("CLIENTES CREADOS");
            fw.close();
        } catch (IOException e) {
            System.out.println("Error E/S:" + e);
        }
    }
    
    //Inserta una nueva fila en una tabla y sus atributos especificados por parametro
    public static void insertar_una_nueva_fila_en_una_tabla(String tabla, String atributos,String sentencia_atributos ,Connection connection_) {
    	
    	try {
    		Statement st_=connection_.createStatement();
    		System.out.println("insert into "+tabla+" ("+atributos+") values ("+sentencia_atributos+")");
			st_.executeUpdate("insert into "+tabla+" ("+atributos+") values ("+sentencia_atributos+")");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    }

    //Crea un cliente 
    public static String[] creacion_persona(ArrayList<String> nombres_hombres, ArrayList<String> nombres_mujeres,
            ArrayList<String> apellidos, ArrayList<String> dni_existentes) {
    	String[] cliente = new String[4];
        // Genero decidido
        int Genero = randomizador(0, 100);
        // Generacion de nombre
        if (Genero >= 55) {
            String nombre = nombres_mujeres.get(randomizador(1, nombres_mujeres.size()));
            //Elimino las comas de lo nombres
            nombre = nombre.replace("'", " ");
            cliente[0]=nombre;
        } else {
            String nombre = nombres_hombres.get(randomizador(1, nombres_hombres.size()));
          //Elimino las comas de lo nombres
            nombre = nombre.replace("'", " ");
            cliente[0]=nombre;
        }

        // Apellidos

        String Apellidos = apellidos.get(randomizador(1, apellidos.size())) + " "
                + apellidos.get(randomizador(1, apellidos.size()));
      //Elimino las comas de los apellidos
        Apellidos = Apellidos.replace("'", " ");
        cliente[1]=Apellidos;

        // Creacion telefono
        cliente[2]=String.valueOf(randomizador(100000000, 999999999));

        // Genero
        if (Genero < 46) {
            cliente[3]="H";
        } else {
            cliente[3]="M";
        }

        // Creacion y validacion de dni
        String dni_actual = " ";
        dni_actual = CrearDNI(dni_existentes);
        dni_existentes.add(dni_actual);
        cliente[4]=dni_actual;
        return cliente;
    }

    public static LocalDate fecha_nacimiento() {
        LocalDate fecha = LocalDate.of(1917, 1, 1);
        fecha = fecha.plusDays(
                randomizador(1, (int) ChronoUnit.DAYS.between(LocalDate.of(1917, 1, 1), LocalDate.of(2020, 12, 31))));
        return fecha;
    }

    public static int randomizador(int desde, int hasta) {
        int numero;
        numero = (int) (Math.floor((hasta - desde + 1) * Math.random()));
        return numero;

    }

    public static String CrearDNI(ArrayList<String> dni_existentes) {
        // primero se genera los 8 numero, despues se para a String y se le aniade la
        // letra final
        String dni;
        do {
            int numero = randomizador(10000000, 99999999);
            char[] letras = { 'T', 'R', 'W', 'A', 'G', 'M', 'Y', 'F', 'P', 'D', 'X', 'B', 'N', 'J', 'Z', 'S', 'Q', 'V',
                    'H', 'L', 'C', 'K', 'E' };
            char letra = letras[numero % 23];
            dni = String.valueOf(numero) + letra;
        } while (dni_existentes.contains(dni) != false);

        return dni;
    }
    
    public static String formatear_fecha(LocalDate fecha) {
    	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
    	return fecha.format(formatter) ;
    }
    
    public static String parsear_cadena(String cadena) {
    	return(isParsable(cadena))? cadena : "'"+cadena+"'";
    }
    
    private static String pedirCadena(String mensaje) {

		Scanner sc = new Scanner(System.in);
		System.out.println(mensaje);

		return sc.nextLine();
	}
    
    private static int pedirEntero(String mensaje) {

		Scanner sc = new Scanner(System.in);

		do {
			System.out.println(mensaje);
			try {
				return sc.nextInt();
			} catch (InputMismatchException ex) {
				System.err.println("Error, debe introducir un número entero.");
				sc.next();
			}
		} while (true);

	}
}
