(defproject convert-clj "0.1.0-SNAPSHOT"
  :description "String conversion challenge problem"
  :plugins [[lein-ancient "0.6.10"]]
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [org.clojure/core.async "0.2.374"]]
  :main ^:skip-aot convert-clj.core
  :target-path "target/%s"
  :global-vars {*assert* true *print-level* 5 *print-length* 20}
  :profiles {:uberjar {:aot :all}}
  )
