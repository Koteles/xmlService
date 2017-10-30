package com.zip;

import java.io.File;
import java.io.IOException;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileSystemView;

public class DemoJFileChooser {
	

	public void displayContent() {

		JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());

		int returnValue = jfc.showOpenDialog(null);
		// int returnValue = jfc.showSaveDialog(null);
		System.out.println(returnValue);
		if (returnValue == JFileChooser.APPROVE_OPTION) {
			File selectedFile = jfc.getSelectedFile();
			//System.out.println(selectedFile.getAbsolutePath());
			try {
				ZipFile zipFile = new ZipFile(selectedFile);
				Enumeration<?> enu = zipFile.entries();
				while (enu.hasMoreElements()) {
					ZipEntry zipEntry = (ZipEntry) enu.nextElement();
					String name = zipEntry.getName();
					long size = zipEntry.getSize();
					long compressedSize = zipEntry.getCompressedSize();
					System.out.println(name);
					/*System.out.printf("name: %-20s | size: %6d | compressed size: %6d\n", 
						name, size, compressedSize);*/
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

}
