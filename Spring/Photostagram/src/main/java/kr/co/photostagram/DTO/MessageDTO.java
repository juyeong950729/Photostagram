package kr.co.photostagram.DTO;

import lombok.Data;

@Data
public class MessageDTO {

	private int no;
	private String rdate;
	private String message;
	private int user_no;
	private int room;

	private String writer;
	private String profileImg;
}
