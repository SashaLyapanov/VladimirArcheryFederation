package com.example.kursachrps.models;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "protocols")
public class Protocol extends GenericEntity {

    @OneToOne
    @JoinColumn(name = "competition_id")
    private Competition competition;

    /**
     * проведена ли квалификация
     */
    @Column(name = "qualification")
    private boolean qualification;

    //////////////////////////////////////
    //3Д-БЛ
    //////////////////////////////////////
    /**
     * 1/8 финала мужчин 3Д-БЛ
     */
    @Column(name = "block_3d_man8")
    private boolean block3DMan8;

    /**
     * 1/8 финала женщин 3Д-БЛ
     */
    @Column(name = "block_3d_woman8")
    private boolean block3DWoman8;

    /**
     * 1/4 финала мужчин 3Д-БЛ
     */
    @Column(name = "block_3d_man4")
    private boolean block3DMan4;

    /**
     * 1/4 финала женщин 3Д-БЛ
     */
    @Column(name = "block_3d_woman4")
    private boolean block3DWoman4;

    /**
     * 1/2 финала мужчин 3Д-БЛ
     */
    @Column(name = "block_3d_man2")
    private boolean block3DMan2;

    /**
     * 1/2 финала женщин 3Д-БЛ
     */
    @Column(name = "block_3d_woman2")
    private boolean block3DWoman2;

    /**
     * Финал мужчин 3Д-БЛ
     */
    @Column(name = "block_3d_man_final")
    private boolean block3DManFinal;

    /**
     * Финал женщин 3Д-БЛ
     */
    @Column(name = "block_3d_woman_final")
    private boolean block3DWomanFinal;

    //////////////////////////////////////
    //3Д-КЛ
    //////////////////////////////////////
    /**
     * 1/8 финала мужчины 3Д-КЛ
     */
    @Column(name = "classic_3d_man8")
    private boolean classic3DMan8;

    /**
     * 1/8 финала женщины 3Д-КЛ
     */
    @Column(name = "classic_3d_woman8")
    private boolean classic3DWoman8;

    /**
     * 1/4 финала мужчины 3Д-КЛ
     */
    @Column(name = "classic_3d_man4")
    private boolean classic3DMan4;

    /**
     * 1/4 финала женщины 3Д-КЛ
     */
    @Column(name = "classic_3d_woman4")
    private boolean classic3DWoman4;

    /**
     * 1/2 финала мужчины 3Д-КЛ
     */
    @Column(name = "classic_3d_man2")
    private boolean classic3DMan2;

    /**
     * 1/2 финала женщины 3Д-КЛ
     */
    @Column(name = "classic_3d_woman2")
    private boolean classic3DWoman2;

    /**
     * Финал мужчин 3Д-KЛ
     */
    @Column(name = "classic_3d_man_final")
    private boolean classic3DManFinal;

    /**
     * Финал женщин 3Д-KЛ
     */
    @Column(name = "classic_3d_woman_final")
    private boolean classic3DWomanFinal;

    //////////////////////////////////////
    //3Д-longBow
    //////////////////////////////////////
    /**
     * 1/8 финала мужчины 3Д-longBow
     */
    @Column(name = "long_3d_man8")
    private boolean long3DMan8;

    /**
     * 1/8 финала женщины 3Д-longBow
     */
    @Column(name = "long_3d_woman8")
    private boolean long3DWoman8;

    /**
     * 1/4 финала мужчины 3Д-longBow
     */
    @Column(name = "long_3d_man4")
    private boolean long3DMan4;

    /**
     * 1/4 финала женщины 3Д-longBow
     */
    @Column(name = "long_3d_woman4")
    private boolean long3DWoman4;

    /**
     * 1/2 финала мужчины 3Д-longBow
     */
    @Column(name = "long_3d_man2")
    private boolean long3DMan2;

    /**
     * 1/2 финала женщины 3Д-longBow
     */
    @Column(name = "long_3d_woman2")
    private boolean long3DWoman2;

    /**
     * Финал мужчин 3Д-long
     */
    @Column(name = "long_3d_man_final")
    private boolean long3DManFinal;

    /**
     * Финал женщин 3Д-long
     */
    @Column(name = "long_3d_woman_final")
    private boolean long3DWomanFinal;

    //////////////////////////////////////
    //3Д-composite
    //////////////////////////////////////
    /**
     * 1/8 финала мужчины 3Д-composite
     */
    @Column(name = "composite_3d_man8")
    private boolean composite3DMan8;

