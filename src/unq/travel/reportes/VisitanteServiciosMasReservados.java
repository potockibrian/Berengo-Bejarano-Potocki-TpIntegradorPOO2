package unq.travel.reportes;

import java.util.HashMap;
import java.util.Map;

import unq.travel.catalogo.Catalogo;
import unq.travel.catalogo.ItemCatalogo;
import unq.travel.catalogo.PaqueteTuristico;
import unq.travel.catalogo.ServicioIndividual;
import unq.travel.cupos.RangoDeFechas;

public class VisitanteServiciosMasReservados implements Visitor {
	
	private Map<ItemCatalogo, Integer> items = new HashMap<>();
	private RangoDeFechas periodo;
	
	public VisitanteServiciosMasReservados(RangoDeFechas periodo) {
		
		this.periodo = periodo;
		
	}
	
	public void setPeriodo(RangoDeFechas periodo) {
		
		this.periodo = periodo;
		
	}
	
	@Override
	public void visitar(ServicioIndividual servicio) {
		
		items.put(servicio, servicio.cantidadDeVentasEnPeriodo(periodo));
		
	}
	
	@Override
	public void visitar(PaqueteTuristico paquete) {

		items.put(paquete, paquete.cantidadDeVentasEnPeriodo(periodo));
		
	}
	
	public ReporteServiciosMasReservados procesar(Catalogo catalogo, RangoDeFechas periodo) {
		
		items.clear();
		this.setPeriodo(periodo);
		
		for(ItemCatalogo item : catalogo.getItems()) {
		
			item.aceptar(this);
		
		}
		
		return new ReporteServiciosMasReservados(periodo, items);
		
	}

}
