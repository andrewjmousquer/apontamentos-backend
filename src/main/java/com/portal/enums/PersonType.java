package com.portal.enums;

import com.portal.model.ClassifierModel;

public enum PersonType {
	PJ( new ClassifierModel( 30, "PJ", "PERSON_CLASSIFICATION" ) ),
	PF( new ClassifierModel( 31, "PF", "PERSON_CLASSIFICATION" ) ),
	ESTRANGEIRO( new ClassifierModel( 32, "ESTRANGEIRO", "PERSON_CLASSIFICATION" ) );

	private ClassifierModel type;
	
	PersonType( ClassifierModel type ) {
		this.type = type;
	}

	public ClassifierModel getType() {
		return type;
	}
	
	public static PersonType getById( Integer id ) {
		if( id != null ) {
			for( PersonType type : PersonType.values() ) {
				if( type.getType().getId().equals( id ) ) {
					return type;
				}
			}
		}
		
		throw new IllegalArgumentException( "PersonType - Não foi encontrado o ENUM com o ID " + id );
	}
}
