package unq.travel.reportes;

import java.time.LocalDate;
import unq.travel.catalogo.ItemCatalogo;

public class RegistroVenta {
	private ItemCatalogo item;
	private LocalDate fecha;
	private float importeCobrado;
	
	public RegistroVenta(
			ItemCatalogo item, 
			LocalDate fecha,
			float importeCobrado) {
		this.item = item;
		this.fecha = fecha;
		this.importeCobrado = importeCobrado;
	}
	
	public ItemCatalogo getItem() {
		return item;
	}
	
	public LocalDate getFecha() {
		return fecha;
	}
	
	public float getImporteCobrado() {
		return importeCobrado;
	}
}
