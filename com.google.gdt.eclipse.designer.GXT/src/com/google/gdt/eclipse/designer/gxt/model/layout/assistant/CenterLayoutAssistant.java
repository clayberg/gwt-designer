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
package com.google.gdt.eclipse.designer.gxt.model.layout.assistant;

import com.google.gdt.eclipse.designer.gxt.model.layout.CenterLayoutInfo;

import org.eclipse.wb.core.editor.actions.assistant.AbstractAssistantPage;
import org.eclipse.wb.core.model.ObjectInfo;

import org.eclipse.swt.widgets.Composite;

import java.util.List;

/**
 * Assistant pages provider for {@link CenterLayoutInfo}.
 * 
 * @author sablin_aa
 * @coverage ExtGWT.model.layout.assistant
 */
public final class CenterLayoutAssistant extends LayoutAssistantSupport<CenterLayoutInfo> {
  ////////////////////////////////////////////////////////////////////////////
  //
  // Constructor
  //
  ////////////////////////////////////////////////////////////////////////////
  public CenterLayoutAssistant(CenterLayoutInfo layout) {
    super(layout);
  }

  ////////////////////////////////////////////////////////////////////////////
  //
  // Pages
  //
  ////////////////////////////////////////////////////////////////////////////
  @Override
  protected AbstractAssistantPage createLayoutPage(Composite parent) {
    return new CenterLayoutAssistantPage(parent, m_layout);
  }

  @Override
  protected AbstractAssistantPage createConstraintsPage(Composite parent, List<ObjectInfo> objects) {
    return null;
  }
}