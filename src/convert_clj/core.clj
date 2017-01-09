; Code Challenge: Parse nested string, printing in original and sorted order
;
; Author: Tom Cooper
; Date: 2017-01-06
(ns convert-clj.core
  (:gen-class))

; The given challenge problem, used if none given on the command line.
(def given-input "(id,created,employee(id,firstname,employeeType(id), lastname),location)")

; Tuples
(defprotocol TupleType (output-name [this]))

(defrecord Tuple [depth name]
  TupleType
  (output-name [{:keys [depth name]}]
      (if (<= depth 1)
        name
        (apply str
               (flatten (list (repeat (dec depth) "-") " " name))))))

; Parsing
(defn tokenize [txt] (re-seq #"\(|\)|\w+" txt))

(defn parse [txt]
  {:post [(= (:depth %) 0)]}
  (letfn [(reduce-fn [acc t]
            (case t
              "(" (update acc :depth inc)
              ")" (update acc :depth dec)
              (update acc :result conj (->Tuple (:depth acc) t))))]
    (reduce reduce-fn {:result () :depth 0} (tokenize txt))))

(defn convert [txt]
  (reverse (:result (parse txt))))

; Output
(defn output-list [c]
  (doseq [w c] (-> w output-name println)))

; Main
(defn -main
  "Convert the given arg string, defaulting to the given challenge problem string"
  [& args]
  (try
    (let [problem (or (first args) given-input)
          converted (convert problem)]
      (println "Original order:")
      (output-list converted)
      (println "\nSorted order:")
      (output-list (sort-by :name converted)))
    (catch AssertionError e
      (println "Mismatched parens: " (.getMessage e)))))
