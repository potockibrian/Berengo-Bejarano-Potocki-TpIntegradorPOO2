package unq.travel.traslado;

import libraries.PlataformaTransporte;

public class TrasladoEstandar implements EstrategiaTraslado {

	private Direccion direccion;
	private int cantidadDePasajeros;

	public TrasladoEstandar(int cantidadDePasajeros, Direccion direccion) {
	
		this.cantidadDePasajeros = cantidadDePasajeros;
		this.direccion = direccion;
		
	}

	@Override
	public float costoTraslado() {

		return PlataformaTransporte.estimarTraslado();

	}

	@Override
	public boolean hayDisponibilidad() {

		return true;

	}

}
