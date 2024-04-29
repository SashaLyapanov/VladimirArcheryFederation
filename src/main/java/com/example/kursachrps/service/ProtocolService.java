package com.example.kursachrps.service;

import com.example.kursachrps.models.BowType;
import com.example.kursachrps.models.Protocol;
import com.example.kursachrps.repositories.ProtocolRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

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

    /**
     * Проверка заполнена ли квалификация для определенных соревнований
     * Т.е. если protocol.qualification == true, возвращаем true, иначе false
     */
    public boolean checkQualificationIsCompleted(String competitionId) {
        Protocol protocol = protocolRepository.findProtocolByCompetitionId(competitionId);
        return protocol.isQualification();
    }

}
