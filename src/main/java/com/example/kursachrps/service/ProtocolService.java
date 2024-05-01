package com.example.kursachrps.service;

import com.example.kursachrps.models.Application;
import com.example.kursachrps.models.BowType;
import com.example.kursachrps.models.Protocol;
import com.example.kursachrps.models.Sex;
import com.example.kursachrps.repositories.ProtocolRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class ProtocolService {
    private static final String LongBow_3D = "af44dbd5-21bb-41f1-b732-af5706b8153d";
    private static final String CompositeBow_3D = "62cb799b-0ff8-4843-82c0-61a215d4af97";
    private static final String CL_3D = "351c3a7e-64b4-4749-b8c8-bb1ecb2f3ef2";
    private static final String BL_3D = "ac6a3094-b354-4ebd-8bb1-19111742c764";
    private static final String Sporting = "62cb799b-0ff8-4843-b732-af5706b8153d";
    private static final String HistoryBow = "26454f95-0e38-45d4-a85e-dd37f4a04944";
    private static final String Olympic = "36671015-5c37-4e5c-8eed-9a353e927f32";
    private static final String Arbalet = "e6dc3841-98a3-4357-a139-61e48ac393e2";

    private static final String LongBow_3D_MAN = "af44dbd5-21bb-41f1-b732-af5706b8153d c99ccd51-5731-42a3-9cfc-31cc4011e035";
    private static final String CompositeBow_3D_MAN = "62cb799b-0ff8-4843-82c0-61a215d4af97 c99ccd51-5731-42a3-9cfc-31cc4011e035";
    private static final String CL_3D_MAN = "351c3a7e-64b4-4749-b8c8-bb1ecb2f3ef2 c99ccd51-5731-42a3-9cfc-31cc4011e035";
    private static final String BL_3D_MAN = "ac6a3094-b354-4ebd-8bb1-19111742c764 c99ccd51-5731-42a3-9cfc-31cc4011e035";
    private static final String Sporting_MAN = "62cb799b-0ff8-4843-b732-af5706b8153d c99ccd51-5731-42a3-9cfc-31cc4011e035";
    private static final String HistoryBow_MAN = "26454f95-0e38-45d4-a85e-dd37f4a04944 c99ccd51-5731-42a3-9cfc-31cc4011e035";
    private static final String Olympic_MAN = "36671015-5c37-4e5c-8eed-9a353e927f32 c99ccd51-5731-42a3-9cfc-31cc4011e035";
    private static final String Arbalet_MAN = "e6dc3841-98a3-4357-a139-61e48ac393e2 c99ccd51-5731-42a3-9cfc-31cc4011e035";

    private static final String LongBow_3D_WOMAN = "af44dbd5-21bb-41f1-b732-af5706b8153d 848f4054-a9c1-4525-9e10-2ab07e3e9b4c";
    private static final String CompositeBow_3D_WOMAN = "62cb799b-0ff8-4843-82c0-61a215d4af97 848f4054-a9c1-4525-9e10-2ab07e3e9b4c";
    private static final String CL_3D_WOMAN = "351c3a7e-64b4-4749-b8c8-bb1ecb2f3ef2 848f4054-a9c1-4525-9e10-2ab07e3e9b4c";
    private static final String BL_3D_WOMAN = "ac6a3094-b354-4ebd-8bb1-19111742c764 848f4054-a9c1-4525-9e10-2ab07e3e9b4c";
    private static final String Sporting_WOMAN = "62cb799b-0ff8-4843-b732-af5706b8153d 848f4054-a9c1-4525-9e10-2ab07e3e9b4c";
    private static final String HistoryBow_WOMAN = "26454f95-0e38-45d4-a85e-dd37f4a04944 848f4054-a9c1-4525-9e10-2ab07e3e9b4c";
    private static final String Olympic_WOMAN = "36671015-5c37-4e5c-8eed-9a353e927f32 848f4054-a9c1-4525-9e10-2ab07e3e9b4c";
    private static final String Arbalet_WOMAN = "e6dc3841-98a3-4357-a139-61e48ac393e2 848f4054-a9c1-4525-9e10-2ab07e3e9b4c";

    private final ProtocolRepository protocolRepository;

    @Autowired
    public ProtocolService(ProtocolRepository protocolRepository) {
        this.protocolRepository = protocolRepository;
    }

    @Transactional
    public void setProtocolFieldTrueForMAN8(BowType bowType, Protocol protocol) {
        if (bowType != null) {
            if (Objects.equals(bowType.getId(), BL_3D)) {
                protocol.setBlock3DMan8(true);
            } else if (Objects.equals(bowType.getId(), CL_3D)) {
                protocol.setClassic3DMan8(true);
            } else if (Objects.equals(bowType.getId(), LongBow_3D)) {
                protocol.setLong3DMan8(true);
            } else if (Objects.equals(bowType.getId(), CompositeBow_3D)) {
                protocol.setComposite3DMan8(true);
            } else if (Objects.equals(bowType.getId(), Sporting)) {
                protocol.setSporting3DMan8(true);
            } else if (Objects.equals(bowType.getId(), HistoryBow)) {
                protocol.setHistoryBow3DMan8(true);
            } else if (Objects.equals(bowType.getId(), Olympic)) {
                protocol.setOlympic3DMan8(true);
            } else if (Objects.equals(bowType.getId(), Arbalet)) {
                protocol.setArbalet3DMan8(true);
            }
        }
    }

    @Transactional
    public void setProtocolFieldTrueForMAN4(BowType bowType, Protocol protocol) {
        if (bowType != null) {
            if (Objects.equals(bowType.getId(), BL_3D)) {
                protocol.setBlock3DMan4(true);
            } else if (Objects.equals(bowType.getId(), CL_3D)) {
                protocol.setClassic3DMan4(true);
            } else if (Objects.equals(bowType.getId(), LongBow_3D)) {
                protocol.setLong3DMan4(true);
            } else if (Objects.equals(bowType.getId(), CompositeBow_3D)) {
                protocol.setComposite3DMan4(true);
            } else if (Objects.equals(bowType.getId(), Sporting)) {
                protocol.setSporting3DMan4(true);
            } else if (Objects.equals(bowType.getId(), HistoryBow)) {
                protocol.setHistoryBow3DMan4(true);
            } else if (Objects.equals(bowType.getId(), Olympic)) {
                protocol.setOlympic3DMan4(true);
            } else if (Objects.equals(bowType.getId(), Arbalet)) {
                protocol.setArbalet3DMan4(true);
            }
        }
    }

    @Transactional
    public void setProtocolFieldTrueForMAN2(BowType bowType, Protocol protocol) {
        if (bowType != null) {
            if (Objects.equals(bowType.getId(), BL_3D)) {
                protocol.setBlock3DMan2(true);
            } else if (Objects.equals(bowType.getId(), CL_3D)) {
                protocol.setClassic3DMan2(true);
            } else if (Objects.equals(bowType.getId(), LongBow_3D)) {
                protocol.setLong3DMan2(true);
            } else if (Objects.equals(bowType.getId(), CompositeBow_3D)) {
                protocol.setComposite3DMan2(true);
            } else if (Objects.equals(bowType.getId(), Sporting)) {
                protocol.setSporting3DMan2(true);
            } else if (Objects.equals(bowType.getId(), HistoryBow)) {
                protocol.setHistoryBow3DMan2(true);
            } else if (Objects.equals(bowType.getId(), Olympic)) {
                protocol.setOlympic3DMan2(true);
            } else if (Objects.equals(bowType.getId(), Arbalet)) {
                protocol.setArbalet3DMan2(true);
            }
        }
    }

    @Transactional
    public void setProtocolFieldTrueForMANFinal(BowType bowType, Protocol protocol) {
        if (bowType != null) {
            if (Objects.equals(bowType.getId(), BL_3D)) {
                protocol.setBlock3DManFinal(true);
            } else if (Objects.equals(bowType.getId(), CL_3D)) {
                protocol.setClassic3DManFinal(true);
            } else if (Objects.equals(bowType.getId(), LongBow_3D)) {
                protocol.setLong3DManFinal(true);
            } else if (Objects.equals(bowType.getId(), CompositeBow_3D)) {
                protocol.setComposite3DManFinal(true);
            } else if (Objects.equals(bowType.getId(), Sporting)) {
                protocol.setSporting3DManFinal(true);
            } else if (Objects.equals(bowType.getId(), HistoryBow)) {
                protocol.setHistoryBow3DManFinal(true);
            } else if (Objects.equals(bowType.getId(), Olympic)) {
                protocol.setOlympic3DManFinal(true);
            } else if (Objects.equals(bowType.getId(), Arbalet)) {
                protocol.setArbalet3DManFinal(true);
            }
        }
    }

    @Transactional
    public void setProtocolFieldTrueForWOMAN8(BowType bowType, Protocol protocol) {
        if (bowType != null) {
            if (Objects.equals(bowType.getId(), BL_3D)) {
                protocol.setBlock3DWoman8(true);
            } else if (Objects.equals(bowType.getId(), CL_3D)) {
                protocol.setClassic3DWoman8(true);
            } else if (Objects.equals(bowType.getId(), LongBow_3D)) {
                protocol.setLong3DWoman8(true);
            } else if (Objects.equals(bowType.getId(), CompositeBow_3D)) {
                protocol.setComposite3DWoman8(true);
            } else if (Objects.equals(bowType.getId(), Sporting)) {
                protocol.setSporting3DWoman8(true);
            } else if (Objects.equals(bowType.getId(), HistoryBow)) {
                protocol.setHistoryBow3DWoman8(true);
            } else if (Objects.equals(bowType.getId(), Olympic)) {
                protocol.setOlympic3DWoman8(true);
            } else if (Objects.equals(bowType.getId(), Arbalet)) {
                protocol.setArbalet3DWoman8(true);
            }
        }
    }

    @Transactional
    public void setProtocolFieldTrueForWOMAN4(BowType bowType, Protocol protocol) {
        if (bowType != null) {
            if (Objects.equals(bowType.getId(), BL_3D)) {
                protocol.setBlock3DWoman4(true);
            } else if (Objects.equals(bowType.getId(), CL_3D)) {
                protocol.setClassic3DWoman4(true);
            } else if (Objects.equals(bowType.getId(), LongBow_3D)) {
                protocol.setLong3DWoman4(true);
            } else if (Objects.equals(bowType.getId(), CompositeBow_3D)) {
                protocol.setComposite3DWoman4(true);
            } else if (Objects.equals(bowType.getId(), Sporting)) {
                protocol.setSporting3DWoman4(true);
            } else if (Objects.equals(bowType.getId(), HistoryBow)) {
                protocol.setHistoryBow3DWoman4(true);
            } else if (Objects.equals(bowType.getId(), Olympic)) {
                protocol.setOlympic3DWoman4(true);
            } else if (Objects.equals(bowType.getId(), Arbalet)) {
                protocol.setArbalet3DWoman4(true);
            }
        }
    }

    @Transactional
    public void setProtocolFieldTrueForWOMAN2(BowType bowType, Protocol protocol) {
        if (bowType != null) {
            if (Objects.equals(bowType.getId(), BL_3D)) {
                protocol.setBlock3DWoman2(true);
            } else if (Objects.equals(bowType.getId(), CL_3D)) {
                protocol.setClassic3DWoman2(true);
            } else if (Objects.equals(bowType.getId(), LongBow_3D)) {
                protocol.setLong3DWoman2(true);
            } else if (Objects.equals(bowType.getId(), CompositeBow_3D)) {
                protocol.setComposite3DWoman2(true);
            } else if (Objects.equals(bowType.getId(), Sporting)) {
                protocol.setSporting3DWoman2(true);
            } else if (Objects.equals(bowType.getId(), HistoryBow)) {
                protocol.setHistoryBow3DWoman2(true);
            } else if (Objects.equals(bowType.getId(), Olympic)) {
                protocol.setOlympic3DWoman2(true);
            } else if (Objects.equals(bowType.getId(), Arbalet)) {
                protocol.setArbalet3DWoman2(true);
            }
        }
    }

    @Transactional
    public void setProtocolFieldTrueForWOMANFinal(BowType bowType, Protocol protocol) {
        if (bowType != null) {
            if (Objects.equals(bowType.getId(), BL_3D)) {
                protocol.setBlock3DWomanFinal(true);
            } else if (Objects.equals(bowType.getId(), CL_3D)) {
                protocol.setClassic3DWomanFinal(true);
            } else if (Objects.equals(bowType.getId(), LongBow_3D)) {
                protocol.setLong3DWomanFinal(true);
            } else if (Objects.equals(bowType.getId(), CompositeBow_3D)) {
                protocol.setComposite3DWomanFinal(true);
            } else if (Objects.equals(bowType.getId(), Sporting)) {
                protocol.setSporting3DWomanFinal(true);
            } else if (Objects.equals(bowType.getId(), HistoryBow)) {
                protocol.setHistoryBow3DWomanFinal(true);
            } else if (Objects.equals(bowType.getId(), Olympic)) {
                protocol.setOlympic3DWomanFinal(true);
            } else if (Objects.equals(bowType.getId(), Arbalet)) {
                protocol.setArbalet3DWomanFinal(true);
            }
        }
    }

    /**
     * Проверка заполнена ли квалификация для определенных соревнований
     * Т.е. если protocol.qualification == true, возвращаем true, иначе false
     */
    public boolean checkQualificationIsCompleted(String competitionId) {
        Protocol protocol = protocolRepository.findProtocolByCompetitionId(competitionId);
        return protocol.isQualification();
    }

    /**
     * Определение какие стадии соревнований генерировать на данный момент для определенных соревнований
     */
    public List<String> getStageForGenerating(String competitionId) {
        Protocol protocol = protocolRepository.findProtocolByCompetitionId(competitionId);
        List<String> falseFields = new ArrayList<>();

        if (!protocol.isBlock3DMan8()) {
            falseFields.add("block3DMan8");
        } else if (!protocol.isBlock3DMan4()) {
            falseFields.add("block3DMan4");
        } else if (!protocol.isBlock3DMan2()) {
            falseFields.add("block3DMan2");
        } else if (!protocol.isBlock3DManFinal()) {
            falseFields.add("block3DManFinal");
        }

        if (!protocol.isBlock3DWoman8()) {
            falseFields.add("block3DWoman8");
        } else if (!protocol.isBlock3DWoman4()) {
            falseFields.add("block3DWoman4");
        } else if (!protocol.isBlock3DWoman2()) {
            falseFields.add("block3DWoman2");
        } else if (!protocol.isBlock3DWomanFinal()) {
            falseFields.add("block3DWomanFinal");
        }

        if (!protocol.isClassic3DMan8()) {
            falseFields.add("classic3DMan8");
        } else if (!protocol.isClassic3DMan4()) {
            falseFields.add("classic3DMan4");
        } else if (!protocol.isClassic3DMan2()) {
            falseFields.add("classic3DMan2");
        } else if (!protocol.isClassic3DManFinal()) {
            falseFields.add("classic3DManFinal");
        }

        if (!protocol.isClassic3DWoman8()) {
            falseFields.add("classic3DWoman8");
        } else if (!protocol.isClassic3DWoman4()) {
            falseFields.add("classic3DWoman4");
        } else if (!protocol.isClassic3DWoman2()) {
            falseFields.add("classic3DWoman2");
        } else if (!protocol.isClassic3DWomanFinal()) {
            falseFields.add("classic3DWomanFinal");
        }

        if (!protocol.isLong3DMan8()) {
            falseFields.add("long3DMan8");
        } else if (!protocol.isLong3DMan4()) {
            falseFields.add("long3DMan4");
        } else if(!protocol.isLong3DMan2()) {
            falseFields.add("long3DMan2");
        } else if (!protocol.isLong3DManFinal()) {
            falseFields.add("long3DManFinal");
        }

        if (!protocol.isLong3DWoman8()) {
            falseFields.add("long3DWoman8");
        } else if (!protocol.isLong3DWoman4()) {
            falseFields.add("long3DWoman4");
        } else if(!protocol.isLong3DWoman2()) {
            falseFields.add("long3DWoman2");
        } else if (!protocol.isLong3DWomanFinal()) {
            falseFields.add("long3DWomanFinal");
        }

        if (!protocol.isComposite3DMan8()) {
            falseFields.add("composite3DMan8");
        } else if (!protocol.isComposite3DMan4()) {
            falseFields.add("composite3DMan4");
        } else if (!protocol.isComposite3DMan2()) {
            falseFields.add("composite3DMan2");
        } else if (!protocol.isComposite3DManFinal()) {
            falseFields.add("composite3DManFinal");
        }

        if (!protocol.isComposite3DWoman8()) {
            falseFields.add("composite3DWoman8");
        } else if (!protocol.isComposite3DWoman4()) {
            falseFields.add("composite3DWoman4");
        } else if (!protocol.isComposite3DWoman2()) {
            falseFields.add("composite3DWoman2");
        } else if (!protocol.isComposite3DWomanFinal()) {
            falseFields.add("composite3DWomanFinal");
        }

        if (!protocol.isSporting3DMan8()) {
            falseFields.add("sporting3DMan8");
        } else if (!protocol.isSporting3DMan4()) {
            falseFields.add("sporting3DMan4");
        } else if (!protocol.isSporting3DMan2()) {
            falseFields.add("sporting3DMan2");
        } else if (!protocol.isSporting3DManFinal()) {
            falseFields.add("sporting3DManFinal");
        }

        if (!protocol.isSporting3DWoman8()) {
            falseFields.add("sporting3DWoman8");
        } else if (!protocol.isSporting3DWoman4()) {
            falseFields.add("sporting3DWoman4");
        } else if (!protocol.isSporting3DWoman2()) {
            falseFields.add("sporting3DWoman2");
        } else if (!protocol.isSporting3DWomanFinal()) {
            falseFields.add("sporting3DWomanFinal");
        }

        if (!protocol.isHistoryBow3DMan8()) {
            falseFields.add("historyBow3DMan8");
        } else if (!protocol.isHistoryBow3DMan4()) {
            falseFields.add("historyBow3DMan4");
        } else if (!protocol.isHistoryBow3DMan2()) {
            falseFields.add("historyBow3DMan2");
        } else if (!protocol.isHistoryBow3DManFinal()) {
            falseFields.add("historyBow3DManFinal");
        }

        if (!protocol.isHistoryBow3DWoman8()) {
            falseFields.add("historyBow3DWoman8");
        } else if (!protocol.isHistoryBow3DWoman4()) {
            falseFields.add("historyBow3DWoman4");
        } else if (!protocol.isHistoryBow3DWoman2()) {
            falseFields.add("historyBow3DWoman2");
        } else if (!protocol.isHistoryBow3DWomanFinal()) {
            falseFields.add("historyBow3DWomanFinal");
        }

        if (!protocol.isOlympic3DMan8()) {
            falseFields.add("olympic3DMan8");
        } else if (!protocol.isOlympic3DMan4()) {
            falseFields.add("olympic3DMan4");
        } else if (!protocol.isOlympic3DMan2()) {
            falseFields.add("olympic3DMan2");
        } else if (!protocol.isOlympic3DManFinal()) {
            falseFields.add("olympic3DManFinal");
        }

        if (!protocol.isOlympic3DWoman8()) {
            falseFields.add("olympic3DWoman8");
        } else if (!protocol.isOlympic3DWoman4()) {
            falseFields.add("olympic3DWoman4");
        } else if (!protocol.isOlympic3DWoman2()) {
            falseFields.add("olympic3DWoman2");
        } else if (!protocol.isOlympic3DWomanFinal()) {
            falseFields.add("olympic3DWomanFinal");
        }

        if (!protocol.isArbalet3DMan8()) {
            falseFields.add("arbalet3DMan8");
        } else if (!protocol.isArbalet3DMan4()) {
            falseFields.add("arbalet3DMan4");
        } else if (!protocol.isArbalet3DMan2()) {
            falseFields.add("arbalet3DMan2");
        } else if (!protocol.isArbalet3DManFinal()) {
            falseFields.add("arbalet3DManFinal");
        }

        if (!protocol.isArbalet3DWoman8()) {
            falseFields.add("arbalet3DWoman8");
        } else if (!protocol.isArbalet3DWoman4()) {
            falseFields.add("arbalet3DWoman4");
        } else if (!protocol.isArbalet3DWoman2()) {
            falseFields.add("arbalet3DWoman2");
        } else if (!protocol.isArbalet3DWomanFinal()) {
            falseFields.add("arbalet3DWomanFinal");
        }

        return falseFields;
    }

    public void markExtraStages(Protocol protocol, HashMap<AbstractMap.SimpleEntry<BowType, Sex>, Application> uniqueApplicationsSet) {
        List<String> bowTypeAndSexList = new ArrayList<>();
        for (Map.Entry<AbstractMap.SimpleEntry<BowType, Sex>, Application> entry : uniqueApplicationsSet.entrySet()) {
            AbstractMap.SimpleEntry<BowType, Sex> key = entry.getKey();
            BowType bowType = key.getKey();
            Sex sex = key.getValue();
            bowTypeAndSexList.add(bowType.getId() + " " + sex.getId());
        }

        if (!bowTypeAndSexList.contains(BL_3D_MAN)) {
            protocol.setBlock3DMan8(true);
            protocol.setBlock3DMan4(true);
            protocol.setBlock3DMan2(true);
            protocol.setBlock3DManFinal(true);
        }
        if (!bowTypeAndSexList.contains(BL_3D_WOMAN)) {
            protocol.setBlock3DWoman8(true);
            protocol.setBlock3DWoman4(true);
            protocol.setBlock3DWoman2(true);
            protocol.setBlock3DWomanFinal(true);
        }
        if (!bowTypeAndSexList.contains(CL_3D_MAN)) {
            protocol.setClassic3DMan8(true);
            protocol.setClassic3DMan4(true);
            protocol.setClassic3DMan2(true);
            protocol.setClassic3DManFinal(true);
        }
        if (!bowTypeAndSexList.contains(CL_3D_WOMAN)) {
            protocol.setClassic3DWoman8(true);
            protocol.setClassic3DWoman4(true);
            protocol.setClassic3DWoman2(true);
            protocol.setClassic3DWomanFinal(true);
        }
        if (!bowTypeAndSexList.contains(LongBow_3D_MAN)) {
            protocol.setLong3DMan8(true);
            protocol.setLong3DMan4(true);
            protocol.setLong3DMan2(true);
            protocol.setLong3DManFinal(true);
        }
        if (!bowTypeAndSexList.contains(LongBow_3D_WOMAN)) {
            protocol.setLong3DWoman8(true);
            protocol.setLong3DWoman4(true);
            protocol.setLong3DWoman2(true);
            protocol.setLong3DWomanFinal(true);
        }
        if (!bowTypeAndSexList.contains(CompositeBow_3D_MAN)) {
            protocol.setComposite3DMan8(true);
            protocol.setComposite3DMan4(true);
            protocol.setComposite3DMan2(true);
            protocol.setComposite3DManFinal(true);
        }
        if (!bowTypeAndSexList.contains(CompositeBow_3D_WOMAN)) {
            protocol.setComposite3DWoman8(true);
            protocol.setComposite3DWoman4(true);
            protocol.setComposite3DWoman2(true);
            protocol.setComposite3DWomanFinal(true);
        }
        if (!bowTypeAndSexList.contains(Sporting_MAN)) {
            protocol.setSporting3DMan8(true);
            protocol.setSporting3DMan4(true);
            protocol.setSporting3DMan2(true);
            protocol.setSporting3DManFinal(true);
        }
        if (!bowTypeAndSexList.contains(Sporting_WOMAN)) {
            protocol.setSporting3DWoman8(true);
            protocol.setSporting3DWoman4(true);
            protocol.setSporting3DWoman2(true);
            protocol.setSporting3DWomanFinal(true);
        }
        if (!bowTypeAndSexList.contains(HistoryBow_MAN)) {
            protocol.setHistoryBow3DMan8(true);
            protocol.setHistoryBow3DMan4(true);
            protocol.setHistoryBow3DMan2(true);
            protocol.setHistoryBow3DManFinal(true);
        }
        if (!bowTypeAndSexList.contains(HistoryBow_WOMAN)) {
            protocol.setHistoryBow3DWoman8(true);
            protocol.setHistoryBow3DWoman4(true);
            protocol.setHistoryBow3DWoman2(true);
            protocol.setHistoryBow3DWomanFinal(true);
        }
        if (!bowTypeAndSexList.contains(Olympic_MAN)) {
            protocol.setOlympic3DMan8(true);
            protocol.setOlympic3DMan4(true);
            protocol.setOlympic3DMan2(true);
            protocol.setOlympic3DManFinal(true);
        }
        if (!bowTypeAndSexList.contains(Olympic_WOMAN)) {
            protocol.setOlympic3DWoman8(true);
            protocol.setOlympic3DWoman4(true);
            protocol.setOlympic3DWoman2(true);
            protocol.setOlympic3DWomanFinal(true);
        }
        if (!bowTypeAndSexList.contains(Arbalet_MAN)) {
            protocol.setArbalet3DMan8(true);
            protocol.setArbalet3DMan4(true);
            protocol.setArbalet3DMan2(true);
            protocol.setArbalet3DManFinal(true);
        }
        if (!bowTypeAndSexList.contains(Arbalet_WOMAN)) {
            protocol.setArbalet3DWoman8(true);
            protocol.setArbalet3DWoman4(true);
            protocol.setArbalet3DWoman2(true);
            protocol.setArbalet3DWomanFinal(true);
        }
        protocolRepository.save(protocol);
    }

    public void markExtraStagesAfterQualification(Protocol protocol, HashMap<AbstractMap.SimpleEntry<BowType, Sex>, Application> uniqueApplicationsSet) {
        List<String> bowTypeAndSexList = new ArrayList<>();
        for (Map.Entry<AbstractMap.SimpleEntry<BowType, Sex>, Application> entry : uniqueApplicationsSet.entrySet()) {
            AbstractMap.SimpleEntry<BowType, Sex> key = entry.getKey();
            BowType bowType = key.getKey();
            Sex sex = key.getValue();
            bowTypeAndSexList.add(bowType.getId() + " " + sex.getId());
        }

        if (bowTypeAndSexList.contains(BL_3D_MAN)) {
            protocol.setBlock3DMan8(true);
            protocol.setBlock3DMan4(true);
            protocol.setBlock3DMan2(true);
            protocol.setBlock3DManFinal(true);
        }
        if (bowTypeAndSexList.contains(BL_3D_WOMAN)) {
            protocol.setBlock3DWoman8(true);
            protocol.setBlock3DWoman4(true);
            protocol.setBlock3DWoman2(true);
            protocol.setBlock3DWomanFinal(true);
        }
        if (bowTypeAndSexList.contains(CL_3D_MAN)) {
            protocol.setClassic3DMan8(true);
            protocol.setClassic3DMan4(true);
            protocol.setClassic3DMan2(true);
            protocol.setClassic3DManFinal(true);
        }
        if (bowTypeAndSexList.contains(CL_3D_WOMAN)) {
            protocol.setClassic3DWoman8(true);
            protocol.setClassic3DWoman4(true);
            protocol.setClassic3DWoman2(true);
            protocol.setClassic3DWomanFinal(true);
        }
        if (bowTypeAndSexList.contains(LongBow_3D_MAN)) {
            protocol.setLong3DMan8(true);
            protocol.setLong3DMan4(true);
            protocol.setLong3DMan2(true);
            protocol.setLong3DManFinal(true);
        }
        if (bowTypeAndSexList.contains(LongBow_3D_WOMAN)) {
            protocol.setLong3DWoman8(true);
            protocol.setLong3DWoman4(true);
            protocol.setLong3DWoman2(true);
            protocol.setLong3DWomanFinal(true);
        }
        if (bowTypeAndSexList.contains(CompositeBow_3D_MAN)) {
            protocol.setComposite3DMan8(true);
            protocol.setComposite3DMan4(true);
            protocol.setComposite3DMan2(true);
            protocol.setComposite3DManFinal(true);
        }
        if (bowTypeAndSexList.contains(CompositeBow_3D_WOMAN)) {
            protocol.setComposite3DWoman8(true);
            protocol.setComposite3DWoman4(true);
            protocol.setComposite3DWoman2(true);
            protocol.setComposite3DWomanFinal(true);
        }
        if (bowTypeAndSexList.contains(Sporting_MAN)) {
            protocol.setSporting3DMan8(true);
            protocol.setSporting3DMan4(true);
            protocol.setSporting3DMan2(true);
            protocol.setSporting3DManFinal(true);
        }
        if (bowTypeAndSexList.contains(Sporting_WOMAN)) {
            protocol.setSporting3DWoman8(true);
            protocol.setSporting3DWoman4(true);
            protocol.setSporting3DWoman2(true);
            protocol.setSporting3DWomanFinal(true);
        }
        if (bowTypeAndSexList.contains(HistoryBow_MAN)) {
            protocol.setHistoryBow3DMan8(true);
            protocol.setHistoryBow3DMan4(true);
            protocol.setHistoryBow3DMan2(true);
            protocol.setHistoryBow3DManFinal(true);
        }
        if (bowTypeAndSexList.contains(HistoryBow_WOMAN)) {
            protocol.setHistoryBow3DWoman8(true);
            protocol.setHistoryBow3DWoman4(true);
            protocol.setHistoryBow3DWoman2(true);
            protocol.setHistoryBow3DWomanFinal(true);
        }
        if (bowTypeAndSexList.contains(Olympic_MAN)) {
            protocol.setOlympic3DMan8(true);
            protocol.setOlympic3DMan4(true);
            protocol.setOlympic3DMan2(true);
            protocol.setOlympic3DManFinal(true);
        }
        if (bowTypeAndSexList.contains(Olympic_WOMAN)) {
            protocol.setOlympic3DWoman8(true);
            protocol.setOlympic3DWoman4(true);
            protocol.setOlympic3DWoman2(true);
            protocol.setOlympic3DWomanFinal(true);
        }
        if (bowTypeAndSexList.contains(Arbalet_MAN)) {
            protocol.setArbalet3DMan8(true);
            protocol.setArbalet3DMan4(true);
            protocol.setArbalet3DMan2(true);
            protocol.setArbalet3DManFinal(true);
        }
        if (bowTypeAndSexList.contains(Arbalet_WOMAN)) {
            protocol.setArbalet3DWoman8(true);
            protocol.setArbalet3DWoman4(true);
            protocol.setArbalet3DWoman2(true);
            protocol.setArbalet3DWomanFinal(true);
        }
        protocolRepository.save(protocol);
    }

}
