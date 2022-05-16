package persona;

import java.time.LocalDate;
import java.util.ArrayList;

public class profesional extends persona {
	String NIF;
	String comision;
	public profesional(String[] datos_paciente,String comision){
		super(datos_paciente);
		this.NIF = datos_paciente[4];
        this.comision = comision;
	}
	
	public String get_NIF() {
		return this.NIF;
	}
	
	public String get_Comision() {
		return this.comision;
	}
	
	public String sentencia_csv() {
		return (super.getNombre()+";"+super.getApellido()+";"+super.getTelefono()+";"+super.getGenero()+";"+get_NIF()+";"+this.comision);
	}
}
