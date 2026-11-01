package unq.travel.metodosDePago;

public abstract class MetodoDePago {
	private String codigoTransaccion;
	
    public final void pagar(float monto) {
        if (!validarDatos(monto)) {
            throw new IllegalArgumentException("Datos de pago invalidos");
        }
        
        reservarFondos();
        String codigo = ejecutarTransaccion();
        notificar(codigo);
    }
    
    protected abstract boolean validarDatos(float monto);
    
    protected abstract void reservarFondos();
    
    protected abstract String ejecutarTransaccion();
    
    protected void notificar(String codigo) {
        this.codigoTransaccion = codigo;
    }
   
    public String getCodigoTransaccion() {
    	return codigoTransaccion;
    }
}
