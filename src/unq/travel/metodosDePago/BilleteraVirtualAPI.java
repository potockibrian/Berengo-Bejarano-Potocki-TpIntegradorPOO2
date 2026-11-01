package unq.travel.metodosDePago;

public interface BilleteraVirtualAPI {
	boolean validarSaldoSuficiente(float monto);
	
	void bloquearSaldo();
	
	String acreditarAlVendedor();
	
	void notificarPush(String codigoTransaccion);
}
