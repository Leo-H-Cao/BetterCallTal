public interface ButtonFactory {

	// Display a popup window with a title and message
	// Can be used to show the user an error or additional information
    public void displayPopup(String title, Node body) {}

	// Similar function as above but with a simple string for the message
	public void displayPopup(String title, String body) {}
}