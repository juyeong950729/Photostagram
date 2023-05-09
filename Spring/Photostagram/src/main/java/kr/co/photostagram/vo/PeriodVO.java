package kr.co.photostagram.vo;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class PeriodVO {

    private String startYear;
    private String startMonth;
    private String startDay;
    private String endYear;
    private String endMonth;
    private String endDay;

}
