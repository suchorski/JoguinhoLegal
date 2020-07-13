package com.suchorski.joguinholegal.telas.menu;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;

import com.suchorski.joguinholegal.definicoes.Cores;
import com.suchorski.motorgraficosimples.abstratos.imagens.Tela;
import com.suchorski.motorgraficosimples.principal.MotorGrafico;
import com.suchorski.motorgraficosimples.utilitarios.TextoUtils;

public class FimDeJogo extends Tela {
	
	private long pontuacao;

	public FimDeJogo(MotorGrafico motorGrafico, long l) {
		super(motorGrafico);
		this.pontuacao = l;
	}
	
	@Override
	public void desenha(Graphics g) {
		g.setColor(Cores.FUNDO);
		g.fillRect(0, 0, getMotorGrafico().getComprimento(), getMotorGrafico().getAltura());
		Font fonte = new Font("Arial", 0, getMotorGrafico().getAltura() / 10);
		g.setColor(Cores.TEXTO);
		TextoUtils.escreverTextoCentralizado(g, "Fim de Jogo!", new Rectangle(getMotorGrafico().getComprimento(), getMotorGrafico().getAltura() / 2), fonte);
		TextoUtils.escreverTextoCentralizado(g, String.format("Pontuação: %d", pontuacao), new Rectangle(0, getMotorGrafico().getAltura() / 2, getMotorGrafico().getComprimento(), getMotorGrafico().getAltura() / 2), fonte);
	}
	
	@Override
	public void teclaPressionada(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_ENTER || e.getKeyCode() == KeyEvent.VK_ESCAPE) {
			getMotorGrafico().setTela(new MenuPrincipal(getMotorGrafico()));
		}
	}

}
