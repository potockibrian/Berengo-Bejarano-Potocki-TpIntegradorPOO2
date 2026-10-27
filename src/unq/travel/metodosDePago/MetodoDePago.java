package unq.travel.metodosDePago;

public abstract class MetodoDePago {
    public void pagar(float monto) {
        if (!validarDatos(monto)) {
            throw new IllegalArgumentException("Datos de pago invalidos");
        }
        reservarFondos();
        ejecutarTransaccion();
        notificar();
    }
    
    protected abstract boolean validarDatos(float monto);
    
    protected abstract void reservarFondos();
    
    protected abstract void ejecutarTransaccion();
    
    protected void notificar() {
        
    }
}
