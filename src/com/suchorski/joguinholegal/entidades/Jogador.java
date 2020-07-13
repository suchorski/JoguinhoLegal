package com.suchorski.joguinholegal.entidades;

import java.awt.Image;
import java.util.List;

import com.suchorski.joguinholegal.definicoes.Parametros;
import com.suchorski.motorgraficosimples.abstratos.entidades.Entidade;
import com.suchorski.motorgraficosimples.entidades.EntidadeAnimada;
import com.suchorski.motorgraficosimples.fisica.CorpoRigido;
import com.suchorski.motorgraficosimples.principal.MotorGrafico;

public class Jogador extends EntidadeAnimada implements CorpoRigido {
	
	public Jogador(List<Image> imagens, MotorGrafico motorGrafico, int x, int y) {
		super(imagens, 100, motorGrafico, x, y, Parametros.JOGADOR_TAMANHO, Parametros.JOGADOR_TAMANHO);
	}

	@Override
	public boolean isColidindo(Entidade entidade) {
		return this.intersects(entidade);
	}
	
	public void movimenta(int x, int y) {
		this.x += x;
		this.y += y;
	}

}
