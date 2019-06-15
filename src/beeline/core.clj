(ns beeline.core
  (:gen-class)
  (:require [clojure.data.json :as json]
            [beeline.converters :as converters]
            [clojure.walk :as walk]
            [honeysql.core :as sql]))

(defn read-json
  "Performing a lossy conversion from json to clj"
  [input]
  (json/read-str input :key-fn converters/colon->keyword))

(def example "{\":select\" [\":b\", \"#sql/inline\", 5]}")

(defn handler
  "Accepts input from stdin"
  ([] (handler example))
  ([input]
   (->> input
       (read-json)
       (walk/postwalk converters/colon->keyword)
       (converters/data->tagged-literals)
       (sql/format)
       (json/write-str)
       (print))))

(defn -main [input]
  (handler input)
  (flush))
