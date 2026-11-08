package unq.travel.reportes;

import java.util.List;
import java.util.Map;

import unq.travel.catalogo.ItemCatalogo;
import unq.travel.cupos.Cupo;
import unq.travel.cupos.RangoDeFechas;

public class ReporteServiciosMasReservados {
	
	// Asocia c/proveedor con su porcentaje de exposicion
	private RangoDeFechas periodo;
	private Map<ItemCatalogo, Integer> items;
	
	public ReporteServiciosMasReservados(RangoDeFechas periodo, Map<ItemCatalogo, Integer> items) {
		
		this.periodo = periodo;
		this.items = items;
	
	}
	
	public Map<ItemCatalogo, Integer> getItems() {
		
		return items;
				
	}

	public RangoDeFechas getPeriodo() {

		return periodo;

	}

	public List<Map.Entry<ItemCatalogo, Integer>> getItemsOrdenados() {
		
		return items.entrySet()
			        .stream()
			        .sorted(Map.Entry.comparingByValue())
			        .toList();
		
	}

	public float getPrecioPromedioCobrado(ItemCatalogo item) {

		return item.getPrecioFinal() / items.get(item);
		
	}

	public Object getCantidad(ItemCatalogo item) {

		return items.get(item);
		
	}

	
	
}
