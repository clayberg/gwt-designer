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
package com.google.gwt.dev.jjs.ast;

import com.google.gwt.dev.jjs.SourceInfo;

/**
 * Java if statement.
 */
public class JIfStatement extends JStatement {

  private JStatement elseStmt;
  private JExpression ifExpr;
  private JStatement thenStmt;

  public JIfStatement(SourceInfo info, JExpression ifExpr, JStatement thenStmt,
      JStatement elseStmt) {
    super(info);
    this.ifExpr = ifExpr;
    this.thenStmt = thenStmt;
    this.elseStmt = elseStmt;
  }

  public JStatement getElseStmt() {
    return elseStmt;
  }

  public JExpression getIfExpr() {
    return ifExpr;
  }

  public JStatement getThenStmt() {
    return thenStmt;
  }

  public void traverse(JVisitor visitor, Context ctx) {
    if (visitor.visit(this, ctx)) {
      ifExpr = visitor.accept(ifExpr);
      if (thenStmt != null) {
        thenStmt = visitor.accept(thenStmt);
      }
      if (elseStmt != null) {
        elseStmt = visitor.accept(elseStmt);
      }
    }
    visitor.endVisit(this, ctx);
  }

  @Override
  public boolean unconditionalControlBreak() {
    boolean thenBreaks = thenStmt != null
        && thenStmt.unconditionalControlBreak();
    if (thenBreaks && ifExpr == JBooleanLiteral.get(true)) {
      // if(true) { /* unconditional break */ }
      return true;
    }

    boolean elseBreaks = elseStmt != null
        && elseStmt.unconditionalControlBreak();
    if (elseBreaks && ifExpr == JBooleanLiteral.get(false)) {
      // if(false) { } else { /* unconditional break */ }
      return true;
    }

    if (thenBreaks && elseBreaks) {
      // both branches have an unconditional break
      return true;
    }
    return false;
  }
}
