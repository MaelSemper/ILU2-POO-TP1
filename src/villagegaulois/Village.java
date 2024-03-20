package villagegaulois;

import personnages.Chef;
import personnages.Gaulois;
import personnages.VillageSansChefException;

public class Village {
	private String nom;
	private Chef chef;
	private Gaulois[] villageois;
	private int nbVillageois = 0;
	private Marche marche;

	public Village(String nom, int nbVillageoisMaximum, int nbEtals) {
		this.nom = nom;
		villageois = new Gaulois[nbVillageoisMaximum];
		marche = new Marche(nbEtals);
	}

	public String getNom() {
		return nom;
	}

	public void setChef(Chef chef) {
		this.chef = chef;
	}

	public void ajouterHabitant(Gaulois gaulois) {
		if (nbVillageois < villageois.length) {
			villageois[nbVillageois] = gaulois;
			nbVillageois++;
		}
	}

	public Gaulois trouverHabitant(String nomGaulois) {
		if (nomGaulois.equals(chef.getNom())) {
			return chef;
		}
		for (int i = 0; i < nbVillageois; i++) {
			Gaulois gaulois = villageois[i];
			if (gaulois.getNom().equals(nomGaulois)) {
				return gaulois;
			}
		}
		return null;
	}

	public String afficherVillageois() throws VillageSansChefException{
		StringBuilder chaine = new StringBuilder();

		if(chef == null){
			throw new VillageSansChefException("pas de chef dans le village");
		}

		if (nbVillageois < 1) {
			chaine.append("Il n'y a encore aucun habitant au village du chef "
					+ chef.getNom() + ".\n");
		} else {
			chaine.append("Au village du chef " + chef.getNom()
					+ " vivent les légendaires gaulois :\n");
			for (int i = 0; i < nbVillageois; i++) {
				chaine.append("- " + villageois[i].getNom() + "\n");
			}
		}

		return chaine.toString();
	}

	public String installerVendeur(Gaulois vendeur, String produit,
			int nbProduit) {
		StringBuilder chaine = new StringBuilder();
		int emplacement;
		
		chaine.append(vendeur.getNom()+" cherche un endroit pour vendre "+nbProduit+" "+produit+".\n");
		
		emplacement = marche.trouverEtalLibre();
		
		if (emplacement == -1) {
			chaine.append("Tous les étals sont occupés, "+vendeur.getNom()+" devra revenir demain.");
		} else {
			chaine.append("Le vendeur "+vendeur.getNom()+" vend des "
					+produit+" à l'étal n°"+emplacement+"\n");
			marche.utiliserEtal(emplacement, vendeur, produit, nbProduit);
		}
		
		return chaine.toString();
	}
	
	public String rechercherVendeursProduit(String produit) {
		StringBuilder chaine = new StringBuilder();
		
		Etal[] etalProduits = marche.trouverEtals(produit);
		
		if (etalProduits.length == 0) {
			chaine.append("Il n'y a pas de vendeur qui propose des "+produit+" au marché.\n");
		} else if (etalProduits.length == 1){
			chaine.append("Seul le vendeur "+etalProduits[0].getVendeur().getNom()+
					" propose des fleurs au marché.\n");
		} else {
			chaine.append("Les vendeurs qui proposent des "+produit+" sont :\n");
			for (int i=0; i<etalProduits.length; i++) {
				chaine.append(etalProduits[i].getVendeur().getNom()+"\n");
			}
		}
		
		return chaine.toString();
	}
	
	public Etal rechercherEtal(Gaulois vendeur) {
		return marche.trouverVendeur(vendeur);
	}
	
	public String partirVendeur(Gaulois vendeur) {
		StringBuilder chaine = new StringBuilder();
		Etal etal = marche.trouverVendeur(vendeur);
				
		chaine.append(etal.libererEtal());
		
		return chaine.toString();
		
		
	}
	
	public String afficherMarche() {
		StringBuilder chaine = new StringBuilder();
	
		chaine.append("Le marché du village \""+nom+"\" possède plusieurs étals :\n");
		chaine.append(marche.afficherMarche());
		
		return chaine.toString();
	}

	
	// Classe interne Marché
	private static class Marche {
		private Etal[] etals;
		
		private Marche (int nbEtal) {
			this.etals = new Etal[nbEtal];
			
			for (int i=0; i<nbEtal; i++) {
				etals[i] = new Etal();
			}
		}
		
		private void utiliserEtal(int indiceEtal, Gaulois vendeur,
				String produit, int nbProduit) {
			if (etals[indiceEtal].isEtalOccupe()) {
				System.out.println("L'étal est déjà occupé\n");
			} else {
				etals[indiceEtal].occuperEtal(vendeur, produit, nbProduit);
			}
		}
		
		private int trouverEtalLibre() {
			for(int i=0; i < etals.length; i++) {
				if (!(etals[i].isEtalOccupe())) {
					return i;
				}
			}
			
			return -1;
		}
		
		private Etal[] trouverEtals(String produit) {
			int indice=0;
			int nbStands = 0;
			
			for (int i=0; i<etals.length; i++) {
				if (etals[i].isEtalOccupe() && etals[i].contientProduit(produit)){
					nbStands ++;
				}
			}
			
			Etal[] etalOccupe = new Etal[nbStands];
			
			for(int i=0; i < etals.length; i++) {
				if (etals[i].isEtalOccupe() && etals[i].contientProduit(produit)){
					etalOccupe[indice] = etals[i];
					indice ++;
				}
			}
			
			return etalOccupe;
		}
		
		private Etal trouverVendeur(Gaulois gaulois) {
			for (int i=0; i < etals.length; i++) {
				if (etals[i].getVendeur() == gaulois) {
					return etals[i];
				}
			}	
			return null;
		}
		
		public String afficherMarche() {
			String affichage = "";
			int nbEtalLibre = 0;
			
			for (int i=0; i< etals.length; i++) {
				if (etals[i].isEtalOccupe()) {
					affichage += etals[i].afficherEtal();
				} else {
					nbEtalLibre ++;
				}
			}
			if (nbEtalLibre > 0) {
				affichage += "Il reste "+nbEtalLibre+"  étals non utilisés dans le marché.\n";
			} else {
				affichage = "Aucun étal n'est occupé";
			}
			
			return affichage;
		}
	}
}