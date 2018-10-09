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

package com.liferay.ide.installer.tests;

import com.liferay.ide.installer.tests.extensions.TempFolder;
import com.liferay.ide.installer.tests.extensions.TempFolderExtension;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.condition.EnabledOnOs;
import org.junit.jupiter.api.condition.OS;
import org.junit.jupiter.api.extension.ExtendWith;

/**
 * @author Gregory Amerson
 */
@DisplayName("MacOS installers")
@EnabledOnOs(OS.MAC)
@ExtendWith(TempFolderExtension.class)
@TestInstance(Lifecycle.PER_CLASS)
public class MacOSInstallersTest {

	@DisplayName("headless install workspace init")
	@Test
	public void headlessInstallInitWorkspace() throws Exception {
		Path workspaceDir = _tempFolder.newDirectory("ws");

		ProcessBuilder processBuilder = new ProcessBuilder();

		List<String> args = processBuilder.command();

		args.add(_installerExecutable.toString());
		args.add("--mode");
		args.add("unattended");
		args.add("--initworkspace");
		args.add("workspace");
		args.add("--workspace");
		args.add(workspaceDir.toString());
		args.add("--isdxp");
		args.add("ce");
		args.add("--bundleversion");
		args.add("7_1");

		processBuilder.redirectOutput();
		processBuilder.redirectError();

		Process process = processBuilder.start();

		String output = _readStream(process.getInputStream());

		Assertions.assertEquals(0, process.waitFor(), output);

		Path gradlePropertiesPath = workspaceDir.resolve("gradle.properties");

		Assertions.assertTrue(Files.exists(gradlePropertiesPath));

		Assertions.assertTrue(Files.exists(_bladePath));
		Assertions.assertTrue(Files.exists(_bndPath));
		Assertions.assertTrue(Files.exists(_gwPath));
		Assertions.assertTrue(Files.exists(_jpmPath));
		Assertions.assertFalse(Files.exists(_tokenPath));
	}

	@DisplayName("headless install skipping workspace init")
	@Test
	public void headlessInstallSkipWorkspace() throws Exception {
		ProcessBuilder processBuilder = new ProcessBuilder();

		List<String> args = processBuilder.command();

		args.add(_installerExecutable.toString());
		args.add("--mode");
		args.add("unattended");
		args.add("--initworkspace");
		args.add("skip");
		args.add("--isdxp");
		args.add("ce");
		args.add("--bundleversion");
		args.add("7_1");

		Process process = processBuilder.start();

		String output = _readStream(process.getInputStream());

		Assertions.assertEquals(0, process.waitFor(), output);

		Assertions.assertTrue(Files.exists(_bladePath));
		Assertions.assertTrue(Files.exists(_bndPath));
		Assertions.assertTrue(Files.exists(_gwPath));
		Assertions.assertTrue(Files.exists(_jpmPath));
		Assertions.assertFalse(Files.exists(_tokenPath));
	}

	@BeforeAll
	public void setUp() throws Exception {
		_liferayProjectSdkInstallerPath = Paths.get(
			_INSTALLERS_OUTPUT_DIR, "LiferayProjectSDK-2018.9.14-osx-installer.dmg");

		Assertions.assertTrue(Files.exists(_liferayProjectSdkInstallerPath));

		_mountPoint = Paths.get(_INSTALLERS_OUTPUT_DIR, "LiferayProjectSDK-2018.9.14-osx-installer.mountpoint");

		ProcessBuilder processBuilder = new ProcessBuilder();

		List<String> args = processBuilder.command();

		args.add("hdiutil");
		args.add("attach");
		args.add("-mountpoint");
		args.add(_mountPoint.toString());
		args.add(_liferayProjectSdkInstallerPath.toString());

		processBuilder.redirectErrorStream(true);

		Process process = processBuilder.start();

		Assertions.assertEquals(0, process.waitFor());

		_installerExecutable = _mountPoint.resolve(
			"LiferayProjectSDK-2018.9.14-osx-installer.app/Contents/MacOS/installbuilder.sh");

		Assertions.assertTrue(Files.exists(_installerExecutable));
	}

	@AfterAll
	public void tearDown() throws Exception {
		ProcessBuilder processBuilder = new ProcessBuilder();

		List<String> args = processBuilder.command();

		args.add("hdiutil");
		args.add("detach");
		args.add(_mountPoint.toString());

		Process process = processBuilder.start();

		Assertions.assertEquals(0, process.waitFor());
	}

	private String _readStream(InputStream inputStream) throws IOException {
		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

		String line;

		StringBuilder stringBuilder = new StringBuilder();

		while ((line = bufferedReader.readLine()) != null) {
			stringBuilder.append(line);
		}

		return stringBuilder.toString();
	}

	private static final String _INSTALLERS_OUTPUT_DIR = System.getProperty("installersOutputDir");

	private static final String _USER_HOME_DIR = System.getProperty("user.home");

	private Path _binPath = Paths.get(System.getProperty("user.home"), "Library/PackageManager/bin");
	private Path _bladePath = _binPath.resolve("blade");
	private Path _bndPath = _binPath.resolve("bnd");
	private Path _gwPath = _binPath.resolve("gw");
	private Path _installerExecutable;
	private Path _jpmPath = _binPath.resolve("jpm");
	private Path _liferayProjectSdkInstallerPath;
	private Path _mountPoint;
	private TempFolder _tempFolder;
	private Path _tokenPath = Paths.get(_USER_HOME_DIR, ".liferay/token");

}