package com.suchorski.joguinholegal.telas.menu;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.util.Arrays;

import com.suchorski.joguinholegal.definicoes.Cores;
import com.suchorski.joguinholegal.telas.jogo.Jogo;
import com.suchorski.motorgraficosimples.abstratos.agrupadores.AgrupadorItemMenu;
import com.suchorski.motorgraficosimples.abstratos.imagens.Tela;
import com.suchorski.motorgraficosimples.itens.ItemMenuTexto;
import com.suchorski.motorgraficosimples.principal.MotorGrafico;
import com.suchorski.motorgraficosimples.utilitarios.CentralizadorUtils;

public class MenuPrincipal extends Tela {

	private AgrupadorItemMenu agrupadorItemMenu;

	public MenuPrincipal(MotorGrafico motorGrafico) {
		super(motorGrafico);
		Font plainFont = new Font("Serif", Font.PLAIN, 24);        
		ItemMenuTexto imt1 = new ItemMenuTexto(plainFont, "Jogar", 300, 60);
		ItemMenuTexto imt2 = new ItemMenuTexto(plainFont, "IA", 300, 60);
		ItemMenuTexto imt3 = new ItemMenuTexto(plainFont, "Sair", 300, 60);
		agrupadorItemMenu = new AgrupadorItemMenu(Arrays.asList(imt1, imt2, imt3), true);
		CentralizadorUtils.layoutVertical(new Rectangle(0, 0, motorGrafico.getComprimento(), motorGrafico.getAltura()), imt1, imt2, imt3);
	}

	@Override
	public void desenha(Graphics g) {
		g.setColor(Cores.FUNDO);
		g.fillRect(0, 0, getMotorGrafico().getComprimento(), getMotorGrafico().getAltura());
		agrupadorItemMenu.desenha(g);
	}

	@Override
	public void teclaPressionada(KeyEvent e) {
		switch (e.getKeyCode()) {
		case KeyEvent.VK_UP:
			agrupadorItemMenu.anterior();
			break;
		case KeyEvent.VK_DOWN:
			agrupadorItemMenu.proximo();
			break;
		case KeyEvent.VK_ENTER:
			switch (agrupadorItemMenu.getIndiceSelecionado()) {
			case 0:
				mudarTela(new Jogo(getMotorGrafico()));
				break;
			case 1:
				mudarTela(new MenuIA(getMotorGrafico()));
				break;
			default:
				System.exit(0);
			}
			break;
		case KeyEvent.VK_ESCAPE:
			System.exit(0);
			break;
		default:
			break;
		}
	}

}
