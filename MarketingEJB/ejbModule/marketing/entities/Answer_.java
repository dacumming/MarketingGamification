package marketing.entities;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2021-05-26T01:24:19.524+0200")
@StaticMetamodel(Answer.class)
public class Answer_ {
	public static volatile SingularAttribute<Answer, Integer> id;
	public static volatile SingularAttribute<Answer, String> answer;
	public static volatile SingularAttribute<Answer, String> questiontext;
	public static volatile SingularAttribute<Answer, Questionnaire> questionnaire;
	public static volatile SingularAttribute<Answer, Question> question;
}
