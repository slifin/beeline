(ns beeline.core
  (:gen-class)
  (:require [clojure.data.json :as json]
            [honeysql.core :as sql]
            [beeline.converters :as converter]
            [clojure.walk :as w]))

(defn read-json
  "Performing a lossy conversion from json to clj"
  [input]
  (json/read-str input :key-fn converter/colon->keyword))

(def example "{\":select\": [\":a\"]}")

(defn handler
  "Accepts input from stdin"
  ([] (handler example))
  ([input]
   (->> input
       (read-json)
       (w/postwalk converter/colon->keyword)
       (w/postwalk converter/hash->callable)
       (sql/format)
       (json/write-str)
       (print))))

(defn -main [input]
  (handler input)
  (flush))
