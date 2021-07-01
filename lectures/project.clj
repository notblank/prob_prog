(defproject lectures "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  ;;:classpath-add ["~/gitrepos/prob_programming/lectures/src/lectures"]
  :license {:name "EPL-2.0 OR GPL-2.0-or-later WITH Classpath-exception-2.0"
            :url "https://www.eclipse.org/legal/epl-2.0/"}
  :dependencies [;; usual dependencies:
                 [org.clojure/clojure "1.10.1"]
                 [anglican "1.1.0"]
                 [metasoarous/oz "1.6.0-alpha1"] ;; vega-lite
                 ;; lecture 4 - models:
                 [nstools "0.2.4"]
                 [org.nfrac/cljbox2d "0.5.0"]
                 [org.nfrac/cljbox2d.testbed "0.5.0"]
                 [clojure-csv/clojure-csv "2.0.1"]
                 [org.clojure/data.priority-map "0.0.7"]
                 [net.mikera/core.matrix "0.33.2"]
                 [net.mikera/core.matrix.stats "0.5.0"]
                 [net.mikera/vectorz-clj "0.29.0"]
                 ]
  :plugins [[org.clojars.benfb/lein-gorilla "0.6.0"]]
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all
                       :jvm-opts ["-Dclojure.compiler.direct-linking=true"]}})
