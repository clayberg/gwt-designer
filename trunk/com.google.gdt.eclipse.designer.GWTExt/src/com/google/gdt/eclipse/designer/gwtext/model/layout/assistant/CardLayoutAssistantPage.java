/*
 * Copyright 2011 Google Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.google.gdt.eclipse.designer.gwtext.model.layout.assistant;

import com.google.gdt.eclipse.designer.gwtext.model.layout.CardLayoutInfo;

import org.eclipse.wb.internal.core.utils.ui.GridLayoutFactory;

import org.eclipse.swt.widgets.Composite;

/**
 * Assistant for GWT-Ext {@link CardLayoutInfo}.
 * 
 * @author sablin_aa
 * @coverage GWTExt.model.layout.assistant
 */
public final class CardLayoutAssistantPage extends AbstractGwtExtAssistantPage {
  public CardLayoutAssistantPage(Composite parent, CardLayoutInfo selection) {
    super(parent, selection);
    GridLayoutFactory.create(this).columns(2);
    addBooleanProperty(this, "deferredRender");
    addBooleanProperty(this, "renderHidden");
    addIntegerProperty(this, "activeItem(int)", "activeItem");
    addStringProperty(this, "spacing");
  }
}