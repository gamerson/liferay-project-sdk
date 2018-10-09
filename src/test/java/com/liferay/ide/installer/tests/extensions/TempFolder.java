/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.ide.installer.tests.extensions;

import java.io.IOException;

import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

/**
 * @author Gregory Amerson
 */
public class TempFolder {

	public TempFolder() throws IOException {
		_tempDirectory = Files.createTempDirectory("junit5-temp-folder");
	}

	public void delete() {
		try {
			Files.walkFileTree(_tempDirectory, new DeleteAllVisitor());
		}
		catch (IOException ioe) {
			throw new RuntimeException(ioe);
		}
	}

	public Path getRoot() {
		return _tempDirectory;
	}

	public Path newDirectory(String name) {
		Path newFolder = _tempDirectory.resolve(name);

		try {
			Files.createDirectories(newFolder);
		}
		catch (IOException ioe) {
			throw new RuntimeException(ioe);
		}

		return newFolder;
	}

	public Path newFile(String name) throws IOException {
		Path newPath = _tempDirectory.resolve(name);

		Files.createFile(newPath);

		return newPath;
	}

	private Path _tempDirectory;

	private static class DeleteAllVisitor extends SimpleFileVisitor<Path> {

		@Override
		public FileVisitResult postVisitDirectory(Path path, IOException exception) throws IOException {
			Files.delete(path);

			return FileVisitResult.CONTINUE;
		}

		@Override
		public FileVisitResult visitFile(Path path, BasicFileAttributes attributes) throws IOException {
			Files.delete(path);

			return FileVisitResult.CONTINUE;
		}

	}

}