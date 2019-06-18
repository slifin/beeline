(ns beeline.core
  (:gen-class)
  (:require [clojure.data.json :as json]
            [honeysql.core :as sql]))

(defn -main [input]
   (-> input
       (json/read-str)
       (str)
       (clojure.string/replace #"\"([#:][^\"]*)\"" "$1")
       (read-string)
       (sql/format)
       (json/write-str)
       (print))
  (flush))
