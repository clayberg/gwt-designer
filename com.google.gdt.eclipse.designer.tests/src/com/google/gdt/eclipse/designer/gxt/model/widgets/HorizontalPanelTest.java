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
package com.google.gdt.eclipse.designer.gxt.model.widgets;

import com.google.gdt.eclipse.designer.gxt.model.GxtModelTest;
import com.google.gdt.eclipse.designer.model.widgets.WidgetInfo;

import org.eclipse.wb.internal.core.model.generic.FlowContainer;
import org.eclipse.wb.internal.core.model.generic.FlowContainerFactory;

/**
 * Test for {@link HorizontalPanelInfo}.
 * 
 * @author scheglov_ke
 */
public class HorizontalPanelTest extends GxtModelTest {
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
  public void test_parse_virtualLayoutData() throws Exception {
    HorizontalPanelInfo panel =
        parseJavaInfo(
            "public class Test extends HorizontalPanel {",
            "  public Test() {",
            "    {",
            "      Button button = new Button();",
            "      add(button);",
            "    }",
            "  }",
            "}");
    assertHierarchy(
        "{this: com.extjs.gxt.ui.client.widget.HorizontalPanel} {this} {/add(button)/}",
        "  {new: com.extjs.gxt.ui.client.widget.button.Button} {local-unique: button} {/new Button()/ /add(button)/}",
        "    {virtual-layout_data: com.extjs.gxt.ui.client.widget.layout.TableData} {virtual-layout-data} {}");
    assertFalse(panel.hasLayout());
    // "button" has LayoutData property
    WidgetInfo button = panel.getWidgets().get(0);
    assertNotNull(button.getPropertyByTitle("LayoutData"));
  }

  public void test_parse_explicitLayoutData() throws Exception {
    HorizontalPanelInfo panel =
        parseJavaInfo(
            "public class Test extends HorizontalPanel {",
            "  public Test() {",
            "    {",
            "      Button button = new Button();",
            "      add(button, new TableData());",
            "    }",
            "  }",
            "}");
    assertHierarchy(
        "{this: com.extjs.gxt.ui.client.widget.HorizontalPanel} {this} {/add(button, new TableData())/}",
        "  {new: com.extjs.gxt.ui.client.widget.button.Button} {local-unique: button} {/new Button()/ /add(button, new TableData())/}",
        "    {new: com.extjs.gxt.ui.client.widget.layout.TableData} {empty} {/add(button, new TableData())/}");
    // "button" has LayoutData property
    WidgetInfo button = panel.getWidgets().get(0);
    assertNotNull(button.getPropertyByTitle("LayoutData"));
  }

  public void test_MOVE_out() throws Exception {
    LayoutContainerInfo container =
        parseJavaInfo(
            "public class Test extends LayoutContainer {",
            "  public Test() {",
            "    {",
            "      HorizontalPanel panel = new HorizontalPanel();",
            "      {",
            "        Button button = new Button();",
            "        panel.add(button, new TableData());",
            "      }",
            "      add(panel);",
            "    }",
            "  }",
            "}");
    assertHierarchy(
        "{this: com.extjs.gxt.ui.client.widget.LayoutContainer} {this} {/add(panel)/}",
        "  {implicit-layout: default} {implicit-layout} {}",
        "  {new: com.extjs.gxt.ui.client.widget.HorizontalPanel} {local-unique: panel} {/new HorizontalPanel()/ /panel.add(button, new TableData())/ /add(panel)/}",
        "    {new: com.extjs.gxt.ui.client.widget.button.Button} {local-unique: button} {/new Button()/ /panel.add(button, new TableData())/}",
        "      {new: com.extjs.gxt.ui.client.widget.layout.TableData} {empty} {/panel.add(button, new TableData())/}");
    HorizontalPanelInfo panel = (HorizontalPanelInfo) container.getWidgets().get(0);
    WidgetInfo button = panel.getWidgets().get(0);
    // move "button" to "container"
    container.getLayout().command_MOVE(button, null);
    assertEditor(
        "public class Test extends LayoutContainer {",
        "  public Test() {",
        "    {",
        "      HorizontalPanel panel = new HorizontalPanel();",
        "      add(panel);",
        "    }",
        "    {",
        "      Button button = new Button();",
        "      add(button);",
        "    }",
        "  }",
        "}");
    assertHierarchy(
        "{this: com.extjs.gxt.ui.client.widget.LayoutContainer} {this} {/add(panel)/ /add(button)/}",
        "  {implicit-layout: default} {implicit-layout} {}",
        "  {new: com.extjs.gxt.ui.client.widget.HorizontalPanel} {local-unique: panel} {/new HorizontalPanel()/ /add(panel)/}",
        "  {new: com.extjs.gxt.ui.client.widget.button.Button} {local-unique: button} {/new Button()/ /add(button)/}");
  }

  public void test_command_CREATE() throws Exception {
    HorizontalPanelInfo panel =
        parseJavaInfo(
            "// filler filler filler",
            "public class Test extends HorizontalPanel {",
            "  public Test() {",
            "  }",
            "}");
    panel.refresh();
    //
    ComponentInfo newButton = createButton();
    FlowContainer flowContainer = new FlowContainerFactory(panel, true).get().get(0);
    flowContainer.command_CREATE(newButton, null);
    assertEditor(
        "// filler filler filler",
        "public class Test extends HorizontalPanel {",
        "  public Test() {",
        "    {",
        "      Button button = new Button();",
        "      add(button);",
        "    }",
        "  }",
        "}");
    assertHierarchy(
        "{this: com.extjs.gxt.ui.client.widget.HorizontalPanel} {this} {/add(button)/}",
        "  {new: com.extjs.gxt.ui.client.widget.button.Button empty} {local-unique: button} {/new Button()/ /add(button)/}",
        "    {virtual-layout_data: com.extjs.gxt.ui.client.widget.layout.TableData} {virtual-layout-data} {}");
  }
}