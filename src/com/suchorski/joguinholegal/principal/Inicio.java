package com.suchorski.joguinholegal.principal;

import com.suchorski.joguinholegal.telas.menu.MenuPrincipal;
import com.suchorski.motorgraficosimples.principal.MotorGrafico;

public class Inicio {
	
	public static void main(String[] args) {
		MotorGrafico motorGrafico = new MotorGrafico("Joguinho Legal", 800, 600, 60);
		MenuPrincipal menuPrincipal = new MenuPrincipal(motorGrafico);
		motorGrafico.setTela(menuPrincipal);
		motorGrafico.iniciar();
	}

}