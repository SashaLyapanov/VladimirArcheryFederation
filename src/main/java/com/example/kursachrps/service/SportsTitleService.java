package com.example.kursachrps.service;

import com.example.kursachrps.models.SportsTitle;
import com.example.kursachrps.repositories.SportsTitleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SportsTitleService {
    private static final String BR = "9ce42a38-457d-4eac-95b8-9ce4fefdda11";
    private static final String R3 = "e4332667-4cb7-4b6f-be47-f86fe5c6f3ae";
    private static final String R2 = "e6b339a1-656b-4738-ae6d-91e146f63f64";
    private static final String R1 = "5a19807c-0630-435b-a0b8-5158ad900456";
    private static final String KMS = "6ac053fd-8568-4a36-ae7c-741980d72684";
    private static final String MS = "36be5498-fd85-4525-b9b6-57abd91c3666";
    private static final String MSMK = "049adae2-b4a7-4f65-aa54-9c3cae532c6a";
    private static final String ZMS = "dd015fc-da4f-4e6e-a7f8-9052584d7fab";


    SportsTitleRepository sportsTitleRepository;

    @Autowired
    public SportsTitleService(SportsTitleRepository sportsTitleRepository) {
        this.sportsTitleRepository = sportsTitleRepository;
    }

    public SportsTitle initializeSportTitleForLongBow_3D_MAN(int sum) {
        SportsTitle sportsTitle;
        if (sum <= 450) {
            sportsTitle = sportsTitleRepository.findById(BR).orElse(null);
        } else if (sum <= 509) {
            sportsTitle = sportsTitleRepository.findById(R3).orElse(null);
        } else if (sum <= 569) {
            sportsTitle = sportsTitleRepository.findById(R2).orElse(null);
        } else if (sum <= 629) {
            sportsTitle = sportsTitleRepository.findById(R1).orElse(null);
        } else if (sum <= 689) {
            sportsTitle = sportsTitleRepository.findById(KMS).orElse(null);
        } else if (sum <= 739) {
            sportsTitle = sportsTitleRepository.findById(MS).orElse(null);
        } else if (sum <= 1056) {
            sportsTitle = sportsTitleRepository.findById(MSMK).orElse(null);
        } else {
            //TODO
            // Уточнить условия присвоения ZMS
            sportsTitle = sportsTitleRepository.findById(ZMS).orElse(null);
        }

        return sportsTitle;
    }

    public SportsTitle initializeSportTitleForLongBow_3D_WOMAN(int sum) {
        SportsTitle sportsTitle;
        if (sum <= 350) {
            sportsTitle = sportsTitleRepository.findById(BR).orElse(null);
        } else if (sum <= 409) {
            sportsTitle = sportsTitleRepository.findById(R3).orElse(null);
        } else if (sum <= 469) {
            sportsTitle = sportsTitleRepository.findById(R2).orElse(null);
        } else if (sum <= 529) {
            sportsTitle = sportsTitleRepository.findById(R1).orElse(null);
        } else if (sum <= 589) {
            sportsTitle = sportsTitleRepository.findById(KMS).orElse(null);
        } else if (sum <= 639) {
            sportsTitle = sportsTitleRepository.findById(MS).orElse(null);
        } else if (sum <= 1056) {
            sportsTitle = sportsTitleRepository.findById(MSMK).orElse(null);
        } else {
            //TODO
            // Уточнить условия присвоения ZMS
            sportsTitle = sportsTitleRepository.findById(ZMS).orElse(null);
        }

        return sportsTitle;
    }

    public SportsTitle initializeSportTitleForCompoundBow_3D_MAN(int sum) {
        SportsTitle sportsTitle;
        if (sum <= 520) {
            sportsTitle = sportsTitleRepository.findById(BR).orElse(null);
        } else if (sum <= 589) {
            sportsTitle = sportsTitleRepository.findById(R3).orElse(null);
        } else if (sum <= 649) {
            sportsTitle = sportsTitleRepository.findById(R2).orElse(null);
        } else if (sum <= 709) {
            sportsTitle = sportsTitleRepository.findById(R1).orElse(null);
        } else if (sum <= 759) {
            sportsTitle = sportsTitleRepository.findById(KMS).orElse(null);
        } else if (sum <= 809) {
            sportsTitle = sportsTitleRepository.findById(MS).orElse(null);
        } else if (sum <= 1056) {
            sportsTitle = sportsTitleRepository.findById(MSMK).orElse(null);
        } else {
            //TODO
            // Уточнить условия присвоения ZMS
            sportsTitle = sportsTitleRepository.findById(ZMS).orElse(null);
        }

        return sportsTitle;
    }

    public SportsTitle initializeSportTitleForCompoundBow_3D_WOMAN(int sum) {
        SportsTitle sportsTitle;
        if (sum <= 420) {
            sportsTitle = sportsTitleRepository.findById(BR).orElse(null);
        } else if (sum <= 489) {
            sportsTitle = sportsTitleRepository.findById(R3).orElse(null);
        } else if (sum <= 549) {
            sportsTitle = sportsTitleRepository.findById(R2).orElse(null);
        } else if (sum <= 609) {
            sportsTitle = sportsTitleRepository.findById(R1).orElse(null);
        } else if (sum <= 659) {
            sportsTitle = sportsTitleRepository.findById(KMS).orElse(null);
        } else if (sum <= 709) {
            sportsTitle = sportsTitleRepository.findById(MS).orElse(null);
        } else if (sum <= 1056) {
            sportsTitle = sportsTitleRepository.findById(MSMK).orElse(null);
        } else {
            //TODO
            // Уточнить условия присвоения ZMS
            sportsTitle = sportsTitleRepository.findById(ZMS).orElse(null);
        }

        return sportsTitle;
    }

    public SportsTitle initializeSportTitleForCL_3D_MAN(int sum) {
        SportsTitle sportsTitle;
        if (sum <= 560) {
            sportsTitle = sportsTitleRepository.findById(BR).orElse(null);
        } else if (sum <= 629) {
            sportsTitle = sportsTitleRepository.findById(R3).orElse(null);
        } else if (sum <= 699) {
            sportsTitle = sportsTitleRepository.findById(R2).orElse(null);
        } else if (sum <= 769) {
            sportsTitle = sportsTitleRepository.findById(R1).orElse(null);
        } else if (sum <= 829) {
            sportsTitle = sportsTitleRepository.findById(KMS).orElse(null);
        } else if (sum <= 879) {
            sportsTitle = sportsTitleRepository.findById(MS).orElse(null);
        } else if (sum <= 1056) {
            sportsTitle = sportsTitleRepository.findById(MSMK).orElse(null);
        } else {
            //TODO
            // Уточнить условия присвоения ZMS
            sportsTitle = sportsTitleRepository.findById(ZMS).orElse(null);
        }

        return sportsTitle;
    }

    public SportsTitle initializeSportTitleForCL_3D_WOMAN(int sum) {
        SportsTitle sportsTitle;
        if (sum <= 520) {
            sportsTitle = sportsTitleRepository.findById(BR).orElse(null);
        } else if (sum <= 589) {
            sportsTitle = sportsTitleRepository.findById(R3).orElse(null);
        } else if (sum <= 659) {
            sportsTitle = sportsTitleRepository.findById(R2).orElse(null);
        } else if (sum <= 719) {
            sportsTitle = sportsTitleRepository.findById(R1).orElse(null);
        } else if (sum <= 769) {
            sportsTitle = sportsTitleRepository.findById(KMS).orElse(null);
        } else if (sum <= 819) {
            sportsTitle = sportsTitleRepository.findById(MS).orElse(null);
        } else if (sum <= 1056) {
            sportsTitle = sportsTitleRepository.findById(MSMK).orElse(null);
        } else {
            //TODO
            // Уточнить условия присвоения ZMS
            sportsTitle = sportsTitleRepository.findById(ZMS).orElse(null);
        }

        return sportsTitle;
    }

    public SportsTitle initializeSportTitleForBL_3D_MAN(int sum) {
        SportsTitle sportsTitle;
        if (sum <= 720) {
            sportsTitle = sportsTitleRepository.findById(BR).orElse(null);
        } else if (sum <= 779) {
            sportsTitle = sportsTitleRepository.findById(R3).orElse(null);
        } else if (sum <= 839) {
            sportsTitle = sportsTitleRepository.findById(R2).orElse(null);
        } else if (sum <= 899) {
            sportsTitle = sportsTitleRepository.findById(R1).orElse(null);
        } else if (sum <= 949) {
            sportsTitle = sportsTitleRepository.findById(KMS).orElse(null);
        } else if (sum <= 979) {
            sportsTitle = sportsTitleRepository.findById(MS).orElse(null);
        } else if (sum <= 1056) {
            sportsTitle = sportsTitleRepository.findById(MSMK).orElse(null);
        } else {
            //TODO
            // Уточнить условия присвоения ZMS
            sportsTitle = sportsTitleRepository.findById(ZMS).orElse(null);
        }

        return sportsTitle;
    }

    public SportsTitle initializeSportTitleForBL_3D_WOMAN(int sum) {
        SportsTitle sportsTitle;
        if (sum <= 610) {
            sportsTitle = sportsTitleRepository.findById(BR).orElse(null);
        } else if (sum <= 679) {
            sportsTitle = sportsTitleRepository.findById(R3).orElse(null);
        } else if (sum <= 749) {
            sportsTitle = sportsTitleRepository.findById(R2).orElse(null);
        } else if (sum <= 819) {
            sportsTitle = sportsTitleRepository.findById(R1).orElse(null);
        } else if (sum <= 879) {
            sportsTitle = sportsTitleRepository.findById(KMS).orElse(null);
        } else if (sum <= 919) {
            sportsTitle = sportsTitleRepository.findById(MS).orElse(null);
        } else if (sum <= 1056) {
            sportsTitle = sportsTitleRepository.findById(MSMK).orElse(null);
        } else {
            //TODO
            // Уточнить условия присвоения ZMS
            sportsTitle = sportsTitleRepository.findById(ZMS).orElse(null);
        }

        return sportsTitle;
    }

    public SportsTitle initializeSportTitleForSporting_3D_MAN(int sum) {
        SportsTitle sportsTitle;
        if (sum <= 720) {
            sportsTitle = sportsTitleRepository.findById(BR).orElse(null);
        } else if (sum <= 779) {
            sportsTitle = sportsTitleRepository.findById(R3).orElse(null);
        } else if (sum <= 839) {
            sportsTitle = sportsTitleRepository.findById(R2).orElse(null);
        } else if (sum <= 899) {
            sportsTitle = sportsTitleRepository.findById(R1).orElse(null);
        } else if (sum <= 949) {
            sportsTitle = sportsTitleRepository.findById(KMS).orElse(null);
        } else if (sum <= 979) {
            sportsTitle = sportsTitleRepository.findById(MS).orElse(null);
        } else if (sum <= 1056) {
            sportsTitle = sportsTitleRepository.findById(MSMK).orElse(null);
        } else {
            //TODO
            // Уточнить условия присвоения ZMS
            sportsTitle = sportsTitleRepository.findById(ZMS).orElse(null);
        }

        return sportsTitle;
    }

    public SportsTitle initializeSportTitleForSporting_3D_WOMAN(int sum) {
        SportsTitle sportsTitle;
        if (sum <= 610) {
            sportsTitle = sportsTitleRepository.findById(BR).orElse(null);
        } else if (sum <= 679) {
            sportsTitle = sportsTitleRepository.findById(R3).orElse(null);
        } else if (sum <= 749) {
            sportsTitle = sportsTitleRepository.findById(R2).orElse(null);
        } else if (sum <= 819) {
            sportsTitle = sportsTitleRepository.findById(R1).orElse(null);
        } else if (sum <= 879) {
            sportsTitle = sportsTitleRepository.findById(KMS).orElse(null);
        } else if (sum <= 919) {
            sportsTitle = sportsTitleRepository.findById(MS).orElse(null);
        } else if (sum <= 1056) {
            sportsTitle = sportsTitleRepository.findById(MSMK).orElse(null);
        } else {
            //TODO
            // Уточнить условия присвоения ZMS
            sportsTitle = sportsTitleRepository.findById(ZMS).orElse(null);
        }

        return sportsTitle;
    }

}
