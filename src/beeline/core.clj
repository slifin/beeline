(ns beeline.core
  (:gen-class)
  (:require [clojure.data.json :as json]
            [honeysql.core :as sql]
            [clojure.walk :as w]))

(defn colon->keyword
  "Converts fake keywords to real keywords"
  [json-key]
  (let [fake? (and (string? json-key)
                   (= \: (first json-key)))]
    (if fake?
      (keyword
        (apply str (rest json-key)))
      json-key)))

(defn nested-callable?
  "Is this element a nested callable?"
  [element]
  (and (vector? element)
       (= \# (first (first element)))))

(defn call->list
  "Given the name of a function create a quoted function call"
  [name & args]
  (into
    args
    (list (resolve (symbol name)))))

(defn function-name
  "Given a vector fetch the function name"
  [vector]
  (apply str (rest (first vector))))

(defn hash->callable
  "Converts [\"#sql/call\" \":+\" 1 1] to function call"
  [element]
  (if (nested-callable? element)
    (apply
      call->list
      (into
        [(function-name element)]
        (rest element)))
    element))

(defn read-json
  "Performing a lossy conversion from json to clj"
  [input]
  (json/read-str
    input
    :key-fn colon->keyword))

(def example "{\":select\": [\":a\"]}")

(defn handler
  "Accepts input from stdin"
  ([] (handler example))
  ([input]
   (->> input
       (read-json)
       (w/postwalk colon->keyword)
       (w/postwalk hash->callable)
       (sql/format)
       (json/write-str)
       (print))))

(defn -main [input]
  (handler input)
  (flush))
