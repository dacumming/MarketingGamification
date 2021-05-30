package marketing.entities;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2021-05-26T01:24:19.964+0200")
@StaticMetamodel(User.class)
public class User_ {
	public static volatile SingularAttribute<User, Integer> id;
	public static volatile SingularAttribute<User, String> username;
	public static volatile SingularAttribute<User, String> password;
	public static volatile SingularAttribute<User, String> email;
	public static volatile SingularAttribute<User, Integer> points;
	public static volatile SingularAttribute<User, Integer> isbanned;
	public static volatile ListAttribute<User, Questionnaire> questionnaires;
	public static volatile ListAttribute<User, UserLogin> userlogins;
}
