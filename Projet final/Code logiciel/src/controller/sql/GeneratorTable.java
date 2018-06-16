package controller.sql;

import model.tableSql.TableDeclaration;

public class GeneratorTable {

	public static boolean generate(Connexion connexion){
		try {
			connexion.connect();
			connexion.getStatement().execute(TableDeclaration.ESPECES_VEGETALES);
			connexion.getStatement().execute(TableDeclaration.VEGETAUX);
			connexion.getStatement().execute(TableDeclaration.ZONE);
			connexion.getStatement().execute(TableDeclaration.CONTIENT_VEGETAUX);
			connexion.getStatement().execute(TableDeclaration.SONDE);
			connexion.getStatement().execute(TableDeclaration.RELEVE_PERIODIQUE_ATTENDU);
			connexion.getStatement().execute(TableDeclaration.RELEVE_PERIODIQUE_RECU);
			connexion.getStatement().execute(TableDeclaration.TYPE_ALERTE);
			connexion.getStatement().execute(TableDeclaration.ALERTE);
			connexion.getStatement().execute(TableDeclaration.PERSONNE_RESPONSABLE);
			connexion.getStatement().execute(TableDeclaration.ENVOIE);
			connexion.getStatement().execute(TableDeclaration.ETRE_RESPONSABLE_DE);
			connexion.getStatement().execute(TableDeclaration.TYPE_ALERTE_CORRESPONDRE_ZONE);
			connexion.close();
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		
		return true;
	}
}
