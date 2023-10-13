package Class;

public class Vacataire extends Enseignant {

    private String etablissement;
    private int heuresEnseignees;

    //region Constructeur
    public Vacataire(int id, String nom, String numeroSecu, int nombreCours, String specialite, String etablissement, int heuresEnseignees) {
        super(id, nom, numeroSecu, nombreCours, specialite);
        this.etablissement = etablissement;
        this.heuresEnseignees = heuresEnseignees;
    }
    //endregion

    //region Getter | Setter
    public String getEtablissement() {
        return etablissement;
    }

    public void setEtablissement(String etablissement) {
        this.etablissement = etablissement;
    }

    public int getHeuresEnseignees() {
        return heuresEnseignees;
    }

    public void setHeuresEnseignees(int heuresEnseignees) {
        this.heuresEnseignees = heuresEnseignees;
    }
    //endregion
}
