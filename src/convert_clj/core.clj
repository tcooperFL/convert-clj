; Code Challenge: Parse nested string, printing in original and (bonus) sorted order
;
; Author: Tom Cooper
; Date: 2017-02-10
(ns convert-clj.core
  (:require [clojure.string :as s])
  (:gen-class))

; The given challenge problem, used if none given on the command line.
; Assumes the outer parens are optional, and that nested lists immediately
; follow a word, and are interpreted as children of that word.
(def given-input "(id,created,employee(id,first name,employeeType(id), lastname),location)")
(def indentation-threshold 2)

; Parsing
(defn new-level
  "Start a new list by pushing an empty list onto the stack"
  [stack]
  (cons '() stack))

(defn end-level
  "End a list by reducing and consing it onto the parent child list"
  [[node [[parent] & sibs] & remaining]]
  (cons (cons (remove nil? (cons parent (reverse node))) sibs) remaining))

(defn add-token
  "Trim the token and cons it onto the current node list"
  [tok [node & remaining]]
  (cons (cons (list tok) node) remaining))

(defn parse
  "Convert sequence of strings into a lisp-style nested list structure starting with :root"
  [tokens]
  {:pre  [(not (assert (= "(" (first tokens)) "missing outer open paren"))
          (not (assert (= ")" (last tokens)) "missing outer close paren"))]
   :post [(not (assert (= :root (first %)) "mismatched parens"))]}
  (ffirst (reduce #(case %2
                     "(" (do (assert (not (nil? (ffirst %))) "orphaned list") (new-level %))
                     ")" (end-level %)
                     "" %                                   ;ignore trimmed spaces before parens
                     (add-token %2 %))
                  '(((:root)))
                  (map s/trim tokens))))

(defn convert
  "Parse the sequence of tokens created by splitting the argument on commas, separating out parens"
  [s]
  (parse (re-seq #"\(|\)|[^()\t\n,]+" s)))

; Output
(defn name-of
  "Return the string naqme of this node at this depth, with the proper '-' prefix"
  [c d]
  (apply str (concat (repeat d "-") (if (> d 0) " " "") (first c))))

(defn output-list
  "Output the children of this ast using the given sort function.
   If not supplied, then print it in the original order."
  ([ast] (output-list ast (constantly 0)))
  ([ast sort-fn] (output-list ast sort-fn (- indentation-threshold 2)))
  ([ast sort-fn depth]
   (doseq [child (sort-by first sort-fn (rest ast))]
     (println (name-of child depth))
     (output-list child sort-fn (inc depth)))))

; Main
(defn -main
  "Convert the given arg string, defaulting to the given challenge problem string"
  [& args]
  (try
    (let [problem (or (first args) given-input)
          converted (convert problem)]
      (println "\nOriginal order:")
      (output-list converted)
      (println "\nSorted order:")
      (output-list converted compare))
    (catch AssertionError e
      (println (.getMessage e)))))
