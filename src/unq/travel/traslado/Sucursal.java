package unq.travel.traslado;

public class Sucursal {

	public int cantidadDeGuias;

	public Sucursal(int cantidadDeGuias) {

		this.cantidadDeGuias = cantidadDeGuias;
		
	}

	public boolean hayDisponibilidad() {

		return cantidadDeGuias > 0;

	}

}
