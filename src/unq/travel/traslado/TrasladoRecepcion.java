package unq.travel.traslado;

public class TrasladoRecepcion implements EstrategiaTraslado {

	private Sucursal sucursal;
	
	public TrasladoRecepcion(Sucursal sucursal) {

		this.sucursal = sucursal;
		
	}

	@Override
	public float costoTasasTraslado(Reserva reserva) {
		
		if (!sucursal.hayDisponibilidad()) throw new NoHayDisponibilidadException();
		
		return 0;
		
	}	

}
