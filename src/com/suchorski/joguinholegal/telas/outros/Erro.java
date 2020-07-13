package com.suchorski.joguinholegal.telas.outros;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;

import com.suchorski.joguinholegal.definicoes.Cores;
import com.suchorski.joguinholegal.telas.menu.MenuPrincipal;
import com.suchorski.motorgraficosimples.abstratos.imagens.Tela;
import com.suchorski.motorgraficosimples.principal.MotorGrafico;
import com.suchorski.motorgraficosimples.utilitarios.TextoUtils;

public class Erro extends Tela {
	
	private String texto;

	public Erro(MotorGrafico motorGrafico, String texto) {
		super(motorGrafico);
		this.texto = texto;
	}
	
	@Override
	public void desenha(Graphics g) {
		g.setColor(Cores.FUNDO);
		g.fillRect(0, 0, getMotorGrafico().getComprimento(), getMotorGrafico().getAltura());
		Font fonte = new Font("Arial", 0, getMotorGrafico().getAltura() / 10);
		g.setColor(Cores.TEXTO);
		TextoUtils.escreverTextoCentralizado(g, texto, new Rectangle(getMotorGrafico().getComprimento(), getMotorGrafico().getAltura()), fonte);
	}
	
	@Override
	public void teclaPressionada(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_ENTER || e.getKeyCode() == KeyEvent.VK_ESCAPE) {
			getMotorGrafico().setTela(new MenuPrincipal(getMotorGrafico()));
		}
	}

}
