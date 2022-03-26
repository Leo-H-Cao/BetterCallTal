public interface MenuAPIs{

    /**
     * Show error API for things like invalid file uploads, invalid Room ID entered, etc
     */
    public void showError(String ErrorType, String ErrorMessage){}
}