package com.suchorski.joguinholegal.telas.jogo;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.suchorski.joguinholegal.definicoes.Parametros;
import com.suchorski.joguinholegal.ia.Cerebro;
import com.suchorski.joguinholegal.ia.InteligenciaArtificial;
import com.suchorski.joguinholegal.ia.RedeNeuralUtils;
import com.suchorski.motorgraficosimples.abstratos.imagens.Tela;
import com.suchorski.motorgraficosimples.principal.MotorGrafico;
import com.suchorski.redeneuralsimples.objetos.RedeNeural;
import com.suchorski.redeneuralsimplesgrafical.graficos.RedeNeuralGrafical;

public class JogoAprenderIA extends Jogo {
	
	private InteligenciaArtificial ia;
	private int populacao;
	private boolean rapido;
	private int contagemMelhor;
	private long melhorPontuacaoTodos;
	private List<Double> melhorDnaTodos;
	private RedeNeuralGrafical grafico;

	public JogoAprenderIA(MotorGrafico motorGrafico, int populacao, boolean rapido, List<Double> dna, int contagemMelhor, long melhorPontuacaoTodos, List<Double> melhorDnaTodos) {
		super(motorGrafico);
		this.ia = new InteligenciaArtificial(populacao, 25, motorGrafico.getAltura() / 2);
		this.populacao = populacao;
		this.rapido = rapido;
		this.contagemMelhor = contagemMelhor;
		this.melhorPontuacaoTodos = melhorPontuacaoTodos;
		this.melhorDnaTodos = melhorDnaTodos;
		atualizaVelocidade();
		if (dna == null) {
			try {
				if (this.melhorDnaTodos == null) {
					this.melhorDnaTodos = new ArrayList<Double>();
				}
				this.melhorPontuacaoTodos = com.suchorski.redeneuralsimples.utils.RedeNeuralUtils.carregarDna(Parametros.IA_DNA, this.melhorDnaTodos);
				ia.carregarDna(this.melhorDnaTodos);
			} catch (FileNotFoundException e) {
				System.err.println("Criando novo arquivo de DNA");
			}
		} else {
			ia.carregarDna(dna);
		}
		if (this.contagemMelhor > Parametros.IA_CONTAGEM && this.melhorDnaTodos != null) {
			this.contagemMelhor = 1;
			ia.carregarDna(melhorDnaTodos);
		}
	}
	public JogoAprenderIA(MotorGrafico motorGrafico, int populacao) {
		this(motorGrafico, populacao, true, null, 1, -1, null);
	}
	
	@Override
	public void mudarTela(Tela tela) {
		super.mudarTela(tela);
		if (tela instanceof JogoAprenderIA) {
			((JogoAprenderIA) tela).setGrafico(grafico);
		}
	}

	@Override
	public void atualiza() {
		super.atualiza();
		ia.pensar(blocos);
		RedeNeural melhor = RedeNeuralUtils.selecionarMelhor(ia.getCerebros()).getRedeNeural();
		if (grafico == null) {
			grafico = new RedeNeuralGrafical(melhor);
		}
		grafico.setRedeNeural(melhor);
		verificarMorte();
		ia.pontuar(pontuacao);
	}

	protected void verificarMorte() {
		if (!RedeNeuralUtils.populacaoMorta(ia.getCerebros())) {
			// TODO: modificado de parallelStream para stream
			ia.getCerebros().stream().filter(c -> c.isVivo()).forEach(c -> {
				if (verificarMorte(c)) {
					c.setVivo(false);
				}
			});
		} else {
			Cerebro melhorCerebro = RedeNeuralUtils.clonarMelhor(ia.getCerebros(), true);
			if (melhorCerebro.getPontuacao() > melhorPontuacaoTodos) {
				melhorPontuacaoTodos = melhorCerebro.getPontuacao();
				melhorDnaTodos = new ArrayList<Double>(melhorCerebro.getDna());
				try {
					com.suchorski.redeneuralsimples.utils.RedeNeuralUtils.salvarDna(Parametros.IA_DNA, melhorDnaTodos, melhorPontuacaoTodos);
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
				contagemMelhor = 1;
			}
			grafico.setTextos(Arrays.asList("Melhor de todos: " + melhorPontuacaoTodos / 100, "Contagem: " + contagemMelhor + "/" + Parametros.IA_CONTAGEM));
			mudarTela(new JogoAprenderIA(getMotorGrafico(), populacao, rapido, melhorCerebro.getDna(), contagemMelhor + 1, melhorPontuacaoTodos, melhorDnaTodos));
		}
	}

	@Override
	protected void verificarMorteJogador() {
		return;
	}

	@Override
	public void desenha(Graphics g) {
		super.desenha(g);
		ia.desenha(g, blocos);
	}

	@Override
	protected void desenhaJogador(Graphics g) {
		return;
	}
	
	private void setGrafico(RedeNeuralGrafical grafico) {
		this.grafico = grafico;
	}

	@Override
	public void teclaSolta(KeyEvent e) {
		switch (e.getKeyCode()) {
		case KeyEvent.VK_F:
			rapido = !rapido;
			atualizaVelocidade();
			break;
		case KeyEvent.VK_ESCAPE:
			getMotorGrafico().setFps(60);
			try {
				Cerebro melhorCerebro = RedeNeuralUtils.clonarMelhor(ia.getCerebros(), true);
				if (melhorCerebro.getPontuacao() > melhorPontuacaoTodos) {
					com.suchorski.redeneuralsimples.utils.RedeNeuralUtils.salvarDna(Parametros.IA_DNA, melhorCerebro.getDna(), melhorCerebro.getPontuacao());
				}
			} catch (FileNotFoundException ex) {
				ex.printStackTrace();
			}
			sair = true;
			break;
		default:
			break;
		}
		super.teclaPressionada(e);
	}

	private void atualizaVelocidade() {
		getMotorGrafico().setFps(rapido ? 500 : 60);
	}

}
