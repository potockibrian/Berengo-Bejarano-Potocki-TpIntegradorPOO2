package unq.travel.reportes;

public interface Exportador {
	
	public String exportar(ReporteExposicionProveedores reporte);
	
	public String exportar(ReporteOcupacionYCupoDisponible reporte);

	public String exportar(ReporteServiciosMasReservados reporte);
	
}
