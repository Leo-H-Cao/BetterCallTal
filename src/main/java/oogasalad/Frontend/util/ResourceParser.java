package oogasalad.Frontend.util;

import java.util.ResourceBundle;

public class ResourceParser {

	public static int getInt(ResourceBundle rb, String key) {
		return Integer.parseInt(rb.getString(key));
	}
}
