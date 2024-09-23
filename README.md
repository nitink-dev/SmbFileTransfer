# SMB File Transfer POC

This project demonstrates how to transfer a local file to a remote SMB share using the `SMBJ` library in Java. The code connects to a remote SMB server, opens or creates a file on the share, and writes data from the local file to the remote file.

## Prerequisites

To run this project, you will need:

1. Java Development Kit (JDK) 8 or higher
2. Maven or Gradle for managing dependencies
3. A remote SMB server accessible from your environment
4. A valid SMB share on the server with appropriate permissions

## Dependencies

Make sure to include the `SMBJ` library in your `pom.xml` (if using Maven) or `build.gradle` (if using Gradle).

### Maven

```xml
<dependency>
    <groupId>com.hierynomus</groupId>
    <artifactId>smbj</artifactId>
    <version>0.10.0</version>
</dependency>
```

### Gradle

```groovy
implementation 'com.hierynomus:smbj:0.10.0'
```

## Code Explanation

1. **Connect to SMB Server:**
   - The program first connects to the SMB server using the provided credentials (`username`, `password`, `domain`, and `serverAddress`).
   
2. **Authenticate:**
   - The authentication is handled via the `AuthenticationContext` class, and a session is created.

3. **Connect to SMB Share:**
   - Using the session, the code connects to the specified SMB share (`shareName`).

4. **Open/Create File:**
   - The program attempts to open or create the remote file (`remoteFilePath`). If the file exists, it will overwrite it. If it doesn’t exist, it will create the file.

5. **Transfer Local File to Remote File:**
   - The local file (`localFilePath`) is read in chunks and written to the remote file.

6. **Resource Cleanup:**
   - All resources (e.g., file streams, SMB connections, sessions) are closed at the end to avoid resource leaks.

## How to Run

1. Clone this repository or copy the provided code into a new Java project.

2. Set up your Maven or Gradle project, ensuring the necessary dependencies are included.

3. Update the following variables in the code with your SMB server details:
   - `serverAddress`: IP address or hostname of your SMB server.
   - `username`: SMB server username.
   - `password`: SMB server password.
   - `domain`: Domain for authentication, if required (leave it blank if not needed).
   - `shareName`: Name of the SMB share to connect to.
   - `localFilePath`: Path to the local file you wish to transfer.
   - `remoteFilePath`: Path on the SMB share where the file will be created.

4. Compile and run the project using your IDE or from the command line:
   ```bash
   mvn clean install
   java -jar target/your-jar-file.jar
   ```

5. The program will transfer the local file to the specified SMB share.

## Example

In this example, the program transfers `file.txt` from the local system to an SMB share located on `3.81.15.48`, which has the share name `SMBShare`. The file is written to the root of the share.

## Troubleshooting

- **Authentication Failures**: Ensure the credentials and domain (if applicable) are correct.
- **File Permission Errors**: Ensure that the user has the necessary read/write permissions to the SMB share.
- **Connection Issues**: Verify network connectivity and ensure that the SMB port (typically 445) is open between your local machine and the server.
