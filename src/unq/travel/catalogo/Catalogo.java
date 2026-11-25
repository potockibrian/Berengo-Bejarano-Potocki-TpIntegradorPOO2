package unq.travel.catalogo;

import java.util.ArrayList;
import java.util.List;

import unq.travel.busquedaCatalogo.Buscador;
import unq.travel.busquedaCatalogo.Criterio;
import unq.travel.cupos.RangoDeFechas;
import unq.travel.reportes.Exportador;
import unq.travel.reportes.Reporte;
import unq.travel.reportes.ReporteExposicionProveedores;
import unq.travel.reportes.ReporteOcupacionYCupoDisponible;
import unq.travel.reportes.ReporteServiciosMasReservados;

public class Catalogo {

	private List<ItemCatalogo> items;
	private Buscador buscador = new Buscador(this);
	private List<Reporte> reportes = new ArrayList<>();

	public Catalogo() {

		this.items = new ArrayList<ItemCatalogo>();

	}

	public void agregarItem(ItemCatalogo item) {

		items.add(item);

	}

	public void eliminarItem(ItemCatalogo item) {

		if (!items.contains(item)) {
			throw new ItemInexistenteException("El item " + item.getNombre() + " no esta en el catalogo");
		}
		items.remove(item);

	}

	public List<ItemCatalogo> getItems() {

		return new ArrayList<ItemCatalogo>(items);

	}

	public List<ItemCatalogo> buscar(Criterio criterio) {

		return buscador.buscar(criterio);

	}
	
	public void crearReporteReservados(RangoDeFechas periodo) {
		
		reportes.add(new ReporteServiciosMasReservados(this, periodo));
		
	}
	
	public void crearReporteOcupacionYCupoDisponible(RangoDeFechas periodo) {
		
		reportes.add(new ReporteOcupacionYCupoDisponible(this, periodo));
		
	}
	
	public void crearReporteProveedores() {
		
		reportes.add(new ReporteExposicionProveedores(this));
		
	}
	
	public List<String> exportarReportesEnFormato(Exportador exportador) {
		
		return reportes.stream().map(r -> r.exportar(exportador)).toList();
		
	}
	
	public void limpiarReportes() {
		
		reportes.clear();
		
	}

}
