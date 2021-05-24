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

	@ManyToOne
	@JoinColumn(name = "question")
	private Question question;

	public Answer() {
	}

	public Answer(String answer, String questiontext, Question question, Questionnaire questionnaire) {
		this.answer = answer;
		this.questiontext = questiontext;
		this.question = question;
		this.questionnaire = questionnaire;
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
	
	
	public Question getQuestion() {
		return this.question;
	}

	public void setQuestion(Question question) {
		this.question = question;
	}

	public Questionnaire getQuestionnaire() {
		return this.questionnaire;
	}

	public void setQuestionnaire(Questionnaire questionnaire) {
		this.questionnaire = questionnaire;
	}

}