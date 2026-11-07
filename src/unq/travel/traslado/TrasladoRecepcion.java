package unq.travel.traslado;

public class TrasladoRecepcion extends EstrategiaTraslado {

	public int cantidadDeGuias;
	
	public TrasladoRecepcion(int cantidadDeGuias) {

		this.cantidadDeGuias = cantidadDeGuias;
		
	}

	@Override
	public float costoTasasTraslado(Reserva reserva) {
		
		return 0;
		
	}
	
	@Override
	public boolean hayDisponibilidad() {
		
		return cantidadDeGuias > 0;
		
	}

}
