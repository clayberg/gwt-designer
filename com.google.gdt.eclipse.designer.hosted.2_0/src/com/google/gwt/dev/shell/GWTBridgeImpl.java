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
package com.google.gwt.dev.shell;

import com.google.gwt.core.client.GWTBridge;

/**
 * This class is the hosted-mode peer for {@link com.google.gwt.core.client.GWT}.
 */
public class GWTBridgeImpl extends GWTBridge {
      
  protected static ThreadLocal<String> uniqueID =
    new ThreadLocal<String>() {
    private int counter = 0;
        
    public String initialValue() {
      return "DevModeThread" + ++counter;
    }
  };

  private final ShellJavaScriptHost host;

  public GWTBridgeImpl(ShellJavaScriptHost host) {
    this.host = host;
  }

  /**
   * Resolves a deferred binding request and create the requested object.
   */
  @Override
  public <T> T create(Class<?> requestedClass) {
    String className = requestedClass.getName();
    try {
      return host.<T> rebindAndCreate(className);
    } catch (Throwable e) {
      String msg = "Deferred binding failed for '" + className
          + "' (did you forget to inherit a required module?)";
      throw new RuntimeException(msg, e);
    }
  }

  @Override
  public String getThreadUniqueID() {
    // TODO(unnurg): Remove this function once Dev Mode rewriting classes are
    // in gwt-dev.
    return uniqueID.get();
  }

  @Override
  public String getVersion() {
	    return "2.0";
  }

  /**
   * Yes, we're running as client code in the hosted mode classloader.
   */
  @Override
  public boolean isClient() {
    return true;
  }

  /**
   * Logs in dev shell.
   */
  @Override
  public void log(String message, Throwable e) {
    host.log(message, e);
  }

}
