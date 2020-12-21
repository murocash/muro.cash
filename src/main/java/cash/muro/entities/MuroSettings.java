package cash.muro.entities;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import org.springframework.data.domain.PageRequest;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class MuroSettings {

	public static final String MURO_TITLE = "title";
	public static final String MURO_LIST = "muroList";
	public static final String MURO_PAGING = "muroPaging";

	@Id
	@Column(updatable = false, columnDefinition = "BOOLEAN CHECK (id = true) AND ")
	private Boolean id = true;
	private int daysFree = 7;
	private int freePages = 5;
	private int rowsPerPage = 10;
	private int freeRows = 4;

	public MuroSettings(int daysFree, int freePages, int rowsPerPage, int freeRows) {
		this.daysFree = daysFree;
		this.daysFree = freePages;
		this.rowsPerPage = rowsPerPage;
		this.freeRows = freeRows;
	}

	public PageRequest pageRequest(int page) {
		return PageRequest.of(page, rowsPerPage);
	}

	public List<?> limitList(List<?> list) {
		return list.subList(0, Math.min(getFreeRows(), list.size()));
	}
}
