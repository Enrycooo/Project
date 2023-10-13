package Class;

public class Enseignant extends Personne {

    private int nombreCours;
    private String specialite;

    //region Constructeur
    public Enseignant(int id, String nom, String numeroSecu, int nombreCours, String specialite) {
        super(id, nom, numeroSecu);
        this.nombreCours = nombreCours;
        this.specialite = specialite;
    }
    //endregion

    //region Getter | Setter
    public int getNombreCours() {
        return nombreCours;
    }

    public void setNombreCours(int nombreCours) {
        this.nombreCours = nombreCours;
    }

    public String getSpecialite() {
        return specialite;
    }

    public void setSpecialite(String specialite) {
        this.specialite = specialite;
    }
    //endregion
}
