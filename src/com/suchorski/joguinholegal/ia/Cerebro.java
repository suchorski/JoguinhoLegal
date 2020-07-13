package com.suchorski.joguinholegal.ia;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Arrays;
import java.util.List;

import com.suchorski.joguinholegal.definicoes.Parametros;
import com.suchorski.motorgraficosimples.abstratos.entidades.Entidade;
import com.suchorski.motorgraficosimples.fisica.CorpoRigido;
import com.suchorski.redeneuralsimples.ativadores.AtivadorMaiorQueZero;
import com.suchorski.redeneuralsimples.funcoesdeativacao.FuncaoDeAtivacaoReluDX;
import com.suchorski.redeneuralsimples.objetos.Neuronio;
import com.suchorski.redeneuralsimples.objetos.RedeNeural;

public class Cerebro extends Entidade implements CorpoRigido {
	
	private RedeNeural redeNeural;
	private boolean vivo;
	private long pontuacao;
	private Color cor;
	
	public Cerebro(int x, int y) {
		super(x, y, Parametros.JOGADOR_TAMANHO, Parametros.JOGADOR_TAMANHO);
		this.redeNeural = new RedeNeural(2, 5, Arrays.asList(9, 5), new FuncaoDeAtivacaoReluDX(), new AtivadorMaiorQueZero());
		vivo = true;
		pontuacao = 0;
		this.cor = new Color((int) (Math.random() * 256), (int) (Math.random() * 256), (int) (Math.random() * 256));
	}
	
	public RedeNeural getRedeNeural() {
		return redeNeural;
	}
	
	public boolean isVivo() {
		return vivo;
	}
	
	public void setVivo(boolean vivo) {
		this.vivo = vivo;
	}
	
	public long getPontuacao() {
		return pontuacao;
	}
	
	public void setPontuacao(long pontuacao) {
		if (vivo) {
			this.pontuacao = pontuacao;
		}
	}
	
	public void pensar(List<java.lang.Double> entrada) {
		List<Neuronio> saida = redeNeural.calcular(entrada);
		int velocidade = saida.get(0).isAtivo() ? Parametros.JOGADOR_VELOCICADE_MAX : Parametros.JOGADOR_VELOCICADE_MIN;
		if (saida.get(1).isAtivo()) {
			y += velocidade;
		}
		if (saida.get(2).isAtivo()) {
			y -= velocidade;
		}
		if (saida.get(3).isAtivo()) {
			x += velocidade;
		}
		if (saida.get(4).isAtivo()) {
			x -= velocidade;
		}
	}
	
	@Override
	public void desenha(Graphics g) {
		if (vivo) {
			g.setColor(cor);
			g.fillRect(x, y, width, height);
		}
	}

	public List<java.lang.Double> getDna() {
		return redeNeural.getDna();
	}

	public void setDna(List<java.lang.Double> dna) {
		redeNeural.setDna(dna);
	}

	public void recalcularPesos() {
		redeNeural.recalcularPesos();
	}

	@Override
	public boolean isColidindo(Entidade entidade) {
		boolean colidindo = this.intersects(entidade);
		if (vivo && colidindo) {
			vivo = false;
		}
		return colidindo;
	}

}
