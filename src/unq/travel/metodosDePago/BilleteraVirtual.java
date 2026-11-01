package unq.travel.metodosDePago;

public class BilleteraVirtual extends MetodoDePago {
	private BilleteraVirtualAPI api;
	
	public BilleteraVirtual(BilleteraVirtualAPI api) {
		this.api = api;
	}
	
	@Override
	protected boolean validarDatos(float monto) {
		return api.validarSaldoSuficiente(monto);
	}
	
	@Override
	protected void reservarFondos() {
		api.bloquearSaldo();
	}
	
	@Override
	protected String ejecutarTransaccion() {
		return api.acreditarAlVendedor();
	}
	
	@Override
	protected void notificar(String codigo) {
		super.notificar(codigo);
		api.notificarPush(codigo);
	}
}
