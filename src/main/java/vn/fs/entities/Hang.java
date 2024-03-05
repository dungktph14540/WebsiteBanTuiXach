package vn.fs.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@SuppressWarnings("serial")
@Data
@Setter @Getter

@Entity
@Table(name = "hang")
public class Hang implements Serializable{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idhang;
	@Column(name = "tenhang")
	private String tenhang;

	
	public Hang() {
		super();
	}

	public Hang(Long idhang, String tenhang) {
		super();
		this.idhang = idhang;
		this.tenhang = tenhang;
	}

	public Long getIdhang() {
		return idhang;
	}

	public void setIdhang(Long idhang) {
		this.idhang = idhang;
	}

	public String getTenhang() {
		return tenhang;
	}

	public void setTenhang(String tenhang) {
		this.tenhang = tenhang;
	}


	
}
