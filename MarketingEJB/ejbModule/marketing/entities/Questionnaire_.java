package marketing.entities;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2021-06-29T21:36:38.861+0200")
@StaticMetamodel(Questionnaire.class)
public class Questionnaire_ {
	public static volatile SingularAttribute<Questionnaire, Integer> id;
	public static volatile SingularAttribute<Questionnaire, Date> date;
	public static volatile SingularAttribute<Questionnaire, Integer> iscanceled;
	public static volatile SingularAttribute<Questionnaire, User> user;
	public static volatile SingularAttribute<Questionnaire, Product> product;
	public static volatile SingularAttribute<Questionnaire, UserData> userdata;
	public static volatile ListAttribute<Questionnaire, Answer> answers;
}
