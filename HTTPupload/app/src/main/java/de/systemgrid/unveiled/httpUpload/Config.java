package de.systemgrid.unveiled.httpUpload;

import android.content.Context;
import android.content.SharedPreferences;

public class Config {
	// File upload url (replace the ip with your server address)
	public static final String FILE_UPLOAD_URL = "http://sas.systemgrid.de:8080/Unveiled-Server/UploadFile";
	//public static final String FILE_UPLOAD_URL = "http://172.16.45.16:8080/Unveiled-Server/UploadFile";

	//Login url
	public static final String API_URL = "http://sas.systemgrid.de/unveiled/ftp/api/";
	
	// Directory name to store captured images and videos
    public static final String IMAGE_DIRECTORY_NAME = "unveiled";

}
