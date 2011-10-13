package org.greatage.db.gae;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;
import org.greatage.db.Database;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public abstract class AbstractGAEDBTest extends Assert {
	private final LocalServiceTestHelper helper = new LocalServiceTestHelper(new LocalDatastoreServiceTestConfig());
	protected DatastoreService dataStore;
	protected Database database;

	@BeforeMethod
	public void setUp() {
		helper.setUp();
		dataStore = DatastoreServiceFactory.getDatastoreService();
		database = new GAEDatabase(dataStore);
	}

	@AfterMethod
	public void tearDown() {
		database = null;
		dataStore = null;
		helper.tearDown();
	}
}
