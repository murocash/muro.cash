package cash.muro;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;


@AllArgsConstructor
public class MuroPaging {
	
	private static final String SEP = "/";
	
	@Setter
	@Getter
	private int currentPage = 1;
	
	private int rowsPerPage = 10;
	
	@Getter
	private final long totalRows;
	
	private String basePath;
	
	public String firstPath() {
		return basePath;
	}
	
	public String lastPath() {
		return buildPath(last());
	}
	
	public String nextPath() {
		return buildPath(next());
	}
	
	public String previousPath() {
		return buildPath(previous()); 
	}
	
	public long first() {
		return 1;
	}
	
	public long last() {
		return (totalRows + rowsPerPage - 1) / rowsPerPage; //Math.ceil(totalRows/rowsPerPage) without type castings
	}

	public long next() {
		long last = last();
		return currentPage + 1 > last ? last : currentPage + 1;
	}
	
	public long previous() {
		long previous = 1;
		return currentPage - 1 < previous ? previous : currentPage - 1;
	}
	
	public long currentFirstRow() {
		return ((currentPage - 1) * rowsPerPage) + 1;
	}
	
	public long currentLastRow() {
		return Math.min(totalRows, currentPage * rowsPerPage);
	}
	
	private String buildPath(long page) {
		StringBuilder path = new StringBuilder(basePath);
		if (page > 1) {
			path.append(SEP).append(page);
		}
		return path.toString();
	}
}
