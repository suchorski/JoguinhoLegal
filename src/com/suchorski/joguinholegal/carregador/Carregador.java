package com.suchorski.joguinholegal.carregador;

import java.awt.Image;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

public class Carregador {

	public static List<Image> imagensJogador() throws IOException {
		List<Image> imagens = new ArrayList<Image>();
		for (int i = 1; i <= 4; ++i) {
			imagens.add(ImageIO.read(Carregador.class.getClassLoader().getResource(String.format("jogador/Jogador-%d.png", i))));
		}
		return imagens;
	}

}
