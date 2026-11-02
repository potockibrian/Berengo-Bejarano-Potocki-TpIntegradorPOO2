package unq.travel.cicloReserva;

import java.util.ArrayList;
import java.util.List;

import unq.travel.traslado.Voucher;

public class Cliente {

	private List<Comprobante> comprobantes;
	private List<Voucher> vouchers;
	private String email;

	public Cliente(String email, List<Comprobante> comprobantes, List<Voucher> vouchers) {
		
		this.email = email;
		this.comprobantes = comprobantes;
		this.vouchers = vouchers;
		
	}
	
	public String getDireccionDeEmail() {

		return email;
		
	}

	public void añadirVoucher(Voucher any) {

		vouchers.add(any);
		
	}

	public void añadirComprobante(Comprobante any) {

		comprobantes.add(any);
		
	}

}
