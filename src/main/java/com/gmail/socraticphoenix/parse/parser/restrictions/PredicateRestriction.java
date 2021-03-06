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
package com.gmail.socraticphoenix.parse.parser.restrictions;

import com.gmail.socraticphoenix.parse.parser.PatternResult;
import com.gmail.socraticphoenix.parse.parser.PatternContext;
import com.gmail.socraticphoenix.parse.parser.PatternRestriction;

import java.util.function.BiFunction;

public class PredicateRestriction implements PatternRestriction {
    private PatternRestriction restriction;
    private BiFunction<String, Integer, PatternResult> predicate;

    public PredicateRestriction(PatternRestriction restriction, BiFunction<String, Integer, PatternResult> predicate) {
        this.restriction = restriction;
        this.predicate = predicate;
    }

    @Override
    public PatternResult match(String string, int start, PatternContext context) {
        PatternResult normal = this.restriction.match(string, start, context);
        if(normal.isSuccesful()) {
            return this.predicate.apply(string.substring(start, normal.getEnd()), normal.getEnd());
        } else {
            return normal;
        }
    }

}
