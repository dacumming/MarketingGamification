package marketing.entities;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2021-05-26T01:24:19.965+0200")
@StaticMetamodel(UserLogin.class)
public class UserLogin_ {
	public static volatile SingularAttribute<UserLogin, Integer> id;
	public static volatile SingularAttribute<UserLogin, Date> datetime;
	public static volatile SingularAttribute<UserLogin, User> user;
}
