public interface ButtonFactory {

    public Button createButton(String displayText, String id, EventHandler<ActionEvent> action) {}

	public void addAction(Button b, EventHandler<ActionEvent> action) {}
}