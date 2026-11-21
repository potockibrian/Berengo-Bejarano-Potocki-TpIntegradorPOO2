package unq.travel.traslado;

import java.util.ArrayList;
import java.util.List;

import unq.travel.catalogo.ItemCatalogo;
import unq.travel.cupos.RangoDeFechas;

public class LineaDeReserva {

	private ItemCatalogo item;
	private RangoDeFechas rango;
	private boolean reservada;

	public LineaDeReserva(ItemCatalogo item, RangoDeFechas rango) {

		this.item = item;
		this.rango = rango;

	}

	public float precioFinal() {

		return item.getPrecioFinal();

	}

	public boolean tieneDisponibilidad() {

		return item.tieneDisponibilidad(rango);

	}
	
	public boolean tieneDisponibilidad(EstrategiaTraslado estrategia) {

		return estrategia.hayDisponibilidad(rango);

	}
	
	public void reservarCupo(EstrategiaTraslado estrategia) {

		estrategia.reservar(rango);
		item.reservar(rango);

	}

	public void liberarCupo(EstrategiaTraslado estrategia) {
		
		estrategia.liberar(rango);
		item.liberar(rango);

	}

}
