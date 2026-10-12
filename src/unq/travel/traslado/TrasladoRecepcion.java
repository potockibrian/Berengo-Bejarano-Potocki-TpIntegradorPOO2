package unq.travel.traslado;

public class TrasladoRecepcion implements EstrategiaTraslado {

	private Sucursal sucursal;
	
	public TrasladoRecepcion(Sucursal sucursal) {

		this.sucursal = sucursal;
		
	}

	@Override
	public float costoTraslado() {

		return 0;
		
	}

	@Override
	public boolean hayDisponibilidad() {
		
		return sucursal.hayDisponibilidad();

	}
	
	

}
