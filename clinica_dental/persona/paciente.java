package persona;

import java.time.LocalDate;
import java.util.ArrayList;

public class paciente extends persona {
	String dni;
	String fecha_nacimiento;
	public paciente(String[] datos_paciente,String fecha_nacimiento){
		super(datos_paciente);
		this.dni = datos_paciente[4];
		this.fecha_nacimiento = fecha_nacimiento;
        
	// ---------------------Getters---------------------
		
    }
	
	public String get_dni() {
		return this.dni;
	}
	
	public String getFechaNacimiento(){
        return fecha_nacimiento;
    }
	
	//---------------------Setters---------------------
	public void set_dni(String dni) {
		this.dni = dni;
	}
    
    public void setFechaNacimiento(String fecha_nacimiento){
        this.fecha_nacimiento = fecha_nacimiento;
    }
    
    public String sentencia_csv() {
		return (super.getNombre()+";"+super.getApellido()+";"+getFechaNacimiento()+";"+super.getTelefono()+";"+super.getGenero()+";"+get_dni());
	}
}
