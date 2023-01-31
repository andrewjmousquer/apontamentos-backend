package com.portal.enums;

import com.portal.model.ClassifierModel;

public enum DocumentType {

	RG( new ClassifierModel( 81, "RG", "DOCUMENT_TYPE" ) ),
	CPF( new ClassifierModel( 82, "CPF", "DOCUMENT_TYPE" ) ),
	CONTRACT( new ClassifierModel( 83, "CONTRACT", "DOCUMENT_TYPE" ) );

	private ClassifierModel type;
	
	DocumentType( ClassifierModel type ) {
		this.type = type;
	}

	public ClassifierModel getType() {
		return type;
	}
	
	public static DocumentType getById( Integer id ) {
		if( id != null ) {
			for( DocumentType type : DocumentType.values() ) {
				if( type.getType().getId().equals( id ) ) {
					return type;
				}
			}
		}
		
		throw new IllegalArgumentException( "DocumentType - Não foi encontrado o ENUM com o ID " + id );
	}
}
