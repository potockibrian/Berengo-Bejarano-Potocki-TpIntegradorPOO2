package unq.travel.reportes;

import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;

import unq.travel.catalogo.*;
import unq.travel.cupos.Cupo;
import unq.travel.cupos.RangoDeFechas;

public class ExportadorTexto implements Exportador {
	
	@Override
	public String exportar(ReporteExposicionProveedores reporte) {
		
		StringBuilder resultado = new StringBuilder();
		
		resultado.append("Exposicion por proveedor\n");
		
		for(Map.Entry<String, Double> entrada : reporte.getPorcentajesPorProveedor().entrySet()) {
			
			resultado.append(entrada.getKey());
			resultado.append(": ");
			resultado.append(String.format(Locale.US, "%.2f", entrada.getValue()));
			resultado.append("\n");
			
		}
		
		return resultado.toString();
		
	}
	

	@Override
	public String exportar(ReporteServiciosMasReservados reporte) {
		
		StringBuilder resultado = new StringBuilder();
		
		resultado.append("Servicios mas reservados\n");
		resultado.append("Periodo: ");
		resultado.append(reporte.getPeriodo());
		resultado.append("\n");
		
		for(Map.Entry<ItemCatalogo, Integer> entrada : reporte.getItemsOrdenados()) {
			
			resultado.append(entrada.getKey().getNombre());
			resultado.append(": ");
			resultado.append(entrada.getValue());
			resultado.append(" unidades, cantidad ");
			resultado.append(String.format(Locale.US, "%.2f", reporte.getPrecioPromedioCobrado(entrada.getKey())));
			resultado.append("\n");
			
		}
		
		return resultado.toString();
		
	}
	
	@Override
	public String exportar(ReporteOcupacionYCupoDisponible reporte) {
		
		StringBuilder resultado = new StringBuilder();
		
		resultado.append("Ocupacion y cupo disponible\n");
		
		resultado.append("Por categoria\n");
		
		for(Map.Entry<Categoria, List<Cupo>> entrada : reporte.getCuposPorCategoria()) {
			
			resultado.append(entrada.getKey());
			resultado.append(": ");
			resultado.append(describirCupos(entrada.getValue()));
			resultado.append("\n");
			
		}
		
		resultado.append("Por periodo\n");
		
		for(Entry<RangoDeFechas, List<Cupo>> entrada : reporte.getCuposPorPeriodo()) {
			
			resultado.append(entrada.getKey());
			resultado.append(": ");
			resultado.append(describirCupos(entrada.getValue()));
			resultado.append("\n");
			
		}
		
		return resultado.toString();
		
	}
	
	private String describirCupos(List<Cupo> cupos) {
		
		return "totales " + cupos.stream().mapToInt(c -> c.getTopeMaximo()).sum()
			 + ", reservados " + cupos.stream().mapToInt(c -> c.cantidadDeVentas()).sum()
			 + ", libres " + cupos.stream().mapToInt(c -> c.cantidadDeCuposDisponibles()).sum();
		
	}
	
}