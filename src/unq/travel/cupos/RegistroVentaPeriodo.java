package unq.travel.cupos;

public class RegistroVentaPeriodo {

	private RangoDeFechas periodo;
	private float precioPagado;
	private int cantidadVentas;
	
	public RegistroVentaPeriodo(RangoDeFechas periodo) {
		
		this.periodo = periodo;
		precioPagado = 0;
		cantidadVentas = 0;
		
	}
	
	public RegistroVentaPeriodo(RangoDeFechas periodo, float precioPagado, int cantidadVentas) {
		
		this.periodo = periodo;
		this.precioPagado = precioPagado;
		this.cantidadVentas = cantidadVentas;
		
	}
	
	public boolean periodoContiene(RangoDeFechas periodo) {
		
		return this.periodo.contieneA(periodo);
		
	}
	
	public boolean tieneMismoPeriodo(RangoDeFechas periodo) {
		
		return this.periodo.equals(periodo);
		
	}
	
	public void agregarVenta(float precio) {
		
		precioPagado += precio;
		cantidadVentas++;
		
	}
	
	public RegistroVentaPeriodo combinarConRegistro(RegistroVentaPeriodo registro) {
		
		return new RegistroVentaPeriodo(this.periodo.union(registro.periodo())
				, this.precioPagado + registro.precioPagado()
				, this.cantidadVentas + registro.cantidadVentas());
		
	}
	
	public RegistroVentaPeriodo combinarConRegistroRecursivo(RegistroVentaPeriodo registro) {
		
		return new RegistroVentaPeriodo(this.periodo.union(registro.periodo())
				, this.precioPagado + registro.precioPagado()
				, Math.max(this.cantidadVentas, registro.cantidadVentas()));
		
	}
	
	public void aplicarDescuento(float descuento) {
		
		precioPagado *= (1 - descuento); 
		
	}
	
	public float precioPromedio() {
		
		return precioPagado / cantidadVentas;
		
	}
	
	public RangoDeFechas periodo() { return periodo; }
	
	public float precioPagado() { return precioPagado; }

	public int cantidadVentas() { return cantidadVentas; }
	
}
