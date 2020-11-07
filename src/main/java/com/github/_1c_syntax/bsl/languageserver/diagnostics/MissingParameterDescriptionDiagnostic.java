/*
 * This file is a part of BSL Language Server.
 *
 * Copyright © 2018-2020
 * Alexey Sosnoviy <labotamy@gmail.com>, Nikita Gryzlov <nixel2007@gmail.com> and contributors
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
import com.github._1c_syntax.bsl.languageserver.context.symbol.ParameterDefinition;
import com.github._1c_syntax.bsl.languageserver.context.symbol.description.ParameterDescription;
import com.github._1c_syntax.bsl.languageserver.diagnostics.metadata.DiagnosticMetadata;
import com.github._1c_syntax.bsl.languageserver.diagnostics.metadata.DiagnosticSeverity;
import com.github._1c_syntax.bsl.languageserver.diagnostics.metadata.DiagnosticTag;
import com.github._1c_syntax.bsl.languageserver.diagnostics.metadata.DiagnosticType;
import org.apache.commons.collections4.map.CaseInsensitiveMap;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Function;
import java.util.stream.Collectors;

@DiagnosticMetadata(
  type = DiagnosticType.CODE_SMELL,
  severity = DiagnosticSeverity.MAJOR,
  minutesToFix = 5,
  tags = {
    DiagnosticTag.STANDARD,
    DiagnosticTag.BADPRACTICE
  }

)
public class MissingParameterDescriptionDiagnostic extends AbstractDiagnostic {

  /**
   * Анализируется только методы, имеющие описание
   * Для удобства кидается несколько разных замечаний
   */
  @Override
  protected void check() {
    documentContext.getSymbolTree().getMethods().stream()
      .filter((MethodSymbol methodSymbol) -> methodSymbol.getDescription().isPresent()
        && !methodSymbol.getDescription().get().getDescription().isEmpty())
      .forEach((MethodSymbol methodSymbol) -> {

        var parameters = methodSymbol.getParameters();
        var parametersDescriptions = methodSymbol.getDescription().get().getParameters();

        // параметров и описания нет, что в принципе не ошибка
        if (parameters.isEmpty() && parametersDescriptions.isEmpty()) {
          return;
        }

        // параметров нет, но есть их описания, что есть ошибка
        if (parameters.isEmpty()) {
          addDiagnostic(methodSymbol, parametersDescriptions);
          return;
        }

        if (parametersDescriptions.isEmpty()) {
          // ошибка отсутствует описание всех параметров
          diagnosticStorage.addDiagnostic(methodSymbol.getSubNameRange());
          return;
        }

        // сопоставление параметров и описаний
        checkParameterDescription(methodSymbol, parameters, parametersDescriptions);
      });
  }

  private void checkParameterDescription(MethodSymbol methodSymbol,
                                         List<ParameterDefinition> parameters,
                                         List<ParameterDescription> parametersDescriptions) {

    AtomicBoolean hasMissingDescription = new AtomicBoolean(false);

    var parametersDescriptionsCopy = new ArrayList<>(parametersDescriptions);
    var descriptions = parametersDescriptions.stream()
      .collect(
        Collectors.toMap(
          ParameterDescription::getName,
          Function.identity(),
          (first, second) -> first,
          CaseInsensitiveMap::new));

    parameters.forEach((ParameterDefinition parameter) -> {
      var description = descriptions.get(parameter.getName());
      // описание параметра отсутствует как таковое
      if (description == null) {
        addDiagnostic(parameter, "missingDescription");
        hasMissingDescription.set(true);
        return;
      }

      // параметр в описании есть, но нет типа и описания типа
      if (description.getTypes().isEmpty() && description.getDescription().isEmpty()) {
        addDiagnostic(parameter, "emptyDescription");
      }

      // найденный параметр удалим из кэша
      parametersDescriptionsCopy.remove(description);
    });

    // лишние описания параметров, отсутствующие в сигнатуре
    if (!parametersDescriptionsCopy.isEmpty()) {
      hasMissingDescription.set(true);
      addDiagnostic(methodSymbol, parametersDescriptionsCopy);
    }

    // проверить порядок параметров в описании
    // но это имеет смысл только при отсутствии ошибок

    if (!hasMissingDescription.get()) {
      var paramDescriptionString = parametersDescriptions.stream()
        .map(ParameterDescription::getName).collect(Collectors.joining(",")).toLowerCase();
      var paramString = parameters.stream()
        .map(ParameterDefinition::getName).collect(Collectors.joining(",")).toLowerCase();
      // если строки не равны, значит порядок описаний не совпадает
      if (!paramDescriptionString.equals(paramString)) {
        diagnosticStorage.addDiagnostic(methodSymbol.getSubNameRange(), info.getResourceString("wrongOrder"));
      }
    }
  }

  private void addDiagnostic(ParameterDefinition parameter, String messageKey) {
    diagnosticStorage.addDiagnostic(parameter.getRange(), info.getResourceString(messageKey, parameter.getName()));
  }

  private void addDiagnostic(MethodSymbol methodSymbol, List<ParameterDescription> parametersDescriptions) {
    var parametersString = parametersDescriptions.stream()
      .map(ParameterDescription::getName).collect(Collectors.joining(", "));
    diagnosticStorage.addDiagnostic(
      methodSymbol.getSubNameRange(),
      info.getResourceString("missingInSignature", parametersString));
  }
}
