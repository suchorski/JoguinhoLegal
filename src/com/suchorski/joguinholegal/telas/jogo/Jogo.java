package com.suchorski.joguinholegal.telas.jogo;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.Deque;

import com.suchorski.joguinholegal.carregador.Carregador;
import com.suchorski.joguinholegal.definicoes.Cores;
import com.suchorski.joguinholegal.definicoes.Parametros;
import com.suchorski.joguinholegal.entidades.Bloco;
import com.suchorski.joguinholegal.entidades.Jogador;
import com.suchorski.joguinholegal.telas.menu.FimDeJogo;
import com.suchorski.joguinholegal.telas.menu.MenuPrincipal;
import com.suchorski.joguinholegal.telas.outros.Erro;
import com.suchorski.motorgraficosimples.abstratos.entidades.Entidade;
import com.suchorski.motorgraficosimples.abstratos.imagens.Tela;
import com.suchorski.motorgraficosimples.principal.MotorGrafico;
import com.suchorski.motorgraficosimples.utilitarios.TextoUtils;

public class Jogo extends Tela {

	protected int mx;
	protected int my;
	protected int velocidade;
	protected Deque<Bloco> blocos;
	protected Jogador jogador;
	protected long pontuacao;
	protected int loop;
	protected boolean sair;

	public Jogo(MotorGrafico motorGrafico) {
		super(motorGrafico);
		mx = my = 0;
		velocidade = 1;
		blocos = new ArrayDeque<Bloco>();
		adicionarBloco();
		try {
			jogador = new Jogador(Carregador.imagensJogador(), getMotorGrafico(), 25, motorGrafico.getAltura() / 2);
		} catch (IOException e) {
			e.printStackTrace();
			mudarTela(new Erro(motorGrafico, e.getLocalizedMessage()));
		}
		pontuacao = 1;
		loop = 0;
		sair = false;
	}

	@Override
	public void atualiza() {
		if (sair) {
			mudarTela(new MenuPrincipal(getMotorGrafico()));
		} else {
			jogador.movimenta(mx * velocidade, my * velocidade);
			if (pontuacao % Parametros.JOGO_DISTANCIA == 0) {
				adicionarBloco();
			}
			while (blocos.size() > 0 && (blocos.getFirst().x + blocos.getFirst().width) < 0) {
				blocos.pop();
			}
			for (Bloco b : blocos) {
				--b.x;
			}
			verificarMorteJogador();
			++pontuacao;
		}
	}

	private void adicionarBloco() {
		int posicao = (int) (Math.random() * (getMotorGrafico().getAltura() - Parametros.JOGADOR_TAMANHO * 2 - 10) + Parametros.JOGADOR_TAMANHO + 5);
		int comprimento = (int) (Math.random() * Parametros.JOGO_COMPRIMENTO_RAND + Parametros.JOGO_COMPRIMENTO_MIN);
		int metadeTamanho = (int) (Math.random() * Parametros.JOGO_METADETAMANHO_RAND + Parametros.JOGO_METADETAMANHO_MIN);
		blocos.add(new Bloco(getMotorGrafico().getWidth(), 0, comprimento, posicao - metadeTamanho));
		blocos.add(new Bloco(getMotorGrafico().getWidth(), posicao + metadeTamanho, comprimento, getMotorGrafico().getAltura() - posicao - metadeTamanho));
	}

	protected boolean verificarMorte(Entidade entidade) {
		Rectangle areaDoJogo = new Rectangle(getMotorGrafico().getComprimento(), getMotorGrafico().getAltura());
		return !areaDoJogo.contains(entidade) || blocos.stream().filter(b -> b.isColidindo(entidade)).findAny().isPresent();
	}

	protected void verificarMorteJogador() {
		if (verificarMorte(jogador)) {
			mudarTela(new FimDeJogo(getMotorGrafico(), pontuacao / 100));
		}
	}

	@Override
	public void desenha(Graphics g) {
		g.setColor(Cores.FUNDO);
		g.fillRect(0, 0, getMotorGrafico().getComprimento(), getMotorGrafico().getAltura());
		Font fonte = new Font("Arial", 0, getMotorGrafico().getAltura() / 4);
		g.setColor(Cores.PENUMBRA);
		TextoUtils.escreverTextoCentralizado(g, String.format("%d", pontuacao / 100), new Rectangle(getMotorGrafico().getComprimento(), getMotorGrafico().getAltura()), fonte);
		for (Bloco b : blocos) {
			b.desenha(g);
		}
		g.setColor(Cores.BLOCO);
		g.drawRect(0, 0, getMotorGrafico().getComprimento() - 1, getMotorGrafico().getAltura() - 1);
		desenhaJogador(g);
	}

	protected void desenhaJogador(Graphics g) {
		jogador.desenha(g);
	}

	@Override
	public void teclaPressionada(KeyEvent e) {
		switch (e.getKeyCode()) {
		case KeyEvent.VK_UP:
			my = -1;
			break;
		case KeyEvent.VK_DOWN:
			my = 1;
			break;
		case KeyEvent.VK_LEFT:
			mx = -1;
			break;
		case KeyEvent.VK_RIGHT:
			mx = 1;
			break;
		case KeyEvent.VK_SHIFT:
			velocidade = Parametros.JOGADOR_VELOCICADE_MAX;
			break;
		default:
			break;
		}
	}

	@Override
	public void teclaSolta(KeyEvent e) {
		switch (e.getKeyCode()) {
		case KeyEvent.VK_UP:
			my = 0;
			break;
		case KeyEvent.VK_DOWN:
			my = 0;
			break;
		case KeyEvent.VK_LEFT:
			mx = 0;
			break;
		case KeyEvent.VK_RIGHT:
			mx = 0;
			break;
		case KeyEvent.VK_SHIFT:
			velocidade = Parametros.JOGADOR_VELOCICADE_MIN;
			break;
		case KeyEvent.VK_ESCAPE:
			sair = true;
			break;
		default:
			break;
		}
	}

}
