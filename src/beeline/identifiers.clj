; Have we identified parts of our made up lang?
(ns beeline.identifiers)

(defn is-keyword?
  "Is this a pretend keyword?"
  [data]
  (and (string? data)
    (= \: (first data))))

