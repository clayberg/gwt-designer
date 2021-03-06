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
package com.google.gdt.eclipse.designer.model.widgets.panels;

import com.google.gdt.eclipse.designer.model.widgets.IWidgetInfo;

/**
 * Interface model for GWT <code>VerticalSplitPanel</code>.
 * 
 * @author scheglov_ke
 * @coverage gwt.model
 */
public interface IVerticalSplitPanelInfo<T extends IWidgetInfo> extends ISplitPanelInfo<T> {
  ////////////////////////////////////////////////////////////////////////////
  //
  // Access
  //
  ////////////////////////////////////////////////////////////////////////////
  /**
   * @return the top {@link IWidgetInfo}, may be <code>null</code>.
   */
  T getTopWidget();

  /**
   * @return the bottom {@link IWidgetInfo}, may be <code>null</code>.
   */
  T getBottomWidget();
}