    /**
     * 1/8 финала женщины 3Д-composite
     */
    @Column(name = "composite_3d_woman8")
    private boolean composite3DWoman8;

    /**
     * 1/4 финала мужчины 3Д-composite
     */
    @Column(name = "composite_3d_man4")
    private boolean composite3DMan4;

    /**
     * 1/4 финала женщины 3Д-composite
     */
    @Column(name = "composite_3d_woman4")
    private boolean composite3DWoman4;

    /**
     * 1/2 финала мужчины 3Д-composite
     */
    @Column(name = "composite_3d_man2")
    private boolean composite3DMan2;

    /**
     * 1/2 финала женщины 3Д-composite
     */
    @Column(name = "composite_3d_woman2")
    private boolean composite3DWoman2;

    /**
     * Финал мужчин 3Д-composite
     */
    @Column(name = "composite_3d_man_final")
    private boolean composite3DManFinal;

    /**
     * Финал женщин 3Д-composite
     */
    @Column(name = "composite_3d_woman_final")
    private boolean composite3DWomanFinal;

    //////////////////////////////////////
    //3Д-sporting
    //////////////////////////////////////
    /**
     * 1/8 финала мужчины 3Д-sporting
     */
    @Column(name = "sporting_3d_man8")
    private boolean sporting3DMan8;

    /**
     * 1/8 финала женщины 3Д-sporting
     */
    @Column(name = "sporting_3d_woman8")
    private boolean sporting3DWoman8;

    /**
     * 1/4 финала мужчины 3Д-sporting
     */
    @Column(name = "sporting_3d_man4")
    private boolean sporting3DMan4;

    /**
     * 1/4 финала женщины 3Д-sporting
     */
    @Column(name = "sporting_3d_woman4")
    private boolean sporting3DWoman4;

    /**
     * 1/2 финала мужчины 3Д-sporting
     */
    @Column(name = "sporting_3d_man2")
    private boolean sporting3DMan2;

    /**
     * 1/2 финала женщины 3Д-sporting
     */
    @Column(name = "sporting_3d_woman2")
    private boolean sporting3DWoman2;

    /**
     * Финал мужчин 3Д-sporting
     */
    @Column(name = "sporting_3d_man_final")
    private boolean sporting3DManFinal;

    /**
     * Финал женщин 3Д-sporting
     */
    @Column(name = "sporting_3d_woman_final")
    private boolean sporting3DWomanFinal;

    //////////////////////////////////////
    //3Д-historyBow
    //////////////////////////////////////
    /**
     * 1/8 финала мужчины 3Д-history
     */
    @Column(name = "history_bow_3d_man8")
    private boolean historyBow3DMan8;

    /**
     * 1/8 финала женщины 3Д-history
     */
    @Column(name = "history_bow_3d_woman8")
    private boolean historyBow3DWoman8;

    /**
     * 1/4 финала мужчины 3Д-history
     */
    @Column(name = "history_bow_3d_man4")
    private boolean historyBow3DMan4;

    /**
     * 1/4 финала женщины 3Д-history
     */
    @Column(name = "history_bow_3d_woman4")
    private boolean historyBow3DWoman4;

    /**
     * 1/2 финала мужчины 3Д-history
     */
    @Column(name = "history_bow_3d_man2")
    private boolean historyBow3DMan2;

    /**
     * 1/2 финала женщины 3Д-history
     */
    @Column(name = "history_bow_3d_woman2")
    private boolean historyBow3DWoman2;

    /**
     * Финал мужчин 3Д-history
     */
    @Column(name = "history_bow_3d_man_final")
    private boolean historyBow3DManFinal;

    /**
     * Финал женщин 3Д-history
     */
    @Column(name = "history_bow_3d_woman_final")
    private boolean historyBow3DWomanFinal;

    //////////////////////////////////////
    //3Д-Olympic
    //////////////////////////////////////
    /**
     * 1/8 финала мужчины 3Д-olympic
     */
    @Column(name = "olympic_3d_man8")
    private boolean olympic3DMan8;

    /**
     * 1/8 финала женщины 3Д-olympic
     */
    @Column(name = "olympic_3d_woman8")
    private boolean olympic3DWoman8;

    /**
     * 1/4 финала мужчины 3Д-olympic
     */
    @Column(name = "olympic_3d_man4")
    private boolean olympic3DMan4;

