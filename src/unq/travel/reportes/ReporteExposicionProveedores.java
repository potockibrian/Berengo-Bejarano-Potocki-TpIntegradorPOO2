package unq.travel.reportes;

import java.util.HashMap;
import java.util.Map;

import unq.travel.catalogo.Catalogo;

public class ReporteExposicionProveedores implements Reporte{
	
	// Asocia c/proveedor con su porcentaje de exposicion
	private BolsaDeProveedores proveedores;
	
	public ReporteExposicionProveedores(Catalogo catalogo) {
		
		// Guarda una copia del mapa recibido
		this.proveedores = catalogo.getItems().stream()
				.map(i -> i.proveedores())
				.reduce(new BolsaDeProveedores(), (acumulador, bolsa) -> acumulador.combinarConBolsaDeProveedores(bolsa));
	
	}
	
	// consultar un proveedor inexistente devuelve 0
	public double getPorcentajePorProveedor(String proveedor) {
		
		return proveedores.getPorcentajePorProveedor(proveedor);
		
		//return porcentajesPorProveedor.getOrDefault(proveedor, 0.0);
		
	}
	
	public Map<String, Double> getPorcentajesPorProveedor() {
		
		return proveedores.getPorcentajesPorProveedor();
		
		//return new HashMap<String, Double>(porcentajesPorProveedor);
		
	}

	@Override
	public String exportar(Exportador exportador) {

		return exportador.exportar(this);
		
	}
	
}