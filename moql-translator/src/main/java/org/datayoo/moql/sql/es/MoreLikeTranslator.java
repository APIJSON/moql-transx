/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.datayoo.moql.sql.es;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.datayoo.moql.Operand;
import org.datayoo.moql.operand.function.Function;

import java.util.List;

/**
 * @author Tang Tadin
 */
public class MoreLikeTranslator extends AbstractESFunctionTranslator {

  public static final String FUNCTION_NAME = "moreLike";

  public MoreLikeTranslator() {
    super(FUNCTION_NAME);
    // TODO Auto-generated constructor stub
  }

  @Override
  protected void innerTranslate(Function function, JsonElement jsonObject) {
    // TODO Auto-generated method stub
    if (function.getParameterCount() != 4) {
      throw new IllegalArgumentException(
          "Error function! The moreLike function's format should be moreLike(fields,likeText,minTermFreq,maxQueryTerms)!");
    }
    JsonObject moreLike = new JsonObject();

    List<Operand> parameters = function.getParameters();
    String fieldString = getOperandName(parameters.get(0));
    String[] fields = fieldString.split(",");
    JsonArray array = new JsonArray();
    for (int i = 0; i < fields.length; i++) {
      array.add(getOperandName(fields[i]));
    }
    moreLike.add("fields", array);
    moreLike.addProperty("like_text", getOperandName(parameters.get(1)));
    moreLike.addProperty("min_term_freq", (Long) parameters.get(2).getValue());
    moreLike
        .addProperty("max_query_terms", (Long) parameters.get(3).getValue());
    putObject(jsonObject, "more_like_this", moreLike);
  }

}
