package unq.travel.reportes;

public interface Exportador {
	
	String exportar(ReporteExposicionProveedores reporte);
	
	String exportar(ReporteOcupacionYCupoDisponible reporte);

	String exportar(ReporteServiciosMasReservados reporte);
	
}
