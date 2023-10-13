package Class;

public class Permanent extends Enseignant {

    private String grade;
    private int anciennete;

    //region Constructeur
    public Permanent(int id, String nom, String numeroSecu, int nombreCours, String specialite, String grade, int anciennete) {
        super(id, nom, numeroSecu, nombreCours, specialite);
        this.grade = grade;
        this.anciennete = anciennete;
    }
    //endregion

    //region Getter | Setter
    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public int getAnciennete() {
        return anciennete;
    }

    public void setAnciennete(int anciennete) {
        this.anciennete = anciennete;
    }
    //endregion
}