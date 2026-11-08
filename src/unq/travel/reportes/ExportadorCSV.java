package unq.travel.reportes;

import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;

import unq.travel.catalogo.*;
import unq.travel.cupos.*;

public class ExportadorCSV implements Exportador {
	
	@Override
	public String exportar(ReporteExposicionProveedores reporte) {
		
		StringBuilder resultado = new StringBuilder();
	
		resultado.append("Proveedor;Exposicion (%)\n");
	
		for(Map.Entry<String, Double> entrada : reporte.getPorcentajesPorProveedor().entrySet()) {
		
			resultado.append(entrada.getKey());
			resultado.append(";");
			resultado.append(String.format("%.2f", entrada.getValue()));
			resultado.append("\n");
			
		}
	
		return resultado.toString();
		
	}
	
	@Override
	public String exportar(ReporteServiciosMasReservados reporte) {
		
		StringBuilder resultado = new StringBuilder();
		
		resultado.append("Servicio;Unidades vendidas;Precio promedio cobrado;Periodo\n");
		
		for(Map.Entry<ItemCatalogo, Integer> entrada : reporte.getItemsOrdenados()) {
			
			resultado.append(escapar(entrada.getKey().getNombre()));
			resultado.append(";");
			resultado.append(entrada.getValue());
			resultado.append(";");
			resultado.append(String.format(Locale.US, "%.2f", reporte.getPrecioPromedioCobrado(entrada.getKey())));
			resultado.append(";");
			resultado.append(escapar(String.valueOf(reporte.getPeriodo())));
			resultado.append("\n");
			
		}
		
		return resultado.toString();
		
	}
	
	@Override
	public String exportar(ReporteOcupacionYCupoDisponible reporte) {
		
		StringBuilder resultado = new StringBuilder();
		
		resultado.append("Agrupacion;Valor;Cupos totales;Cupos reservados;Cupos libres\n");
		
		for(Entry<Categoria, List<Cupo>> entrada : reporte.getCuposPorCategoria()) {
			
			resultado.append(filaDeCupos("Categoria", entrada.getKey().toString(), entrada.getValue()));
			
		}
		
		for(Entry<RangoDeFechas, List<Cupo>> entrada : reporte.getCuposPorPeriodo()) {
			
			resultado.append(filaDeCupos("Periodo", entrada.getKey().toString(), entrada.getValue()));
			
		}
		
		return resultado.toString();
		
	}
	
	private String filaDeCupos(String agrupacion, String valor, List<Cupo> cupos) {
		
		return agrupacion
			 + ";" + escapar(valor)
			 + ";" + cupos.stream().mapToInt(c -> c.getTopeMaximo()).sum()
			 + ";" + cupos.stream().mapToInt(c -> c.cantidadDeVentas()).sum()
			 + ";" + cupos.stream().mapToInt(c -> c.cantidadDeCuposDisponibles()).sum()
			 + "\n";
		
	}
	
	public String escapar(String valor) {
		
		String texto = String.valueOf(valor);
		
		if(texto.contains(";") || texto.contains("\"") || texto.contains("\n") || texto.contains("\r")) {
			
			return "\"" + texto.replace("\"", "\"\"") + "\"";
			
		}
		
		return texto;
		
	}
}
