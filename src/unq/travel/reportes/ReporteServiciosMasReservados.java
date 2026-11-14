package unq.travel.reportes;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import unq.travel.catalogo.ItemCatalogo;
import unq.travel.cupos.Cupo;
import unq.travel.cupos.RangoDeFechas;

public class ReporteServiciosMasReservados {
	
	private RangoDeFechas periodo;
	private Map<ItemCatalogo, Integer> items;
	private RegistroDeVentas registroDeVentas;
	
	public ReporteServiciosMasReservados(
			RangoDeFechas periodo, 
			Map<ItemCatalogo, Integer> items,
			RegistroDeVentas registroDeVentas) {
		
		this.periodo = periodo;
		this.items = new HashMap<>(items);
		this.registroDeVentas = registroDeVentas;
	
	}

	public RangoDeFechas getPeriodo() {
		return periodo;

	}

	public List<Map.Entry<ItemCatalogo, Integer>> getItemsOrdenados() {
		
		return items.entrySet()
			        .stream()
			        .sorted(Map.Entry.<ItemCatalogo, Integer>
			        			comparingByValue().reversed())
			        .toList();
		
	}

	public float getPrecioPromedioCobrado(ItemCatalogo item) {
		return registroDeVentas.precioPromedioDe(item, periodo);
	}
	
}
