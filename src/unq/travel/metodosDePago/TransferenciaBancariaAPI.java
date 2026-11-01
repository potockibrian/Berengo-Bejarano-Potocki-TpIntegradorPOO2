package unq.travel.metodosDePago;

public interface TransferenciaBancariaAPI {
	boolean validarDatos(String cbu, String alias);
	
	String ejecutarTransferenciaInmediata();
	
	String programarTransferencia();
	
	void generarYRegistrarComprobante(String codigoOperacion);
}
