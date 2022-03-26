public interface ButtonFactory {

	// Used to help create easily repeatable buttons on the frontend
    public Button createButton(String displayText, String id, EventHandler<ActionEvent> action) {}

	// Used to run a specified action on the specified button
	public void addAction(Button b, EventHandler<ActionEvent> action) {}
}