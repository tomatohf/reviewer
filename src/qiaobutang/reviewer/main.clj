(ns qiaobutang.reviewer.main
  (:require [clojure.tools.namespace.find :as ns]
            [clojure.java.classpath :as cp]))

(defn -main [& args])

(defn task-ns []
  (filter #(.startsWith (str %) "qiaobutang.reviewer.task.")
          (ns/find-namespaces (cp/classpath))))

(defn task [ns]
  (require ns)
  (ns-resolve ns 'run))
