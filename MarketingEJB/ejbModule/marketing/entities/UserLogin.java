package marketing.entities;


import java.io.Serializable;

import javax.persistence.*;
import java.util.Date;
import java.sql.Timestamp;

@Entity
@Table(name = "userlogin", schema = "marketingdb")

public class UserLogin implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date datetime;
	
	@ManyToOne
	@JoinColumn(name = "user")
	private User user;

	public UserLogin() {
	}

	public UserLogin(User user) {
		this.datetime = new Timestamp(System.currentTimeMillis());
		this.user = user;
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public Date getDatetime() {
		return this.datetime;
	}

	public void setDatetime(Date datetime) {
		this.datetime = datetime;
	}
	
	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}