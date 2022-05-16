package servicios;

public class servicio {
	String fecha;
	String IDpaciente;
	String IDespecialista;
	String IDtratamiento;
	String IDliquidacion;
	String precio;
	String cobrado;
	
	
	public servicio(String [] valores) {
		this.fecha = valores[0];
		this.IDpaciente = valores[1];
		this.IDespecialista = valores[2];
		this.IDtratamiento = valores[3];
		this.IDliquidacion = valores[4];
		this.precio = valores[5];
		this.cobrado = valores[6];
	}
	
	public String [] sentencia_sql(){
		String [] sentencia_sql = {this.fecha,this.IDpaciente,this.IDespecialista,this.IDtratamiento,this.IDliquidacion,this.precio,this.cobrado};
		return sentencia_sql;
	}
	
	public String getFecha() {
		return fecha;
	}
	public String getIDpaciente() {
		return IDpaciente;
	}
	public String getIDespecialista() {
		return IDespecialista;
	}
	public String getIDtratamiento() {
		return IDtratamiento;
	}
	public String getIDliquidacion() {
		return IDliquidacion;
	}
	public String getCobrado() {
		return cobrado;
	}
	public void setFecha(String fecha) {
		this.fecha = fecha;
	}
	public void setIDpaciente(String iDpaciente) {
		IDpaciente = iDpaciente;
	}
	public void setIDespecialista(String iDespecialista) {
		IDespecialista = iDespecialista;
	}
	public void setIDtratamiento(String iDtratamiento) {
		IDtratamiento = iDtratamiento;
	}
	public void setIDliquidacion(String iDliquidacion) {
		IDliquidacion = iDliquidacion;
	}
	public void setCobrado(String cobrado) {
		this.cobrado = cobrado;
	}

}
