(ns beeline.core
  (:gen-class)
  (:require [clojure.data.json :as json]
            [honeysql.core :as sql]))

(def example "{\":select\" [\":b\", \"#sql/inline\", 5]}")

(defn handler
  "Accepts input from stdin"
  ([] (handler example))
  ([input]
   (-> input
       (json/read-str)
       (str)
       (clojure.string/replace #"(\")(#|:)(.*?)(\")" "$2$3")
       (read-string)
       (sql/format)
       (json/write-str)
       (print))))

(defn -main [input]
  (handler input)
  (flush))
