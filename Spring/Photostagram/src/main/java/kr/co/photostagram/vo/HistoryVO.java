package kr.co.photostagram.vo;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class HistoryVO {
    private int no;
    private int user_no;
    private String type;
    private String detail;
    private String rdate;
}
