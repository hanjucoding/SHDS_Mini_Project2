package omok;
import lombok.*;

@Getter
@Setter
@ToString
public class RecordVO {
	private String id;
	private int games;
	private int win;
	private int lose;
	private int draw;
	private int rank;
}
