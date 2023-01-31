package com.portal.utils;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;

public class PortalNumberUtils {
	
	public static boolean intToBoolean( int value ) {
		return value == 1;
	}

	public static int booleanToInt( Boolean value ) {
		if( value != null ) {
			return ( value ? 1 : 0 );
		}

		return 0;
	}

	public static String formatDouble(Double d){
		DecimalFormat df = new DecimalFormat("##.##");
		return df.format(d).replaceAll("\\,", ".");
	}
	
	public static String formatBigDecimal(BigDecimal d) {
        return NumberFormat.getCurrencyInstance().format(d);
    }
	
	public static String normalizeCnpj(String cnpj) {
		return cnpj.replace( "/", "" ).replace( ".", "" ).replace("-", "");
	}
	
}
