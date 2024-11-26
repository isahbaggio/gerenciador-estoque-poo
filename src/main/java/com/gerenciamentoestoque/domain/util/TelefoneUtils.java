package com.gerenciamentoestoque.domain.util;

public class TelefoneUtils
{
	public static String formatterTelefone(String telefone)
	{
		if (telefone == null || telefone.length() != 11) {
			return telefone;
		}
		return String.format("(%s) %s %s-%s",
			telefone.substring(0, 2),
			telefone.substring(2, 3),
			telefone.substring(3, 7),
			telefone.substring(7));
	}
}
