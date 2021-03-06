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

import com.google.gwt.core.ext.linker.CompilationResult;
import com.google.gwt.core.ext.linker.SelectionProperty;
import com.google.gwt.core.ext.linker.StatementRanges;
import com.google.gwt.core.ext.linker.SymbolData;
import com.google.gwt.dev.util.DiskCache;

import java.io.Serializable;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Map;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;

/**
 * The standard implementation of {@link CompilationResult}.
 */
public class StandardCompilationResult extends CompilationResult {

  private static final class MapComparator implements
      Comparator<SortedMap<SelectionProperty, String>>, Serializable {
    public int compare(SortedMap<SelectionProperty, String> arg0,
        SortedMap<SelectionProperty, String> arg1) {
      int diff = arg0.size() - arg1.size();
      if (diff != 0) {
        return diff;
      }

      Iterator<String> i0 = arg0.values().iterator();
      Iterator<String> i1 = arg1.values().iterator();

      StringBuffer sb0 = new StringBuffer();
      StringBuffer sb1 = new StringBuffer();

      while (i0.hasNext()) {
        assert i1.hasNext();
        sb0.append(i0.next());
        sb1.append(i1.next());
      }
      assert !i1.hasNext();

      return sb0.toString().compareTo(sb1.toString());
    }
  }

  /**
   * Smaller maps come before larger maps, then we compare the concatenation of
   * every value.
   */
  public static final Comparator<SortedMap<SelectionProperty, String>> MAP_COMPARATOR = new MapComparator();

  private static final DiskCache diskCache = new DiskCache();

  private final long jsToken[];
  
  private final int permutationId;

  private final SortedSet<SortedMap<SelectionProperty, String>> propertyValues = new TreeSet<SortedMap<SelectionProperty, String>>(
      MAP_COMPARATOR);

  private final StatementRanges[] statementRanges;

  private final String strongName;

  private final long symbolToken;

  public StandardCompilationResult(String strongName, byte[][] js,
      byte[] serializedSymbolMap, StatementRanges[] statementRanges, int permutationId) {
    super(StandardLinkerContext.class);
    this.strongName = strongName;
    jsToken = new long[js.length];
    for (int i = 0; i < jsToken.length; ++i) {
      jsToken[i] = diskCache.writeByteArray(js[i]);
    }
    symbolToken = diskCache.writeByteArray(serializedSymbolMap);
    this.statementRanges = statementRanges;
    this.permutationId = permutationId;
  }

  /**
   * Record a particular permutation of SelectionProperty values that resulted
   * in the compilation.
   */
  public void addSelectionPermutation(Map<SelectionProperty, String> values) {
    SortedMap<SelectionProperty, String> map = new TreeMap<SelectionProperty, String>(
        StandardLinkerContext.SELECTION_PROPERTY_COMPARATOR);
    map.putAll(values);
    propertyValues.add(Collections.unmodifiableSortedMap(map));
  }

  @Override
  public String[] getJavaScript() {
    String[] js = new String[jsToken.length];
    for (int i = 0; i < jsToken.length; ++i) {
      js[i] = diskCache.readString(jsToken[i]);
    }
    return js;
  }

  @Override
  public int getPermutationId() {
    return permutationId;
  }
  
  @Override
  public SortedSet<SortedMap<SelectionProperty, String>> getPropertyMap() {
    return Collections.unmodifiableSortedSet(propertyValues);
  }

  @Override
  public StatementRanges[] getStatementRanges() {
    return statementRanges;
  }

  @Override
  public String getStrongName() {
    return strongName;
  }

  @Override
  public SymbolData[] getSymbolMap() {
    return diskCache.readObject(symbolToken, SymbolData[].class);
  }
}
