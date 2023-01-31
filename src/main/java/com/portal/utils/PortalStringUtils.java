package com.portal.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Path;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.Base64;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.text.MaskFormatter;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import com.itextpdf.html2pdf.HtmlConverter;
import com.portal.exceptions.AppException;

public class PortalStringUtils {

	public static String integerListToString(List<Integer> list) {
		if (list != null && !list.isEmpty()) {
			StringBuilder builder = new StringBuilder();

			for (Integer i : list) {
				builder.append(i);
				builder.append(",");
			}

			return builder.substring(0, builder.length() - 1);
		}

		return "";
	}

	public static <I> String listToString(List<I> list) {
		if (list != null && !list.isEmpty()) {
			StringBuilder builder = new StringBuilder();

			for (I i : list) {
				builder.append(i.toString());
				builder.append(",");
			}

			return builder.substring(0, builder.length() - 1);
		}

		return "";
	}

	public static String isValidString(String string) {
		if (string != null) {
			if (!string.equals("") && !string.equalsIgnoreCase("null")) {
				return string;
			}
		}

		return null;
	}

	public static String numberFormat(Double value, String format) {
		if (value == null || value.isNaN()) {
			value = 0d;
		}

		DecimalFormat df = new DecimalFormat(format);
		return df.format(value).replaceAll("\\,", ".");
	}

	public static boolean validateEmail(String str) {
		if (str == null || str.contains("")) {
			return false;
		}

		Pattern p = Pattern
				.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
		Matcher m = p.matcher(str.trim());
		return m.matches();
	}

	public static boolean validateFoneNumber(String str) {
		if (str == null || str.contains("")) {
			return false;
		}

		Pattern p = Pattern.compile(".((10)|([1-9][1-9]).)\\s9?[6-9][0-9]{3}-[0-9]{4}");
		Matcher m = p.matcher(str);

		Pattern p2 = Pattern.compile(".((10)|([1-9][1-9]).)\\s[2-5][0-9]{3}-[0-9]{4}");
		Matcher m2 = p2.matcher(str);

		return m.matches() || m2.matches();
	}

	/**
	 * Converte de bytes para a unidade mais adequada.
	 * 
	 * @param bytes
	 * @param si
	 * @return
	 */
	public static String humanReadableByteCount(long bytes, boolean si) {
		int unit = si ? 1000 : 1024;

		if (bytes < unit)
			return bytes + " B";

		int exp = (int) (Math.log(bytes) / Math.log(unit));
		String pre = (si ? "kMGTPE" : "KMGTPE").charAt(exp - 1) + (si ? "" : "i");

		return String.format("%.0f %sB", bytes / Math.pow(unit, exp), pre);
	}

	public static String formatCnpj(String cnpj) {
		try {
			MaskFormatter mask = new MaskFormatter("###.###.###/####-##");
			mask.setValueContainsLiteralCharacters(false);
			return mask.valueToString(cnpj);
		} catch (ParseException ex) {
		}

		return cnpj;
	}

	public static String formatCpf(String cpf) {
		try {
			MaskFormatter mask = new MaskFormatter("###.###.###-##");
			mask.setValueContainsLiteralCharacters(false);
			return mask.valueToString(cpf);
		} catch (ParseException ex) {
		}

		return cpf;
	}

	public static String generateRandomAlphaNumericPassword(int numberOfCharacter) {

		int leftLimit = 48;
		int rightLimit = 122;

		Random random = new Random();

		return random.ints(leftLimit, rightLimit + 1).filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
				.limit(numberOfCharacter)
				.collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append).toString();
	}

	private static String makeAuthorizationHeader() {

		// Concatena Username e Password
		String plainCredentials = "demandasti@carbonblindados.com.br" + ":" + "XBRBJSpzDXKE9RYCo4KJ2F93";
		// Codifica para Base 64
		String base64Credentials = new String(Base64.getEncoder().encode(plainCredentials.getBytes()));
		// Cria o Header codificado
		String authorizationHeader = "Basic " + base64Credentials;

		return authorizationHeader;
	}

	public static void main(String[] args) throws IOException, InterruptedException, AppException {

		TemplateEngine templateEngine = new TemplateEngine();

		String uploadFolder = "D:\\Projetos SBM\\CARBON_APONTAMENTOS\\";

		String fullPath = uploadFolder + "uploaded-files" + File.separator + "teste.pdf";

		Path path = Path.of(fullPath);

		OutputStream fileOutputStream = new FileOutputStream(path.toString());
		Context context = new Context();
		context.setVariable("teste",
				"\"Lorum ipsum some text after image. Lorum ipsum some text after image. Lorum ipsum some text after image. Lorum ipsum some text after image. Lorum ipsum some text after image. Lorum ipsum some text after image. Lorum ipsum some text after image. Lorum ipsum some text after image. Lorum ipsum some text after image.\";");

		String processTeamplated = templateEngine.process("relatorio-pdf-checklist", context);

		HtmlConverter.convertToPdf(processTeamplated, fileOutputStream);

	}
}
