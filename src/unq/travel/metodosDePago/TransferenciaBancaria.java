package unq.travel.metodosDePago;

public class TransferenciaBancaria extends MetodoDePago {
	private TransferenciaBancariaAPI api;
	private String cbu;
	private String alias;
	private boolean transferenciaProgramada;
	
	public TransferenciaBancaria(
			TransferenciaBancariaAPI api,
			String cbu,
			String alias,
			boolean transferenciaProgramada) {
		this.api = api;
		this.cbu = cbu;
		this.alias = alias;
		this.transferenciaProgramada = transferenciaProgramada;
	}
	
	@Override
	protected boolean validarDatos(float monto) {
		return api.validarDatos(cbu, alias);
	}
	
	@Override
	protected void reservarFondos() {
		// La transferencia bancaria no requiere reservar fondos
	}
	
	@Override
	protected String ejecutarTransaccion() {
		if(transferenciaProgramada) {
			return api.programarTransferencia();
		}
		return api.ejecutarTransferenciaInmediata();
	}
	
	@Override
	protected void notificar(String codigo) {
		super.notificar(codigo);
		api.generarYRegistrarComprobante(codigo);
	}
}
