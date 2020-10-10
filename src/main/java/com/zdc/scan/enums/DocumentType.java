package com.zdc.scan.enums;

/**
 * @description: DocumentType
 * @author: zero
 * @date: 2020/2/19
 */
public enum DocumentType {
    DT_PDF,
    DT_RESULT,
    DT_RESULT_ANALYSIS,
    DT_CURRENT,
    DT_PHASE_RESULT,
    DT_CUMULATIVE_RESULT,
    DT_POOL_STANDING,
    DT_BRACKETS,
    DT_PLAY_BY_PLAY,
    DT_STATS,
    DT_RANKING,
    DT_MEDALLISTS,
    DT_MEDALLISTS_DISCIPLINE,
    DT_PRESENTER,
    DT_COMMUNICATION,
    DT_CONFIG,
    DT_IMAGE,
    DT_WEATHER,
    DT_RECORD,
    DT_SCHEDULE,
    DT_SCHEDULE_UPDATE,
    DT_PARTIC,
    DT_PARTIC_UPDATE,
    DT_PARTIC_TEAMS,
    DT_PARTIC_TEAMS_UPDATE,
    DT_PARTIC_NAME,
    DT_MEDALS,
    DT_MEDALLISTS_DAY;

    public String value() {
        return name();
    }

    public static DocumentType fromValue(String v) {
        return valueOf(v);
    }
}
