package unq.travel.reportes;

import java.util.HashMap;
import java.util.Map;

public class ReporteExposicionProveedores {
	
	// Asocia c/proveedor con su porcentaje de exposicion
	private Map<String, Double> porcentajesPorProveedor;
	
	public ReporteExposicionProveedores(Map<String, Double> porcentajesPorProveedor) {
		
		// Guarda una copia del mapa recibido
		this.porcentajesPorProveedor = new HashMap<String, Double>(porcentajesPorProveedor);
	
	}
	
	// consultar un proveedor inexistente devuelve 0
	public double getPorcentajePorProveedor(String proveedor) {
		
		return porcentajesPorProveedor.getOrDefault(proveedor, 0.0);
		
	}
	
	public Map<String, Double> getPorcentajesPorProveedor() {
		
		return new HashMap<String, Double>(porcentajesPorProveedor);
		
	}
	
}