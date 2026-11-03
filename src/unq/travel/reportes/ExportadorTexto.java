package unq.travel.reportes;

import java.util.Locale;
import java.util.Map;

public class ExportadorTexto 
	implements Exportador<ReporteExposicionProveedores> {
	@Override
	public String exportar(ReporteExposicionProveedores reporte) {
		StringBuilder resultado = new StringBuilder();
		
		resultado.append("Exposicion por proveedor\n");
		
		for(Map.Entry<String, Double> entrada : 
			reporte.getPorcentajesPorProveedor().entrySet()) {
			
			resultado.append(entrada.getKey());
			resultado.append(": ");
			resultado.append(String.format(
					Locale.US, "%.2f", entrada.getValue()
			));
			resultado.append("%n");
		}
		
		return resultado.toString();
	}
}
