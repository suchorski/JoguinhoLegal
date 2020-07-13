package com.suchorski.joguinholegal.entidades;

import java.awt.Graphics;

import com.suchorski.joguinholegal.definicoes.Cores;
import com.suchorski.motorgraficosimples.abstratos.entidades.Entidade;
import com.suchorski.motorgraficosimples.fisica.CorpoRigido;

public class Bloco extends Entidade implements CorpoRigido {
	
	public Bloco(int x, int y, int comprimento, int altura) {
		super(x, y, comprimento, altura);
	}
	
	@Override
	public void desenha(Graphics g) {
		g.setColor(Cores.BLOCO);
		g.fillRect(x, y, width, height);
	}

	@Override
	public boolean isColidindo(Entidade entidade) {
		return this.intersects(entidade);
	}

}
