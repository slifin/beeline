; Converts between our made up syntax
; & Clojure data types to pass to honeySQL.
(ns beeline.converters
  (:require [beeline.identifiers :as ident]))

(defn colon->keyword
  "Converts fake keywords to real keywords"
  [data]
  (if (ident/is-keyword? data)
    (keyword
      (apply str (rest data)))
    data))

(defn replace-tags
  [data tag]
  (clojure.string/replace
    data
    (str "\"#" tag "\"")
    (str "#" tag)))

(defn data->tagged-literals [data]
  (read-string
    (reduce
      replace-tags
      (str data)
      (keys *data-readers*))))
