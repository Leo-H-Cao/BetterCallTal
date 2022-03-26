public interface MenuAPIs{

    /**
     * Show error API for things like invalid file uploads, invalid Room ID entered, etc
     */
    public void showError(String ErrorType, String ErrorMessage){}

    /**
     * The file that the user wants to upload
     */

    public File chooseLoadFile();

    /**
     * The file into which the user wants to save
     */
    public File chooseSaveFile();
}