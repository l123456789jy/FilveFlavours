package suzhou.dataup.cn.myapplication.DB;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import android.content.Context;
import android.os.Environment;

public class DBManager {
	private final int BUFFER_SIZE = 400000;
	public static final String PACKAGE_NAME = "com.eyu.weatherbycityname";
	public static final String DB_NAME = "myapp.db";
	public static final String DB_PATH = "/data"
			+ Environment.getDataDirectory().getAbsolutePath() + "/"
			+ PACKAGE_NAME + "/databases";

	private Context context;

	public DBManager(Context context) {
		this.context = context;
	}

	/** copy the database under raw*/
	public void copyDatabase() {

		File file = new File(DB_PATH);
		if (!file.isDirectory())
			file.mkdir();
		String dbfile = DB_PATH + "/" + DB_NAME;

		try {
			if (new File(dbfile).length() == 0) {

				FileOutputStream fos = new FileOutputStream(dbfile);
				byte[] buffer = new byte[BUFFER_SIZE];

				readDB(fos, buffer, R.raw.citychina);

				fos.close();
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void readDB(FileOutputStream fos, byte[] buffer, int db_id)
			throws IOException {
		int count;
		InputStream is;
		is = this.context.getResources().openRawResource(db_id);
		while ((count = is.read(buffer)) > 0) {
			fos.write(buffer, 0, count);
		}
		is.close();
	}
}
