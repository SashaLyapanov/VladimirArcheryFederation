package com.example.kursachrps.models;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "protocols")
@Data
public class Protocol extends GenericEntity{

    @OneToOne
    @JoinColumn(name = "competition_id")
    private Competition competition;

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
     * Флаг, который указывает все ли вышеперечисленные поля являются true
     */
    @Column(name = "is_all_flags_true")
    private boolean isAllFlagsTrue;

    @Override
    public String toString() {
        return "Protocol: " + getId() + " for competition: " + getCompetition().getId();
    }
}
