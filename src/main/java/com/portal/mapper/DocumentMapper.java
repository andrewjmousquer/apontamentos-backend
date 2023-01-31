package com.portal.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.portal.enums.DocumentType;
import com.portal.model.Document;
import com.portal.model.UserModel;

public class DocumentMapper implements RowMapper<Document> {
	@Override
	public Document mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		int usrId = rs.getInt( "usr_id" );
		UserModel user = null;
		if( usrId != 0 ) {
			user = new UserModel( usrId );
		}
		
		return Document.builder()
						.id( rs.getInt( "doc_id" ) )
						.fileName( rs.getString( "file_name" ) )
						.contentType( rs.getString( "content_type" ) )
						.description( rs.getString( "description" ) )
						.filePath( rs.getString( "file_path" ) )
						.createDate( rs.getTimestamp( "create_date" ).toLocalDateTime() )
						.type( DocumentType.getById( rs.getInt( "type_cla_id" ) ) )
						.user( user )
						.build();
	}
}
