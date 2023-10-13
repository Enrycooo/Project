package Class;

public class Vendeur extends Personne {

    private int anciennete;
    private String nomDuStand;
    private boolean permanent;
    private int numeroBureau;

    //region Constructeur
    public Vendeur(int id, String nom, String numeroSecu, int anciennete, String nomDuStand, boolean permanent, int numeroBureau) {
        super(id, nom, numeroSecu);
        this.anciennete = anciennete;
        this.nomDuStand = nomDuStand;
        this.permanent = permanent;
        this.numeroBureau = numeroBureau;
    }
    //endregion

    //region Getter | Setter
    public int getAnciennete() {
        return anciennete;
    }

    public void setAnciennete(int anciennete) {
        this.anciennete = anciennete;
    }

    public String getNomDuStand() {
        return nomDuStand;
    }

    public void setNomDuStand(String nomDuStand) {
        this.nomDuStand = nomDuStand;
    }

    public boolean isPermanent() {
        return permanent;
    }

    public void setPermanent(boolean permanent) {
        this.permanent = permanent;
    }

    public int getNumeroBureau() {
        return numeroBureau;
    }

    public void setNumeroBureau(int numeroBureau) {
        this.numeroBureau = numeroBureau;
    }
    //endregion
}
