package marketing.entities;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2021-06-29T21:36:38.873+0200")
@StaticMetamodel(UserData.class)
public class UserData_ {
	public static volatile SingularAttribute<UserData, Integer> id;
	public static volatile SingularAttribute<UserData, String> answer1;
	public static volatile SingularAttribute<UserData, String> answer2;
	public static volatile SingularAttribute<UserData, String> answer3;
	public static volatile SingularAttribute<UserData, Questionnaire> questionnaire;
}
