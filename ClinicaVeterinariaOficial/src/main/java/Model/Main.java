package Model;

import java.time.LocalTime;
import java.util.Calendar;

/**
 *
 * @author Manko
 */
public class Main {
    public static void main(String[] args) {

//       ClienteDAO.getInstance().create("Carlos", "Rua dos pássaros, 234, Piracicaba-SP", "13425620", "carloseduardo2101@gmail.com", "19996060222");
//        ClienteDAO.getInstance().create("Luiz", "Rua dos bobos, 324, Campinas-SP", "13245678", "luiz@gmail.com", "19996660504");
//        ClienteDAO.getInstance().create("Pedro", "Rua dos pedros, 100, Piracicaba-SP", "13234234", "pedro@gmail.com", "19993008989");
//        ClienteDAO.getInstance().create("Vitor", "Rua das cachoeiras, 124, Limeira-SP", "13566545", "vitor@gmail.com", "19984752222");
//
//        AnimalDAO.getInstance().create(1, "Shine", 2009, "Masculino", 1);
//        AnimalDAO.getInstance().create(2, "Totó", 2006, "Masculino", 1);
//        AnimalDAO.getInstance().create(2, "Thor", 2019, "Masculino", 1);
//        AnimalDAO.getInstance().create(3, "Bela", 2015, "Feminino", 1);
//        AnimalDAO.getInstance().create(3, "Lila", 2012, "Feminino", 1);
//        AnimalDAO.getInstance().create(4, "Pipoca", 2012, "Masculino", 1);
//        AnimalDAO.getInstance().create(4, "Relâmpago", 2015, "Masculino", 2);

//        Calendar ini = Calendar.getInstance();
//        Calendar fim = Calendar.getInstance();
//
//        ini.set(2022, 1, 1);
//        fim.set(2022, 12, 30);
//        TratamentoDAO.getInstance().create("Fisioterapia", ini, fim, 1, 0);
//
//        ini.set(2021, 8, 1);
//        fim.set(2022, 12, 30);
//        TratamentoDAO.getInstance().create("Vermicida", ini, fim, 2, 0);
//
//        ini.set(2019, 1, 1);
//        fim.set(2022, 1, 1);
//        TratamentoDAO.getInstance().create("Fisioterapia", ini, fim, 2, 0);
//
//        ini.set(2021, 12, 1);
//        fim.set(2022, 1, 1);
//        TratamentoDAO.getInstance().create("Remédio para Gripe", ini, fim, 7, 0);
//
//        ini.set(2021, 12, 1);
//        fim.set(2022, 1, 1);
//        TratamentoDAO.getInstance().create("Combate às Pulgas", ini, fim, 4, 0);
//
//        Calendar data = Calendar.getInstance();
//        data.set(2022, 1, 10, 9, 0, 0);
//        ConsultaDAO.getInstance().create(data, "Trazer comida para o Shine", 1, 1, 1, 0);


//        ConsultaDAO.getInstance().create("15/01/2022", "12", "Trazer comida para o Shine", 1, 1, 1, 0);
//        ConsultaDAO.getInstance().create("20/01/2022", "12", "Trazer comida para o Shine", 1, 1, 1, 0);
//        ConsultaDAO.getInstance().create("25/01/2022", "12", "Trazer comida para o Shine", 1, 1, 1, 0);
//        ConsultaDAO.getInstance().create("30/01/2022", "12", "Trazer comida para o Shine", 1, 1, 1, 0);
//        ConsultaDAO.getInstance().create("20/12/2021", "18", "Trazer animal alimentado", 2, 1, 2, 0);
//        ConsultaDAO.getInstance().create("15/12/2021", "9", "Trazer comida para o Totó", 2, 1, 3, 0);
//        ConsultaDAO.getInstance().create("15/12/2021", "10", "Não trazer animal selado", 7, 1, 4, 0);
//        ConsultaDAO.getInstance().create("10/12/2021", "15", "", 4, 1, 5, 0);

        System.out.println(ConsultaDAO.getInstance().retrieveById(1).getData().get(Calendar.HOUR_OF_DAY));
    }
}
