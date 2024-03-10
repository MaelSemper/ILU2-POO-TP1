package histoire;

import personnages.Chef;
import personnages.Druide;
import personnages.Gaulois;
import villagegaulois.Etal;
import villagegaulois.Village;
import villagegaulois.VillageSansChefException;

public class ScenarioCasDegrade {

	public static void main(String[] args) {
		Village village = new Village("le village des irréductibles", 10, 5);
		Chef abraracourcix = new Chef("Abraracourcix", 10, village);
		village.setChef(abraracourcix);
		Druide druide = new Druide("Panoramix", 2, 5, 10);
		Gaulois obelix = new Gaulois("Obélix", 25);
		Gaulois asterix = new Gaulois("Astérix", 8);
		Gaulois assurancetourix = new Gaulois("Assurancetourix", 2);
		Gaulois bonemine = new Gaulois("Bonemine", 7);
		
		village.ajouterHabitant(bonemine);
		village.ajouterHabitant(assurancetourix);
		village.ajouterHabitant(asterix);
		village.ajouterHabitant(obelix);
		village.ajouterHabitant(druide);
		village.ajouterHabitant(abraracourcix);
		try {
			village.afficherVillageois();
		} catch (VillageSansChefException e) {
			System.out.println("Erreur: "+e);
		}
		
		Etal etal = new Etal();
		
		village.installerVendeur(bonemine, "fleurs", 20);
		Etal etalF = village.rechercherEtal(bonemine);
		
		try {
			System.out.println(etalF.acheterProduit(2,obelix));
			System.out.println(etal.acheterProduit(3, obelix));
		}catch (IllegalArgumentException | IllegalStateException e) {
			System.err.println("Erreur :" + e);
		}
		System.out.println("Fin du test");


	}

}