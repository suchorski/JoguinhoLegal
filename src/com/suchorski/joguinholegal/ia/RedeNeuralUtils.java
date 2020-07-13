package com.suchorski.joguinholegal.ia;

import java.util.List;

public class RedeNeuralUtils extends com.suchorski.redeneuralsimples.utils.RedeNeuralUtils {

	public static boolean populacaoMorta(List<Cerebro> redesNeurais) {
		for (Cerebro c : redesNeurais) {
			if (c.isVivo()) {
				return false;
			}
		}
		return true;
	}

	public static Cerebro selecionarMelhor(List<Cerebro> redesNeurais) {
		Cerebro melhor = null;
		for (Cerebro c : redesNeurais) {
			if (melhor == null || melhor.getPontuacao() < c.getPontuacao()) {
				melhor = c;
			}
		}
		return melhor;
	}

	public static Cerebro clonarMelhor(List<Cerebro> redesNeurais, boolean recalcularPesos) {
		Cerebro melhor = selecionarMelhor(redesNeurais);
		for (Cerebro r : redesNeurais) {
			r.setDna(melhor.getDna());
			if (recalcularPesos) {
				r.recalcularPesos();
			}
		}
		redesNeurais.get(0).setDna(melhor.getDna());
		return melhor;
	}

}
