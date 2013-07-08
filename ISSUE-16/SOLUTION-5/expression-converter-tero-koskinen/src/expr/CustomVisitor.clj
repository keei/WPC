;
; Olimex Weekend Programming Challenge Issue 16
; Infix to Postfix converter 
;
; http://olimex.wordpress.com/2013/07/05/weekend-programming-challenge-issue-16-infix-to-postfix-converter/
;
; Copyright (c) 2013 Tero Koskinen <tero.koskinen@iki.fi>
;
; Permission to use, copy, modify, and distribute this software for any
; purpose with or without fee is hereby granted, provided that the above
; copyright notice and this permission notice appear in all copies.
;
; THE SOFTWARE IS PROVIDED "AS IS" AND THE AUTHOR DISCLAIMS ALL WARRANTIES
; WITH REGARD TO THIS SOFTWARE INCLUDING ALL IMPLIED WARRANTIES OF
; MERCHANTABILITY AND FITNESS. IN NO EVENT SHALL THE AUTHOR BE LIABLE FOR
; ANY SPECIAL, DIRECT, INDIRECT, OR CONSEQUENTIAL DAMAGES OR ANY DAMAGES
; WHATSOEVER RESULTING FROM LOSS OF USE, DATA OR PROFITS, WHETHER IN AN
; ACTION OF CONTRACT, NEGLIGENCE OR OTHER TORTIOUS ACTION, ARISING OUT OF
; OR IN CONNECTION WITH THE USE OR PERFORMANCE OF THIS SOFTWARE.
;

(ns expr.CustomVisitor
  (:import (expr EXPRParser EXPRParser$ExprContext))
  (
   :gen-class
   :extends expr.EXPRBaseVisitor
   :constructors {[] []}
   :main false
  )
)

(defn -visitExpr [this ctx]
  {:term (.visit this (.term ctx))
   :tail (doall (map (fn [s] (.visit this s)) (.termtail ctx)))
  }
)

(defn -visitTermtail [this ctx]
  {:term (.visit this (.term ctx))
   :op   (if (nil? (.PLUS (.addoperator ctx))) "-" "+")
  }
)

(defn -visitTerm [this ctx]
  {:factor (.visit this (.factor ctx))
   :tail   (doall (map (fn [s] (.visit this s)) (.factortail ctx)))
  }
)

(defn -visitFactortail [this ctx]
  {:factor (.visit this (.factor ctx))
   :op     (if (nil? (.MULT (.muloperator ctx))) "/" "*")
  }
)


(defn -visitFactorNUMBER [this ctx]
  {:type :number
   :value (.getText (.INT (.number ctx)))
  }
)

(defn -visitFactorEXPR [this ctx]
  {:type :expr
   :value (.visit this (.expr ctx))
  }
)
