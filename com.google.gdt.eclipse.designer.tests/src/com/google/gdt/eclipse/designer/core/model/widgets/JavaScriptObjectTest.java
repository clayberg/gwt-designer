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
package com.google.gdt.eclipse.designer.core.model.widgets;

import com.google.gdt.eclipse.designer.core.model.GwtModelTest;
import com.google.gdt.eclipse.designer.model.widgets.panels.RootPanelInfo;

/**
 * Test for using <code>JavaScriptObject</code> methods.
 * <p>
 * Problem is that <code>CompilingClassLoader</code> rewrites <code>JavaScriptObject</code> classes
 * to make them interfaces and moves implementation into <code>ObjectName$</code>. See for detailed
 * description <code>HostedModeClassRewriter</code>.
 * 
 * @author scheglov_ke
 */
public class JavaScriptObjectTest extends GwtModelTest {
  ////////////////////////////////////////////////////////////////////////////
  //
  // Exit zone :-) XXX
  //
  ////////////////////////////////////////////////////////////////////////////
  public void _test_exit() throws Exception {
    System.exit(0);
  }

  ////////////////////////////////////////////////////////////////////////////
  //
  // Tests
  //
  ////////////////////////////////////////////////////////////////////////////
  public void test_0() throws Exception {
    dontUseSharedGWTState();
    setFileContentSrc(
        "test/client/MyObject.java",
        getTestSource(
            "public class MyObject extends JavaScriptObject {",
            "  protected MyObject() {",
            "  }",
            "  public static native MyObject newInstance() /*-{",
            "    return {};",
            "  }-*/;",
            "}"));
    waitForAutoBuild();
    // parse
    RootPanelInfo frame =
        parseJavaInfo(
            "public class Test implements EntryPoint {",
            "  public void onModuleLoad() {",
            "    RootPanel rootPanel = RootPanel.get();",
            "    Button button = new Button(MyObject.newInstance().toString());",
            "    rootPanel.add(button);",
            "  }",
            "}");
    frame.refresh();
    assertNoErrors(frame);
  }
}