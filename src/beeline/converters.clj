; Converts between our made up syntax
; & Clojure data types to pass to honeySQL.
(ns beeline.converters
  (:require [beeline.identifiers :as ident]))

(defn vector->function-name
  "Given a vector fetch the function name"
  [vector]
  (apply str (rest (first vector))))

(defn colon->keyword
  "Converts fake keywords to real keywords"
  [data]
  (if (ident/is-keyword? data)
    (keyword
      (apply str (rest data)))
    data))

(defn call->list
  "Given the name of a function create a quoted function call"
  [name & args]
  (into
    args
    (list (ns-resolve 'honeysql.types (symbol name)))))

; I think the rules for this are:
; The next thing after a function call is the args
; if the next thing is an array, then that's arg list
; if it's scalar then that's the single argument
(defn hash->callable
  "Converts [\"#sql/call\" \":+\" 1 1] to function call"
  [element]
 (if (ident/is-callable? element)
   (apply
     call->list
     (into
       [(vector->function-name element)]
       (rest element)))
   element))