package unq.travel.metodosDePago;

public interface TarjetaDeCreditoAPI {
	boolean validarDatos(
			int numeroTarjeta,
			String codigoSeguridad,
			String fechaVencimiento
	);
	
	void preAutorizacion();
	
	String transferenciaInmediata();
	
	void generarYRegistrarVoucher(String codigoTransaccion);
}
