/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2016 socraticphoenix@gmail.com
 * Copyright (c) 2016 contributors
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and
 * associated documentation files (the "Software"), to deal in the Software without restriction,
 * including without limitation the rights to use, copy, modify, merge, publish, distribute,
 * sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or
 * substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT
 * NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM,
 * DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package com.gmail.socraticphoenix.parse.tokenizer.action;

import com.gmail.socraticphoenix.collect.coupling.Pair;
import com.gmail.socraticphoenix.parse.parser.PatternResult;
import com.gmail.socraticphoenix.parse.token.TokenParameters;
import com.gmail.socraticphoenix.parse.tokenizer.TokenizerAction;
import com.gmail.socraticphoenix.parse.tokenizer.TokenizerContext;

import java.util.ArrayList;
import java.util.List;

public class SequenceAction implements TokenizerAction {
    private TokenizerAction[] sequence;

    public SequenceAction(TokenizerAction... sequence) {
        this.sequence = sequence;
    }

    @Override
    public Pair<List<TokenParameters.Element>, PatternResult> tokenize(String string, int start, TokenizerContext context) {
        List<PatternResult> subResults = new ArrayList<>();
        List<TokenParameters.Element> elements = new ArrayList<>();
        int i = 0;
        for (TokenizerAction action : this.sequence) {
            i++;
            Pair<List<TokenParameters.Element>, PatternResult> result = action.tokenize(string, start, context);
            PatternResult patternResult = result.getB();
            if (patternResult.isSuccesful()) {
                subResults.add(patternResult);
                start = patternResult.getEnd();
                elements.addAll(result.getA());
            } else {
                subResults.add(patternResult);
                break;
            }
        }
        return Pair.of(elements, PatternResult.composed("Failed sequence on pattern #" + i, start, subResults));
    }

}
