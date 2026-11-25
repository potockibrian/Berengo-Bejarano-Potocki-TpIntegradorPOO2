package unq.travel.reportes;

import java.util.*;
import java.util.stream.Collectors;

import unq.travel.catalogo.*;
import unq.travel.cupos.*;

public class ReporteOcupacionYCupoDisponible implements Reporte {
	
	// Asocia c/proveedor con su porcentaje de exposicion
	private Map<Categoria, Cupo> cuposPorCategoria;
	private RangoDeFechas periodo;
	
	//private Map<RangoDeFechas, List<Cupo>> cuposPorPeriodo;
	
	public ReporteOcupacionYCupoDisponible(Catalogo catalogo, RangoDeFechas periodo) {
		
		generarCuposPorCategoria(catalogo, periodo);
		
		this.periodo = periodo;
		
	}

	public List<Map.Entry<Categoria, Cupo>> getCuposPorCategoria() {

		return cuposPorCategoria
					.entrySet()
					.stream()
			        .toList();
				
	}
	
	/*
	public List<Map.Entry<RangoDeFechas, Cupo>> getCuposPorPeriodo() {
		
		return cuposPorPeriodo
					.entrySet()
					.stream()
					.toList();
		
	}
	*/

	@Override
	public String exportar(Exportador exportador) {

		return exportador.exportar(this);
		
	}

	private void generarCuposPorCategoria(Catalogo catalogo, RangoDeFechas periodo) {
		
		Set<Categoria> categorias = obtenerCategorias(catalogo);

		generarCuposDeCategorias(catalogo, periodo, categorias);
		
	}

	private void generarCuposDeCategorias(Catalogo catalogo, RangoDeFechas periodo, Set<Categoria> categorias) {
		
		for(Categoria categoria : categorias) {
			
			cuposPorCategoria.put(categoria, cuposDeCategoria(catalogo, periodo, categoria));
			
		}
		
	}

	private Cupo cuposDeCategoria(Catalogo catalogo, RangoDeFechas periodo, Categoria categoria) {
		
		return catalogo.getItems().stream()
					.map(i -> i.cuposEnPeriodoDeCategoria(periodo, categoria))
					.reduce(new Cupo(0), (acumulador, nuevo) -> acumulador.combinarConCupo(nuevo));
		
	}

	private Set<Categoria> obtenerCategorias(Catalogo catalogo) {
		
		return catalogo.getItems().stream()
					.map(i -> i.categorias())
					.reduce(new HashSet(), (acumulador, set) -> { acumulador.addAll(set); return acumulador; });
		
	}

	public RangoDeFechas periodo() {

		return periodo;

	}
	
}