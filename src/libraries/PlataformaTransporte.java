package libraries;

import unq.travel.traslado.Direccion;

public interface PlataformaTransporte {

	public float estimarTraslado(int cantidadPasajeros, Direccion direccionDestino);

}
