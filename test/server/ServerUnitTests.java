package server;

import org.junit.* ;

public class ServerUnitTests {
	
	@Before
	public void setup() {
	}
	
	@After
	public void teardown() {
	}
	
	@Test
	public void test_1() {
		
	}

	public static void main(String[] args) {
		
		String[] testClasses = new String[] {
				"server.ServerUnitTests",
				"server.database.BatchDAOTest",
				"server.database.FieldDAOTest",
				"server.database.IndexedDataDAOTest",
				"server.database.ProjectDAOTest",
				"server.database.RecordDAOTest",
				"server.database.UserDAOTest",
				"shared.model.BatchManagerTest",
				"shared.model.FieldManagerTest",
				"shared.model.IndexedDataManagerTest",
				"shared.model.ProjectManagerTest",
				"shared.model.RecordManagerTest",
				"shared.model.UserManagerTest",
				"utilities.DataImporterTest",
				"client.communication.ClientCommunicatorTest"
		};

		org.junit.runner.JUnitCore.main(testClasses);
	}
	
}

