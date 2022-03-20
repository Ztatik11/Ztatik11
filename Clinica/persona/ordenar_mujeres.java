
package persona;
import java.util.Comparator;

public class ordenar_mujeres implements Comparator<paciente> {
    public int compare(paciente o1, paciente o2) {
		int comparar_apellido = (o1.getApellido_1()+o1.getApellido_2()).compareTo(o2.getApellido_1()+o2.getApellido_2());
        int comparar_nombre = o1.getNombre().compareTo(o2.getNombre());
        //Multiplicamos el resultado por -1 poruqe si no saldrian las fechas de la gente mas mayor y no saldria la gente mas joven
        int comparar_edad = -1*(o1.getFechaNacimiento().compareTo(o2.getFechaNacimiento()));


        if (comparar_edad!=0) {
            return comparar_edad;
        }

        if(comparar_apellido != 0){
            return comparar_apellido;
        }

        
        return comparar_nombre;
    }
}
