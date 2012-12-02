package appelli.ristorante;

public class Ristorante {
	public static final int numCamerieri = 10;
	public static final int numLavaPiatti = 6;
	public static final int numAsciugaPiatti = 3;

	public static void main(String[]args) {
		/**/
		PortaPiatti contenitore = new PortaPiattiSem(50, PortaPiatti.Tipo.Contenitore);
		PortaPiatti scolaPiatti = new PortaPiattiSem(30, PortaPiatti.Tipo.Scolapiatti);
		/**/
		/*
		PortaPiatti contenitore = new PortaPiattiLC(50, PortaPiatti.Tipo.Contenitore);
		PortaPiatti scolaPiatti = new PortaPiattiLC(30, PortaPiatti.Tipo.Scolapiatti);
		/**/

		Cameriere[] camerieri = new Cameriere[numCamerieri];
		LavaPiatti[] lavaPiatti = new LavaPiatti[numLavaPiatti];
		AsciugaPiatti[] asciugaPiatti = new AsciugaPiatti[numAsciugaPiatti];

		for (int i = 0; i < numCamerieri; i++)
			camerieri[i] = new Cameriere(contenitore);
		for (int i = 0; i < numLavaPiatti; i++)
			lavaPiatti[i] = new LavaPiatti(contenitore, scolaPiatti);
		for (int i = 0; i < numAsciugaPiatti; i++)
			asciugaPiatti[i] = new AsciugaPiatti(scolaPiatti);

		for (int i = 0; i < numCamerieri; i++)
			camerieri[i].start();
		for (int i = 0; i < numLavaPiatti; i++)
			lavaPiatti[i].start();
		for (int i = 0; i < numAsciugaPiatti; i++)
			asciugaPiatti[i].start();
	}
}