    /**
     * 1/4 финала женщины 3Д-olympic
     */
    @Column(name = "olympic_3d_woman4")
    private boolean olympic3DWoman4;

    /**
     * 1/2 финала мужчины 3Д-olympic
     */
    @Column(name = "olympic_3d_man2")
    private boolean olympic3DMan2;

    /**
     * 1/2 финала женщины 3Д-olympic
     */
    @Column(name = "olympic_3d_woman2")
    private boolean olympic3DWoman2;

    /**
     * Финал мужчин 3Д-olympic
     */
    @Column(name = "olympic_3d_man_final")
    private boolean olympic3DManFinal;

    /**
     * Финал женщин 3Д-olympic
     */
    @Column(name = "olympic_3d_woman_final")
    private boolean olympic3DWomanFinal;

    //////////////////////////////////////
    //3Д-arbalet
    //////////////////////////////////////
    /**
     * 1/8 финала мужчины 3Д-arbalet
     */
    @Column(name = "arbalet_3d_man8")
    private boolean arbalet3DMan8;

    /**
     * 1/8 финала женщины 3Д-arbalet
     */
    @Column(name = "arbalet_3d_woman8")
    private boolean arbalet3DWoman8;

    /**
     * 1/4 финала мужчины 3Д-arbalet
     */
    @Column(name = "arbalet_3d_man4")
    private boolean arbalet3DMan4;

    /**
     * 1/4 финала женщины 3Д-arbalet
     */
    @Column(name = "arbalet_3d_woman4")
    private boolean arbalet3DWoman4;

    /**
     * 1/2 финала мужчины 3Д-arbalet
     */
    @Column(name = "arbalet_3d_man2")
    private boolean arbalet3DMan2;

    /**
     * 1/2 финала женщины 3Д-arbalet
     */
    @Column(name = "arbalet_3d_woman2")
    private boolean arbalet3DWoman2;

    /**
     * Финал мужчин 3Д-arbalet
     */
    @Column(name = "arbalet_3d_man_final")
    private boolean arbalet3DManFinal;

    /**
     * Финал женщин 3Д-arbalet
     */
    @Column(name = "arbalet_3d_woman_final")
    private boolean arbalet3DWomanFinal;

    ////////////////////////////////////
    /**
     * Флаг, который указывает все ли вышеперечисленные поля являются true
     */
    @Column(name = "is_all_flags_true")
    private boolean isAllFlagsTrue;

    public boolean updateIsAllFlagsTrue() {
        isAllFlagsTrue = block3DWomanFinal && block3DManFinal && block3DWoman2 && block3DMan2 && block3DWoman4 && block3DMan4 && block3DWoman8 && block3DMan8 &&
                classic3DMan8 && classic3DWoman8 && classic3DMan4 && classic3DWoman4 && classic3DMan2 && classic3DWoman2 && classic3DManFinal && classic3DWomanFinal &&
                long3DMan8 && long3DWoman8 && long3DMan4 && long3DWoman4 && long3DMan2 && long3DWoman2 && long3DManFinal && long3DWomanFinal &&
                composite3DMan8 && composite3DWoman8 && composite3DMan4 && composite3DWoman4 && composite3DMan2 && composite3DWoman2 && composite3DManFinal && composite3DWomanFinal &&
                sporting3DMan8 && sporting3DWoman8 && sporting3DMan4 && sporting3DWoman4 && sporting3DMan2 && sporting3DWoman2 && sporting3DManFinal && sporting3DWomanFinal &&
                historyBow3DMan8 && historyBow3DWoman8 && historyBow3DMan4 && historyBow3DWoman4 && historyBow3DMan2 && historyBow3DWoman2 && historyBow3DManFinal && historyBow3DWomanFinal &&
                olympic3DMan8 && olympic3DWoman8 && olympic3DMan4 && olympic3DWoman4 && olympic3DMan2 && olympic3DWoman2 && olympic3DManFinal && olympic3DWomanFinal &&
                arbalet3DMan8 && arbalet3DWoman8 && arbalet3DMan4 && arbalet3DWoman4 && arbalet3DMan2 && arbalet3DWoman2 && arbalet3DManFinal && arbalet3DWomanFinal;
        return isAllFlagsTrue;
    }

    @Override
    public String toString() {
        return "Protocol: " + getId() + " for competition: " + getCompetition().getId();
    }
}
