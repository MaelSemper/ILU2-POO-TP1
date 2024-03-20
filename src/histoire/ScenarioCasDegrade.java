package histoire;

import villagegaulois.Etal;
import personnages.Gaulois;

public class ScenarioCasDegrade {
	
	public static void main(String[] args) throws IllegalArgumentException {
		Gaulois bonemine = new Gaulois("bonemine",3);
		Etal etal = new Etal();
		etal.occuperEtal(bonemine, "Fleurs", 10);
		try {
			etal.acheterProduit(0,bonemine);
		} catch (IllegalArgumentException e){
			e.printStackTrace();
		}
		System.out.println("Fin du test");
		}

}
