package unq.travel.reportes;

import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;

import unq.travel.catalogo.Categoria;
import unq.travel.catalogo.ItemCatalogo;
import unq.travel.cupos.Cupo;
import unq.travel.cupos.RangoDeFechas;

public class ExportadorHTML implements Exportador {

	@Override
	public String exportar(ReporteExposicionProveedores reporte) {
		
		StringBuilder resultado = new StringBuilder();
		
		resultado.append("<html>\n");
		resultado.append("<head>\n");
		resultado.append("<title>Exposicion por proveedor</title>\n");
		resultado.append("<head>\n");
		resultado.append("<body>\n");
		
		resultado.append("<h1>Exposicion por proveedor</h1>\n");
		resultado.append("<table>\n");
		
		resultado.append("<tr>");
		resultado.append("<th>Proveedor</th>");
		resultado.append("<th>Exposicion (%) </th>");
		resultado.append("</tr>\n");
		
		for (Map.Entry<String, Double> entrada : reporte.getPorcentajesPorProveedor().entrySet()) {
			
			resultado.append("<tr>");
			resultado.append("<td>");
			resultado.append(entrada.getKey());
			resultado.append("</td>");
			
			resultado.append("<td>");
			resultado.append(String.format("%.2f", entrada.getValue()));
			resultado.append("%");
			resultado.append("</td>");
			
			resultado.append("</tr>\n");
			
		}
		
		resultado.append("</table>\n");
		resultado.append("</body>\n");
		resultado.append("</html>\n");
		
		return resultado.toString();
		
	}
	
	@Override
	public String exportar(ReporteServiciosMasReservados reporte) {
		
		StringBuilder resultado = new StringBuilder();
		
		abrirDocumento(resultado, "Servicios mas reservados");
		
		resultado.append("<p>Periodo: ");
		resultado.append(escapar(String.valueOf(reporte.getPeriodo())));
		resultado.append("</p>\n");
		
		resultado.append("<table>\n");
		
		resultado.append("<tr>");
		resultado.append("<th>Servicio</th>");
		resultado.append("<th>Unidades vendidas</th>");
		resultado.append("<th>Precio promedio cobrado</th>");
		resultado.append("</tr>\n");
		
		for(Entry<ItemCatalogo, Integer> entrada : reporte.getItemsOrdenados()) {
			
			resultado.append("<tr>");
			resultado.append("<td>");
			resultado.append(escapar(entrada.getKey().getNombre()));
			resultado.append("</td>");
			
			resultado.append("<td>");
			resultado.append(entrada.getValue());
			resultado.append("</td>");
			
			resultado.append("<td>");
			resultado.append(String.format(Locale.US, "%.2f", reporte.getPrecioPromedioCobrado(entrada.getKey())));
			resultado.append("</td>");
			
			resultado.append("</tr>\n");
			
		}
		
		resultado.append("</table>\n");
		
		cerrarDocumento(resultado);
		
		return resultado.toString();
		
	}
	
	@Override
	public String exportar(ReporteOcupacionYCupoDisponible reporte) {
		
		StringBuilder resultado = new StringBuilder();
		
		abrirDocumento(resultado, "Ocupacion y cupo disponible");
		
		resultado.append("<h2>Por categoria</h2>\n");
		abrirTablaDeCupos(resultado, "Categoria");
		
		for(Entry<Categoria, List<Cupo>> entrada : reporte.getCuposPorCategoria()) {
			
			filaDeCupos(resultado, entrada.getKey().toString(), entrada.getValue());
			
		}
		
		resultado.append("</table>\n");
		
		resultado.append("<h2>Por periodo</h2>\n");
		abrirTablaDeCupos(resultado, "Periodo");
		
		for(Entry<RangoDeFechas, List<Cupo>> entrada : reporte.getCuposPorPeriodo()) {
			
			filaDeCupos(resultado, entrada.getKey().toString(), entrada.getValue());
			
		}
		
		resultado.append("</table>\n");
		
		cerrarDocumento(resultado);
		
		return resultado.toString();
		
	}
	
	private void abrirDocumento(StringBuilder resultado, String titulo) {
		
		resultado.append("<html>\n");
		resultado.append("<head>\n");
		resultado.append("<meta charset=\"UTF-8\">\n");
		resultado.append("<title>" + escapar(titulo) + "</title>\n");
		resultado.append("</head>\n");
		resultado.append("<body>\n");
		resultado.append("<h1>" + escapar(titulo) + "</h1>\n");
		
	}
	
	private void cerrarDocumento(StringBuilder resultado) {
		
		resultado.append("</body>\n");
		resultado.append("</html>\n");
		
	}
	
	private void abrirTablaDeCupos(StringBuilder resultado, String columnaClave) {
		
		resultado.append("<table>\n");
		resultado.append("<tr>");
		resultado.append("<th>" + columnaClave + "</th>");
		resultado.append("<th>Cupos totales</th>");
		resultado.append("<th>Cupos reservados</th>");
		resultado.append("<th>Cupos libres</th>");
		resultado.append("</tr>\n");
		
	}
	
	private void filaDeCupos(StringBuilder resultado, String clave, List<Cupo> cupos) {
		
		resultado.append("<tr>");
		resultado.append("<td>" + escapar(clave) + "</td>");
		resultado.append("<td>" + cupos.stream().mapToInt(c -> c.getTopeMaximo()).sum() + "</td>");
		resultado.append("<td>" + cupos.stream().mapToInt(c -> c.cantidadDeVentas()).sum() + "</td>");
		resultado.append("<td>" + cupos.stream().mapToInt(c -> c.cantidadDeCuposDisponibles()).sum() + "</td>");
		resultado.append("</tr>\n");
		
	}
	
	String escapar(String valor) {
		
		return String.valueOf(valor).replace("&", "&amp;")
									.replace("<", "&lt;")
									.replace(">", "&gt;")
									.replace("\"", "&quot;")
									.replace("'", "&#39;");
		
	}
}
