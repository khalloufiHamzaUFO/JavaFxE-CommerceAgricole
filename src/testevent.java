
import models.Evenement;
import models.Participation;
import services.EvenementService;
import services.PartService;

import java.sql.Timestamp;
import java.util.Date;

public class testevent {
        public static void main(String[] args) {
            PartService partService = new PartService();
            System.out.println(partService.getAllPart());;
        }
    }



