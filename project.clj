(defproject beeline "0.1.0-SNAPSHOT"
  :plugins [[io.taylorwood/lein-native-image "0.3.0"]]
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "EPL-2.0 OR GPL-2.0-or-later WITH Classpath-exception-2.0"
            :url "https://www.eclipse.org/legal/epl-2.0/"}
  :dependencies [[org.clojure/clojure "1.9.0"]
                 [honeysql "0.9.4"]
                 [org.clojure/data.json "0.2.6"]]
  :main ^:skip-aot beeline.core
  :repl-options {:init-ns beeline.core}
  :native-image {
                 :opts [
                        "--static"
                        "--initialize-at-build-time"
                        "-H:+ReportExceptionStackTraces"
                        "--report-unsupported-elements-at-runtime"]})

