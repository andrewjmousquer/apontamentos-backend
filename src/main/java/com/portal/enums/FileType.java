package com.portal.enums;

public enum FileType {

	CHECKLIST_PHOTO("checklist_photo"), CHECKLIST_PDF("checklist_pdf");

	private String name;

	private FileType(String stringVal) {
		name = stringVal;
	}

	@Override
	public String toString() {
		return name;
	}

	public static String getEnumByString(String code) {
		for (FileType e : FileType.values()) {
			if (e.name.equals(code))
				return e.name();
		}
		return null;
	}
}