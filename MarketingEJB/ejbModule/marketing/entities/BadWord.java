package marketing.entities;

import java.io.Serializable;

import javax.persistence.*;



@Entity
@Table(name = "badwords", schema = "marketingdb")

public class BadWord implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	private String badword;

	public BadWord() {
	}

	public BadWord(String badword) {
		this.badword = badword;
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getBadword() {
		return this.badword;
	}

	public void setBadword(String badword) {
		this.badword = badword;
	}

}