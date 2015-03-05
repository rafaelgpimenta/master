package pt.c01interfaces.s01knowledge.s02app.actors;

import pt.c01interfaces.s01knowledge.s01base.impl.BaseConhecimento;
import pt.c01interfaces.s01knowledge.s01base.inter.IBaseConhecimento;
import pt.c01interfaces.s01knowledge.s01base.inter.IDeclaracao;
import pt.c01interfaces.s01knowledge.s01base.inter.IEnquirer;
import pt.c01interfaces.s01knowledge.s01base.inter.IObjetoConhecimento;
import pt.c01interfaces.s01knowledge.s01base.inter.IResponder;

import java.util.ArrayList;
import java.util.List;


public class Enquirer implements IEnquirer
{
	IObjetoConhecimento obj;

	public Enquirer()
	{
	}


	@Override
	public void connect(IResponder responder)
	{
		List<String> armazenaPergunta = new ArrayList<String>();
		List<String> armazenaResposta = new ArrayList<String>();
		IBaseConhecimento bc = new BaseConhecimento();
		String[] bichos = bc.listaNomes();
		int i = 0;
		IDeclaracao decl = null;
		do{

			obj = bc.recuperaObjeto(bichos[i]);
			decl = obj.primeira();

			boolean animalEsperado = true;
			while (decl != null && animalEsperado) {
				String pergunta = decl.getPropriedade();
				String respostaEsperada = decl.getValor();

				if(!armazenaPergunta.contains(pergunta)){
					String resposta = responder.ask(pergunta);
					armazenaPergunta.add(pergunta);
					armazenaResposta.add(resposta);

					if (resposta.equalsIgnoreCase(respostaEsperada))
						decl = obj.proxima();
					else
						animalEsperado = false;
				}
				else{
					int indexResposta;
					indexResposta = armazenaPergunta.indexOf(pergunta);
					if (armazenaResposta.get(indexResposta).equalsIgnoreCase(respostaEsperada))
						decl = obj.proxima();
					else
						animalEsperado = false;
				}
			}


			i++;
		}while(i < bichos.length && decl != null);
		boolean acertei = responder.finalAnswer(bichos[i]);

		if (acertei)
			System.out.println("Oba! Acertei!");
		else
			System.out.println("fuem! fuem! fuem!");

	}

}
