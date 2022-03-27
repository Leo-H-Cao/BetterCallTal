package oogasalad.Frontend;

import javafx.stage.Stage;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Stream;

public class LanguageModal {
	private ResourceBundle myResources;

	public LanguageModal(Stage stage) {

	}




	// Reads available language files from the specified path; used in generating the language dropdown
	// @return list of language names
	private List<String> readAvailableLanguages() {
		ArrayList<String> ret = new ArrayList<>();
		String PATH_TO_LANGUAGES = myResources.getString("LanguagePath");
		try (Stream<Path> paths = Files.walk(Paths.get(System.getProperty("user.dir") + PATH_TO_LANGUAGES))) {
			paths.filter(Files::isRegularFile).forEach((file) -> {
				String fileName = String.valueOf(file.getFileName()).split("\\.")[0];
				ret.add(fileName); // Add name of file without the extension
			});
		} catch (IOException e) {
			e.printStackTrace();
		}

		return ret;
	}
}
