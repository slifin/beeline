(ns beeline.core
  (:require [clojure.data.json :as json]
            [honeysql.core :as sql]
            [clojure.walk :as w]))

(defn fake-keyword
  "Converts fake keywords to real keywords"
  [json-key]
  (let [fake? (and (string? json-key)
                   (= \: (first json-key)))]
    (if fake?
      (keyword
        (apply str (rest json-key)))
      json-key)))

(defn read-json
  "Performing a lossy conversion from json to clj"
  [input]
  (json/read-str
    input
    :key-fn fake-keyword))

(def example "{\":select\": [\":a\"]}")

(defn handler
  "Accepts input from stdin"
  ([] (handler example))
  ([input]
   (->> input
       (read-json)
       (w/postwalk fake-keyword)
       (sql/format)
       (json/write-str)
       (print))))

(defn -main [input]
  (handler input))
