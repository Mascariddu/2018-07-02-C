package it.polito.tdp.extflightdelays.model;

public class Adiacenza{
	
	private int a1;
	private int a2;
	private double peso;
	public int getA1() {
		return a1;
	}
	public void setA1(int a1) {
		this.a1 = a1;
	}
	public int getA2() {
		return a2;
	}
	public void setA2(int a2) {
		this.a2 = a2;
	}
	public double getPeso() {
		return peso;
	}
	public void setPeso(double peso) {
		this.peso = peso;
	}
	public Adiacenza(int a1, int a2, double peso) {
		super();
		this.a1 = a1;
		this.a2 = a2;
		this.peso = peso;
	}

}
