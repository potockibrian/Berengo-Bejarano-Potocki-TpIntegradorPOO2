package unq.travel.reportes;

import java.util.Map;

public class ExportadorHTML 
	implements Exportador<ReporteExposicionProveedores> {

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
}
