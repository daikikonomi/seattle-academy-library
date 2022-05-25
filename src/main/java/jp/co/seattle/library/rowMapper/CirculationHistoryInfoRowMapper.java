package jp.co.seattle.library.rowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.RowMapper;

import jp.co.seattle.library.dto.CirculationHistoryInfo;

@Configuration
public class CirculationHistoryInfoRowMapper implements RowMapper<CirculationHistoryInfo>{
	
	@Override
    public CirculationHistoryInfo mapRow(ResultSet rs, int rowNum) throws SQLException {
        // Query結果（ResultSet rs）を、オブジェクトに格納する実装
		CirculationHistoryInfo CirculationHistoryInfo = new CirculationHistoryInfo();

		CirculationHistoryInfo.setBookId(rs.getInt("id"));
		CirculationHistoryInfo.setTitle(rs.getString("title")); 
		CirculationHistoryInfo.setRentDate(rs.getString("rent_date"));
		CirculationHistoryInfo.setReturnDate(rs.getString("return_date"));
		
        return CirculationHistoryInfo;
    }


}
