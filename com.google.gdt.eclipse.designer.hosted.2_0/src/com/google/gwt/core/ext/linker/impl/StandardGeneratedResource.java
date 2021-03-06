/*******************************************************************************
 * Copyright 2011 Google Inc. All Rights Reserved.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/
package com.google.gwt.core.ext.linker.impl;

import com.google.gwt.core.ext.Generator;
import com.google.gwt.core.ext.TreeLogger;
import com.google.gwt.core.ext.UnableToCompleteException;
import com.google.gwt.core.ext.linker.GeneratedResource;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * The standard implementation of {@link GeneratedResource}.
 */
public class StandardGeneratedResource extends GeneratedResource {
  private final File file;

  public StandardGeneratedResource(Class<? extends Generator> generatorType,
      String partialPath, File file) {
    super(StandardLinkerContext.class, generatorType, partialPath);
    this.file = file;
  }

  @Override
  public InputStream getContents(TreeLogger logger)
      throws UnableToCompleteException {
    try {
      return new FileInputStream(file);
    } catch (IOException e) {
      logger.log(TreeLogger.ERROR, "Unable to open file", e);
      throw new UnableToCompleteException();
    }
  }

  @Override
  public long getLastModified() {
    return file.lastModified();
  }
}
