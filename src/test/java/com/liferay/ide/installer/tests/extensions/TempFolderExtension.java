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

import java.lang.reflect.Field;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Stream;

import org.junit.jupiter.api.extension.AfterTestExecutionCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestInstancePostProcessor;

/**
 * @author Gregory Amerson
 */
public class TempFolderExtension implements AfterTestExecutionCallback, TestInstancePostProcessor {

	public TempFolderExtension() {
		_tempFolders = new ArrayList<>();
	}

	@Override
	public void afterTestExecution(ExtensionContext extensionContext) throws Exception {
		_tempFolders.forEach(TempFolder::delete);
	}

	@Override
	public void postProcessTestInstance(Object testInstance, ExtensionContext extensionContext) throws Exception {
		Class<? extends Object> testClass = testInstance.getClass();

		Stream<Field> stream = Arrays.stream(testClass.getDeclaredFields());

		stream.filter(
			field -> field.getType() == TempFolder.class
		).peek(
			field -> field.setAccessible(true)
		).forEach(
			field -> _injectTemporaryFolder(testInstance, field)
		);
	}

	private TempFolder _createTempFolder() throws IOException {
		TempFolder tempFolder = new TempFolder();

		_tempFolders.add(tempFolder);

		return tempFolder;
	}

	private void _injectTemporaryFolder(Object instance, Field field) {
		try {
			field.set(instance, _createTempFolder());
		}
		catch (Exception exception) {
			throw new RuntimeException(exception);
		}
	}

	private final Collection<TempFolder> _tempFolders;

}