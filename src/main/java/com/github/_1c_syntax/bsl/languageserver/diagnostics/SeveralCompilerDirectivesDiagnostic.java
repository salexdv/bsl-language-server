/*
 * This file is a part of BSL Language Server.
 *
 * Copyright (c) 2018-2022
 * Alexey Sosnoviy <labotamy@gmail.com>, Nikita Fedkin <nixel2007@gmail.com> and contributors
 *
 * SPDX-License-Identifier: LGPL-3.0-or-later
 *
 * BSL Language Server is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3.0 of the License, or (at your option) any later version.
 *
 * BSL Language Server is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with BSL Language Server.
 */
package com.github._1c_syntax.bsl.languageserver.diagnostics;

import com.github._1c_syntax.bsl.languageserver.context.symbol.MethodSymbol;
import com.github._1c_syntax.bsl.languageserver.diagnostics.metadata.DiagnosticMetadata;
import com.github._1c_syntax.bsl.languageserver.diagnostics.metadata.DiagnosticSeverity;
import com.github._1c_syntax.bsl.languageserver.diagnostics.metadata.DiagnosticTag;
import com.github._1c_syntax.bsl.languageserver.diagnostics.metadata.DiagnosticType;
import com.github._1c_syntax.bsl.languageserver.utils.Trees;
import com.github._1c_syntax.bsl.parser.BSLParser;
import org.antlr.v4.runtime.tree.ParseTree;

@DiagnosticMetadata(
  type = DiagnosticType.ERROR,
  severity = DiagnosticSeverity.CRITICAL,
  minutesToFix = 5,
  tags = {
    DiagnosticTag.UNPREDICTABLE,
    DiagnosticTag.ERROR
  }
)
public class SeveralCompilerDirectivesDiagnostic extends AbstractVisitorDiagnostic {

  @Override
  public ParseTree visitModuleVar(BSLParser.ModuleVarContext ctx) {
    if (Trees.findAllRuleNodes(ctx, BSLParser.RULE_compilerDirective).size() > 1) {
      diagnosticStorage.addDiagnostic(ctx.moduleVarsList());
    }
    return ctx;
  }

  @Override
  public ParseTree visitSub(BSLParser.SubContext ctx) {
    documentContext.getSymbolTree().getMethodSymbol(ctx).ifPresent((MethodSymbol methodSymbol) -> {
      if (methodSymbol.getCompilerDirectiveKind().isPresent()
        && Trees.findAllRuleNodes(ctx, BSLParser.RULE_compilerDirective).size() > 1) {
        diagnosticStorage.addDiagnostic(methodSymbol.getSubNameRange());
      }
    });

    return ctx;
  }
}
