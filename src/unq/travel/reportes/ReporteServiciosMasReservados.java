package unq.travel.reportes;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import unq.travel.catalogo.Catalogo;
import unq.travel.catalogo.ItemCatalogo;
import unq.travel.cupos.Cupo;
import unq.travel.cupos.RangoDeFechas;
import unq.travel.cupos.RegistroVentaPeriodo;

public class ReporteServiciosMasReservados implements Reporte {
	
	private Map<ItemCatalogo, RegistroVentaPeriodo> items = new HashMap<ItemCatalogo, RegistroVentaPeriodo>();
	private RangoDeFechas periodo;
	
	/*
	private Map<ItemCatalogo, Integer> items1 = new HashMap<>();
	private RegistroDeVentas registroDeVentas;
	private Map<ItemCatalogo, RegistroVentaPeriodo> items = new HashMap<>();
	
	public ReporteServiciosMasReservados(
			RangoDeFechas periodo, 
			Map<ItemCatalogo, Integer> items,
			RegistroDeVentas registroDeVentas) {
		
		this.periodo = periodo;
		this.items = new HashMap<>(items);
		this.registroDeVentas = registroDeVentas;
	
	}
	*/

	public ReporteServiciosMasReservados(Catalogo catalogo, RangoDeFechas periodo) {

		catalogo.getItems().stream().forEach(i -> this.items.put(i, i.cantidadYTotalCobradoDeVentasEnPeriodo(periodo)));
		this.periodo = periodo;
		
	}

	public RangoDeFechas getPeriodo() {
		
		return periodo;

	}

	public List<Map.Entry<ItemCatalogo, Integer>> getItemsOrdenados() {
		
		return items.entrySet()
			        .stream()
			        .map(kv -> Map.entry(kv.getKey(), kv.getValue().cantidadVentas()))
			        .sorted(Map.Entry.<ItemCatalogo, Integer>comparingByValue().reversed())
			        .toList();
		
	}

	public float getPrecioPromedioCobrado(ItemCatalogo item) {
		
		RegistroVentaPeriodo registro = this.items.get(item);
		
		return (registro == null) ? 0f : registro.precioPromedio();
	
	}

	@Override
	public String exportar(Exportador exportador) {

		return exportador.exportar(this);
		
	}
	
}
