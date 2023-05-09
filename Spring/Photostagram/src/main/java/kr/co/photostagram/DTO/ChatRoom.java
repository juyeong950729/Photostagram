package kr.co.photostagram.DTO;

import lombok.Data;

import java.util.List;

@Data
public class ChatRoom {
    private int no;
    private List<Integer> users;
    private List<Integer> mes;
}
