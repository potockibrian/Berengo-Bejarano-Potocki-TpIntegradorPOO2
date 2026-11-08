package unq.travel.reportes;

import java.util.*;

import unq.travel.catalogo.*;
import unq.travel.cupos.*;

public class ReporteOcupacionYCupoDisponible {
	
	// Asocia c/proveedor con su porcentaje de exposicion
	private Map<Categoria, List<Cupo>> cuposPorCategoria;
	private Map<RangoDeFechas, List<Cupo>> cuposPorPeriodo;
	
	public ReporteOcupacionYCupoDisponible(Map<Categoria, List<Cupo>> cuposPorCategoria, Map<RangoDeFechas, List<Cupo>> cuposPorPeriodo) {
		
		this.cuposPorCategoria = cuposPorCategoria;
		this.cuposPorPeriodo = cuposPorPeriodo;
	
	}
	
	public List<Map.Entry<Categoria, List<Cupo>>> getCuposPorCategoria() {

		return cuposPorCategoria
					.entrySet()
					.stream()
			        .toList();
				
	}
	
	public List<Map.Entry<RangoDeFechas, List<Cupo>>> getCuposPorPeriodo() {
		
		return cuposPorPeriodo
					.entrySet()
					.stream()
					.toList();
		
	}
	
}