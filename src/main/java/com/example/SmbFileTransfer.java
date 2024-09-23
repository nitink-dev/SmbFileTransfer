package com.example;

import com.hierynomus.msdtyp.AccessMask;
import com.hierynomus.msfscc.FileAttributes;
import com.hierynomus.mssmb2.SMB2CreateDisposition;
import com.hierynomus.mssmb2.SMB2CreateOptions;
import com.hierynomus.mssmb2.SMB2ShareAccess;
import com.hierynomus.smbj.SMBClient;
import com.hierynomus.smbj.auth.AuthenticationContext;
import com.hierynomus.smbj.connection.Connection;
import com.hierynomus.smbj.session.Session;
import com.hierynomus.smbj.share.DiskShare;
import com.hierynomus.smbj.share.File;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.EnumSet;
import java.util.Set;

public class SmbFileTransfer {
    public static void main(String[] args) {
        String serverAddress = "3.81.15.48";
        String username = "Administrator";
        String password = "JQqHxgCmlZIA6m&3h(MQQM4zCWri=-Lj";
        String domain = "";  // Set domain if necessary, else leave it empty.
        String shareName = "SMBShare";
        String localFilePath = "file.txt";
        String remoteFilePath = "file.txt";

        SMBClient client = null;
        Connection connection = null;
        Session session = null;
        DiskShare share = null;
        File remoteFile = null;
        FileInputStream fis = null;

        try {
            client = new SMBClient();
            connection = client.connect(serverAddress);
            AuthenticationContext ac = new AuthenticationContext(username, password.toCharArray(), domain);
            session = connection.authenticate(ac);

            // Connect to the share
            share = (DiskShare) session.connectShare(shareName);

            Set<AccessMask> accessMask; // Set read and write access
            accessMask = EnumSet.of(AccessMask.GENERIC_READ, AccessMask.GENERIC_WRITE);
            Set<FileAttributes> attributes = EnumSet.noneOf(FileAttributes.class); // No special attributes
            Set<SMB2ShareAccess> shareAccesses = EnumSet.of(SMB2ShareAccess.FILE_SHARE_READ); // Allow read access to others
            SMB2CreateDisposition createDisposition = SMB2CreateDisposition.FILE_OVERWRITE_IF; // Create if exists, overwrite
            Set<SMB2CreateOptions> createOptions = EnumSet.noneOf(SMB2CreateOptions.class); // No additional options

            // Attempt to open the file
            try {
                remoteFile = share.openFile(remoteFilePath, accessMask, attributes, shareAccesses, createDisposition, createOptions);
                System.out.println("File opened successfully: " + remoteFilePath);
            } catch (Exception e) {
                e.printStackTrace();
                System.err.println("Failed to open/create file: " + remoteFilePath);
            }

            // Write data to the remote file
            fis = new FileInputStream(localFilePath);
            byte[] buffer = new byte[4096];
            int bytesRead;
            long fileOffset = 0;

            while ((bytesRead = fis.read(buffer)) != -1) {
                // Perform the write operation
                remoteFile.write(buffer, fileOffset, 0, bytesRead);
                fileOffset += bytesRead;  // Move the offset by the number of bytes written
            }

            System.out.println("File transfer complete.");

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // Close resources manually
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (remoteFile != null) {
                remoteFile.close();
            }
            if (share != null) {
                try {
                    share.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (session != null) {
                try {
                    session.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (client != null) {
                client.close();
            }
        }
    }
}
