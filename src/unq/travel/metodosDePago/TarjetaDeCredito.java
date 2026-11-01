package unq.travel.metodosDePago;

public class TarjetaDeCredito extends MetodoDePago{
	private TarjetaDeCreditoAPI api;
	private int numeroTarjeta;
	private String codigoSeguridad;
	private String fechaVencimiento;
	
	public TarjetaDeCredito(
			TarjetaDeCreditoAPI api,
			int numeroTarjeta,
			String codigoSeguridad,
			String fechaVencimiento) {
		this.api = api;
		this.numeroTarjeta = numeroTarjeta;
		this.codigoSeguridad = codigoSeguridad;
		this.fechaVencimiento = fechaVencimiento;
	}
	
	@Override
	protected boolean validarDatos(float monto) {
		return api.validarDatos(
				numeroTarjeta, 
				codigoSeguridad, 
				fechaVencimiento);
	}
	
	@Override
	protected void reservarFondos() {
		api.preAutorizacion();
	}
	
	@Override
	protected String ejecutarTransaccion() {
		return api.transferenciaInmediata();
	}
	
	@Override
	protected void notificar(String codigo) {
		super.notificar(codigo);
		api.generarYRegistrarVoucher(codigo);
	}
}
