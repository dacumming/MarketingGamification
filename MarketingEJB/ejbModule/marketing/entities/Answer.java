package marketing.entities;

import java.io.Serializable;
import javax.persistence.*;


@Entity
@Table(name = "answer", schema = "marketingdb")

public class Answer implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	private String answer;
	
	private String questiontext;

	@ManyToOne
	@JoinColumn(name = "questionnaire")
	private Questionnaire questionnaire;

	 

	public Answer() {
	}

	public Answer(String answer, String questiontext) {
		this.answer = answer;
		this.questiontext = questiontext;
		this.questionnaire = null;
	}
	
	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public String getAnswer() {
		return this.answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}
	
	public String getQuestionText() {
		return this.questiontext;
	}

	public void setQuestionText(String questiontext) {
		this.questiontext = questiontext;
	}
	

	public Questionnaire getQuestionnaire() {
		return this.questionnaire;
	}

	public void setQuestionnaire(Questionnaire questionnaire) {
		this.questionnaire = questionnaire;
	}

}