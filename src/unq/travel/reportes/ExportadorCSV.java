package unq.travel.reportes;

import java.util.Map;

public class ExportadorCSV 
	implements Exportador<ReporteExposicionProveedores> {
	
	@Override
	public String exportar(ReporteExposicionProveedores reporte) {
		StringBuilder resultado = new StringBuilder();
	
		resultado.append("Proveedor;Exposicion (%)\n");
	
		for(Map.Entry<String, Double> entrada :
			reporte.getPorcentajesPorProveedor().entrySet()) {
		
			resultado.append(entrada.getKey());
			resultado.append(";");
			resultado.append(String.format("%.2f", entrada.getValue()));
			resultado.append("\n");
		}
	
		return resultado.toString();
	}
}
