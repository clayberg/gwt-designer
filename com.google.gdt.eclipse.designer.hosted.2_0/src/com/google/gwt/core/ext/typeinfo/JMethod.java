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
package com.google.gwt.core.ext.typeinfo;

import java.lang.annotation.Annotation;
import java.util.Map;

/**
 * Represents a method declaration.
 */
public class JMethod extends JAbstractMethod {

  private final JClassType enclosingType;

  private JType returnType;

  JMethod(JClassType enclosingType, JMethod srcMethod) {
    super(srcMethod);
    this.enclosingType = enclosingType;
    this.returnType = srcMethod.returnType;
  }

  JMethod(JClassType enclosingType, String name) {
    this(enclosingType, name, null, null);
  }

  JMethod(JClassType enclosingType, String name,
      Map<Class<? extends Annotation>, Annotation> declaredAnnotations,
      JTypeParameter[] jtypeParameters) {
    super(name, declaredAnnotations, jtypeParameters);
    this.enclosingType = enclosingType;
    enclosingType.addMethod(this);
  }

  @Override
  public JClassType getEnclosingType() {
    return enclosingType;
  }

  @Override
  public String getJsniSignature() {
    StringBuilder sb = new StringBuilder("@");
    sb.append(getEnclosingType().getQualifiedSourceName());
    sb.append("::");
    sb.append(getName());
    sb.append("(");
    for (JParameter param : getParameters()) {
      sb.append(param.getType().getJNISignature());
    }
    sb.append(")");
    return sb.toString();
  }

  @Override
  public String getReadableDeclaration() {
    return getReadableDeclaration(getModifierBits());
  }

  public String getReadableDeclaration(boolean noAccess, boolean noNative,
      boolean noStatic, boolean noFinal, boolean noAbstract) {
    int bits = getModifierBits();
    if (noAccess) {
      bits &= ~(TypeOracle.MOD_PUBLIC | TypeOracle.MOD_PRIVATE | TypeOracle.MOD_PROTECTED);
    }
    if (noNative) {
      bits &= ~TypeOracle.MOD_NATIVE;
    }
    if (noStatic) {
      bits &= ~TypeOracle.MOD_STATIC;
    }
    if (noFinal) {
      bits &= ~TypeOracle.MOD_FINAL;
    }
    if (noAbstract) {
      bits &= ~TypeOracle.MOD_ABSTRACT;
    }
    return getReadableDeclaration(bits);
  }

  public JType getReturnType() {
    return returnType;
  }

  public boolean isAbstract() {
    return 0 != (getModifierBits() & TypeOracle.MOD_ABSTRACT);
  }

  @Override
  public JConstructor isConstructor() {
    return null;
  }

  public boolean isFinal() {
    return 0 != (getModifierBits() & TypeOracle.MOD_FINAL);
  }

  @Override
  public JMethod isMethod() {
    return this;
  }

  public boolean isNative() {
    return 0 != (getModifierBits() & TypeOracle.MOD_NATIVE);
  }

  public boolean isStatic() {
    return 0 != (getModifierBits() & TypeOracle.MOD_STATIC);
  }

  @Override
  public String toString() {
    return getReadableDeclaration();
  }

  String getReadableDeclaration(int modifierBits) {
    String[] names = TypeOracle.modifierBitsToNames(modifierBits);
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < names.length; i++) {
      sb.append(names[i]);
      sb.append(" ");
    }
    if (getTypeParameters().length > 0) {
      toStringTypeParams(sb);
      sb.append(" ");
    }
    sb.append(returnType.getParameterizedQualifiedSourceName());
    sb.append(" ");
    sb.append(getName());

    toStringParamsAndThrows(sb);

    return sb.toString();
  }

  void setReturnType(JType type) {
    returnType = type;
  }
}
