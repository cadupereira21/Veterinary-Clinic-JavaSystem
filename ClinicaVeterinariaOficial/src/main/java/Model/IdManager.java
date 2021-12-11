package Model;

public abstract class IdManager {

    private static int ID_CLIENTE = -1;
    private static int ID_ANIMAL = -1;
    private static int ID_TRATAMENTO = -1;
    private static int ID_CONSULTA = -1;
    private static int ID_VETERINARIO = -1;
    private static int ID_ESPECIE = 0;
    private static int ID_EXAME = -1;

    public static int getIdCliente() {setIdCliente(); return ID_CLIENTE;}
    private static void setIdCliente() {ID_CLIENTE += 1;}

    public static int getIdAnimal() {setIdAnimal(); return ID_ANIMAL;}
    private static void setIdAnimal() {ID_ANIMAL += 1;}

    public static int getIdTratamento() { setIdTratamento(); return ID_TRATAMENTO;}
    private static void setIdTratamento() {ID_TRATAMENTO += 1;}

    public static int getIdConsulta() {setIdConsulta(); return ID_CONSULTA;}
    private static void setIdConsulta() {ID_CONSULTA += 1;}

    public static int getIdVeterinario() {setIdVeterinario(); return ID_VETERINARIO;}
    private static void setIdVeterinario() {ID_VETERINARIO  += 1;}

    public static int getIdEspecie() {setIdEspecie(); return ID_ESPECIE;}
    private static void setIdEspecie() {ID_ESPECIE  += 1;}

    public static int getIdExame() {setIdExame(); return ID_EXAME;}
    private static void setIdExame() {ID_EXAME  += 1;}
}
