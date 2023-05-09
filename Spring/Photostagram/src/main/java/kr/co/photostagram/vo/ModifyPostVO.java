package kr.co.photostagram.vo;

import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class ModifyPostVO {
    private List<Integer> tags;
    private List<Integer> tops;
    private List<Integer> lefts;
    private List<Integer> pages;
    private String content;
    private int post_no;
}
