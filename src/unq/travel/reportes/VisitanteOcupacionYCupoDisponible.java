package unq.travel.reportes;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import unq.travel.catalogo.Catalogo;
import unq.travel.catalogo.Categoria;
import unq.travel.catalogo.ItemCatalogo;
import unq.travel.catalogo.PaqueteTuristico;
import unq.travel.catalogo.ServicioIndividual;
import unq.travel.cupos.Cupo;
import unq.travel.cupos.RangoDeFechas;

public class VisitanteOcupacionYCupoDisponible implements Visitor{

	private Map<Categoria, List<Cupo>> cuposPorCategoria;
	private Map<RangoDeFechas, List<Cupo>> cuposPorPeriodo;
	
	@Override
	public void visitar(ServicioIndividual servicio) {
		
		List<Cupo> cupos = cuposPorCategoria.getOrDefault(servicio.getCategoria(), new ArrayList<Cupo>());
		cupos.addAll(servicio.cupos());
		cuposPorCategoria.put(servicio.getCategoria(), cupos);
		
		servicio.cuposPorPeriodo().forEach((k1, v1) ->
		{ 	
			List<Cupo> cuposPeriodo = cuposPorPeriodo.get(k1);
			if (cuposPeriodo == null) {
				cuposPorPeriodo.put(k1, new ArrayList<Cupo>(v1));
			}
			else {
				cuposPeriodo.addAll(v1);
			}
		});
		
	}

	@Override
	public void visitar(PaqueteTuristico servicio) {

		return; // la recursión se realiza en la implementación de PaqueteTuristico
		
	}
	
	public ReporteOcupacionYCupoDisponible procesar(Catalogo catalogo, RangoDeFechas periodo) {
		
		// reinicia contador para generar un calculo nuevo c/vez
		cuposPorCategoria.clear();
		cuposPorPeriodo.clear();
		
		for(ItemCatalogo item : catalogo.getItems()) {
		
			item.aceptar(this);
		
		}
		
		return new ReporteOcupacionYCupoDisponible(cuposPorCategoria, cuposPorPeriodo);
		
	}

}
