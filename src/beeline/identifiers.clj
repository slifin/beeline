; Have we identified parts of our made up lang?
(ns beeline.identifiers)

(defn is-keyword?
  "Is this a pretend keyword?"
  [data]
  (and (string? data)
    (= \: (first data))))

(defn is-callable?
  "Is this element a nested callable?"
  [element]
  (and (vector? element)
       (string? (first element))
       (= \# (first (first element)))))