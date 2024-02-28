package villagegaulois;

import personnages.Chef;
import personnages.Gaulois;

public class Village {
	private String nom;
	private Chef chef;
	private Gaulois[] villageois;
	private int nbVillageois = 0;
	private Marche marche;

	public Village(String nom, int nbVillageoisMaximum, int nbEtal) {
		this.nom = nom;
		villageois = new Gaulois[nbVillageoisMaximum];
		marche = new Marche(nbEtal);

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

	public String afficherVillageois() {
		StringBuilder chaine = new StringBuilder();
		if (nbVillageois < 1) {
			chaine.append("Il n'y a encore aucun habitant au village du chef " + chef.getNom() + ".\n");
		} else {
			chaine.append("Au village du chef " + chef.getNom() + " vivent les lÃ©gendaires gaulois :\n");
			for (int i = 0; i < nbVillageois; i++) {
				chaine.append("- " + villageois[i].getNom() + "\n");
			}
		}
		return chaine.toString();
	}

	public String installerVendeur(Gaulois gaulois, String produit, int nbProduit) {
		StringBuilder chaine = new StringBuilder();
		chaine.append(gaulois.getNom() + " cherche un endroit pour vendre " + nbProduit + " " + produit + ".\n");
		if (marche.trouverEtalLibre() != -1) {
			marche.utiliserEtal(marche.trouverEtalLibre(), gaulois, produit, nbProduit);
			chaine.append("le vendeur " + gaulois.getNom() + " vend des " + produit + "a l'etal n° "
					+ marche.trouverEtalLibre());
		} else {
			chaine.append("Le vendeur " + gaulois.getNom() + " n'a pas trouve d'etale libre");
		}
		return chaine.toString();
	}

	public String rechercherVendeursProduit(String produit) {
		StringBuilder chaine = new StringBuilder();
		Etal[] etalQuiVend = marche.trouverEtals(produit);
		if (etalQuiVend.length == 0) {
			chaine.append("Il n'y a pas de vendeur qui propose des " + produit + " au marche.");
		} else if (etalQuiVend.length == 1) {
			chaine.append("Seul le vendeur " + etalQuiVend[0].getVendeur() + " propose des " + produit + " au marche.");
		}

		else {
			chaine.append("Les vendeurs qui proposent des " + produit + " sont :");
			for (int i = 0; i < etalQuiVend.length; i++) {
				chaine.append("- " + etalQuiVend[i].getVendeur());
			}
		}

		return chaine.toString();
	}

	public class Marche {
		private Etal[] etals;

		public Marche(int nb) {
			etals = new Etal[nb];

			for (int i = 0; i < nb; i++) {
				etals[i] = new Etal();
			}
		}

		public void utiliserEtal(int indiceEtal, Gaulois vendeur, String produit, int nbProduit) {
			etals[indiceEtal].occuperEtal(vendeur, produit, nbProduit);
		}

		public int trouverEtalLibre() {
			int i = -1;
			int j = 0;
			while (j < etals.length && i == -1) {
				if (!etals[j].isEtalOccupe()) {
					i = j;
				}
				j++;
			}
			return i;
		}

		public Etal[] trouverEtals(String produit) {
			Etal[] etalAvecProduit = new Etal[etals.length];
			int j = 0;
			for (int i = 0; i < etals.length; i++) {
				if (etals[i].contientProduit(produit)) {
					etalAvecProduit[j] = etals[i];
					j++;
				}
			}
			return etalAvecProduit;
		}

		public Etal trouverVendeur(Gaulois gaulois) {
			for (int i = 0; i < etals.length; i++) {
				if (etals[i].getVendeur() == gaulois) {
					return etals[i];
				}
			}
			return null;
		}

		public String afficherMarche() {
			String affichage = "\n";
			int etalNonOccuppes = 0;
			for (int i = 0; i < etals.length; i++) {
				if (etals[i].isEtalOccupe()) {
					affichage += etals[i].afficherEtal();
				} else {
					etalNonOccuppes++;
				}
			}
			if (etalNonOccuppes > 0) {
				affichage += "Il reste " + etalNonOccuppes + " non utilises dans le marche.\n";
			}
			return affichage;
		}

	}

}
