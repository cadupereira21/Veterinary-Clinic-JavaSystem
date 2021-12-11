package Model;

/**
 *
 * @author Manko
 */
public class Animal {

    private final int idAnimal;
    private final int idDono;
    private String nome;
    private int anoNasc;
    private String sexo; //Macho | Femea
    private int idEspecie;

    public Animal(int idAnimal, int idDono, String nome, int anoNasc, String sexo, int idEspecie) {
        this.idAnimal = idAnimal;
        this.idDono = idDono;
        this.nome = nome;
        this.anoNasc = anoNasc;
        this.sexo = sexo;
        this.idEspecie = idEspecie;
    }

    public int getId() {  return idAnimal; }
    public int getIdDono() {  return idDono; }

    public String getNome() {  return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public int getAnoNasc() {  return anoNasc; }
    public void setAnoNasc(int anoNasc) {  this.anoNasc = anoNasc; }

    public String getSexo() { return sexo; }
    public void setSexo(String sexo) {   this.sexo = sexo; }

    public int getIdEspecie() {return idEspecie;}
    public void setIdEspecie(int newEspecieId) { this.idEspecie = newEspecieId; }

    @Override
    public String toString() {
        return "Animal{" +
                "idAnimal=" + idAnimal +
                ", nome='" + nome + '\'' +
                '}';
    }
}
