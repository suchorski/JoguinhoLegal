package com.suchorski.joguinholegal.ia;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.List;
import java.util.stream.Collectors;

import com.suchorski.joguinholegal.definicoes.Cores;
import com.suchorski.joguinholegal.entidades.Bloco;

public class InteligenciaArtificial {
	
	private List<Cerebro> cerebros;
	
	public InteligenciaArtificial(int tamanho, int x, int y) {
		this.cerebros = new ArrayList<Cerebro>(tamanho);
		for (int i = 0; i < tamanho; ++i) {
			this.cerebros.add(new Cerebro(x, y));
		}
	}
	
	public List<Cerebro> getCerebros() {
		return cerebros;
	}
	
	public void carregarDna(List<Double> dna) {
		cerebros.parallelStream().forEach(c -> {
			c.setDna(dna);
			c.recalcularPesos();
		});
		cerebros.get(0).setDna(dna);
	}
	
	public void pontuar(long pontuacao) {
		cerebros.parallelStream().forEach(c -> c.setPontuacao(pontuacao));
	}
	
	public void pensar(Deque<Bloco> blocos) {
		for (Cerebro c : cerebros) {
			List<Bloco> paredes = proximoBloco(c, blocos);
			List<Double> entrada = Arrays.asList(c.getY(), paredes.get(0).getHeight());
			c.pensar(entrada);
		}
	}
	
	private List<Bloco> proximoBloco(Cerebro cerebro, Deque<Bloco> blocos) {
		List<Bloco> paredes = blocos.stream().collect(Collectors.toList());
		int pos = -1;
		for (int i = 0; i < paredes.size(); i += 2) {
			if (cerebro.x < (paredes.get(i).x + paredes.get(i).width + 1)) {
				pos = i;
				break;
			}
		}
		pos = Math.max(pos, 0);
		return Arrays.asList(paredes.get(pos), paredes.get(pos + 1));
	}
	
	public void desenha(Graphics g, Deque<Bloco> blocos) {
		cerebros.parallelStream().filter(c -> c.isVivo()).forEach(c -> c.desenha(g));
		Cerebro melhor = RedeNeuralUtils.selecionarMelhor(cerebros);
		List<Bloco> paredes = proximoBloco(melhor, blocos);
		g.setColor(Cores.SENSOR);
		g.drawLine(melhor.x + melhor.width, melhor.y, paredes.get(0).x, paredes.get(0).y + paredes.get(0).height);
		g.drawLine(melhor.x + melhor.width, melhor.y, paredes.get(0).x + paredes.get(0).width, paredes.get(0).y + paredes.get(0).height);
		g.drawLine(melhor.x + melhor.width, melhor.y + melhor.width, paredes.get(1).x, paredes.get(1).y);
		g.drawLine(melhor.x + melhor.width, melhor.y + melhor.width, paredes.get(1).x + paredes.get(0).width, paredes.get(1).y);
		g.drawRect(paredes.get(0).x, paredes.get(0).y + paredes.get(0).height, paredes.get(0).width, paredes.get(1).y - paredes.get(0).height);
		g.drawRect(melhor.x, melhor.y, melhor.width - 1, melhor.height - 1);
	}

}
